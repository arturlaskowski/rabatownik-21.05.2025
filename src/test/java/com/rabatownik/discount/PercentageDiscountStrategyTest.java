package com.rabatownik.discount;

import com.rabatownik.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PercentageDiscountStrategyTest {

    private final PercentageDiscountStrategy percentageDiscountStrategy = new PercentageDiscountStrategy(20);

    @Test
    void calculateDiscountShouldApplyPercentageDiscount() {
        var price = new Money(BigDecimal.valueOf(100));

        var result = percentageDiscountStrategy.calculateDiscount(price);

        assertThat(result).isEqualTo(new Money(new BigDecimal("80.00")));
    }

    @Test
    void calculateDiscountShouldHandleZeroPrice() {
        var price = Money.ZERO;

        var result = percentageDiscountStrategy.calculateDiscount(price);

        assertThat(result).isEqualTo(Money.ZERO);
    }

    @Test
    void cannotSetZeroOrNegativeDiscountPercentage() {
        assertThatThrownBy(() -> new PercentageDiscountStrategy(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Discount percentage must be greater than 0");

        assertThatThrownBy(() -> new PercentageDiscountStrategy(-5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Discount percentage must be greater than 0");
    }
}
