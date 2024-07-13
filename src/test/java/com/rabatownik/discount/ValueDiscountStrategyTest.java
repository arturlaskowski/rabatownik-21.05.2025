package com.rabatownik.discount;

import com.rabatownik.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void cannotSetNegativeDiscountValue() {
        var negativeDiscountValue = new BigDecimal("-5.00");

        assertThatThrownBy(() -> new ValueDiscountStrategy(negativeDiscountValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Discount value cannot be negative");
    }
}