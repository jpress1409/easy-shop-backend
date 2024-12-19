package org.yearup.models;

import org.yearup.data.mysql.interfaces.ProductDao;

import java.math.BigDecimal;

public class LineItem {

    private int lineItemId;
    private int orderId;
    private int productId;
    private BigDecimal salesPrice;
    private int quantity;
    private BigDecimal discount;
    private ProductDao productDao;
    private BigDecimal productPrice;

    public LineItem(int orderId, int productId, BigDecimal salesPrice, int quantity, BigDecimal discount) {
        this.lineItemId = lineItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.salesPrice = salesPrice;
        this.quantity = quantity;
        this.discount = discount;
    }

    public int getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(int lineItemId) {
        this.lineItemId = lineItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public BigDecimal getSalesPrice() {
        return  getSalesPrice();
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    public BigDecimal getProductPrice(){
        return productDao.getById(getProductId()).getPrice();
    }
}
