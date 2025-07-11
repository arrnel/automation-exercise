package com.automationexercise.tests.page._component.auth;

import com.automationexercise.tests.page._component.BaseComponent;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@ParametersAreNonnullByDefault
public class RegisterComponent extends BaseComponent<RegisterComponent> {

    private final RegisterComponentLocator locator;

    public RegisterComponent(Locator self) {
        super(self);
        this.locator = new RegisterComponentLocator(self);
    }

    @Step("Register by username and password")
    public void register(String name,
                         String email
    ) {
        log.info("Register by name = [{}] and email = [{}]", name, email);
        fillName(name);
        fillEmail(email);
        submit();
    }

    @Step("Fill name: {name}")
    private void fillName(String name) {
        log.info("Fill name: [{}]", name);
        locator.name().fill(name);
    }

    @Step("Fill email: {email}")
    private void fillEmail(String email) {
        log.info("Fill email: [{}]", email);
        locator.email().fill(email);
    }

    @Step("Submit sign up")
    private void submit() {
        log.info("Submit registration");
        locator.submit().click();
    }

    @Override
    public RegisterComponent shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        locator.name().waitFor(VISIBLE_CONDITION);
        locator.email().waitFor(VISIBLE_CONDITION);
        locator.submit().waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
    }

}
