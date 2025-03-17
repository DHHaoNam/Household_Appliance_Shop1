/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DeliveryOption;

/**
 *
 * @author HP
 */
public class DeliveryDAO extends DAO {

    public List<DeliveryOption> getAllDeliveryOptions() {
        List<DeliveryOption> deliveryOptions = new ArrayList<>();
        String sql = "SELECT * FROM [DeliveryOption]";

        try ( Connection conn = this.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                deliveryOptions.add(new DeliveryOption(
                        rs.getInt("deliveryOptionID"),
                        rs.getString("optionName"),
                        rs.getString("estimatedTime")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveryOptions;
    }
}
