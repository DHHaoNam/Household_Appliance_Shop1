package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Product;

public class CartDAO extends DAO {

    public List<CartItem> getcart(int customerID) {
        String sql = "SELECT Cart.cartID, CartItem.quantity, "
                + "Product.productID, Product.productName, Product.description, Product.image, Product.price, "
                + "Product.categoryID, Product.stock_Quantity, Product.brandID, "
                + "(CartItem.quantity * Product.price) AS totalPrice "
                + "FROM Cart "
                + "INNER JOIN CartItem ON Cart.cartID = CartItem.cartID "
                + "INNER JOIN Product ON CartItem.productID = Product.productID "
                + "WHERE Cart.customerID = ?";

        List<CartItem> list = new ArrayList<>();

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("productID"),
                        rs.getString("productName"),
                        rs.getString("description"),
                        rs.getInt("categoryID"),
                        rs.getDouble("price"),
                        rs.getInt("stock_Quantity"),
                        rs.getInt("brandID"),
                        rs.getString("image")
                );

                CartItem cartItem = new CartItem(
                        rs.getInt("cartID"),
                        rs.getInt("productID"),
                        rs.getInt("quantity"),
                        rs.getDouble("totalPrice"),
                        product
                );
                list.add(cartItem);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy giỏ hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public boolean isProductInCart(int customerID, int productID) {
        String query = "SELECT COUNT(*) FROM CartItem WHERE cartID = (SELECT cartID FROM Cart WHERE customerID = ?) AND productID = ?";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerID);
            ps.setInt(2, productID);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra sản phẩm trong giỏ hàng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCartItemQuantity(int customerID, int productId, int quantity) {
        String sql = "UPDATE CartItem SET quantity = ?, totalPrice = ? * (SELECT price FROM Product WHERE productID = ?) WHERE cartID = (SELECT cartID FROM Cart WHERE customerID = ?) AND productID = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, quantity);
            ps.setInt(3, productId);
            ps.setInt(4, customerID);
            ps.setInt(5, productId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating product quantity: " + e.getMessage());
            return false;
        }
    }

    public boolean addCartItem(int customerID, int productId, int quantity) {
        if (isProductInCart(customerID, productId)) {
            int currentQuantity = getCartItemQuantity(customerID, productId);
            return updateCartItemQuantity(customerID, productId, currentQuantity + quantity);
        }
        // Kiểm tra xem giỏ hàng đã tồn tại hay chưa
        String getCartIdQuery = "SELECT cartID FROM Cart WHERE customerID = ?";
        String createCartQuery = "INSERT INTO Cart (customerID) VALUES (?)";
        String priceQuery = "SELECT price FROM Product WHERE productID = ?";
        String insertQuery = "INSERT INTO CartItem (cartID, productID, quantity, totalPrice) VALUES (?, ?, ?, ?)";
        try ( Connection conn = getConnection();  PreparedStatement cartStmt = conn.prepareStatement(getCartIdQuery);  PreparedStatement createCartStmt = conn.prepareStatement(createCartQuery);  PreparedStatement priceStmt = conn.prepareStatement(priceQuery);  PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            // Lấy cartID
            cartStmt.setInt(1, customerID);
            ResultSet cartRs = cartStmt.executeQuery();
            int cartID;

            if (!cartRs.next()) {
                // Nếu không có giỏ hàng, tạo giỏ hàng mới
                createCartStmt.setInt(1, customerID);
                createCartStmt.executeUpdate();
                ResultSet newCartRs = cartStmt.executeQuery();
                if (!newCartRs.next()) {
                    return false;
                }
                cartID = newCartRs.getInt("cartID");
            } else {
                cartID = cartRs.getInt("cartID");
            }
            // Lấy giá sản phẩm
            priceStmt.setInt(1, productId);
            ResultSet rs = priceStmt.executeQuery();
            if (!rs.next()) {
                return false;
            }
            double price = rs.getDouble("price");
            // Thêm sản phẩm vào giỏ hàng
            insertStmt.setInt(1, cartID);
            insertStmt.setInt(2, productId);
            insertStmt.setInt(3, quantity);
            insertStmt.setDouble(4, price * quantity);
            return insertStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi thêm sản phẩm vào giỏ hàng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeCartItem(int customerID, int productId) {
        String sql = "DELETE FROM CartItem WHERE cartID = (SELECT cartID FROM Cart WHERE customerID = ?) AND productID = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi xóa sản phẩm khỏi giỏ hàng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int getCartItemQuantity(int customerID, int productId) {
        String sql = "SELECT quantity FROM CartItem WHERE cartID = (SELECT cartID FROM Cart WHERE customerID = ?) AND productID = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("quantity") : 0;
        } catch (SQLException e) {
            System.out.println("Lỗi lấy số lượng sản phẩm: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}
