package com.automationexercise.tests.page._component.product;

import com.microsoft.playwright.Locator;

class ReviewFormLocator {

    private final Locator self;

    ReviewFormLocator(Locator self) {
        this.self = self;
    }

    private Locator form() {
        return self.locator("#review-form");
    }

    Locator name() {
        return form().locator("#name");
    }

    Locator email() {
        return form().locator("#email");
    }

    Locator review() {
        return form().locator("#review");
    }

    Locator reviewStatusWrapper() {
        return self.locator("#review-section .alert");
    }

    Locator reviewStatusMessage() {
        return self.locator("#review-section .alert span");
    }

    Locator submit() {
        return form().locator("button[type=submit]");
    }

}
