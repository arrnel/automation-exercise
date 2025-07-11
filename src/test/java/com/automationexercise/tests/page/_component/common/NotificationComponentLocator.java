package com.automationexercise.tests.page._component.common;

import com.microsoft.playwright.Locator;

import javax.annotation.Nonnull;

class NotificationComponentLocator {

    private final Locator self;

    NotificationComponentLocator(Locator self) {
        this.self = self;
    }

    @Nonnull
    Locator title() {
        return self.locator("h4");
    }

    @Nonnull
    Locator description() {
        return self.locator(".modal-body p:first-child");
    }

    @Nonnull
    Locator descriptionLink() {
        return self.locator(".modal-body a");
    }

    @Nonnull
    Locator close() {
        return self.locator(".close-modal");
    }

}
