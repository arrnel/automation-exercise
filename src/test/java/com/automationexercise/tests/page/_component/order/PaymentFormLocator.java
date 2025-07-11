package com.automationexercise.tests.page._component.order;

import com.microsoft.playwright.Locator;

import javax.annotation.Nonnull;

class PaymentFormLocator {

    private final Locator self;

    PaymentFormLocator(Locator self) {
        this.self = self;
    }

    @Nonnull
    Locator name() {
        return self.locator("[name=name_on_card]");
    }

    @Nonnull
    Locator cardNumber() {
        return self.locator("[name=card_number]");
    }

    @Nonnull
    Locator cvc() {
        return self.locator("[name=cvc]");
    }

    @Nonnull
    Locator expiryMonth() {
        return self.locator("[name=expiry_month]");
    }

    @Nonnull
    Locator expiryYear() {
        return self.locator("[name=expiry_year]");
    }

    @Nonnull
    Locator payAndConfirm() {
        return self.locator("#submit");
    }

    @Nonnull
    Locator payStatusMessage() {
        return self.locator("#success_message .alert-success");
    }

}
