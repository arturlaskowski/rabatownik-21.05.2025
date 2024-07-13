package com.rabatownik.discount;

import com.rabatownik.Money;

public interface DiscountStrategy {

    Money calculateDiscount(Money price);
}

