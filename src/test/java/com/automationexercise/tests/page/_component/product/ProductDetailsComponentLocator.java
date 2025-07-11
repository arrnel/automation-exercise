package com.automationexercise.tests.page._component.product;

import com.microsoft.playwright.Locator;

class ProductDetailsComponentLocator {

    private final Locator self;

    ProductDetailsComponentLocator(Locator self) {
        this.self = self;
    }

    Locator photo() {
        return self.locator(".view-product img");
    }

    private Locator productInfo() {
        return self.locator(".product-information");
    }

    Locator newArrival() {
        return productInfo().locator(".newarrival");
    }

    Locator title() {
        return productInfo().locator("h2");
    }

    Locator category() {
        return productInfo().locator("//p[not(./b)]");
    }

    Locator rating() {
        return productInfo().locator("[src*=rating]");
    }

    private Locator productInfoContent() {
        return productInfo().locator("span");
    }

    Locator price() {
        return productInfoContent().locator("span");
    }

    Locator quantity() {
        return productInfoContent().locator("input[name=quantity]");
    }

    Locator addToCart() {
        return productInfoContent().locator("button.cart");
    }

    private Locator characteristic(String title) {
        return productInfo().locator("//p[./b[text()='%s:']]".formatted(title));
    }

    Locator availability() {
        return characteristic("Availability");
    }

    Locator condition() {
        return characteristic("Condition");
    }

    Locator brand() {
        return characteristic("Brand");
    }

}
