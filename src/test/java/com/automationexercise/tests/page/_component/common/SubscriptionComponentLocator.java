package com.automationexercise.tests.page._component.common;

import com.microsoft.playwright.Locator;

class SubscriptionComponentLocator {

    private final Locator self;

    SubscriptionComponentLocator(Locator self) {
        this.self = self;
    }

    Locator email() {
        return self.locator("[type=email]");
    }

    Locator submit() {
        return self.locator("button");
    }

    Locator statusMessageWrapper() {
        return self.locator("//ancestor::footer").locator("#success-subscribe");
    }

    Locator statusMessage() {
        return statusMessageWrapper().locator("div");
    }

    Locator description() {
        return self.locator("p");
    }

}
