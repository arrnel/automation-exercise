package com.automationexercise.tests.page._component.contact;

import com.microsoft.playwright.Locator;

class ContactUsFormLocator {

    private final Locator self;

    ContactUsFormLocator(Locator self) {
        this.self = self;
    }

    Locator formTitle() {
        return self.locator("h2");
    }

    Locator name() {
        return self.locator("input[data-qa=name]");
    }

    Locator email() {
        return self.locator("input[data-qa=email]");
    }

    Locator subject() {
        return self.locator("input[data-qa=subject]");
    }

    Locator message() {
        return self.locator("textarea[data-qa=message]");
    }

    Locator file() {
        return self.locator("input[name=upload_file]");
    }

    Locator submit() {
        return self.locator("input[data-qa='submit-button']");
    }

    Locator statusMessage() {
        return self.locator(".status.alert");
    }

    Locator home() {
        return self.locator(".btn-success");
    }

}
