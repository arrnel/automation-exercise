package com.automationexercise.tests.page._component.common;

import com.microsoft.playwright.Locator;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BreadCrumbComponentLocator {

    private final Locator self;

    BreadCrumbComponentLocator(Locator self) {
        this.self = self;
    }

    Locator breadCrumbs() {
        return self.locator("li");
    }

    Locator home() {
        return self.locator("//a[text()='Home']");
    }

    Locator breadCrumb(String title) {
        return self.locator("//li[text()='%s']".formatted(title));
    }

    Locator activeBreadCrumb() {
        return self.locator(".active");
    }

}
