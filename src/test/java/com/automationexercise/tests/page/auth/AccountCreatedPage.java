package com.automationexercise.tests.page.auth;

import com.automationexercise.tests.page.BasePage;
import com.automationexercise.tests.page.products.MainPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class AccountCreatedPage extends BasePage<AccountCreatedPage> {

    private static final String ACCOUNT_CREATED_MESSAGE = "Account Created!";
    private static final String CONGRATULATION_MESSAGE = "Congratulations! Your new account has been successfully created!";

    private final AccountCreatedPageLocator locator;

    public AccountCreatedPage() {
        locator = new AccountCreatedPageLocator(page.locator("section .container"));
    }

    @Step("Go to [Main] page")
    public MainPage next() {
        log.info("Next page");
        Allure.step("Click [Continue] button", () ->
                locator.next().click());
        return new MainPage();
    }

    @Step("Check user successfully created message exists")
    public void checkAccountCreatedMessagesVisible() {
        log.info("Checking user successfully created message exists");
        Allure.step("Check [Account created] message exists", () -> {
            assertThat(locator.title()).isVisible();
            assertThat(locator.title()).hasText(ACCOUNT_CREATED_MESSAGE);
        });

        Allure.step("Check [Congratulation] message exists", () -> {
            assertThat(locator.message()).isVisible();
            assertThat(locator.message()).hasText(CONGRATULATION_MESSAGE);
        });
    }

    @Nonnull
    @Override
    public AccountCreatedPage shouldVisiblePage() {
        locator.title().waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisiblePage() {
        locator.title().waitFor(DETACHED_CONDITION);
    }

}
