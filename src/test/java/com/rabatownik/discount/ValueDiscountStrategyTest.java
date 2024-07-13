package com.rabatownik.discount;

import com.rabatownik.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ValueDiscountStrategyTest {

    private final ValueDiscountStrategy valueDiscountStrategy = new ValueDiscountStrategy(BigDecimal.valueOf(10));

    @Test
    void calculateDiscountShouldSubtractDiscountValueFromPrice() {
        var price = new Money(BigDecimal.valueOf(100));

        var result = valueDiscountStrategy.calculateDiscount(price);

        assertThat(result).isEqualTo(new Money(new BigDecimal("90.00")));
    }

    @Test
    void calculateDiscountShouldReturnZeroWhenDiscountExceedsPrice() {
        var price = new Money(BigDecimal.valueOf(5));

        var result = valueDiscountStrategy.calculateDiscount(price);

        assertThat(result).isEqualTo(Money.ZERO);
    }

    @Test
    void calculateDiscountShouldHandleExactDiscountValue() {
        var price = new Money(BigDecimal.valueOf(10));

        var result = valueDiscountStrategy.calculateDiscount(price);

        assertThat(result).isEqualTo(Money.ZERO);
    }
}