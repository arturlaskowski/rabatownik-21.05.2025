package com.rabatownik.discount;

import com.rabatownik.Money;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "discount.type", havingValue = "none", matchIfMissing = true)
public class NoDiscountStrategy implements DiscountStrategy {

    public Money calculateDiscount(Money price) {
        return price;
    }
}
