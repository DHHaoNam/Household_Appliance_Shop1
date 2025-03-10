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
import model.Category;

public class CategoryDAO extends DAO {

    private static final String INSERT_CATEGORY_SQL = "INSERT INTO Category (categoryName) VALUES (?)";
    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM Category WHERE categoryID = ?";
    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM Category";
    private static final String DELETE_CATEGORY_SQL = "DELETE FROM Category WHERE categoryID = ?";
    private static final String UPDATE_CATEGORY_SQL = "UPDATE Category SET categoryName = ? WHERE categoryID = ?";

    // 1. Thêm danh mục
    public void insertCategory(Category category) throws SQLException {
        try ( Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY_SQL)) {
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Lấy danh mục theo ID
    public Category selectCategory(int categoryID) {
        Category category = null;
        try ( Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY_BY_ID)) {
            preparedStatement.setInt(1, categoryID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                category = new Category(
                        rs.getInt("categoryID"),
                        rs.getString("categoryName")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    // 3. Lấy tất cả danh mục
    public List<Category> selectAllCategories() {
        List<Category> categories = new ArrayList<>();
        try ( Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORIES)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("categoryID"),
                        rs.getString("categoryName")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // 4. Cập nhật danh mục
    public boolean updateCategory(Category category) throws SQLException {
        boolean rowUpdated;
        try ( Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CATEGORY_SQL)) {
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setInt(2, category.getCategoryID());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // 5. Xóa danh mục
    public boolean deleteCategory(int categoryID) throws SQLException {
        boolean rowDeleted;
        try ( Connection connection = getConnection();  PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CATEGORY_SQL)) {
            preparedStatement.setInt(1, categoryID);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}
