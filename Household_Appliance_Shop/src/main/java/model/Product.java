package model;

public class Product {

    private int productID;
    private String productName;
    private String description;
    private int categoryID;
    private double price;
    private int stock_Quantity;
    private int brandID;
    private String image;

    public Product() {
    }

    public Product(String productName, String description, int categoryID, double price, int stock_Quantity, int brandID, String image) {
        this.productName = productName;
        this.description = description;
        this.categoryID = categoryID;
        this.price = price;
        this.stock_Quantity = stock_Quantity;
        this.brandID = brandID;
        this.image = image;
    }

    public Product(int productID, String productName, String description, int categoryID, double price, int stock_Quantity, int brandID, String image) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.categoryID = categoryID;
        this.price = price;
        this.stock_Quantity = stock_Quantity;
        this.brandID = brandID;
        this.image = image;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock_Quantity() {
        return stock_Quantity;
    }

    public void setStock_Quantity(int stock_Quantity) {
        this.stock_Quantity = stock_Quantity;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
