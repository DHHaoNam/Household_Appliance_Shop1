/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author TRUNG NHAN
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class ProductDAO extends DAO {

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO Product (productName, description, categoryID, price, stock_Quantity, brandID, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM Product WHERE productID = ?";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM Product";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM Product WHERE productID = ?";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE Product SET productName=?, description=?, categoryID=?, price=?, stock_Quantity=?, brandID=?, image=? WHERE productID=?";
    private static final String SELECT_PRODUCT_BY_CATEGORYID = "SELECT * FROM Product WHERE categoryID = ?";
    private static final String SEARCH_PRODUCT = "SELECT * FROM Product WHERE productName LIKE ?";

    // 1. Thêm sản phẩm
    public void insertProduct(Product product) throws SQLException {
        try ( Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getCategoryID());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getStock_Quantity());
            preparedStatement.setInt(6, product.getBrandID());
            preparedStatement.setString(7, product.getImage());
            preparedStatement.executeUpdate();
        }
    }

    // 2. Lấy sản phẩm theo ID
    public Product selectProduct(int productID) {
        Product product = null;
        try ( Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, productID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                product = new Product(
                        rs.getInt("productID"),
                        rs.getString("productName"),
                        rs.getString("description"),
                        rs.getInt("categoryID"),
                        rs.getDouble("price"),
                        rs.getInt("stock_Quantity"),
                        rs.getInt("brandID"),
                        rs.getString("image")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // 3. Lấy tất cả sản phẩm
    public List<Product> selectAllProducts() {
        List<Product> products = new ArrayList<>();
        try (
                 Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("productID"),
                        rs.getString("productName"),
                        rs.getString("description"),
                        rs.getInt("categoryID"),
                        rs.getDouble("price"),
                        rs.getInt("stock_Quantity"),
                        rs.getInt("brandID"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // 4. Cập nhật sản phẩm
    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdated;
        try ( Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getCategoryID());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getStock_Quantity());
            preparedStatement.setInt(6, product.getBrandID());
            preparedStatement.setString(7, product.getImage());
            preparedStatement.setInt(8, product.getProductID());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // 5. Xóa sản phẩm
    public boolean deleteProduct(int id) {
    boolean rowDeleted = false;
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
        
        preparedStatement.setInt(1, id);
        rowDeleted = preparedStatement.executeUpdate() > 0; // Kiểm tra số dòng bị ảnh hưởng
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return rowDeleted;
}


    //6. Lấy sản phẩm theo categoryID
    public List<Product> selectProductBycategory(int categoryID) {
        List<Product> list = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_CATEGORYID);
            preparedStatement.setInt(1, categoryID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("productID"),
                        rs.getString("productName"),
                        rs.getString("description"),
                        rs.getInt("categoryID"),
                        rs.getDouble("price"),
                        rs.getInt("stock_Quantity"),
                        rs.getInt("brandID"),
                        rs.getString("image")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //7. Seach product by name
    public List<Product> searchProduct(String name) {
        List<Product> list = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_PRODUCT);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("productID"),
                        rs.getString("productName"),
                        rs.getString("description"),
                        rs.getInt("categoryID"),
                        rs.getDouble("price"),
                        rs.getInt("stock_Quantity"),
                        rs.getInt("brandID"),
                        rs.getString("image")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> filterProductsByPrice(Double minPrice, Double maxPrice) throws SQLException {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE 1=1";

        if (minPrice != null) {
            sql += " AND price >= ?";
        }
        if (maxPrice != null) {
            sql += " AND price <= ?";
        }

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            if (minPrice != null) {
                ps.setDouble(index++, minPrice);
            }
            if (maxPrice != null) {
                ps.setDouble(index++, maxPrice);
            }

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
                productList.add(product);
            }
        }
        return productList;
    }

}
