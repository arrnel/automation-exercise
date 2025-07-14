package com.automationexercise.tests.page.order;

import com.automationexercise.tests.models.CardInfo;
import com.automationexercise.tests.page.BasePage;
import com.automationexercise.tests.page._component.order.PaymentForm;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@ParametersAreNonnullByDefault
public class PaymentPage extends BasePage<PaymentPage> {

    public static final String URL = CFG.baseUrl() + "/payment";

    private final PaymentForm paymentForm;

    public PaymentPage() {
        paymentForm = new PaymentForm(page.locator(".payment-information"));
    }

    @Nonnull
    @Step("Navigate to the [Payment] page")
    public PaymentPage open() {
        log.info("Navigate to the [Payment] page");
        page.navigate(URL);
        return this;
    }

    @Step("Pay with card")
    public OrderPlacedPage pay(CardInfo card) {
        log.info("Pay with payment card: {}", card);
        paymentForm.pay(card);
        return new OrderPlacedPage();
    }

    @Step("Pay with card")
    public PaymentPage payWithError(CardInfo card) {
        log.info("Pay with payment card: {}", card);
        paymentForm.pay(card);
        return this;
    }

    /**
     * Returns void cause after paying
     */
    @Step("Check payment")
    public void checkPayIsSuccessful() {
        log.info("Check pay is successful");
        paymentForm.checkPayFormHasSuccessMessage();
    }

    @Nonnull
    @Override
    public PaymentPage shouldVisiblePage() {
        paymentForm.shouldVisibleComponent();
        return this;
    }

    @Override
    public void shouldNotVisiblePage() {
        paymentForm.shouldNotVisibleComponent();
    }

}
