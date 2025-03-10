<%-- 
    Document   : admin-welcome
    Created on : Nov 4, 2024, 12:57:34 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            /* Sidebar Styles */
            .sidebar {
                width: 250px;
                background-color: #343a40;
                padding: 20px;
                height: 100%;
                position: fixed;
                top: 0;
                left: 0;
                color: white;
                display: flex;
                flex-direction: column;
                align-items: flex-start;
            }
            .sidebar h3 {
                margin-bottom: 30px;
                text-transform: uppercase;
                font-size: 18px;
            }

            .sidebar a {
                color: white;
                text-decoration: none;
                display: block;
                padding: 10px 15px;
                margin: 5px 0;
                width: 100%;
                text-align: left;
                border-radius: 5px;
            }

            .sidebar a:hover {
                background-color: #495057;
                transition: background-color 0.3s;
            }

            /* Main content */
            .content {
                margin-left: 250px; /* Space for the sidebar */
                padding: 20px;
                background-color: #f8f9fa;
                width: calc(100% - 250px);
                float: left;
            }

            /* Prevent floating issues */
            .clearfix::after {
                content: "";
                display: table;
                clear: both;
            }
        </style>
    </head>
    <body>
        <!-- Sidebar -->
        <jsp:include page="admin_dashboard_header.jsp"></jsp:include>
        <div class="sidebar">
                <h3>Admin Dashboard</h3>
                <a href="CategoryController"><i class="fas fa-list"></i> Category Management</a>
                <a href="ProductController"><i class="fas fa-box"></i> Product Management</a>
                <a href="admin-account-crud"><i class="fas fa-users"></i> Account Management</a>
                <a href="listAdminOrders"><i class="fas fa-shopping-cart"></i> Order Management</a>
                <a href="revenue-chart"><i class="fa-solid fa-chart-simple"></i> Revenue Management</a>
            </div>

        <!-- Main Content -->
        <div class="content">
            <h1>Welcome to Admin Dashboard</h1>
            <p>Here, you can manage categories, products, accounts, and orders.</p>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

