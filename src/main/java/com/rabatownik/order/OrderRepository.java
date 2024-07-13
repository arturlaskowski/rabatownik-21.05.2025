package com.rabatownik.order;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepository {

    private final Map<String, Order> store = new HashMap<>();

    public void save(Order order) {
        store.put(order.getOrderCode(), order);
    }

    public Order getOrder(String orderId) {
        return store.get(orderId);
    }
}
