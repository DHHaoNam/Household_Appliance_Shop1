/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
public class DeliveryOption {

    private int deliveryOptionID;
    private String optionName;
    private String estimatedTime;

    public DeliveryOption() {
    }

    public DeliveryOption(int deliveryOptionID, String optionName, String estimatedTime) {
        this.deliveryOptionID = deliveryOptionID;
        this.optionName = optionName;
        this.estimatedTime = estimatedTime;
    }

    public int getDeliveryOptionID() {
        return deliveryOptionID;
    }

    public void setDeliveryOptionID(int deliveryOptionID) {
        this.deliveryOptionID = deliveryOptionID;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
    
    

}
