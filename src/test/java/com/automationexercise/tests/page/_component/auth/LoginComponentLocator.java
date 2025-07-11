package com.automationexercise.tests.page._component.auth;

import com.microsoft.playwright.Locator;

class LoginComponentLocator {

    private final Locator self;

    LoginComponentLocator(Locator self) {
        this.self = self;
    }

    Locator email() {
        return self.locator("[data-qa=login-email]");
    }

    Locator password() {
        return self.locator("[data-qa=login-password]");
    }

    Locator submit() {
        return self.locator("[data-qa=login-button]");
    }

    Locator error() {
        return self.locator("p");
    }

}
