package com.automationexercise.tests.page._component.filter;

import com.microsoft.playwright.Locator;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.apache.commons.lang3.StringUtils.capitalize;

@ParametersAreNonnullByDefault
class CategoryFilterLocator {

    private final Locator self;

    CategoryFilterLocator(Locator self) {
        this.self = self;
    }

    Locator userTypes() {
        return self.locator(".panel");
    }

    Locator userTypesTitles() {
        return self.locator("div");
    }

    Locator userTypesWrapper(String categoryTitle) {
        return self.locator("//div[@class='panel-heading' and .//a[@data-parent='#accordian' and @href='#%s']]"
                .formatted(capitalize(categoryTitle)));
    }

    Locator categoriesWrapper(String categoryTitle) {
        return self.locator("#%s".formatted(categoryTitle));
    }

    Locator userType(String userTypeTitle) {
        return self.locator("//h4//a[normalize-space()='%s']".formatted(userTypeTitle));
    }

    Locator category(String userTypeTitle, String categoryTitle) {
        return categoriesWrapper(userTypeTitle)
                .locator("//*[text()='%s ']".formatted(capitalize(categoryTitle)));
    }

}
