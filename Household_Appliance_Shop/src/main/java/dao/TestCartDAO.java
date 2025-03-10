package dao;

import dao.CartDAO;
import model.CartItem;

import java.util.List;

public class TestCartDAO {

    public static void main(String[] args) {
        CartDAO cartDAO = new CartDAO();

        int customerID = 2; // ID khách hàng (test)
        int productID = 2;  // ID sản phẩm (test)
        int quantity = 2;   // Số lượng muốn thêm

        // 1️⃣ Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        System.out.println("🔍 Kiểm tra sản phẩm trong giỏ hàng...");
        boolean isInCart = cartDAO.isProductInCart(customerID, productID);
        System.out.println("✅ Sản phẩm có trong giỏ hàng? " + isInCart);

        // 2️⃣ Thêm sản phẩm vào giỏ hàng
        System.out.println("\n🛒 Thêm sản phẩm vào giỏ hàng...");
        boolean addSuccess = cartDAO.addCartItem(customerID, productID, quantity);
        System.out.println("✅ Thêm sản phẩm thành công? " + addSuccess);

        // 3️⃣ Lấy danh sách sản phẩm trong giỏ hàng
        System.out.println("\n📋 Danh sách sản phẩm trong giỏ hàng:");
        List<CartItem> cartItems = cartDAO.getcart(customerID);
        for (CartItem item : cartItems) {
            System.out.println("- " + item.getProduct().getProductName() + " | Số lượng: " + item.getQuantity() + " | Tổng giá: " + item.getTotalPrice());
        }

        // 4️⃣ Cập nhật số lượng sản phẩm
        int newQuantity = 5;
        System.out.println("\n✏️ Cập nhật số lượng sản phẩm lên " + newQuantity);
        boolean updateSuccess = cartDAO.updateCartItemQuantity(customerID, productID, newQuantity);
        System.out.println("✅ Cập nhật thành công? " + updateSuccess);

        // 5️⃣ Xóa sản phẩm khỏi giỏ hàng
        System.out.println("\n🗑 Xóa sản phẩm khỏi giỏ hàng...");
        boolean removeSuccess = cartDAO.removeCartItem(customerID, productID);
        System.out.println("✅ Xóa sản phẩm thành công? " + removeSuccess);
    }
}
