package com.automationexercise.tests.page._component._type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartListType {
    CART_PRODUCTS("Cart products"),
    CHECKOUT_PRODUCTS("Checkout products");
    private final String componentTitle;
}
