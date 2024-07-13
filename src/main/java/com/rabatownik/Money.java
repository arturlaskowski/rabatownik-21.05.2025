package com.rabatownik;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = setScale(amount);
    }

    public Money add(Money money) {
        return new Money(this.amount.add(money.amount));
    }

    public Money subtract(Money money) {
        return new Money(this.amount.subtract(money.amount));
    }

    public Money subtractToZeroMinimum(Money money) {
        BigDecimal result = this.amount.subtract(money.amount);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            return Money.ZERO;
        }
        return new Money(result);
    }

    public Money applyPercentageDiscount(int discountPercentage) {
        if (discountPercentage < 1 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 1 and 100");
        }

        var discountMultiplier = BigDecimal.valueOf(discountPercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);

        var discountAmount = new Money(this.amount.multiply(discountMultiplier));
        return this.subtract(discountAmount);
    }

    public boolean isPositiveOrZero() {
        return this.amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    private static BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
