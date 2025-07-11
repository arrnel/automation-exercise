package com.automationexercise.tests.page.auth;

import com.automationexercise.tests.models.UserTitle;
import com.microsoft.playwright.Locator;

class RegisterPageLocator {

    private final Locator self;

    RegisterPageLocator(Locator self) {
        this.self = self;
    }

    Locator title(UserTitle userTitle) {
        return self.locator("input[type=radio][value=%s]".formatted(userTitle.getValue()));
    }

    Locator name() {
        return self.locator("#name");
    }

    Locator email() {
        return self.locator("#email");
    }

    Locator password() {
        return self.locator("#password");
    }

    Locator birthDay() {
        return self.locator("#days");
    }

    Locator birthMonth() {
        return self.locator("#months");
    }

    Locator birthYear() {
        return self.locator("#years");
    }

    Locator newsletter() {
        return self.locator("#newsletter");
    }

    Locator specialOffers() {
        return self.locator("#optin");
    }

    Locator firstName() {
        return self.locator("#first_name");
    }

    Locator lastName() {
        return self.locator("#last_name");
    }

    Locator company() {
        return self.locator("#company");
    }

    Locator address1() {
        return self.locator("#address1");
    }

    Locator address2() {
        return self.locator("#address2");
    }

    Locator country() {
        return self.locator("#country");
    }

    Locator state() {
        return self.locator("#state");
    }

    Locator city() {
        return self.locator("#city");
    }

    Locator zipCode() {
        return self.locator("#zipcode");
    }

    Locator phoneNumber() {
        return self.locator("#mobile_number");
    }

    Locator createAccount() {
        return self.locator("button[data-qa=create-account]");
    }


}
