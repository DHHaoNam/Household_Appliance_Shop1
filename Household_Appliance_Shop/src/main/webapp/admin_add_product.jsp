<%-- 
    Document   : admin_add_product
    Created on : Oct 18, 2024, 12:41:13 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add New Product</title>
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

            <div class="sidebar">
                <h3>Admin Dashboard</h3>
                <a href="CategoryController"><i class="fas fa-list"></i> Category Management</a>
                <a href="ProductController"><i class="fas fa-box"></i> Product Management</a>
                <a href="admin-account-crud"><i class="fas fa-users"></i> Account Management</a>
                <a href="listAdminOrders"><i class="fas fa-shopping-cart"></i> Order Management</a>
                <a href="revenue-chart"><i class="fa-solid fa-chart-simple"></i> Revenue Management</a>
            </div>
            <div class="content">
                <div class="container mt-5">
                    <h1>Add New Product</h1>


                    <form action="insert" method="POST">
                        <div class="mb-3">
                            <label for="name" class="form-label">Product Name</label>
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">Price</label>
                            <input type="number" step="0.01" class="form-control" id="price" name="price" required>
                        </div>
                        <div class="mb-3">
                            <label for="desciption" class="form-label">Desciption</label>
                            <input type="text" class="form-control" id="desciption" name="desciption" required>
                        </div>
                        <div class="mb-3">
                            <label for="quantity" class="form-label">Quantity</label>
                            <input type="number" class="form-control" id="quantity" name="quantity" required>
                        </div>
                        <div class="mb-3">
                            <label for="category" class="form-label">Category</label>
                            <select class="form-select" id="category" name="categoryID" required>
                                <option value="">Select Category</option>
                            <c:forEach var="category" items="${category}">
                                <option name="categoryID" value="${category.categoryID}"><c:out value="${category.categoryName}"></c:out></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="brand" class="form-label">Brand</label>
                        <select class="form-select" id="brand" name="brandID" required>
                            <option value="">Select Brand</option>
                            <c:forEach var="brand" items="${brand}">
                                <option name="brandID" value="${brand.brandID}"><c:out value="${brand.brandName}"></c:out></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="imageUrl" class="form-label">Image URL</label>
                        <input type="text" class="form-control" id="image" name="imageUrl">
                    </div>
                    <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Save Product</button>
                </form>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
