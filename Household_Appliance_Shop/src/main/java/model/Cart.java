/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
import java.sql.Date;
import java.util.List;

public class Cart {

    private int cartID;
    private int customerID;
    private Date CreateAt;
    private List<CartItem> items;

    public Cart() {
    }

    public Cart(int cartID, int customerID, Date CreateAt, List<CartItem> items) {
        this.cartID = cartID;
        this.customerID = customerID;
        this.CreateAt = CreateAt;
        this.items = items;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Date getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(Date CreateAt) {
        this.CreateAt = CreateAt;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

}
