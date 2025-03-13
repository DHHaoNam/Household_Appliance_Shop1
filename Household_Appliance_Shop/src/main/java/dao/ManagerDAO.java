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
import model.Manager;

/**
 *
 * @author Nam
 */
public class ManagerDAO extends DAO {
    
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

    public Manager login(String userName, String password) throws SQLException {
        String query = "SELECT * FROM Manager WHERE userName = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userName);
            stmt.setString(2, hashPasswordMD5(password));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Manager(
                    rs.getInt("managerID"),
                    rs.getString("fullName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("userName"),
                    rs.getString("password"),
                    rs.getDate("registrationDate").toLocalDate(),
                    rs.getBoolean("status"),
                    rs.getInt("roleID")
                );
            }
        }
        return null;
    }
    public boolean changePassword(int managerID, String oldPassword, String newPassword) throws SQLException {
        String sql = "SELECT password FROM Manager WHERE managerID = ?";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String hashedOldPassword = hashPasswordMD5(oldPassword); // Băm mật khẩu cũ trước khi so sánh

                if (!storedPassword.equals(hashedOldPassword)) {
                    return false; // Sai mật khẩu cũ
                }
            } else {
                return false; // Không tìm thấy người dùng
            }
        }

        // Nếu mật khẩu đúng, cập nhật mật khẩu mới
        String updateSql = "UPDATE Manager SET password = ? WHERE managerID = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(updateSql)) {
            ps.setString(1, hashPasswordMD5(newPassword)); // Băm mật khẩu mới trước khi lưu
            ps.setInt(2, managerID);
            return ps.executeUpdate() > 0;
        }
    }
}
