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
}
