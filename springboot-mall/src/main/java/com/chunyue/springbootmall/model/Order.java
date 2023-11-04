package com.chunyue.springbootmall.model;

import java.util.Date;


public class Order {

    private Integer orderId;
    private Integer userId;
    private Integer totalAmount;
    private Date createdDate;
    private Date lastModifiedDay;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDay() {
        return lastModifiedDay;
    }

    public void setLastModifiedDay(Date lastModifiedDay) {
        this.lastModifiedDay = lastModifiedDay;
    }
}
