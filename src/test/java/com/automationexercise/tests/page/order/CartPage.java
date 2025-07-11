package com.automationexercise.tests.page.order;

import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.page.BasePage;
import com.automationexercise.tests.page._component.common.BreadCrumbComponent;
import com.automationexercise.tests.page._component.products.CartListComponent;
import com.automationexercise.tests.page.products.ProductPage;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.automationexercise.tests.page._component._type.CartListType.CART_PRODUCTS;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class CartPage extends BasePage<CartPage> {

    private static final String URL = CFG.baseUrl() + "/view_cart";

    // Components
    private final BreadCrumbComponent breadcrumb;
    private final CartListComponent cartList;

    // Locators
    private final Locator emptyCartLocator;
    private final Locator proceedToCheckoutLocator;

    public CartPage() {

        this.breadcrumb = new BreadCrumbComponent(page.locator(".breadcrumb"));
        this.cartList = new CartListComponent(page.locator("#cart_items"), CART_PRODUCTS);

        this.emptyCartLocator = page.locator("#empty_cart");
        this.proceedToCheckoutLocator = page.locator("a.check_out");

    }

    @Nonnull
    public BreadCrumbComponent breadcrumb() {
        return breadcrumb;
    }

    @Nonnull
    @Step("Navigate to [Cart] page")
    public CartPage open() {
        log.info("Navigate to the [Cart] page");
        page.navigate(URL);
        return this;
    }

    @Nonnull
    @Step("Proceed to checkout")
    public CheckoutPage proceedToCheckout() {
        log.info("Proceed to checkout");
        Allure.step("Click on [Proceed to checkout] button", () -> proceedToCheckoutLocator.click());
        return new CheckoutPage();
    }

    @Nonnull
    public ProductPage openProduct(String productTitle) {
        cartList.openProduct(productTitle);
        return new ProductPage();
    }

    @Nonnull
    public CartPage removeProductFromCart(String productTitle) {
        cartList.removeProduct(productTitle);
        return this;
    }

    @Nonnull
    public CartPage changeProductQuantity(String productTitle, int quantity) {
        cartList.getProductQuantity(productTitle);
        return this;
    }

    @Step("Check [Proceed To Checkout] exists")
    public CartPage checkProceedToCheckoutIsVisible() {
        log.info("Check button [Proceed To Checkout] is visible");
        assertThat(proceedToCheckoutLocator).isVisible();
        return this;
    }

    @Step("Check [Proceed To Checkout] not exists")
    public CartPage checkProceedToCheckoutNotExists() {
        log.info("Check button [Proceed To Checkout] not exists");
        assertThat(proceedToCheckoutLocator).not().isAttached();
        return this;
    }

    @Step("Check cart list is empty")
    public CartPage checkCartPageIsEmpty() {
        log.info("Check cart list is empty");
        assertThat(emptyCartLocator).isVisible();
        return this;
    }

    @Step("Check empty cart list not exists")
    public CartPage checkEmptyCartListNotVisible() {
        log.info("Check empty cart list not exists");
        assertThat(emptyCartLocator).not().isVisible();
        return this;
    }

    @Nonnull
    public CartPage checkProductExistsInCart(String productTitle) {
        cartList.checkProductExists(productTitle);
        return this;
    }

    @Nonnull
    public CartPage checkProductNotExistsInCart(String productTitle) {
        cartList.checkProductNotExists(productTitle);
        return this;
    }

    @Nonnull
    public CartPage checkProductPrice(String productTitle, PriceDTO price) {
        cartList.checkProductHasPrice(productTitle, price);
        return this;
    }

    @Nonnull
    public CartPage checkProductQuantity(String productTitle, int quantity) {
        cartList.checkProductHasQuantity(productTitle, quantity);
        return this;
    }

    @Nonnull
    public CartPage checkProductTotal(String productTitle, PriceDTO total) {
        cartList.checkProductHasTotal(productTitle, total);
        return this;
    }

    @Nonnull
    public CartPage checkProductQuantityAndTotal(String productTitle, int quantity, PriceDTO total) {
        var stepText = "Check product with title = [%s] has quantity = [%d] and total = [%s]"
                .formatted(productTitle, quantity, total.getPriceText());
        log.info(stepText);
        Allure.step(stepText, () -> {
            cartList.checkProductHasQuantity(productTitle, quantity);
            cartList.checkProductHasTotal(productTitle, total);
        });
        return this;
    }

    @Nonnull
    @Override
    public CartPage shouldVisiblePage() {
        log.info("Check [Cart] page is visible");
        // Add logic
        return this;
    }

    @Override
    public void shouldNotVisiblePage() {
        log.info("Check [Cart] page not visible");
        // Add logic
    }

}
