package com.automationexercise.tests.page._component.products;

import com.microsoft.playwright.Locator;

public class ProductListComponentLocator {

    private final Locator self;

    public ProductListComponentLocator(Locator self) {
        this.self = self;
    }

    public Locator title() {
        return self.locator("h2");
    }

    public Locator products() {
        return self.locator(".product-image-wrapper");
    }

    public Locator productWrapper(String productTitle) {
        return self.locator("//div[@class='product-image-wrapper' and .//p[text()='%s']]".formatted(productTitle));
    }

    public Locator openProduct(String productTitle) {
        return productWrapper(productTitle).locator(".choose a");
    }

    public Locator productContent(String productTitle) {
        return productWrapper(productTitle).locator(".productinfo");
    }

    public Locator productImage(String productTitle) {
        return productContent(productTitle).locator("img");
    }

    public Locator productPrice(String productTitle) {
        return productContent(productTitle).locator("h2");
    }

    public Locator addToCart(String productTitle) {
        return productContent(productTitle).locator(".add-to-cart");
    }

}
