package com.automationexercise.tests.page._component.product;

import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.models.UserType;
import com.automationexercise.tests.page._component.BaseComponent;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class ProductDetailsComponent extends BaseComponent<ProductDetailsComponent> {

    private final ProductDetailsComponentLocator locator;

    public ProductDetailsComponent(Locator self) {
        super(self);
        locator = new ProductDetailsComponentLocator(self);
    }

    @Step("Add products to cart: {quantity}")
    public ProductDetailsComponent addToCart(int quantity) {
        fillProductQuantity(quantity);
        clickAddToCart();
        return this;
    }

    @Step("Add product to cart")
    public ProductDetailsComponent addToCart() {
        fillProductQuantity(1);
        clickAddToCart();
        return this;
    }

    @Step("Click button add product to cart")
    private void clickAddToCart() {
        log.info("Click button add product to cart");
        locator.addToCart().click();
    }

    @Step("Set product quantity: {quantity}")
    private void fillProductQuantity(int quantity) {
        log.info("Click button add product to cart");
        locator.quantity().fill(String.valueOf(quantity));
    }

    @Step("Check product image has expected photo")
    public ProductDetailsComponent checkProductHasPhoto(String pathToPhoto, double percentOfTolerance, boolean rewriteScreenshot) {
        return checkElementHasScreenshot(locator.photo(), pathToPhoto, percentOfTolerance, rewriteScreenshot);
    }

    @Step("Check product has title: {title}")
    public ProductDetailsComponent checkProductHasTitle(String title) {
        log.info("Check product has title: {}", title);
        assertThat(locator.title()).hasText(title);
        return this;
    }

    @Step("Check product has new arrival badge")
    public ProductDetailsComponent checkProductHasNewArrivalBadge() {
        log.info("Check product has new arrival badge");
        assertThat(locator.newArrival()).isVisible();
        return this;
    }

    @Step("Check product not have new arrival badge")
    public ProductDetailsComponent checkProductNotHaveNewArrivalBadge() {
        log.info("Check product not have new arrival badge");
        assertThat(locator.newArrival()).not().isAttached();
        return this;
    }

    @Step("Check product has user type: {userType}")
    public ProductDetailsComponent checkProductHasUserType(UserType userType) {
        log.info("Check product has user type: {}", userType);
        if (userType == UserType.EMPTY)
            throw new IllegalArgumentException("UserType can not equals UserType.EMPTY");
        assertThat(locator.category()).containsText("Category: %s > ".formatted(userType.getValue()));
        return this;
    }

    @Step("Check product has category: {category}")
    public ProductDetailsComponent checkProductHasCategory(String category) {
        log.info("Check product has category: {}", category);
        assertThat(locator.category()).containsText("> %s".formatted(category));
        return this;
    }

    @Step("Check product has user type = [{userType}] and category = [{category}]")
    public ProductDetailsComponent checkProductHasUserTypeAndCategory(UserType userType, String category) {
        log.info("Check product has user type = [{}] and category = [{}]", userType, category);
        if (userType == UserType.EMPTY)
            throw new IllegalArgumentException("UserType can not equals UserType.EMPTY");
        assertThat(locator.category()).hasText("Category: %s > %s".formatted(userType.getValue(), category));
        return this;
    }

    @Step("Check product has rating: {rating}")
    public ProductDetailsComponent checkProductHasRating(int rating) {
        log.info("Check product has rating: {}", rating);
        if (rating < 0 || rating > 10)
            throw new IllegalArgumentException("Rating can not be less than 0 or greater than 10");
        var ratingPhoto = "%s/rating/%d.png".formatted(CFG.pathToScreenshotsDirectory(), rating);
        checkElementHasScreenshot(locator.rating(), ratingPhoto, 0.02, false);
        return this;
    }

    @Step("Check product has rating: {rating}")
    public ProductDetailsComponent checkProductHasRating(int rating, boolean rewriteScreenshot) {
        log.info("Check product has rating: {}", rating);
        if (rating < 0 || rating > 10)
            throw new IllegalArgumentException("Rating can not be less than 0 or greater than 10");
        var ratingPhoto = "/rating/%d.png".formatted(rating);
        checkElementHasScreenshot(locator.rating(), ratingPhoto, 0.02, rewriteScreenshot);
        return this;
    }

    @Step("Check product has title: {title}")
    public ProductDetailsComponent checkProductHasPrice(PriceDTO price) {
        log.info("Check product has price: {}", price.getPriceText());
        assertThat(locator.price()).hasText(price.getPriceText());
        return this;
    }

    @Step("Check product has quantity: {quantity}")
    public ProductDetailsComponent checkProductHasQuantity(int quantity) {
        log.info("Check product has quantity: {}", quantity);
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity can not be less than 0");
        assertThat(locator.price()).hasText(String.valueOf(quantity));
        return this;
    }

    @Step("Check product has availability: {availability}")
    public ProductDetailsComponent checkProductHasInStockAvailability(String availability) {
        log.info("Check product has availability: {}", availability);
        assertThat(locator.availability()).hasText("Availability: %s".formatted(availability));
        return this;
    }

    @Step("Check product has condition: {condition}")
    public ProductDetailsComponent checkProductHasCondition(String condition) {
        log.info("Check product has condition: {}", condition);
        assertThat(locator.condition()).hasText("Condition: %s".formatted(condition));
        return this;
    }

    @Step("Check product has in stock availability")
    public ProductDetailsComponent checkProductHasBrand(String brand) {
        log.info("Check product has brand: {}", brand);
        assertThat(locator.brand()).hasText("Brand: %s".formatted(brand));
        return this;
    }

    @Step("Check product details has screenshot")
    public ProductDetailsComponent checkProductDetailsHasScreenshot(String pathToScreenshot, double percentOfTolerance, boolean rewriteScreenshot) {
        log.info("Check product details has screenshot");
        checkElementHasScreenshot(self, pathToScreenshot, percentOfTolerance, rewriteScreenshot);
        return this;
    }

    @Override
    public ProductDetailsComponent shouldVisibleComponent() {
        locator.availability().waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        locator.availability().waitFor(DETACHED_CONDITION);
    }

}
