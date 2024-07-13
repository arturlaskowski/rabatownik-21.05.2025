package com.rabatownik.discount;

import com.rabatownik.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "discount.type", havingValue = "percentage")
class PercentageDiscountStrategy implements DiscountStrategy {

    private final int discountPercentage;

    public PercentageDiscountStrategy(@Value("${discount.percentage}") int discountPercentage) {
        if (discountPercentage <= 0) {
            throw new IllegalArgumentException("Discount percentage must be greater than 0");
        }
        this.discountPercentage = discountPercentage;
    }

    public Money calculateDiscount(Money price) {
        return price.applyPercentageDiscount(discountPercentage);
    }
}
