<%-- 
    Document   : admin_edit_product
    Created on : Oct 18, 2024, 12:44:33 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Product</title>
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
                    <h1>Edit Product</h1>

                    <!-- Form to edit product -->
                    <form action="edit" method="post">
                        <!-- Product ID (readonly) -->
                        <div class="mb-3">
                            <label for="id" class="form-label" value="" >Product ID</label>
                            <input type="text" class="form-control" id="id" name="id" value="${p.productID}" readonly>
                    </div>

                    <!-- Product Name -->
                    <div class="mb-3">
                        <label for="name" class="form-label">Product Name</label>
                        <input type="text" class="form-control" id="name" name="name" value="${p.productName}" required>
                    </div>
                    
                    <!-- Description -->
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <input type="text" class="form-control" id="description" name="description" value="${p.description}" required>
                    </div>

                    <!-- Price -->
                    <div class="mb-3">
                        <label for="price" class="form-label">Price</label>
                        <input type="number" step="0.01" class="form-control" id="price" name="price" value="${p.price}" required>
                    </div>

                    <!-- Quantity -->
                    <div class="mb-3">
                        <label for="quantity" class="form-label">Quantity</label>
                        <input type="number" class="form-control" id="quantity" name="quantity" value="${p.stock_Quantity}" required>
                    </div>

                    <!-- Category -->
                    <div class="mb-3">
                        <label for="category" class="form-label">Category</label>
                        <select class="form-select" id="category" name="categoryID" required>
                            <option value="">Select Category</option>
                            <c:forEach var="c" items="${requestScope.c}">

                                <option value="${c.categoryID}" ${c.categoryID == p.categoryID ? 'selected="selected"' : ''}>${c.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="brand" class="form-label">Brand</label>
                        <select class="form-select" id="brand" name="brandID" required>
                            <option value="">Select Brand</option>
                            <c:forEach var="b" items="${requestScope.b}">
                                <option value="${b.brandID}" ${b.brandID == p.brandID ? 'selected="selected"' : ''}>${b.brandName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Image URL -->
                    <div class="mb-3">
                        <label for="imageUrl" class="form-label">Image URL</label>
                        <input type="text" class="form-control" id="image" name="image" value="${p.image}">
                    </div>

                    <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Update Product</button>
                </form>
            </div>
        </div>


        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

