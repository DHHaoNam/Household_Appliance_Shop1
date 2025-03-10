<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Giới thiệu - Trang bán đồ ăn vặt</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="./CSS/HeaderAndFooter_CSS.css">
        <link rel="stylesheet" href="./CSS/home.css">
        <script src="./Script/header-script.js"></script>
        <style>
            .intro-section {
                padding: 40px 0;
                background-color: #f9f9f9;
            }

            .intro-title {
                color: #FC6E51;
                font-weight: bold;
                text-align: center;
                margin-bottom: 20px;
            }

            .intro-content {
                font-size: 18px;
                color: #333;
                line-height: 1.6;
            }

            .feature-item {
                padding: 15px;
                border: 1px solid #ddd;
                border-radius: 8px;
                background-color: #fff;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                margin-top: 20px;
            }

            .feature-title {
                color: #FC6E51;
                font-weight: bold;
            }
        </style>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"></jsp:include>

            <div class="container intro-section">
                <h1 class="intro-title">Giới thiệu về SnackZone - Thiên đường đồ ăn vặt của bạn!</h1>

                <div class="row">
                    <div class="col-md-6 intro-content">
                        <p>Chào mừng bạn đến với SnackZone - trang web bán đồ ăn vặt của chúng tôi! Tại đây, bạn có thể khám phá và thưởng thức hàng loạt món ăn vặt phong phú, từ những món truyền thống đến những hương vị mới lạ và độc đáo.</p>

                        <p>Mục tiêu của SnackZone là mang đến cho bạn trải nghiệm mua sắm đồ ăn vặt tiện lợi, nhanh chóng, với chất lượng sản phẩm đảm bảo và hương vị tuyệt hảo. Chúng tôi tự hào cung cấp các sản phẩm được lựa chọn kỹ lưỡng và chế biến cẩn thận để đảm bảo an toàn và hài lòng cho khách hàng.</p>
                    </div>
                    <div class="col-md-6">
                        <img src="image/snacks.jpg" alt="Đồ ăn vặt" class="img-fluid rounded">
                    </div>
                </div>

                <div class="row mt-5 gap-5 d-flex justify-content-center">
                    <div class="col-md-3 feature-item ">
                        <h4 class="feature-title">Đa dạng sản phẩm</h4>
                        <p>Chúng tôi cung cấp nhiều loại đồ ăn vặt từ khắp nơi, bao gồm bánh kẹo, snack, trái cây sấy khô và nhiều món ăn hấp dẫn khác.</p>
                    </div>
                    <div class="col-md-3 feature-item">
                        <h4 class="feature-title">Cam kết chất lượng</h4>
                        <p>SnackZone cam kết cung cấp những sản phẩm chất lượng, đảm bảo vệ sinh an toàn thực phẩm, để bạn có thể an tâm khi mua sắm và thưởng thức.</p>
                    </div>
                    <div class="col-md-3 feature-item">
                        <h4 class="feature-title">Dịch vụ thân thiện</h4>
                        <p>Chúng tôi luôn sẵn sàng hỗ trợ bạn trong quá trình mua sắm, đảm bảo dịch vụ chăm sóc khách hàng tận tâm và chuyên nghiệp.</p>
                    </div>
                </div>
            </div>

            <!-- Footer -->
        <jsp:include page="footer.jsp"></jsp:include>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>

</html>
