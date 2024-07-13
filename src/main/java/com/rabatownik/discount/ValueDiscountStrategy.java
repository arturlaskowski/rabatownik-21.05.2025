package com.rabatownik.discount;

import com.rabatownik.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConditionalOnProperty(name = "discount.type", havingValue = "value")
class ValueDiscountStrategy implements DiscountStrategy {

    private final Money discountValue;

    public ValueDiscountStrategy(@Value("${discount.value}") BigDecimal discountValue) {
        this.discountValue = new Money(discountValue);
    }

    public Money calculateDiscount(Money price) {
        return price.subtractToZeroMinimum(discountValue);
    }
}
