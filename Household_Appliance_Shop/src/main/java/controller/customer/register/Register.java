/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer.register;

import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import model.Customer;

/**
 *
 * @author Nam
 */
public class Register extends HttpServlet {

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
            out.println("<title>Servlet Register</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("register.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Kiểm tra tên người dùng đã tồn tại chưa
        CustomerDAO customerDAO = new CustomerDAO();
        try {
            boolean hasError = false;
            if (!password.equals(confirmPassword)) {
                session.setAttribute("errorConfirm", "Mật khẩu nhập lại không khớp");
                hasError = true;
            }
            if (customerDAO.checkUserExists(userName)) {
                session.setAttribute("errorName", "Tên đăng nhập đã tồn tại");
                hasError = true;
            }
            if (customerDAO.checkEmailExists(email)) {
                session.setAttribute("errorEmail", "Email đã tồn tại");
                hasError = true;
            }
            if (password.length() <= 7) {
                session.setAttribute("errorPassword", "Mật khẩu phải dài hơn 7 ký tự");
                hasError = true;
            }
            if (!phone.matches("\\d+")) {
                session.setAttribute("errorPhoneFormat", "Số điện thoại chỉ được chứa chữ số!");
                hasError = true;
            }
            System.out.println("Phone: " + phone);


            // Kiểm tra số điện thoại đã tồn tại chưa
            if (customerDAO.checkPhoneExists(phone)) {
                session.setAttribute("errorPhone", "Số điện thoại đã được sử dụng!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            if (hasError) {
                response.sendRedirect("register.jsp");
                return;
            }

            // Mã hóa mật khẩu bằng MD5
//            String hashedPassword = hashPasswordMD5(password);
            // Tạo đối tượng Customer
            Customer customer = new Customer();
            customer.setFullName(fullName);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setUserName(userName);
            customer.setPassword(password); // Lưu mật khẩu đã mã hóa

            // Thực hiện đăng ký
            if (customerDAO.register(customer)) {
                session.setAttribute("registersuccessmassage", "Đăng ký thành công! Vui lòng đăng nhập.");
                response.sendRedirect("register.jsp");
            } else {
                session.setAttribute("errorGeneral", "Đăng ký thất bại! Vui lòng thử lại.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException("Lỗi database: " + e.getMessage());
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
