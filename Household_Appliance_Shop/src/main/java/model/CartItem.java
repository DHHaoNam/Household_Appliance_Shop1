/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class CartItem {

    private int cartID;
    private int productID;
    private int quantity;
    private double totalPrice;
    private Product product;

    public CartItem() {
    }

    public CartItem(int cartID, int productID, int quantity, double totalPrice, Product product) {
        this.cartID = cartID;
        this.productID = productID;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartItem(int cartID, int productID, int quantity, double totalPrice) {
        this.cartID = cartID;
        this.productID = productID;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
