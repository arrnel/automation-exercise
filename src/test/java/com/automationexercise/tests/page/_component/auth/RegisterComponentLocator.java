package com.automationexercise.tests.page._component.auth;

import com.microsoft.playwright.Locator;

public class RegisterComponentLocator {

    private final Locator self;

    RegisterComponentLocator(Locator self) {
        this.self = self;
    }

    Locator name() {
        return self.locator("[data-qa=signup-name]");
    }

    Locator email() {
        return self.locator("[data-qa=signup-email]");
    }

    Locator submit() {
        return self.locator("[data-qa=signup-button]");
    }

}
