/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.homeController;

import dao.HomeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Brand;
import model.Category;
import model.Product;

/**
 *
 * @author HP
 */
public class HomeController extends HttpServlet {

    private HomeDAO homeDAO;

    @Override
    public void init() {
        homeDAO = new HomeDAO();
    }

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
            out.println("<title>Servlet HomeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeController at " + request.getContextPath() + "</h1>");
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
        String action = request.getServletPath();
        try {
            //loadCart(request);
            switch (action) {
                case "/home-search":
                    getProductByName(request, response);
                    break;
                case "/product-detail":
                    detailProduct(request, response);
                    break;
                default:
                    getData(request, response);
                    break;
            }
        } catch (ServletException | IOException e) {
            System.out.println(e);
        }
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
        doGet(request, response);
    }

    protected void getData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String indexStr = request.getParameter("index");
        String categoryIdStr = request.getParameter("categoryid");
        String brandIdStr = request.getParameter("brandid");
        Integer categoryid = null;
        Integer brandid = null;

        if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
            categoryid = Integer.parseInt(categoryIdStr);
        }

        if (brandIdStr != null && !brandIdStr.isEmpty()) {
            brandid = Integer.parseInt(brandIdStr);
        }

        List<Category> categorys = homeDAO.selectAllCategorys();
        List<Brand> brands = homeDAO.selectAllBrand(); // Fetch brands

        int index = (indexStr == null || indexStr.isEmpty()) ? 1 : Integer.parseInt(indexStr);
        List<Product> products = homeDAO.pagingProducts(index, categoryid, brandid); // Pass both category and brand

        int count = homeDAO.countProductByFilters(categoryid, brandid); // Count products based on filters
        int endPage = (count % 12 == 0) ? count / 12 : count / 12 + 1;

        request.setAttribute("currentPage", index);
        request.setAttribute("endPage", endPage);
        request.setAttribute("products", products);
        request.setAttribute("categorys", categorys);
        request.setAttribute("brands", brands); // Set brands as request attribute
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    protected void getProductByName(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nameInput = request.getParameter("search");
        List<Product> products = homeDAO.selectProductByName(nameInput);
        List<Category> categorys = homeDAO.selectAllCategorys();
        request.setAttribute("products", products);
        request.setAttribute("categorys", categorys);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    protected void detailProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("productID");
        Product product = homeDAO.getProductBYID(id);
        Brand brand = homeDAO.getBrandById(product.getBrandID());
        request.setAttribute("brand", brand);
        request.setAttribute("details", product);
        request.getRequestDispatcher("product_detail.jsp").forward(request, response);
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
