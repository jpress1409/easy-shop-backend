package org.yearup.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public class Order {

    private int orderId;
    private int userId;
    private Date orderDate;
    private List<LineItem> items = new ArrayList<>();
    private String address;
    private String city;
    private String state;
    private String zip;
    private BigDecimal shippingAmount;
    private int lineId;

    public Order(int userId, Date orderDate, String address, String city, String state, String zip, BigDecimal shippingAmount) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.shippingAmount = shippingAmount;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public BigDecimal getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(BigDecimal shippingAmount) {
        this.shippingAmount = shippingAmount;
    }
    public void add(LineItem item) {
        items.add(item);
    }
}
