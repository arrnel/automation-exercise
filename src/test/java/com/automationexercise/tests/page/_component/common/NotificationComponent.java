package com.automationexercise.tests.page._component.common;

import com.automationexercise.tests.page._component.BaseComponent;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
public class NotificationComponent extends BaseComponent<NotificationComponent> {

    private static final String SUCCESS_ADD_PRODUCT_TO_CART_MESSAGE = "Your product has been added to cart.",
            NOT_AUTHORIZED_MESSAGE = "Register / Login account to proceed on checkout.";

    private final NotificationComponentLocator locator;

    public NotificationComponent(Locator self) {
        super(self);
        this.locator = new NotificationComponentLocator(self);
    }

    @Step("Close notification")
    public NotificationComponent close() {
        log.info("Close notification");
        locator.close().click();
        return this;
    }

    @Step("Click on notification link")
    public void clickOnLink() {
        log.info("Click on notification link");
        locator.descriptionLink().click();
    }

    @Step("Check notification has title: {title}")
    public NotificationComponent checkNotificationHasTitle(String title) {
        log.info("Check notification has title: {}", title);
        assertThat(locator.title()).hasText(title);
        return this;
    }

    @Step("Check notification has title: {title}")
    public NotificationComponent checkNotificationHasDescription(String description) {
        log.info("Check notification has description: {}", description);
        assertThat(locator.description()).hasText(description);
        return this;
    }

    @Step("Check notification has success added product to cart message")
    public NotificationComponent checkNotificationHasSuccessAddedProductMessage() {
        log.info("Check notification has success added product to cart message: {}", SUCCESS_ADD_PRODUCT_TO_CART_MESSAGE);
        assertThat(locator.description()).hasText(SUCCESS_ADD_PRODUCT_TO_CART_MESSAGE);
        return this;
    }

    @Step("Check notification has not authorized message")
    public NotificationComponent checkNotificationHasNotAuthorizedMessage() {
        log.info("Check notification has not authorized message: {}", NOT_AUTHORIZED_MESSAGE);
        assertThat(locator.description()).hasText(NOT_AUTHORIZED_MESSAGE);
        return this;
    }

    @Step("Check notification has title: {title}")
    public NotificationComponent checkNotificationHasDescriptionLink(String descriptionLink) {
        log.info("Check notification has description link: {}", descriptionLink);
        assertThat(locator.description()).hasAttribute("href", descriptionLink);
        return this;
    }

    @Step("Check notification has title: {title}")
    public NotificationComponent checkNotificationHasDescriptionLinkText(String descriptionLinkText) {
        log.info("Check notification has description link text: {}", descriptionLinkText);
        assertThat(locator.description().locator("u")).hasText(descriptionLinkText);
        return this;
    }

    @Step("Check notification has expected screenshot")
    public NotificationComponent checkNotificationHasScreenshot(String pathToScreenshot,
                                                                double percentOfTolerance,
                                                                boolean rewriteScreenshot
    ) {
        log.info("Check notification has expected screenshot");
        checkElementHasScreenshot(self, pathToScreenshot, percentOfTolerance, rewriteScreenshot);
        return this;
    }

    @Nonnull
    @Override
    public NotificationComponent shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
    }

}
