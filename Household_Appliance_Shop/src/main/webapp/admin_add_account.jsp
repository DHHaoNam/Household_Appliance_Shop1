<%-- 
    Document   : admin_add_account_
    Created on : Oct 25, 2024, 11:18:16 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard - Account Management</title>
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
              integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
              rel="stylesheet">
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
                <c:if test="${sessionScope.managerRole == 1}">
                    <a href="CategoryController"><i class="fas fa-list"></i> Category Management</a>
                </c:if>        
                <a href="ProductController"><i class="fas fa-box"></i> Product Management</a>
                <a href="CustomerController_temp"><i class="fas fa-users"></i> Account Management</a>
                <a href="listAdminOrders"><i class="fas fa-shopping-cart"></i> Order Management</a>
                <a href="revenue-chart"><i class="fa-solid fa-chart-simple"></i> Revenue Management</a>
            </div>

            <!-- Form to add new account -->
            <div class="content">
                <h1 class="mb-4">Add New Account</h1>
                <form method="POST" action="admin-add-account" class="border p-4 rounded bg-light">
                    <div class="mb-3">
                        <label for="name" class="form-label">Name</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" name="username" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="phone_number" class="form-label">Phone Number</label>
                        <input type="text" class="form-control" id="phone_number" name="phone_number" required>
                    </div>
                    <fmt:formatDate value="<%= new java.util.Date()%>" pattern="yyyy-MM-dd" var="currentDate"/>
                    <div class="mb-3">
                        <label for="registrationDate" class="form-label">Registration Date</label>
                        <input type="text" class="form-control" id="registrationDate" name="registrationDate" value="${currentDate}" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="status" class="form-label">Status</label>
                        <select class="form-control" id="status" name="status">
                            <option value="1" selected>Active</option>
                            <option value="0">Inactive</option>
                        </select>
                    </div>
                    <% if (request.getAttribute("message") != null) {%>
                    <div class="alert alert-success" role="alert">
                        <%= request.getAttribute("message")%>
                    </div>
                    <% } else if (request.getAttribute("error") != null) {%>
                    <div class="alert alert-danger" role="alert">
                        <%= request.getAttribute("error")%>
                    </div>
                    <% }%>
                    <button type="submit" class="btn btn-primary"><i class="fas fa-plus"></i> Add Account</button>
                    <a href="CustomerController_temp" class="btn btn-secondary ms-2">Back</a>
                </form>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
