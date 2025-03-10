/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Customer;

/**
 *
 * @author Nam
 */
public class CustomerDAO extends DAO {

    public Customer login(String userName, String password) throws SQLException {
        Customer customer = null;
        String sql = "SELECT * FROM Customer WHERE userName = ? AND password = ?";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userName);
            ps.setString(2, hashPasswordMD5(password));

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setCustomerID(rs.getInt("customerID"));
                    customer.setFullName(rs.getString("fullName"));
                    customer.setUserName(rs.getString("userName"));
                    customer.setPassword(rs.getString("password"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setStatus(rs.getBoolean("status"));

                    // Nếu tài khoản bị khóa (status = false), không trả về đối tượng Customer
                    if (!customer.getStatus()) {
                        return null;
                    }
                }
            }
        }

        return customer;
    }

    public boolean register(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer (fullName, email, phone, userName, password, registrationDate, status) VALUES (?, ?, ?, ?, ?, GETDATE(), ?)";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getUserName());
            ps.setString(5, hashPasswordMD5(customer.getPassword())); // Băm mật khẩu
            ps.setBoolean(6, true); // Mặc định tài khoản mới được kích hoạt

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean updateCustomerInfo(int customerID, String fullName, String email, String phone) {
        try {
            if (checkEmailExistsForUpdate(customerID, email)) {
                return false; // Email is already in use
            }
            if (checkPhoneExistsForUpdate(customerID, phone)) {
                return false; // Phone number is already in use
            }

            // Proceed to update customer information
            String sql = "UPDATE customer SET fullName = ?, email = ?, phone = ? WHERE customerID = ?";
            try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, fullName);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setInt(4, customerID);

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hàm băm mật khẩu bằng MD5
    public String hashPasswordMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi mã hóa MD5", e);
        }
    }

    public boolean checkUserExists(String userName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Customer WHERE userName = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean checkEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Customer WHERE email = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean checkPhoneExists(String phone) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Customer WHERE phone = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private boolean checkEmailExistsForUpdate(int customerID, String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Customer WHERE email = ? AND customerID != ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setInt(2, customerID);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private boolean checkPhoneExistsForUpdate(int customerID, String phone) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Customer WHERE phone = ? AND customerID != ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ps.setInt(2, customerID);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean changePassword(int customerID, String oldPassword, String newPassword) throws SQLException {
        String sql = "SELECT password FROM Customer WHERE customerID = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (!storedPassword.equals(oldPassword)) {
                    return false; // Sai mật khẩu cũ
                }
            } else {
                return false; // Không tìm thấy người dùng
            }
        }

        // Nếu mật khẩu đúng, cập nhật mật khẩu mới
        String updateSql = "UPDATE Customer SET password = ? WHERE customerID = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(updateSql)) {
            ps.setString(1, hashPasswordMD5(newPassword)); // Băm mật khẩu mới trước khi lưu
            ps.setInt(2, customerID);
            return ps.executeUpdate() > 0;
        }
    }

}
