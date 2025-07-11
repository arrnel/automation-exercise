package com.automationexercise.tests.page._component.address;

import com.microsoft.playwright.Locator;

import javax.annotation.Nonnull;

class AddressDetailsComponentLocator {

    private static final String COMPANY_ADDRESS_LINE_SELECTOR = "//li[contains(@class,'address-address1')][%d]";

    private final Locator self;

    AddressDetailsComponentLocator(Locator self) {
        this.self = self;
    }

    @Nonnull
    Locator title() {
        return self.locator("h3");
    }

    @Nonnull
    Locator fullName() {
        return self.locator(".address_firstname");
    }

    @Nonnull
    Locator company() {
        return self.locator(COMPANY_ADDRESS_LINE_SELECTOR.formatted(1));
    }

    Locator address1() {
        return self.locator(COMPANY_ADDRESS_LINE_SELECTOR.formatted(2));
    }

    Locator address2() {
        return self.locator(COMPANY_ADDRESS_LINE_SELECTOR.formatted(3));
    }

    Locator cityStateZip() {
        return self.locator(".address_city");
    }

    Locator country() {
        return self.locator(".address_country_name");
    }

    Locator phoneNumber() {
        return self.locator(".address_phone");
    }

    @Nonnull
    Locator close() {
        return self.locator(".close-modal");
    }

}
