package com.rabatownik.order;

import com.rabatownik.Money;
import com.rabatownik.discount.DiscountStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderRepository orderRepository;
    private final DiscountStrategy discountStrategy;

    public OrderService(OrderRepository orderRepository, DiscountStrategy discountStrategy) {
        this.orderRepository = orderRepository;
        this.discountStrategy = discountStrategy;
    }

    public void addOrder(String orderCode, BigDecimal totalPrice) {
        var totalOrderPrice = new Money(totalPrice);
        var priceWithDiscount = discountStrategy.calculateDiscount(totalOrderPrice);

        logger.info("Zamówionko o kodzie: {} miało kwotę przed zniżką: {}zł, a po zniżce ma: {}zł",
                orderCode, totalOrderPrice.amount(), priceWithDiscount.amount());

        var order = new Order(orderCode, totalOrderPrice, priceWithDiscount);
        orderRepository.save(order);
    }
}
