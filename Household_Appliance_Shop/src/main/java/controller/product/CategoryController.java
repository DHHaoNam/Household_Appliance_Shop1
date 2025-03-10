/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.product;

import dao.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;

/**
 *
 * @author TRUNG NHAN
 */
public class CategoryController extends HttpServlet {

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
            out.println("<title>Servlet CategoryController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryController at " + request.getContextPath() + "</h1>");
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
        response.getWriter().println("CategoryController is working!");
        switch (action) {
            case "/new-category":
                showNewForm(request, response);
                break;
            case "/insert-category":
                addCategory(request, response);
                break;
            case "/newedit-category":
                neweditCategory(request, response);
                break;
            case "/delete-category": {
                try {
                    deleteCategory(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case "/edit-category": {
                try {
                    editCategory(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            default:
                adminCategory(request, response);
                break;
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
    CategoryDAO cdao = new CategoryDAO();

    private void adminCategory(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws IOException, ServletException {
        List<Category> cList = cdao.selectAllCategories();
        request.setAttribute("categories", cList);
        request.getRequestDispatcher("admin_category_crud.jsp").forward(request, response);
    }

    private void showNewForm(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws IOException, ServletException {
        response.sendRedirect("admin_add_category.jsp");

    }

    private void addCategory(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws IOException, ServletException {
        String name = request.getParameter("name");
        try {
            cdao.insertCategory(new Category(name));
            response.sendRedirect(request.getContextPath() + "/CategoryController");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void neweditCategory(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Category category = cdao.selectCategory(id);
            request.setAttribute("category", category);
            request.getRequestDispatcher("admin_edit_category.jsp").forward(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editCategory(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws IOException, ServletException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        try {
            cdao.updateCategory(new Category(id, name));
            response.sendRedirect(request.getContextPath() + "/CategoryController");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteCategory(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws IOException, ServletException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            cdao.deleteCategory(id);
            response.sendRedirect(request.getContextPath() + "/CategoryController");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
