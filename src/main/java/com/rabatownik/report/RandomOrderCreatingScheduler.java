package com.rabatownik.report;

import com.rabatownik.order.OrderService;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@Profile("random-order")
class RandomOrderCreatingScheduler {

    private final OrderService orderService;

    public RandomOrderCreatingScheduler(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(fixedRateString = "${scheduling.order.creation.rate:5000}")
    public void scheduleRandomOrderCreation() {
        var randomOrderCode = UUID.randomUUID().toString();
        var randomTotalPrice = BigDecimal.valueOf(Math.random() * 1000);

        orderService.addOrder(randomOrderCode, randomTotalPrice);
    }
}