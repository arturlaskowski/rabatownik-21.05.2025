package com.rabatownik;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MoneyTest {

    @Test
    void constructorShouldThrowExceptionWhenAmountIsNull() {
        assertThatThrownBy(() -> new Money(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount cannot be null");
    }

    @Test
    void constructorShouldThrowExceptionWhenAmountIsNegative() {
        assertThatThrownBy(() -> new Money(new BigDecimal("-1")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount cannot be negative");
    }

    @Test
    void constructorShouldSetScaleForAmount() {
        var initialAmount = new BigDecimal("10.1234");
        var money = new Money(initialAmount);

        assertThat(money.amount().scale()).isEqualTo(2);
        assertThat(money.amount()).isEqualByComparingTo("10.12");
    }

    @Test
    void shouldCorrectlyAddTwoMoneyAmounts() {
        var money1 = new Money(BigDecimal.valueOf(10));
        var money2 = new Money(BigDecimal.valueOf(15));

        var result = money1.add(money2);

        assertThat(result.amount()).isEqualByComparingTo("25.00");
    }

    @Test
    void shouldCorrectlySubtractTwoMoneyAmounts() {
        var money1 = new Money(BigDecimal.valueOf(20));
        var money2 = new Money(BigDecimal.valueOf(5));

        var result = money1.subtract(money2);

        assertThat(result.amount()).isEqualByComparingTo("15.00");
    }

    @ParameterizedTest
    @CsvSource({
            "20, 10, 10.00", // Wynik dodatni
            "10, 10, 0.00",  // Wynik równy zero
            "5, 10, 0.00"    // Wynik ujemny, powinien zwrócić zero
    })
    void shouldSubtractToZeroMinimumCorrectly(BigDecimal amount1, BigDecimal amount2, String expected) {
        var money1 = new Money(amount1);
        var money2 = new Money(amount2);

        var result = money1.subtractToZeroMinimum(money2);

        assertThat(result.amount()).isEqualByComparingTo(expected);
    }

    @Test
    void applyPercentageDiscountShouldCorrectlyApplyDiscount() {
        var money = new Money(BigDecimal.valueOf(100));

        var result = money.applyPercentageDiscount(25);

        assertThat(result.amount()).isEqualByComparingTo("75.00");
    }

    @Test
    void applyPercentageDiscountShouldThrowExceptionForInvalidPercentage() {
        var money = new Money(BigDecimal.valueOf(100));

        assertThatThrownBy(() -> money.applyPercentageDiscount(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Discount percentage must be between 1 and 100");

        assertThatThrownBy(() -> money.applyPercentageDiscount(101))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Discount percentage must be between 1 and 100");
    }

    @Test
    void isPositiveOrZeroShouldReturnTrueForPositiveAmount() {
        var money = new Money(BigDecimal.valueOf(1));

        assertThat(money.isPositiveOrZero()).isTrue();
    }

    @Test
    void isPositiveOrZeroShouldReturnTrueForZeroAmount() {
        var money = Money.ZERO;

        assertThat(money.isPositiveOrZero()).isTrue();
    }
}