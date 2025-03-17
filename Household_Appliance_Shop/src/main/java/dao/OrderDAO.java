/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.DeliveryOption;
import model.OrderDetail;
import model.OrderInfo;
import model.PaymentMethod;

/**
 *
 * @author HP
 */
public class OrderDAO extends DAO {

    public int createOrder(OrderInfo order) {
        String sql = "INSERT INTO OrderInfo (customerID, orderStatus, orderDate, deliveryOptionID, managerID, paymentMethodID, totalPrice, deliveryAddress) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int orderID = -1;

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getCustomerID());
            ps.setInt(2, order.getOrderStatus());
            ps.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            ps.setInt(4, order.getDeliveryOptionID());
            ps.setInt(5, order.getManagerID());
            ps.setInt(6, order.getPaymentMethodID());
            ps.setDouble(7, order.getTotalPrice());
            ps.setString(8, order.getDeliveryAddress());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderID = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderID;
    }

    public boolean addOrderItems(int orderID, List<OrderDetail> items) {
        String checkSql = "SELECT COUNT(*) FROM OrderDetail WHERE orderID = ? AND productID = ?";
        String updateSql = "UPDATE OrderDetail SET quantity = quantity + ?, totalPrice = totalPrice + ? WHERE orderID = ? AND productID = ?";
        String insertSql = "INSERT INTO OrderDetail (orderID, productID, quantity, totalPrice) VALUES (?, ?, ?, ?)";

        try ( Connection conn = getConnection()) {
            for (OrderDetail item : items) {
                try ( PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                    checkPs.setInt(1, orderID);
                    checkPs.setInt(2, item.getProductID());
                    ResultSet rs = checkPs.executeQuery();

                    if (rs.next() && rs.getInt(1) > 0) {
                        // Nếu sản phẩm đã có trong đơn hàng → UPDATE
                        try ( PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                            updatePs.setInt(1, item.getQuantity());
                            updatePs.setDouble(2, item.getTotalPrice());
                            updatePs.setInt(3, orderID);
                            updatePs.setInt(4, item.getProductID());
                            updatePs.executeUpdate();
                        }
                    } else {
                        // Nếu sản phẩm chưa có → INSERT mới
                        try ( PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                            insertPs.setInt(1, orderID);
                            insertPs.setInt(2, item.getProductID());
                            insertPs.setInt(3, item.getQuantity());
                            insertPs.setDouble(4, item.getTotalPrice());
                            insertPs.executeUpdate();
                        }
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin đơn hàng theo ID
    public OrderInfo getOrderById(int orderID) {
        String sql = "SELECT * FROM OrderInfo WHERE orderID = ?";
        OrderInfo order = null;

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                order = new OrderInfo();
                order.setOrderID(rs.getInt("orderID"));
                order.setCustomerID(rs.getInt("customerID"));
                order.setOrderStatus(rs.getInt("orderStatus"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setDeliveryOptionID(rs.getInt("deliveryOptionID"));
                order.setManagerID(rs.getInt("managerID"));
                order.setPaymentMethodID(rs.getInt("paymentMethodID"));
                order.setTotalPrice(rs.getDouble("totalPrice"));
                order.setDeliveryAddress(rs.getString("deliveryAddress"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<OrderDetail> getOrderDetails(int orderID) {
        String sql = "SELECT * FROM OrderDetail WHERE orderID = ?";
        List<OrderDetail> orderItems = new ArrayList<>();

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetail item = new OrderDetail(
                        rs.getInt("orderID"),
                        rs.getInt("productID"),
                        rs.getInt("quantity"),
                        rs.getDouble("totalPrice")
                );
                orderItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }
    
      
     

}
