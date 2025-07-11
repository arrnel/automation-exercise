package com.automationexercise.tests.page._component.filter;

import com.microsoft.playwright.Locator;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class BrandFilterLocator {

    private final Locator self;

    BrandFilterLocator(@Nonnull Locator self) {
        this.self = self;
    }

    @Nonnull
    Locator title() {
        return self.locator("h2");
    }

    @Nonnull
    Locator brandsWrapper() {
        return self.locator(".brands-name");
    }

    @Nonnull
    Locator brands() {
        return brandsWrapper().locator("a");
    }

    @Nonnull
    Locator brand(String brand) {
        return brandsWrapper().locator("//a[text()='%s']".formatted(brand));
    }

    @Nonnull
    Locator brandQuantity(String brand) {
        return brand(brand).locator("span");
    }

}
