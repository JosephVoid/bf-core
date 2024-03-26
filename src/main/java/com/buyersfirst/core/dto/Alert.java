package com.buyersfirst.core.dto;

public class Alert {
    public String phone;
    public String message;

    public Alert(String phone, String message) {
        this.phone = phone;
        this.message = "Your alert are: " + message;
    }
}
