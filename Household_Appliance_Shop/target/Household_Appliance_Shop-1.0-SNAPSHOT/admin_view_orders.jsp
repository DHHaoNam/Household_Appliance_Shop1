<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard - Order Details</title>
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

                <!-- Order Details Content -->
                <div class="content">
                    <h1 class="mb-4">Order Details</h1>
                    <!-- Order ID -->
                    <div class="order-details">
                        <div class="mb-3">
                            <label for="orderId" class="form-label">Order ID</label>
                            <input type="text" class="form-control" id="orderId" value="${order.id}" readonly>
                    </div>
                    <!-- Customer Name -->
                    <div class="mb-3">
                        <label for="customerName" class="form-label">Customer</label>
                        <input type="text" class="form-control" id="customerName" value="${order.address.name}" readonly>
                    </div>
                    <!-- Order Date -->
                    <div class="mb-3">
                        <label for="orderDate" class="form-label">Order Date</label>
                        <input type="text" class="form-control" id="orderDate" value="${order.orderDate}" readonly>
                    </div>
                    <!-- Status -->
                    <div class="mb-3">
                        <label for="status" class="form-label">Status</label>
                        <input type="text" class="form-control" id="status" value="${order.status}" readonly>
                    </div>
                    <!-- Address and Phone Number -->
                    <div class="mb-3">
                        <label for="address" class="form-label">Address</label>
                        <input type="text" class="form-control" id="address" value="${order.address.addressLine}, ${order.address.city}" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Phone Number</label>
                        <input type="text" class="form-control" id="phoneNumber" value="${order.address.phoneNumber}" readonly>
                    </div>

                    <h3 class="mt-4">Order Items</h3>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Product Name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${order.orderItems}">
                                <tr>
                                    <td>${item.product.name}</td>
                                    <td>${item.quantity}</td>
                                    <td>${item.price}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="mb-3">
                        <label for="totalAmount" class="form-label">Total Amount</label>
                        <input type="text" class="form-control" id="totalAmount" value="${order.totalAmount}" readonly>
                    </div>

                    <a href="listAdminOrders" class="btn btn-secondary mt-3">Back to Orders</a>
                    <a href="deleteOrder?id=${order.id}" class="btn btn-danger mt-3" onclick="return confirm('Bạn có chắc chắn muốn xóa đơn hàng này không?');"> 
                        Delete
                    </a>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
