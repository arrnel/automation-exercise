package com.automationexercise.tests.page._component.common;

import com.automationexercise.tests.page._component.BaseComponent;
import com.automationexercise.tests.page.contact.ContactUsPage;
import com.automationexercise.tests.page.order.CartPage;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.page.products.ProductsListPage;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class HeaderComponent extends BaseComponent<HeaderComponent> {

    private final HeaderComponentLocator locator;

    public HeaderComponent(Locator self) {
        super(self);
        this.locator = new HeaderComponentLocator(self);
    }

    @Nonnull
    @Step("Navigate to [Main] page")
    public MainPage mainPageByLogo() {
        log.info("Navigate to the [Main] page");
        locator.logo().click();
        return new MainPage();
    }

    @Nonnull
    @Step("Navigate to [Main] page")
    public MainPage mainPage() {
        log.info("Navigate to the [Main] page");
        locator.home().click();
        return new MainPage();
    }

    @Nonnull
    @Step("Navigate to [Products] page")
    public ProductsListPage products() {
        log.info("Navigate to the [Products] page");
        locator.products().click();
        return new ProductsListPage();
    }

    @Nonnull
    @Step("Navigate to [Cart] page")
    public CartPage cart() {
        log.info("Navigate to the [Cart] page");
        locator.cart().click();
        return new CartPage();
    }

    @Nonnull
    @Step("Go to [Login] page")
    public MainPage login() {
        log.info("Navigate to the [Login] page");
        locator.logout().click();
        return new MainPage();
    }

    @Nonnull
    @Step("Logout")
    public MainPage logout() {
        log.info("Logout");
        locator.logout().click();
        return new MainPage();
    }

    @Nonnull
    @Step("Delete account")
    public MainPage deleteAccount() {
        log.info("Delete account");
        locator.deleteAccount().click();
        return new MainPage();
    }

    @Nonnull
    @Step("Contact us")
    public ContactUsPage contactUs() {
        log.info("Navigate to the [Contact us] page");
        locator.contactUs().click();
        return new ContactUsPage();
    }

    @Nonnull
    @Step("Check user logged in as: {name}")
    public HeaderComponent checkUserLoggedInWithName(String name) {
        log.info("Check user logged in as: {}", name);
        assertThat(locator.loggedInAs().locator("b")).hasText(name);
        return this;
    }

    @Nonnull
    @Step("Check user logged in")
    public HeaderComponent checkUserIsLoggedIn() {
        log.info("Check user is logged in");
        assertThat(locator.loggedInAs()).isVisible();
        return this;
    }

    @Nonnull
    @Step("Check user is not authorized")
    public HeaderComponent checkUserIsNotAuthorized() {
        log.info("Check user is not authorized");
        assertThat(locator.login()).isVisible();
        return this;
    }

    @Override
    public HeaderComponent shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        // THIS COMPONENT ALWAYS VISIBLE
    }

}
