package com.automationexercise.tests.page._component.order;

import com.automationexercise.tests.models.CardInfo;
import com.automationexercise.tests.page._component.BaseComponent;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class PaymentForm extends BaseComponent<PaymentForm> {

    private static final String SUCCESS_PAYING_MESSAGE = "Your order has been placed successfully!";

    private final PaymentFormLocator locator;

    public PaymentForm(Locator self) {
        super(self);
        locator = new PaymentFormLocator(self);
    }

    @Step("Pay with card data")
    public void pay(CardInfo card) {
        fillName(card.name());
        fillCardNumber(card.number());
        fillCvc(card.cvc());
        fillExpiryMonth(card.expiryMonth());
        fillExpiryYear(card.expiryYear());
        submit();
    }

    @Step("Fill [Name on Card] input: {name}")
    private void fillName(String name) {
        log.info("Fill [Name on Card] input: {}", name);
        locator.name().fill(name);
    }

    @Step("Fill [Card Number] input: {cardNumber}")
    private void fillCardNumber(String cardNumber) {
        log.info("Fill [Card Number] input: {}", cardNumber);
        locator.cardNumber().fill(cardNumber);
    }

    @Step("Fill [CVC] input: {cvc}")
    private void fillCvc(String cvc) {
        log.info("Fill [CVC] input: {}", cvc);
        locator.cvc().fill(cvc);
    }

    @Step("Fill [Expiration month] input: {expiryMonth}")
    private void fillExpiryMonth(String expiryMonth) {
        log.info("Fill [Expiration month] input: {}", expiryMonth);
        locator.expiryMonth().fill(expiryMonth);
    }

    @Step("Fill [Expiration year] input: {expiryYear}")
    private void fillExpiryYear(String expiryYear) {
        log.info("Fill [Expiration year] input: {}", expiryYear);
        locator.expiryYear().fill(expiryYear);
    }

    @Step("Click on [Pay and Confirm Order] button")
    private void submit() {
        log.info("Click on [Pay and Confirm Order] button");
        locator.payAndConfirm().click();
    }

    public void checkPayFormHasSuccessMessage() {
        var stepText = "Check visible of pay success message: %s".formatted(SUCCESS_PAYING_MESSAGE);
        log.info(stepText);
        Allure.step(stepText, () ->
                assertThat(locator.payStatusMessage()).hasText(SUCCESS_PAYING_MESSAGE));

    }

    @Override
    public PaymentForm shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        locator.cardNumber().waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
        locator.cardNumber().waitFor(DETACHED_CONDITION);
    }

}
