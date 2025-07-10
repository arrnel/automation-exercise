package com.automationexercise.tests.page._component.auth;

import com.automationexercise.tests.page._component.BaseComponent;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class LoginComponent extends BaseComponent<LoginComponent> {

    private static final String INVALID_PASSWORD_MESSAGE = "Your email or password is incorrect!";

    private final LoginComponentLocator locator;

    public LoginComponent(Locator self) {
        super(self);
        this.locator = new LoginComponentLocator(self);
    }

    @Step("Sign in by email and password")
    public void signIn(String email,
                       String password
    ) {
        log.info("Sign in by username = [{}] and password = [{}]", email, password);
        fillEmail(email);
        fillPassword(password);
        submit();
    }

    @Step("Fill email: {email}")
    private void fillEmail(String email) {
        log.info("Fill email: [{}]", email);
        locator.email().fill(email);
    }

    @Step("Fill password: {password}")
    private void fillPassword(String password) {
        log.info("Fill password: [{}]", password);
        locator.password().fill(password);
    }

    @Step("Submit sign in")
    private void submit() {
        log.info("Submit sign in");
        locator.submit().click();
    }

    @Step("Check sign in error has message: {message}")
    public void checkSignInErrorHasMessage(String message) {
        log.info("Check sign in error has message: [{}]", message);
        assertThat(locator.error()).hasText(message);
    }

    @Step("Check sign in error has invalid password message")
    public void checkSignInErrorHasInvalidPasswordMessage() {
        log.info("Check sign in error has invalid password message");
        assertThat(locator.error()).hasText(INVALID_PASSWORD_MESSAGE);
    }

    @Override
    public LoginComponent shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        locator.email().waitFor(VISIBLE_CONDITION);
        locator.password().waitFor(VISIBLE_CONDITION);
        locator.submit().waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
    }

}
