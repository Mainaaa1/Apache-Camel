package com.example.camel.model;

public class Order {

    private Long orderId;
    private String customerName;
    private Double amount;

    public Order() {}

    public Order(Long orderId, String customerName, Double amount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.amount = amount;
    }

    public Long getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public Double getAmount() { return amount; }

    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setAmount(Double amount) { this.amount = amount; }
}
