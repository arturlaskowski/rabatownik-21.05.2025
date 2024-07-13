package com.rabatownik.discount;

import com.rabatownik.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class NoDiscountStrategyTest {

    @Test
    void calculateDiscountShouldReturnOriginalPrice() {
        var noDiscountStrategy = new NoDiscountStrategy();
        var originalPrice = new Money(BigDecimal.valueOf(100));

        var result = noDiscountStrategy.calculateDiscount(originalPrice);

        assertThat(result).isEqualTo(originalPrice);
        assertThat(result).isSameAs(originalPrice);
    }
}