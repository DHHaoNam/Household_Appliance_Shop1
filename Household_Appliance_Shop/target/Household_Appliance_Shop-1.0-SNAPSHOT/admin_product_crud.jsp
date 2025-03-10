<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
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
                    <a href="CategoryController"><i class="fas fa-list"></i> Category Management</a>
                    <a href="ProductController"><i class="fas fa-box"></i> Product Management</a>
                    <a href="admin-account-crud"><i class="fas fa-users"></i> Account Management</a>
                    <a href="listAdminOrders"><i class="fas fa-shopping-cart"></i> Order Management</a>
                    <a href="revenue-chart"><i class="fa-solid fa-chart-simple"></i> Revenue Management</a>
                </div>

                <!-- Main content -->
                <div class="content">
                    <h1>Product Management</h1>

                    <!-- Search Form -->
                    <!-- Form tìm kiếm -->
                    <form method="GET" action="searchProduct" class="mb-3">
                        <div class="row">
                            <div class="col-md-8">
                                <input type="text" name="search" class="form-control" placeholder="Search product by name" value="${param.search}">
                        </div>
                        <div class="col-md-4">
                            <button type="submit" class="btn btn-primary w-100"><i class="fas fa-search"></i> Search</button>
                        </div>
                    </div>
                </form>
                <c:set var="i" value="${currentPage}" />        
                <!-- Form phân loại theo danh mục --> 
                <form method="GET" action="ProductController" class="mb-3">
                    <!-- Thêm input hidden để truyền index -->
                    <input type="hidden" name="index" value="${currentPage}"/>

                    <div class="row">
                        <div class="col-md-8">
                            <select name="categoryID" class="form-select">
                                <option value="">All Categories</option>
                                <c:forEach var="c" items="${categories}">
                                    <option value="${c.categoryID}" ${param.categoryID == c.categoryID ? 'selected' : ''}>${c.categoryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <button type="submit" class="btn btn-secondary w-100"><i class="fas fa-filter"></i> Filter</button>
                        </div>
                    </div>
                </form>


                <!-- Add Product Button -->
                <a href="<%=request.getContextPath()%>/new" class="btn btn-success mb-3"><i class="fas fa-plus"></i> Add New Product</a>

                <!-- Product Table -->
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Product Name</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Category</th>
                            <th>Brand</th>
                            <th>Image</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${requestScope.p}">
                            <tr>
                                <td>${p.productID}</td>
                                <td>${p.productName}</td>
                                <td>${p.price}</td>
                                <td>${p.stock_Quantity}</td>
                                <td>${p.categoryID}</td>
                                <td>${p.brandID}</td>
                                <td><img src="${p.image}" alt="${p.productName}" style="width: 50px; height: auto;"></td>
                                <td>
                                    <a href="newedit?id=${p.productID}" class="btn btn-primary btn-sm"><i class="fas fa-edit"></i> Edit</a>
                                    <a href="delete?id=${p.productID}" class="btn btn-danger btn-sm"
                                       onclick="return confirm('Are you sure you want to delete this product?');"><i class="fas fa-trash"></i> Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <c:if test="${not empty error}">
                    <strong>${error}</strong>
                </c:if>

                <div class="float-end">
                    <nav aria-label="Page navigation example">
                        <ul class="pagination">
                            <li class="page-item <c:if test='${currentPage <= 1}'>disabled</c:if>'">
                                <a class="page-link" href="<c:if test='${currentPage > 1}'>productcontrol?index=${currentPage - 1}&categoryid=${param.categoryid}</c:if>" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>

                            <c:forEach begin="1" end="${endPage}" var="i">
                                <li class="page-item <c:if test='${i == currentPage}'>active</c:if>'">
                                    <a class="page-link" href="productcontrol?index=${i}&categoryid=${param.categoryid}">${i}</a>
                                </li>
                            </c:forEach>

                            <li class="page-item <c:if test='${currentPage >= endPage}'>disabled</c:if>'">
                                <a class="page-link" href="<c:if test='${currentPage < endPage}'>productcontrol?index=${currentPage + 1}&categoryid=${param.categoryid}</c:if>" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                           document.addEventListener('DOMContentLoaded', function () {
                                               // Get the current URL
                                               const currentURL = window.location.href;

                                               // Get all pagination links
                                               const paginationLinks = document.querySelectorAll('.pagination a');

                                               // Loop through all pagination links
                                               paginationLinks.forEach(link => {
                                                   const parentLi = link.closest('li');
                                                   const isPageNumber = !parentLi.classList.contains('disabled') && !link.getAttribute('aria-label');

                                                   // Check if the link's href matches the current URL and it's a page number link
                                                   if (currentURL.includes(link.href) && isPageNumber) {
                                                       link.classList.add('active');
                                                   }
                                               });
                                           });
        </script>
    </body>

</html>
