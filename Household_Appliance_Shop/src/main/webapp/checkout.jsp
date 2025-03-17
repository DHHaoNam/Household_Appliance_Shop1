<%@page import="model.Customer"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="model.CartItem, model.DeliveryOption, model.PaymentMethod, model.Address" %>

<%
    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
    List<DeliveryOption> deliveryOptions = (List<DeliveryOption>) request.getAttribute("deliveryOptions");
    List<PaymentMethod> paymentMethods = (List<PaymentMethod>) request.getAttribute("paymentMethods");
    Address defaultAddress = (Address) request.getAttribute("defaultAddress");
    Customer customer = (Customer) request.getAttribute("customer");
    Double total = (Double) request.getAttribute("total");
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="./CSS/Style.css">
        <link rel="stylesheet" href="./CSS/HeaderAndFooter_CSS.css">
        <link rel="stylesheet" href="./CSS/home.css">
        <script src="./Script/header-script.js"></script>
    </head>
    <body>

        <jsp:include page="header.jsp"></jsp:include>

            <div class="container mt-4 order-history">
                <h3 class="text-center mb-4">Thanh toán đơn hàng</h3>

            <% if (cartItems == null || cartItems.isEmpty()) { %>
            <p class="text-center">Giỏ hàng của bạn đang trống. <a href="home">Tiếp tục mua sắm</a></p>
            <% } else { %>
            <form action="checkout" method="post">
                <table class="table table-bordered text-center">
                    <thead class="table-primary">
                        <tr>
                            <th>Sản phẩm</th>
                            <th>Số lượng</th>
                            <th>Giá</th>
                            <th>Tổng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (CartItem item : cartItems) {%>
                        <tr>
                            <td class="product-name"><%= item.getProduct().getProductName()%></td>
                            <td><%= item.getQuantity()%></td>
                            <td><%= item.getProduct().getPrice()%> VND</td>
                            <td><%= item.getQuantity() * item.getProduct().getPrice()%> VND</td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>

                <p class="text-end"><b>Tổng tiền: <%= total%> VND</b></p>

                <!-- Thông tin khách hàng -->
                <div class="mb-3">
                    <label class="form-label">Họ và tên:</label>
                    <input type="text" class="form-control" name="fullName" 
                           value="<%= (customer != null) ? customer.getFullName() : ""%>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label">Số điện thoại:</label>
                    <input type="text" class="form-control" name="phoneNumber" 
                           value="<%= (customer != null) ? customer.getPhone() : ""%>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <input type="email" class="form-control" name="email" 
                           value="<%= (customer != null) ? customer.getEmail() : ""%>" readonly>
                </div>

                <!-- Địa chỉ giao hàng -->
                <div class="mb-3">
                    <label class="form-label">Địa chỉ giao hàng:</label>
                    <input type="text" class="form-control" name="deliveryAddress" 
                           value="<%= (defaultAddress != null) ? defaultAddress.getAddressDetail() : ""%>" required>
                </div>

                <!-- Phương thức giao hàng -->
                <div class="mb-3">
                    <label class="form-label">Phương thức giao hàng:</label>
                    <select class="form-select" name="deliveryOptionID" required>
                        <% for (DeliveryOption option : deliveryOptions) {%>
                        <option value="<%= option.getDeliveryOptionID()%>"><%= option.getOptionName()%></option>
                        <% } %>
                    </select>
                </div>

                <!-- Phương thức thanh toán -->
                <div class="mb-3">
                    <label class="form-label">Phương thức thanh toán:</label>
                    <select class="form-select" name="paymentMethodID" required>
                        <% for (PaymentMethod method : paymentMethods) {%>
                        <option value="<%= method.getPaymentMethodID()%>"><%= method.getMethodName()%></option>
                        <% } %>
                    </select>
                </div>

                <div class="text-center" style="margin-bottom: 30px">
                    <button type="submit" class="btn btn-success" style="border-radius: 20px">Thanh toán</button>
                    <a href="cart.jsp" class="btn btn-secondary">Quay lại giỏ hàng</a>
                </div>
            </form>
            <% }%>
        </div>

        <jsp:include page="footer.jsp"></jsp:include>

    </body>
</html>
