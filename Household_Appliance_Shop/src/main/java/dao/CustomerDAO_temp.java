/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import model.Customer;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class CustomerDAO_temp extends DAO {

    // Lấy danh sách tất cả khách hàng
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        try ( Connection conn = getConnection();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setFullName(rs.getString("fullName"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setUserName(rs.getString("userName"));
                customer.setPassword(rs.getString("password"));
                customer.setRegistrationDate(rs.getObject("registrationDate", LocalDate.class));
                customer.setStatus(rs.getBoolean("status"));
                list.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm mới khách hàng
    public boolean insertCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (fullName, email, phone, userName, password, registrationDate, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getUserName());
            stmt.setString(5, customer.getPassword());
            stmt.setDate(6, Date.valueOf(customer.getRegistrationDate()));
            stmt.setBoolean(7, customer.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật khách hàng
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE Customer SET fullName=?, email=?, phone=?, userName=?, password=?, registrationDate=?, status=? WHERE CustomerID=?";
        try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getUserName());
            stmt.setString(5, customer.getPassword());
            stmt.setDate(6, Date.valueOf(customer.getRegistrationDate()));
            stmt.setBoolean(7, customer.getStatus());
            stmt.setInt(8, customer.getCustomerID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa khách hàng
    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM Customer WHERE CustomerID = ?";
        try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy khách hàng theo ID
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM Customer WHERE CustomerID = ?";
        try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setCustomerID(rs.getInt("CustomerID"));
                    customer.setFullName(rs.getString("fullName"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setUserName(rs.getString("userName"));
                    customer.setPassword(rs.getString("password"));
                    customer.setRegistrationDate(rs.getObject("registrationDate", LocalDate.class));
                    customer.setStatus(rs.getBoolean("status"));
                    return customer;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy khách hàng
    }

    public List<Customer> searchCustomerByPhone(String keyword) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer WHERE phone LIKE ?";

        try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%"); // Tìm kiếm gần đúng (LIKE '%keyword%')

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setCustomerID(rs.getInt("CustomerID"));
                    customer.setFullName(rs.getString("fullName"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setUserName(rs.getString("userName"));
                    customer.setPassword(rs.getString("password"));
                    customer.setRegistrationDate(rs.getObject("registrationDate", LocalDate.class));
                    customer.setStatus(rs.getBoolean("status"));
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

//6. băm mật khẩu MD5
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

    //6. cap nhat status
    public boolean updateCustomerStatus(int id, boolean status) {
        String sql = "UPDATE Customer SET status = ? WHERE customerID = ?";
        try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, status);
            stmt.setInt(2, id);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
