package com.automationexercise.tests.page._component.products;

import com.microsoft.playwright.Locator;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class CartListComponentLocator {

    private final Locator self;

    CartListComponentLocator(Locator self) {
        this.self = self;
    }

    Locator productsListWrapper() {
        return self.locator("tbody");
    }

    Locator product(String productTitle) {
        return productsListWrapper()
                .locator("//tr[.//h4/a[contains(text(),'%s')]]".formatted(productTitle));
    }

    Locator photo(String productTitle) {
        return product(productTitle).locator(".product_image");
    }

    Locator title(String productTitle) {
        return product(productTitle).locator(".cart_description a");
    }

    Locator category(String productTitle) {
        return product(productTitle).locator(".cart_description p");
    }

    Locator price(String productTitle) {
        return product(productTitle).locator(".cart_price p");
    }

    Locator quantity(String productTitle) {
        return product(productTitle).locator(".cart_quantity button");
    }

    Locator productTotal(String productTitle) {
        return product(productTitle).locator(".cart_total_price");
    }

    Locator cartTotal() {
        return productsListWrapper().locator("tr:not([id*=product])").locator(".cart_total_price");
    }

    Locator deleteProduct(String productTitle) {
        return product(productTitle).locator(".cart_quantity_delete");
    }

}
