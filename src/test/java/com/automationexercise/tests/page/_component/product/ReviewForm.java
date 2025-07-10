package com.automationexercise.tests.page._component.product;

import com.automationexercise.tests.models.ReviewInfo;
import com.automationexercise.tests.page._component.BaseComponent;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class ReviewForm extends BaseComponent<ReviewForm> {

    private static final String SUCCESS_STATUS_MESSAGE = "Thank you for your review.";

    private final ReviewFormLocator locator;

    public ReviewForm(Locator self) {
        super(self);
        locator = new ReviewFormLocator(self);
    }

    @Step("Send review")
    public void sendReview(ReviewInfo review) {
        fillName(review.name());
        fillEmail(review.email());
        fillReview(review.message());
        submit();
    }

    @Step("Fill name: {name}")
    private void fillName(String name) {
        log.info("Fill name: {}", name);
        locator.name().fill(name);
    }

    @Step("Fill email: {email}")
    private void fillEmail(String email) {
        log.info("Fill email: {}", email);
        locator.email().fill(email);
    }

    @Step("Fill review: {review}")
    private void fillReview(String review) {
        log.info("Fill review: {}", review);
        locator.review().fill(review);
    }

    @Step("Submit")
    private void submit() {
        log.info("Submit");
        locator.submit().click();
    }

    @Step("Check review has successfully sent")
    public void checkReviewSuccessfullySent() {
        checkReviewHasSuccessStatus();
        checkReviewHasSuccessStatusMessage();
    }

    @Step("Check review has success status message")
    private void checkReviewHasSuccessStatusMessage() {
        log.info("Check review has success status message");
        assertThat(locator.reviewStatusMessage()).hasText(SUCCESS_STATUS_MESSAGE);
    }

    @Step("Check review has success status")
    private void checkReviewHasSuccessStatus() {
        log.info("Check review has success status");
        assertThat(locator.reviewStatusWrapper()).hasClass("alert-success alert");
    }

    @Override
    public ReviewForm shouldVisibleComponent() {
        locator.name().waitFor(VISIBLE_CONDITION);
        locator.email().waitFor(VISIBLE_CONDITION);
        locator.review().waitFor(VISIBLE_CONDITION);
        locator.submit().waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
    }

}
