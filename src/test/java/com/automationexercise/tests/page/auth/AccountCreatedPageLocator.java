package com.automationexercise.tests.page.auth;

import com.microsoft.playwright.Locator;

class AccountCreatedPageLocator {

    private final Locator self;

    AccountCreatedPageLocator(Locator self) {
        this.self = self;
    }

    Locator title() {
        return self.locator("[data-qa=account-created]");
    }

    Locator message() {
        return self.locator("//p[1]");
    }

    Locator next() {
        return self.locator("[data-qa=continue-button]");
    }

}
