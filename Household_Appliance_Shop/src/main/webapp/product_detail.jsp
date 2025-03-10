<%-- 
    Document   : food_detail
    Created on : Oct 17, 2024, 7:41:02 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi Tiết Món Ăn</title>
        <!-- Bootstrap -->
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="./CSS/Style.css">
        <link rel="stylesheet" href="./CSS/HeaderAndFooter_CSS.css">
        <link rel="stylesheet" href="./CSS/home.css">
        <script src="./Script/header-script.js"></script>

        <style>
            /* General Body Styling */
            body {
                font-family: 'Arial', sans-serif;
                background-color: #fffdf8;
                color: #333;
            }

            mg-100px {
                margin: 100px;
            }


            /* Food Detail Page Styling */
            .food-detail-page {
                padding: 50px 0;
            }

            .food-img {
                max-width: 100%;
                border-radius: 15px;
                box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s ease;
            }

            .food-img:hover {
                transform: scale(1.05);
            }

            .food-name {
                font-size: 34px;
                font-weight: bold;
                color: #FC6E51;
                text-transform: uppercase;
            }

            .food-price {
                font-size: 26px;
                font-weight: bold;
                color: #d9534f;
                margin-top: 10px;
            }

            .food-desc {
                font-size: 16px;
                color: #777;
                line-height: 1.7;
                margin: 20px 0;
            }

            .quantity {
                width: 80px;
                text-align: center;
                border-radius: 8px;
                border: 1px solid #ddd;
                padding: 5px;
            }

            .btn-cart {
                background-color: #FF7F50;
                border: none;
                color: #fff;
                font-size: 16px;
                font-weight: bold;
                padding: 12px 25px;
                border-radius: 25px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                transition: background-color 0.3s ease, transform 0.2s ease;
            }

            .btn-cart:hover {
                background-color: #FF6347;
                transform: translateY(-2px);
            }

            /* Custom Back Button */
            .btn-back {
                background-color: #ddd;
                color: #333;
                font-size: 16px;
                padding: 10px 20px;
                border-radius: 25px;
                text-decoration: none;
                display: inline-block;
                transition: background-color 0.3s ease, color 0.3s ease;
            }

            .btn-back:hover {
                background-color: #bbb;
                color: #fff;
            }

            /* Footer Styling */
            .footer {
                background-color: #333;
                color: #f1f1f1;
                padding: 40px 0;
                font-size: 14px;
            }

            .footer strong {
                color: #FC6E51;
            }

            .footer p {
                margin: 0;
                font-size: 14px;
            }

        </style>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"></jsp:include>

            <div class="container my-5">
                <div class="row">
                    <!-- Food Image -->
                    <div class="col-md-6">
                        <img src="${details.image}" alt="Tên món ăn" class="food-img">
                </div>

                <!-- Food Details -->
                <div class="col-md-6 margin-left">
                    <h2 class="food-name">${details.productName}</h2>
                    <p class="food-price">${details.price} VND</p>

                    <!-- Quantity and Add to Cart -->
                    <form action="ProductDetailController" method="post"> <!-- Đổi action thành 'cart' để gửi đến CartController -->
                        <div class="form-group">
                            <label for="quantity">Số Lượng:</label>
                            <input type="number" id="stock_Quantity" name="stock_Quantity" class="form-control quantity" min="1" value="1">
                        </div>
                        <div class="form-group">
                            <label for="description">Mô Tả</label>
                            <textarea id="description" name="description" class="form-control" rows="2" readonly>${details.description}</textarea>
                        </div>
                        
                        
                        <div class="form-group">
                            <label for="Brand">Brand</label>
                             <input type="text" id="brandID" name="brand" class="form-control" value="${brand.brandName}" readonly>
                        </div>
                       


                        <input type="hidden" name="productId" value="${details.productID}"> <!-- ID của món ăn -->
                        <c:if test="${details.stock_Quantity > 0}">
                            <p>Tình trạng: Còn hàng</p>
                            <button type="submit" class="btn btn-cart mt-3 text-white">Thêm Vào Giỏ Hàng</button>
                        </c:if>
                        <c:if test="${details.stock_Quantity <= 0}">
                            <p>Tình trạng: Hết hàng</p>
                        </c:if>

                        <a class="btn btn-cart mt-3 bg-gradient-secondary text-white" href="home">Trở lại</a>
                    </form>

                </div>
            </div>
        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"></jsp:include>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>

</html>


