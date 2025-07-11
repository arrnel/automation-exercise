package com.automationexercise.tests.page._component.carousel;

import com.microsoft.playwright.Locator;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class CarouselComponentLocator {

    private static final String PRODUCT_CONTAINER_SELECTOR = "//div[contains(@class,'productInfo') and text()='%s']";

    private final Locator self;

    CarouselComponentLocator(Locator self) {
        this.self = self;
    }

    @Nonnull
    Locator carouselInner() {
        return self.locator(".carousel-inner");
    }

    @Nonnull
    Locator activeCarouselSlide() {
        return self.locator(".item.active");
    }

    @Nonnull
    Locator carouselSlides() {
        return self.locator(".item");
    }

    /**
     * Use only for product carousel
     */
    @Nonnull
    Locator carouselProduct(String productTitle) {
        return carouselSlides().locator(PRODUCT_CONTAINER_SELECTOR.formatted(productTitle));
    }

    @Nonnull
    Locator activeCarouselProduct(String productTitle) {
        return activeCarouselSlide().locator(PRODUCT_CONTAINER_SELECTOR.formatted(productTitle));
    }

    @Nonnull
    Locator previous() {
        return self.locator("a[data-slide=prev]");
    }

    @Nonnull
    Locator next() {
        return self.locator("a[data-slide=next]");
    }

}
