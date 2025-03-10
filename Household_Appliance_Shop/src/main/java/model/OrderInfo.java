/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author HP
 */
public class OrderInfo {
    private int orderID;
    private int customerID;
    private int orderStatus;
    private Date orderDate;
    private int deliveryOptionID;
    private int managerID;
    private int paymentMethodID;
    private BigDecimal totalPrice;
    private String deliveryAddress;

    public OrderInfo() {
    }

    public OrderInfo(int orderID, int customerID, int orderStatus, Date orderDate, int deliveryOptionID, int managerID, int paymentMethodID, BigDecimal totalPrice, String deliveryAddress) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.deliveryOptionID = deliveryOptionID;
        this.managerID = managerID;
        this.paymentMethodID = paymentMethodID;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getDeliveryOptionID() {
        return deliveryOptionID;
    }

    public void setDeliveryOptionID(int deliveryOptionID) {
        this.deliveryOptionID = deliveryOptionID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public int getPaymentMethodID() {
        return paymentMethodID;
    }

    public void setPaymentMethodID(int paymentMethodID) {
        this.paymentMethodID = paymentMethodID;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    
    
    
}
