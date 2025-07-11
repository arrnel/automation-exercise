package com.automationexercise.tests.page._component.filter;

import com.microsoft.playwright.Locator;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class SearchFilterLocator {

    private final Locator self;

    SearchFilterLocator(Locator self) {
        this.self = self;
    }

    Locator search() {
        return self.locator("#search_product");
    }

    Locator submit() {
        return self.locator("#submit_search");
    }

}
