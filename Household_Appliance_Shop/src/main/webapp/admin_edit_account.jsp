<%-- 
    Document   : admin_add_account_
    Created on : Oct 25, 2024, 11:18:16 PM
    Author     : admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard - Edit Account</title>
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="./CSS/Style.css" />
        <link rel="stylesheet" href="./CSS/HeaderAndFooter_CSS.css" />
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
        <jsp:include page="admin_dashboard_header.jsp"></jsp:include>


            <div class="wrapper clearfix">
                <!-- Sidebar -->
                <div class="sidebar">
                    <h3>Admin Dashboard</h3>
                    <a href="CategoryControl"><i class="fas fa-list"></i> Category Management</a>
                    <a href="productcontrol"><i class="fas fa-box"></i> Product Management</a>
                    <a href="admin-account-crud"><i class="fas fa-users"></i> Account Management</a>
                    <a href="listAdminOrders"><i class="fas fa-shopping-cart"></i> Order Management</a>
                    <a href="revenue-chart"><i class="fa-solid fa-chart-simple"></i> Revenue Management</a>
                </div>

                <!-- Form to edit account -->
                <div class="content">
                    <h1 class="mb-4">Edit Account</h1>
                    <form method="POST" action="admin-update-account" class="border p-4 rounded bg-light">
                        <!-- ID Field (Read-only) -->
                        <div class="mb-3">
                            <label for="id" class="form-label">ID</label>
                            <input type="text" class="form-control" id="id" name="id" value="${account.id}" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" name="username" value="${account.username}" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="text" class="form-control" id="password" name="password" value="${account.password}" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" value="${account.email}" required>
                    </div>
                    <div class="mb-3">
                        <label for="phone_number" class="form-label">Phone Number</label>
                        <input type="text" class="form-control" id="phone_number" name="phone_number" value="${account.phone_number}" required>
                    </div>
                    <div class="mb-3">
                        <label for="role" class="form-label">Role</label>
                        <select id="role" name="isAdmin" class="form-control" required>
                            <option value="0" ${account.isAdmin() == false ? 'selected' : ''}>User</option>
                            <option value="1" ${account.isAdmin()== true ? 'selected' : ''}>Admin</option>
                        </select>
                    </div>

                    <!-- Success and Error Messages -->
                    <% if (request.getAttribute("message") != null) { %>
                    <div class="alert alert-success" role="alert">
                        <%= request.getAttribute("message") %>
                    </div>
                    <% } else if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger" role="alert">
                        <%= request.getAttribute("error") %>
                    </div>
                    <% } %>

                    <!-- Submit Button -->
                    <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Save Changes</button>
                    <a href="admin-account-crud" class="btn btn-secondary ms-2">Back</a>
                </form>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
