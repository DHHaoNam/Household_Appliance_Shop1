package dao;

import dao.OrderDAO;
import model.OrderDetail;
import model.OrderInfo;
import java.util.ArrayList;
import java.util.List;

public class TestOrderDAO {
    public static void main(String[] args) {
        OrderDAO orderDAO = new OrderDAO();

        // 🔹 Tạo đơn hàng mới
        OrderInfo order = new OrderInfo();
        order.setCustomerID(1); // Thay bằng ID có trong database
        order.setOrderStatus(1); // 1 = Đang xử lý
        order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));
        order.setDeliveryOptionID(2); // Ví dụ: giao hàng nhanh
        order.setManagerID(1); // Quản lý mặc định
        order.setPaymentMethodID(1); // 1 = Thanh toán khi nhận hàng
        order.setTotalPrice(500.0);
        order.setDeliveryAddress("123 Đường ABC, TP.HCM");

        int orderID = orderDAO.createOrder(order);
        if (orderID != -1) {
            System.out.println("✅ Tạo đơn hàng thành công! Order ID: " + orderID);
        } else {
            System.out.println("❌ Lỗi khi tạo đơn hàng!");
            return;
        }

        // 🔹 Thêm sản phẩm vào đơn hàng
        List<OrderDetail> orderItems = new ArrayList<>();
        orderItems.add(new OrderDetail(orderID, 101, 2, 200.0)); // Sản phẩm 101
        orderItems.add(new OrderDetail(orderID, 102, 1, 300.0)); // Sản phẩm 102

        boolean addedItems = orderDAO.addOrderItems(orderID, orderItems);
        if (addedItems) {
            System.out.println("✅ Thêm sản phẩm vào đơn hàng thành công!");
        } else {
            System.out.println("❌ Lỗi khi thêm sản phẩm vào đơn hàng!");
        }

        // 🔹 Lấy thông tin đơn hàng theo ID
        OrderInfo retrievedOrder = orderDAO.getOrderById(orderID);
        if (retrievedOrder != null) {
            System.out.println("✅ Thông tin đơn hàng:");
            System.out.println("Order ID: " + retrievedOrder.getOrderID());
            System.out.println("Customer ID: " + retrievedOrder.getCustomerID());
            System.out.println("Tổng tiền: " + retrievedOrder.getTotalPrice());
            System.out.println("Địa chỉ giao hàng: " + retrievedOrder.getDeliveryAddress());
        } else {
            System.out.println("❌ Không tìm thấy đơn hàng!");
        }

        // 🔹 Lấy danh sách sản phẩm trong đơn hàng
        List<OrderDetail> retrievedItems = orderDAO.getOrderDetails(orderID);
        if (!retrievedItems.isEmpty()) {
            System.out.println("✅ Chi tiết sản phẩm trong đơn hàng:");
            for (OrderDetail item : retrievedItems) {
                System.out.println("Sản phẩm ID: " + item.getProductID() +
                        ", Số lượng: " + item.getQuantity() +
                        ", Tổng tiền: " + item.getTotalPrice());
            }
        } else {
            System.out.println("❌ Không tìm thấy sản phẩm trong đơn hàng!");
        }
    }
}
