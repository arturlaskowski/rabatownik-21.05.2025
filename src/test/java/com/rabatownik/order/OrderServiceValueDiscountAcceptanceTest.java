package com.rabatownik.order;

import com.rabatownik.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"discount.type=value", "discount.value=100"})
class OrderServiceValueDiscountAcceptanceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Value("${discount.value}")
    private BigDecimal discountValue;

    @Test
    void createOrderWithValueDiscount() {
        //given
        var orderCode = "ORD-V100";
        var totalPrice = new BigDecimal("300");
        var expectedPriceAfterDiscount = new Money(totalPrice).subtractToZeroMinimum(new Money(discountValue));

        //when
        orderService.addOrder(orderCode, totalPrice);

        //then
        var order = orderRepository.getOrder(orderCode);
        assertThat(order)
                .isNotNull()
                .hasFieldOrPropertyWithValue("orderCode", orderCode)
                .hasFieldOrPropertyWithValue("priceBeforeDiscount", new Money(totalPrice))
                .hasFieldOrPropertyWithValue("priceAfterDiscount", expectedPriceAfterDiscount);
    }
}
