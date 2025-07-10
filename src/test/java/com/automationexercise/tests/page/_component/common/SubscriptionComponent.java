package com.automationexercise.tests.page._component.common;

import com.automationexercise.tests.page._component.BaseComponent;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class SubscriptionComponent extends BaseComponent<SubscriptionComponent> {

    private static final String SUCCESS_SUBSCRIPTION_MESSAGE = "You have been successfully subscribed!";
    private final SubscriptionComponentLocator locator;

    public SubscriptionComponent(Locator self) {
        super(self);
        this.locator = new SubscriptionComponentLocator(self);
    }

    @Step("Send email to subscribe")
    public SubscriptionComponent subscribe(String email) {
        log.info("Send email to subscribe");
        locator.email().fill(email);
        locator.submit().click();
        checkSubscribeStatusMessageIsVisible();
        return this;
    }

    @Step("Check success subscribe status is visible")
    public SubscriptionComponent checkSubscribeStatusMessageIsVisible() {
        log.info("Check success subscribe status is visible");
        assertThat(locator.statusMessageWrapper()).isVisible();
        locator.submit().click();
        return this;
    }

    @Step("Check success subscribe status message has expected message")
    public SubscriptionComponent checkSuccessSubscribeStatusMessageHasCorrectText() {
        log.info("Check success subscribe status message has text: {}", SUCCESS_SUBSCRIPTION_MESSAGE);
        assertThat(locator.statusMessage()).hasText(SUCCESS_SUBSCRIPTION_MESSAGE);
        locator.submit().click();
        return this;
    }


    @Override
    public SubscriptionComponent shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        locator.email().waitFor(VISIBLE_CONDITION);
        return this;
    }

    /**
     * THIS COMPONENT IS ALWAYS VISIBLE
     */
    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
        locator.email().waitFor(DETACHED_CONDITION);
    }

}
