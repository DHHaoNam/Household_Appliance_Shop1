/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Brand;
import model.CartItem;
import model.Category;
import model.Product;

/**
 *
 * @author HP
 */
public class HomeDAO extends DAO {

    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM Product";
    private static final String SELECT_ALL_CATEGORYS = "SELECT * FROM Category";
    private static final String SELECT_ALL_BRANDS = "SELECT * FROM Brand";
    private static final String SELECT_ALL_PRODUCTS_BY_NAME = "SELECT * FROM Product WHERE productName LIKE ?";
    private static final String SELECT_ALL_PRODUCTS_BY_CATEGORYID = "SELECT * FROM Product WHERE CategoryID = ?";

    public List<Product> selectAllProducts() {
        List<Product> products = new ArrayList<>();
        try ( Connection connection = getConnection();  PreparedStatement prepareStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS)) {
            ResultSet rs = prepareStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("productName");
                String description = rs.getString("Description");
                int categoryId = rs.getInt("CategoryID");
                double price = rs.getDouble("Price");
                int stockQuantity = rs.getInt("Stock_Quantity");
                String imageUrl = rs.getString("image");
                int brandId = rs.getInt("BrandID");
                products.add(new Product(id, name, description, categoryId, price, stockQuantity, brandId, imageUrl));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> pagingProducts(int index, Integer categoryid, Integer brandid) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Product ");
        boolean hasWhereClause = false;

        if (categoryid != null) {
            sql.append("WHERE CategoryID = ? ");
            hasWhereClause = true;
        }

        if (brandid != null) {
            if (hasWhereClause) {
                sql.append("AND BrandID = ? ");
            } else {
                sql.append("WHERE BrandID = ? ");
                hasWhereClause = true;
            }
        }

        sql.append("ORDER BY ProductID ")
                .append("OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY;");

        List<Product> products = new ArrayList<>();
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (categoryid != null) {
                ps.setInt(paramIndex++, categoryid);
            }
            if (brandid != null) {
                ps.setInt(paramIndex++, brandid);
            }
            ps.setInt(paramIndex, (index - 1) * 12);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("productName");
                String description = rs.getString("Description");
                int categoryId = rs.getInt("CategoryID");
                double price = rs.getDouble("Price");
                int stockQuantity = rs.getInt("Stock_Quantity");
                String imageUrl = rs.getString("image");
                int brandId = rs.getInt("BrandID");
                products.add(new Product(id, name, description, categoryId, price, stockQuantity, brandId, imageUrl));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Category> selectAllCategorys() {
        List<Category> categorys = new ArrayList<>();
        try ( Connection connection = getConnection();  PreparedStatement prepareStatement = connection.prepareStatement(SELECT_ALL_CATEGORYS)) {
            ResultSet rs = prepareStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("categoryID");
                String name = rs.getString("categoryName");
                categorys.add(new Category(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorys;
    }

    public List<Brand> selectAllBrand() {
        List<Brand> brands = new ArrayList<>();
        try ( Connection connection = getConnection();  PreparedStatement prepareStatement = connection.prepareStatement(SELECT_ALL_BRANDS)) {
            ResultSet rs = prepareStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("BrandID");
                String name = rs.getString("brandName");
                brands.add(new Brand(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brands;
    }

    public Brand getBrandById(int brandId) {
        Brand brand = null;
        String sql = "SELECT * FROM Brand WHERE BrandID = ?";
        try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, brandId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                brand = new Brand(rs.getInt("BrandID"), rs.getString("brandName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brand;
    }

    public List<Product> selectProductByName(String nameInput) {
        List<Product> products = new ArrayList<>();
        try ( Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS_BY_NAME)) {
            preparedStatement.setString(1, "%" + nameInput + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("productName");
                String description = rs.getString("Description");
                int categoryId = rs.getInt("CategoryID");
                double price = rs.getDouble("Price");
                int stockQuantity = rs.getInt("Stock_Quantity");
                String imageUrl = rs.getString("image");
                int brandId = rs.getInt("BrandID");
                products.add(new Product(id, name, description, categoryId, price, stockQuantity, brandId, imageUrl));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductBYID(String id) {
        String sql = "SELECT * FROM Product WHERE ProductID = ?";
        try ( Connection connection = getConnection();  PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, id);
            ResultSet pr = prepareStatement.executeQuery();
            if (pr.next()) {
                return new Product(pr.getInt("productID"),
                        pr.getString("productName"),
                        pr.getString("description"),
                        pr.getInt("categoryID"),
                        pr.getDouble("price"),
                        pr.getInt("stock_Quantity"),
                        pr.getInt("brandID"),
                        pr.getString("image"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countProduct() {
        String sql = "SELECT COUNT(*) FROM Product";
        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Product> selectProductsByBrand(int brandId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE BrandID = ?";

        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, brandId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("productName");
                String description = rs.getString("Description");
                int categoryId = rs.getInt("CategoryID");
                double price = rs.getDouble("Price");
                int stockQuantity = rs.getInt("Stock_Quantity");
                String imageUrl = rs.getString("image");
                int brandID = rs.getInt("BrandID");
                products.add(new Product(id, name, description, categoryId, price, stockQuantity, brandId, imageUrl));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    
//    private List<CartItem> getCartItems(int cartId) {
//        List<CartItem> cartItems = new ArrayList<>();
//        String query = "SELECT * FROM CartItem WHERE cartID = ?";
//
//        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setInt(1, cartId);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int cartID = rs.getInt("cartID");
//                int productID = rs.getInt("productID");
//                int stock_Quantity = rs.getInt("stock_Quantity");
//                double totalPrice = rs.getDouble("totalPrice");
//                // Giả định bạn có phương thức lấy thông tin sản phẩm theo ID
//                Product product = getProductBYID(user); // Cần phương thức này
//                cartItems.add(new CartItem(cartID, productID, stock_Quantity, totalPrice));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return cartItems;
//    }
    public int countProductByCategoryId(Integer categoryId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Product WHERE CategoryID = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countProductByFilters(Integer categoryId, Integer brandId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Product ");
        boolean hasWhereClause = false;

        if (categoryId != null) {
            sql.append("WHERE CategoryID = ? ");
            hasWhereClause = true;
        }

        if (brandId != null) {
            if (hasWhereClause) {
                sql.append("AND BrandID = ? ");
            } else {
                sql.append("WHERE BrandID = ? ");
            }
        }

        try ( Connection connection = getConnection();  PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (categoryId != null) {
                ps.setInt(paramIndex++, categoryId);
            }
            if (brandId != null) {
                ps.setInt(paramIndex++, brandId);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
