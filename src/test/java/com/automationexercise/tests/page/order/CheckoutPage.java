package com.automationexercise.tests.page.order;

import com.automationexercise.tests.models.AddressInfo;
import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.page.BasePage;
import com.automationexercise.tests.page._component.address.AddressDetailsComponent;
import com.automationexercise.tests.page._component.common.BreadCrumbComponent;
import com.automationexercise.tests.page._component.products.CartListComponent;
import com.automationexercise.tests.page.products.ProductPage;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.automationexercise.tests.page._component._type.AddressType.BILLING_ADDRESS;
import static com.automationexercise.tests.page._component._type.AddressType.DELIVERY;
import static com.automationexercise.tests.page._component._type.CartListType.CHECKOUT_PRODUCTS;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class CheckoutPage extends BasePage<CheckoutPage> {

    private static final String URL = CFG.baseUrl() + "/checkout";

    private final BreadCrumbComponent breadcrumb;
    private final AddressDetailsComponent deliveryAddress;
    private final AddressDetailsComponent billingAddress;
    private final CartListComponent cartList;

    private final Locator orderComment;
    private final Locator placeOrder;

    public CheckoutPage() {
        this.breadcrumb = new BreadCrumbComponent(page.locator(".breadcrumb"));
        this.deliveryAddress = new AddressDetailsComponent(page.locator("#address_delivery"), DELIVERY);
        this.billingAddress = new AddressDetailsComponent(page.locator("#address_invoice"), BILLING_ADDRESS);
        this.cartList = new CartListComponent(page.locator(".table-condensed"), CHECKOUT_PRODUCTS);

        this.orderComment = page.locator("[name=message]");
        this.placeOrder = page.locator("a.check_out");
    }

    @Nonnull
    public BreadCrumbComponent breadcrumb() {
        return breadcrumb;
    }

    @Nonnull
    @Step("Navigate to the [Checkout] page")
    public CheckoutPage open() {
        log.info("Navigate to the [Checkout] page");
        page.navigate(URL);
        return this;
    }

    @Step("Add order comment")
    public CheckoutPage commentOrder(String comment) {
        log.info("Add order comment: {}", comment);
        Allure.step("Fill order comment textarea", () ->
                orderComment.fill(comment));
        return this;
    }

    @Step("Place order")
    public PaymentPage placeOrder() {
        log.info("Place order");
        Allure.step("Click [Place order] button", () ->
                placeOrder.click());
        return new PaymentPage();
    }

    @Step("Place order")
    public CheckoutPage placeOrderWithError() {
        log.info("Place order");
        Allure.step("Click [Place order] button", () ->
                placeOrder.click());
        return this;
    }

    @Step("Check delivery address has expected data")
    public CheckoutPage checkDeliveryAddressHasExpectedData(AddressInfo address) {
        log.info("Check delivery address has expected data: {}", address);
        deliveryAddress.shouldHaveAddressInfo(address.title("Your delivery address"));
        return this;
    }

    @Step("Check billing address has expected data")
    public CheckoutPage checkBillingAddressHasExpectedData(AddressInfo address) {
        log.info("Check billing address has expected data: {}", address);
        billingAddress.shouldHaveAddressInfo(address.title("Your billing address"));
        return this;
    }

    @Nonnull
    public ProductPage openProduct(String productTitle) {
        cartList.openProduct(productTitle);
        return new ProductPage();
    }

    @Step("Check [Proceed To Checkout] exists")
    public CheckoutPage checkPlaceOrderIsVisible() {
        log.info("Check button [Proceed To Checkout] is visible");
        assertThat(placeOrder).isVisible();
        return this;
    }

    @Step("Check [Proceed To Checkout] not exists")
    public CheckoutPage checkProceedToCheckoutNotExists() {
        log.info("Check button [Proceed To Checkout] not exists");
        assertThat(placeOrder).not().isAttached();
        return this;
    }

    @Nonnull
    public CheckoutPage checkProductExistsInCheckout(String productTitle) {
        cartList.checkProductExists(productTitle);
        return this;
    }

    @Nonnull
    public CheckoutPage checkProductNotExistsInCart(String productTitle) {
        cartList.checkProductNotExists(productTitle);
        return this;
    }

    @Nonnull
    public CheckoutPage checkProductPrice(String productTitle, PriceDTO price) {
        cartList.checkProductHasPrice(productTitle, price);
        return this;
    }

    @Nonnull
    public CheckoutPage checkProductQuantity(String productTitle, int quantity) {
        cartList.checkProductHasQuantity(productTitle, quantity);
        return this;
    }

    @Nonnull
    public CheckoutPage checkProductTotal(String productTitle, PriceDTO total) {
        cartList.checkProductHasTotal(productTitle, total);
        return this;
    }

    @Nonnull
    @Step("Check product with title = [{productTitle}] has quantity = [{quantity}] and total = [{total.currency}. {total.amount}]")
    public CheckoutPage checkProductQuantityAndTotal(String productTitle, int quantity, PriceDTO total) {
        log.info("Check product with title = [{}] has quantity = [{}] and total = [{}]", productTitle, quantity, total.getPriceText());
        cartList.checkProductHasQuantity(productTitle, quantity);
        cartList.checkProductHasTotal(productTitle, total);
        return this;
    }

    @Nonnull
    public CheckoutPage checkTotalPrice(PriceDTO total) {
        cartList.checkProductsTotalPrice(total);
        return this;
    }

    @Nonnull
    public CheckoutPage checkOrderCommentHasText(String text) {
        assertThat(orderComment).hasText(text);
        return this;
    }

    @Nonnull
    @Override
    public CheckoutPage shouldVisiblePage() {
        orderComment.waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisiblePage() {
        orderComment.waitFor(DETACHED_CONDITION);
    }

}
