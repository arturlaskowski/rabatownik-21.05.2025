package com.rabatownik.order;

import com.rabatownik.Money;

public class Order {

    private String orderCode;
    private Money priceBeforeDiscount;
    private Money priceAfterDiscount;

    public Order(String orderCode, Money priceBeforeDiscount, Money priceAfterDiscount) {
        this.orderCode = orderCode;
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public Money getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public Money getPriceAfterDiscount() {
        return priceAfterDiscount;
    }
}
