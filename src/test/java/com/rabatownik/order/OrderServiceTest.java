package com.rabatownik.order;

import com.rabatownik.discount.DiscountStrategy;
import com.rabatownik.discount.NoDiscountStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {

    private final OrderRepository orderRepository = new OrderRepository();
    private final DiscountStrategy noDiscountStrategy = new NoDiscountStrategy();
    private final OrderService orderServiceNoDiscount = new OrderService(orderRepository, noDiscountStrategy);

    @Test
    void shouldSaveOrderWithCorrectTotalPrice() {
        var orderCode = "ORD100";
        var totalPrice = new BigDecimal("200");

        orderServiceNoDiscount.addOrder(orderCode, totalPrice);

        var resultOrder = orderRepository.getOrder(orderCode);
        assertThat(resultOrder.getPriceAfterDiscount().amount()).isEqualByComparingTo(totalPrice);
    }
}