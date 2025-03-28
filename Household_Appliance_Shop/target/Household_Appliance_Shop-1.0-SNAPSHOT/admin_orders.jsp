<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Orders Dashboard</title>
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
              integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
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

                <!-- Main content -->
                <div class="content">
                    <h1>Order Management</h1>

                    <!-- Thông báo trạng thái -->
                <c:if test="${param.message != null}">
                    <div class="alert alert-success">${param.message}</div>
                </c:if>
                <c:if test="${param.error != null}">
                    <div class="alert alert-danger">${param.error}</div>
                </c:if>

                <!-- Form lọc đơn hàng theo số điện thoại  -->
                <form method="GET" action="searchByPhone" class="mb-3">
                    <div class="row">
                        <div class="col-md-6">
                            <input type="text" name="phone" class="form-control" placeholder="Search by phone number" value="${param.phone}">
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </div>
                    </div>
                </form>

                <!-- Form lọc đơn hàng theo trạng thái -->
                <form method="GET" action="listAdminOrders" class="mb-4">
                    <div class="input-group">
                        <select name="status" class="form-select">
                            <option value="">All</option>
                            <option value="chưa xử lý" ${param.status == 'chưa xử lý' ? 'selected' : ''}>Chưa xử lý</option>
                            <option value="hoàn thành" ${param.status == 'hoàn thành' ? 'selected' : ''}>Hoàn thành</option>
                        </select>
                        <button type="submit" class="btn btn-secondary">Filter</button>
                    </div>
                </form>
                        
                <c:if test="${not empty message}">
                    <div class="alert alert-success">${message}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <!-- Bảng hiển thị danh sách đơn hàng -->
                <table class="table table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th>Order ID</th>
                            <th>Customer</th>
                            <th>Order Date</th>
                            <th>Shipping address</th>
                            <th>Status</th>
                            <th>Total Amount</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${listOrders}">
                            <tr>
                                <td>${order.id}</td>
                                <td>${order.address.name}</td>
                                <td>${order.orderDate}</td>
                                <td>
                                    ${order.address.addressLine}, ${order.address.city} <br/> 
                                    Số điện thoại: ${order.address.phoneNumber} 
                                </td>
                                <td>
                                    <span class="badge
                                          <c:choose>
                                              <c:when test="${order.status == 'chưa xử lý'}">bg-warning</c:when>
                                              <c:when test="${order.status == 'hoàn thành'}">bg-success</c:when>
                                          </c:choose>">${order.status}
                                    </span>
                                </td>
                                <td>${order.totalAmount}</td>
                                <td>
                                    <a href="viewOrderDetails?id=${order.id}" class="btn btn-primary btn-sm"><i class="fas fa-eye"></i>View</a>
                                    <c:if test="${order.status == 'chưa xử lý'}">
                                        <a href="updateOrderStatus?id=${order.id}&status=hoàn thành" class="btn btn-success btn-sm">
                                            <i class="fas fa-check"></i> Complete
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
