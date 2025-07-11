package com.automationexercise.tests.page._component._type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AddressType {
    DELIVERY("Delivery address"),
    BILLING_ADDRESS("Billing address");
    private final String componentTitle;
}
