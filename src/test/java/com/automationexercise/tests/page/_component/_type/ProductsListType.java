package com.automationexercise.tests.page._component._type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductsListType {
    MAIN_PRODUCTS("Main products list"),
    ALL_PRODUCTS("All products list"),
    FILTERED_PRODUCTS("Filtered products list");
    private final String componentTitle;
}
