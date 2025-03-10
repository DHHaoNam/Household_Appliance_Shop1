/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.product;

import dao.BrandDAO;
import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Brand;
import model.Category;
import model.Product;

/**
 *
 * @author TRUNG NHAN
 */
public class ProductController extends HttpServlet {

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
            out.println("<title>Servlet ProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductController at " + request.getContextPath() + "</h1>");
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
        System.out.println("yeu cau nhan duoc: " + action);
        try {
            switch (action) {
                case "/delete":
                    delete(request, response);
                    break;
                case "/insert":
                    addProduct(request, response);
                    break;
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/newedit":
                    newedit(request, response);
                    break;
                case "/edit":
                    edit(request, response);
                    break;
                case "/searchProduct":
                    search(request, response);
                    break;
                default:
                    adminProduct(request, response);
                    break;
            }
        } catch (IOException e) {
            e.getStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    ProductDAO pdao = new ProductDAO();
    CategoryDAO cdao = new CategoryDAO();
    BrandDAO bdao = new BrandDAO();

    private void adminProduct(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws SQLException, IOException, jakarta.servlet.ServletException {
        List<Product> product;
        String categoryID_raw = request.getParameter("categoryID");

        if (categoryID_raw != null && !categoryID_raw.isEmpty()) {
            int categoryID = Integer.parseInt(categoryID_raw);
            product = pdao.selectProductBycategory(categoryID);
        } else {
            product = pdao.selectAllProducts();
        }
        List<Brand> brand = bdao.selectAllBrands();
        List<Category> categories = cdao.selectAllCategories();
        request.setAttribute("brand", brand);
        request.setAttribute("categories", categories);
        request.setAttribute("p", product);
        request.getRequestDispatcher("admin_product_crud.jsp").forward(request, response);
    }

    private void addProduct(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws SQLException, IOException, jakarta.servlet.ServletException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String desciption = request.getParameter("description");
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        int brandID = Integer.parseInt(request.getParameter("brandID"));
        String image = request.getParameter("image");
        try {
            pdao.insertProduct(new Product(name, desciption, categoryID, price, brandID, brandID, image));
            response.sendRedirect(request.getContextPath() + "/ProductController");
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

    private void showNewForm(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws IOException, ServletException {
        List<Category> category = cdao.selectAllCategories();
        List<Brand> brand = bdao.selectAllBrands();
        request.setAttribute("category", category);
        request.setAttribute("brand", brand);
        request.getRequestDispatcher("admin_add_product.jsp").forward(request, response);
    }

    private void newedit(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = pdao.selectProduct(id);
        List<Brand> brandList = bdao.selectAllBrands();
        List<Category> categoryList = cdao.selectAllCategories();
        request.setAttribute("p", product);
        request.setAttribute("b", brandList);
        request.setAttribute("c", categoryList);
        request.getRequestDispatcher("/admin_edit_product.jsp").forward(request, response);
    }

    private void edit(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String desciption = request.getParameter("description");
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int brandID = Integer.parseInt(request.getParameter("brandID"));
        String image = request.getParameter("image");
        try {
            pdao.updateProduct(new Product(id, name, desciption, categoryID, price, quantity, brandID, image));
            response.sendRedirect(request.getContextPath() + "/ProductController");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    private void delete(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            pdao.deleteProduct(id);
            response.sendRedirect(request.getContextPath() + "/ProductController");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    private void search(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws IOException, ServletException {
        List<Product> products = new ArrayList<>();
        String search = request.getParameter("search");
        try {
            if (search == null || search.trim().isEmpty()) {
                request.setAttribute("error", "Vui long nhap tu khoa tim kiem");
                request.getRequestDispatcher("admin_product_crud.jsp").forward(request, response);
                return;
            }
            products = pdao.searchProduct(search);
           
            if (products.isEmpty()) {
                request.setAttribute("error", "Khong tim thay san pham hop le");
            } else {
                request.setAttribute("p", products);
            }
            request.getRequestDispatcher("admin_product_crud.jsp").forward(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
