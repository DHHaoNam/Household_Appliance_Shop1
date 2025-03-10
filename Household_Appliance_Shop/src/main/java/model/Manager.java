/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author HP
 */
public class Manager {

    private int managerID;
    private String fullName;
    private String email;
    private String phone;
    private String userName;
    private String password;
    private Date registrationDate;
    private boolean status;
    private int roleID;

    public Manager() {
    }

    public Manager(int managerID, String fullName, String email, String phone, String userName, String password, Date registrationDate, boolean status, int roleID) {
        this.managerID = managerID;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
        this.registrationDate = registrationDate;
        this.status = status;
        this.roleID = roleID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
    
    
    
}
