/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.cartController;

import dao.AddressDAO;
import dao.CartDAO;
import dao.CustomerDAO;
import dao.DeliveryDAO;
import dao.OrderDAO;
import dao.PaymentDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Address;
import model.CartItem;
import model.Customer;
import model.DeliveryOption;
import model.OrderDetail;
import model.OrderInfo;
import model.PaymentMethod;

/**
 *
 * @author HP
 */
public class Checkout extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Checkout</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Checkout at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        CartDAO cartDAO = new CartDAO();
        DeliveryDAO deliveryDAO = new DeliveryDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        AddressDAO addressDAO = new AddressDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        // Lấy danh sách sản phẩm trong giỏ hàng
        List<CartItem> cartItems = cartDAO.getcart(customer.getCustomerID());

        // Lấy danh sách phương thức giao hàng
        List<DeliveryOption> deliveryOptions = deliveryDAO.getAllDeliveryOptions();

        // Lấy danh sách phương thức thanh toán
        List<PaymentMethod> paymentMethods = paymentDAO.getAllPaymentMethods();

        // Lấy địa chỉ mặc định của khách hàng
        Address defaultAddress = addressDAO.getDefaultAddress(customer.getCustomerID());

        // Tính tổng tiền
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }

        // Gửi dữ liệu đến trang JSP
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("deliveryOptions", deliveryOptions);
        request.setAttribute("paymentMethods", paymentMethods);
        request.setAttribute("defaultAddress", defaultAddress);
        request.setAttribute("customer", customer);
        request.setAttribute("total", total);

        RequestDispatcher dispatcher = request.getRequestDispatcher("checkout.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        CustomerDAO customerDAO = new CustomerDAO();
        CartDAO cartDAO = new CartDAO();
        OrderDAO orderDAO = new OrderDAO();

        List<CartItem> cartItems = cartDAO.getcart(customer.getCustomerID());

        if (cartItems == null || cartItems.isEmpty()) {
            request.setAttribute("errorMessage", "Giỏ hàng của bạn đang trống!");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        try {
            int deliveryOptionID = parseIntSafe(request.getParameter("deliveryOptionID"));
            int paymentMethodID = parseIntSafe(request.getParameter("paymentMethodID"));
            String deliveryAddress = request.getParameter("deliveryAddress");

            if (deliveryOptionID == -1 || paymentMethodID == -1 || deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Vui lòng nhập đầy đủ thông tin giao hàng!");
                request.getRequestDispatcher("cart.jsp").forward(request, response);
                return;
            }

            OrderInfo order = new OrderInfo();
            order.setCustomerID(customer.getCustomerID());
            order.setOrderStatus(1);
            order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));
            order.setDeliveryOptionID(deliveryOptionID);
            order.setManagerID(1);
            order.setPaymentMethodID(paymentMethodID);
            order.setTotalPrice(calculateTotal(cartItems));
            order.setDeliveryAddress(deliveryAddress);

            int orderID = orderDAO.createOrder(order);

            if (orderID == -1) {

                request.setAttribute("errorMessage", "Lỗi khi tạo đơn hàng. Vui lòng thử lại!");
                request.getRequestDispatcher("cart.jsp").forward(request, response);
                return;
            }

            List<OrderDetail> orderDetails = new ArrayList<>();
            for (CartItem item : cartItems) {
                orderDetails.add(new OrderDetail(orderID, item.getProductID(), item.getQuantity(), item.getTotalPrice()));
            }

            boolean added = orderDAO.addOrderItems(orderID, orderDetails);
            if (!added) {
                request.setAttribute("errorMessage", "Không thể thêm sản phẩm vào đơn hàng!");
                request.getRequestDispatcher("cart.jsp").forward(request, response);
                return;
            }

            boolean cleared = cartDAO.clearCart(customer.getCustomerID());
            session.removeAttribute("cartlists");

            response.sendRedirect("home");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Đã xảy ra lỗi không xác định!");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }

    private double calculateTotal(List<CartItem> cartItems) {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total; // Trả về tổng giá trị dưới dạng double
    }

    private int parseIntSafe(String param) {
        try {
            return (param != null && !param.trim().isEmpty()) ? Integer.parseInt(param) : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
