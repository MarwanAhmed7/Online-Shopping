package com.example.e_commerce.Model;

import java.util.Date;

public class Order  {

    private int orderId,custId;
    private String orderAddress,phone,feedback,rate;
    private static String orderDate;



    public Order(int custId, String orderAddress, String orderDate) {
        this.custId = custId;
        this.orderAddress = orderAddress;
        this.orderDate = orderDate;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public static void setOrderAddress(String orderAddress) {
        orderAddress = orderAddress;
    }

    public static String getOrderDate() {
        return orderDate;
    }

    public static void setOrderDate(String orderDate) {
        orderDate = orderDate;
    }

    public String getPhone() { return phone; }

    public static void setPhone(String phone) { phone = phone; }

    public String getFeedback() { return feedback; }

    public static void setFeedback(String feedback) { feedback = feedback; }

    public String getRate() { return rate; }

    public static void setRate(String rate) { rate = rate; }

}
