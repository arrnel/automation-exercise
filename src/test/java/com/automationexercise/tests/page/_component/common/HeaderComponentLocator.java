package com.automationexercise.tests.page._component.common;

import com.microsoft.playwright.Locator;

class HeaderComponentLocator {

    private final Locator self;

    HeaderComponentLocator(Locator self) {
        this.self = self;
    }

    Locator logo() {
        return self.locator(".logo");
    }

    Locator navbar() {
        return self.locator(".shop-menu");
    }

    Locator home() {
        return navbar().locator("text=Home");
    }

    Locator cart() {
        return navbar().locator("text=Cart");
    }

    Locator products() {
        return navbar().locator("text=Products");
    }

    Locator signup() {
        return navbar().locator("text=Signup / Login");
    }

    Locator login() {
        return signup();
    }

    Locator logout() {
        return navbar().locator("text=Logout");
    }

    Locator deleteAccount() {
        return navbar().locator("text=Delete Account");
    }

    Locator contactUs() {
        return navbar().locator("text=Contact us");
    }

    Locator loggedInAs() {
        return navbar().locator("text=Logged in as");
    }

}
