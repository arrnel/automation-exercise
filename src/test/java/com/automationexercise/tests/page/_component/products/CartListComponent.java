package com.automationexercise.tests.page._component.products;

import com.automationexercise.tests.models.CartProductInfo;
import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.models.UserType;
import com.automationexercise.tests.page._component.BaseComponent;
import com.automationexercise.tests.page._component._type.CartListType;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class CartListComponent extends BaseComponent<CartListComponent> {

    private final CartListComponentLocator locator;

    public CartListComponent(Locator self, CartListType cartListType) {
        super(self, cartListType.getComponentTitle());
        this.locator = new CartListComponentLocator(self);
    }

    @Step("Click on title of product: {productTitle}")
    public void openProduct(String productTitle) {
        log.info("Click on title of product: {}", productTitle);
        locator.title(productTitle).click();
    }

    @Step("Remove product from [{this.componentTitle}]: {productTitle}")
    public void removeProduct(String productTitle) {
        log.info("Click on [Trash] button of product: {}", productTitle);
        locator.deleteProduct(productTitle).click();
    }

    public int getProductQuantity(String title) {
        return Integer.parseInt(locator.quantity(title).innerText());
    }

    @Step("Check visible product title: {title}")
    public CartListComponent checkProductExists(String title) {
        log.info("Check visibility of product title: {}", title);
        assertThat(locator.product(title)).isVisible();
        return this;
    }

    @Step("Check not visible product title: {title}")
    public CartListComponent checkProductNotExists(String title) {
        log.info("Check product = [{}] not exists", title);
        assertThat(locator.product(title)).not().isAttached();
        return this;
    }

    @Step("Check product = [{title}] has user type: {userType}")
    public CartListComponent checkProductHasUserType(String title, UserType userType) {
        log.info("Check product = [{}] has user type: {}", title, userType);
        if (userType == UserType.EMPTY)
            throw new IllegalArgumentException("Product category cannot equals UserType.EMPTY");
        assertThat(locator.category(title)).containsText(userType.getValue());
        return this;
    }

    @Step("Check product has info in [{this.componentTitle}]: {cartProductInfo.title}")
    public CartListComponent checkProductHasData(CartProductInfo cartProductInfo) {
        log.info("Check product has info in [{}]: {}", this.componentTitle, cartProductInfo);
        checkProductHasCategory(cartProductInfo.title(), cartProductInfo.category());
        checkProductHasPrice(cartProductInfo.title(), cartProductInfo.price());
        checkProductHasQuantity(cartProductInfo.title(), cartProductInfo.quantity());
        checkProductHasTotal(cartProductInfo.title(), cartProductInfo.total());
        return this;
    }

    @Step("Check product = [{title}] has category: {category}")
    public CartListComponent checkProductHasCategory(String title, String category) {
        log.info("Check product = [{}] has category: {}", title, category);
        assertThat(locator.category(title)).containsText(category);
        return this;
    }

    @Step("Check product = [{title}] has price: {price.amount}")
    public CartListComponent checkProductHasPrice(String title, PriceDTO price) {
        log.info("Check product = [{}] has price: {}", title, price);
        assertThat(locator.price(title)).hasText(price.getPriceText());
        return this;
    }

    @Step("Check product = [{title}] has quantity: {quantity}")
    public CartListComponent checkProductHasQuantity(String title, int quantity) {
        log.info("Check product = [{}] has quantity: {}", title, quantity);
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be negative or equal to zero");
        assertThat(locator.quantity(title)).hasText(String.valueOf(quantity));
        return this;
    }

    @Step("Check product = [{title}] has price: {price.amount}")
    public CartListComponent checkProductHasTotal(String title, PriceDTO total) {
        log.info("Check product = [{}] has total: {}", title, total);
        assertThat(locator.productTotal(title)).hasText(total.getPriceText());
        return this;
    }

    @Step("Check products total price equals: {total.amount}")
    public void checkProductsTotalPrice(PriceDTO total) {
        log.info("Check products total price equals: {}", total);
        assertThat(locator.cartTotal()).hasText(total.getPriceText());
    }

    @Override
    public CartListComponent shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
    }

}
