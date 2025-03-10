<%-- 
    Document   : header
    Created on : Oct 17, 2024, 8:06:54 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand rounded-pill logo" href="home"><img src="" alt="">Metal</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="home">Trang Chủ</a>
                </li>
                
                
                <li class="nav-item">
                    <a class="nav-link" href="#">Blog</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Liên Hệ</a>
                </li>
                <!-- Sửa điều kiện kiểm tra isAdmin -->
                <c:if test="${sessionScope.customer != null && sessionScope.accustomer.isAdmin() == true}">
                    <li class="nav-item">
                        <a class="nav-link admin-btn" href="admin-account-crud">Admin Dashboard</a>
                    </li>
                </c:if>
            </ul>
            <div class="user-section">
                <div class="position-relative">

                    <a href="#" class="nav-link user-toggle">
                        <i class="fa-solid fa-user"><c:if test="${sessionScope.acc != null}">
                            <span class="username-display">${acc.username}</span>
                        </c:if></i>  
                    </a>
                    <div id="userMenu" class="user-menu">
                        <c:if test="${sessionScope.customer == null}">
                            <ul class="list-unstyled">
                                <li><a href="login.jsp">Đăng nhập</a></li>
                                <li><a href="register.jsp">Đăng ký</a></li>
                            </ul>
                        </c:if>
                        <c:if test="${sessionScope.customer != null}">
                            <p>Xin chào, ${sessionScope.customer.userName}!</p>
                            <ul class="list-unstyled">
                                <li><a href="account.jsp"><i class="fas fa-user-circle me-2"></i>Tài khoản của tôi</a></li>
                                <li><a href="listAddress"><i class="fas fa-map-marker-alt me-2"></i>Danh sách địa chỉ</a></li>
                                <li><a href="listOrders"><i class="fas fa-history me-2"></i>Lịch sử mua hàng</a></li>
                                <li><a href="logout"><i class="fas fa-sign-out-alt me-2"></i>Đăng xuất</a></li>
                            </ul>
                        </c:if> 
                    </div>
                </div>
                <a href="cart" class="nav-link">
                    <i class="fa-solid fa-basket-shopping"></i>
                </a>
            </div>
        </div>
    </div>
</nav>