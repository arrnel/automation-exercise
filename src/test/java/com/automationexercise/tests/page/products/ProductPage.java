package com.automationexercise.tests.page.products;

import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.models.ReviewInfo;
import com.automationexercise.tests.models.UserType;
import com.automationexercise.tests.page.BasePage;
import com.automationexercise.tests.page._component.filter.BrandFilter;
import com.automationexercise.tests.page._component.filter.CategoryFilter;
import com.automationexercise.tests.page._component.product.ProductDetailsComponent;
import com.automationexercise.tests.page._component.product.ReviewForm;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@ParametersAreNonnullByDefault
public class ProductPage extends BasePage<ProductPage> {

    public static final String URL = CFG.baseUrl() + "/product_details/%d";

    // Components
    private final CategoryFilter categoryFilter;
    private final BrandFilter brandFilter;
    private final ProductDetailsComponent productDetails;
    private final ReviewForm reviewForm;

    public ProductPage() {
        this.categoryFilter = new CategoryFilter(page.locator(".category-products"));
        this.brandFilter = new BrandFilter(page.locator(".brands_products"));
        this.productDetails = new ProductDetailsComponent(page.locator(".product-details"));
        this.reviewForm = new ReviewForm(page.locator(".shop-details-tab"));
    }

    @Nonnull
    public ProductPage open(Long productId) {
        var url = URL.formatted(productId);
        log.info("Navigate to the [Product] page: {}", url);

        Allure.step("Navigate to the [Product] page: %s".formatted(url), () -> {
            if (productId < 0)
                throw new IllegalArgumentException("Product id can not be negative");
            page.navigate(url);
        });

        return this;
    }

    @Nonnull
    public ProductPage addToCart() {
        productDetails.addToCart();
        return this;
    }

    @Nonnull
    public ProductPage addToCartAndCloseNotification() {
        productDetails.addToCart();
        notification().checkNotificationHasSuccessAddedProductMessage();
        closeNotification();
        return this;
    }

    @Nonnull
    public ProductPage addToCart(int quantity) {
        productDetails.addToCart(quantity);
        return this;
    }

    @Nonnull
    public ProductPage addToCartAndCloseNotification(int quantity) {
        productDetails.addToCart(quantity);
        notification().checkNotificationHasSuccessAddedProductMessage();
        closeNotification();
        return this;
    }

    @Nonnull
    public ProductPage sendReview(ReviewInfo review) {
        reviewForm.sendReview(review);
        reviewForm.checkReviewSuccessfullySent();
        return this;
    }

    @Nonnull
    public ProductPage sendReviewWithError(ReviewInfo review) {
        reviewForm.sendReview(review);
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasPhoto(String pathToPhoto) {
        return checkProductHasPhoto(pathToPhoto, 0, false);
    }

    @Nonnull
    public ProductPage checkProductHasPhoto(String pathToPhoto, boolean rewriteScreenshot) {
        return checkProductHasPhoto(pathToPhoto, 0, rewriteScreenshot);
    }

    @Nonnull
    public ProductPage checkProductHasPhoto(String pathToPhoto, double percentOfTolerance) {
        return checkProductHasPhoto(pathToPhoto, percentOfTolerance, false);
    }

    @Nonnull
    public ProductPage checkProductHasPhoto(String pathToPhoto,
                                            double percentOfTolerance,
                                            boolean rewriteScreenshot
    ) {
        productDetails.checkProductHasPhoto(pathToPhoto, percentOfTolerance, rewriteScreenshot);
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasTitle(String title) {
        productDetails.checkProductHasTitle(title);
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasNewArrivalBadge() {
        productDetails.checkProductHasNewArrivalBadge();
        return this;
    }

    @Nonnull
    public ProductPage checkProductNotHaveNewArrivalBadge() {
        productDetails.checkProductNotHaveNewArrivalBadge();
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasUserType(UserType userType) {
        productDetails.checkProductHasUserType(userType);
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasCategory(String category) {
        productDetails.checkProductHasCategory(category);
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasUserTypeAndCategory(UserType userType, String category) {
        productDetails.checkProductHasUserTypeAndCategory(userType, category);
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasRating(int rating) {
        productDetails.checkProductHasRating(rating);
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasRating(int rating, boolean rewriteScreenshot) {
        productDetails.checkProductHasRating(rating, rewriteScreenshot);
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasPrice(PriceDTO price) {
        productDetails.checkProductHasPrice(price);
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasQuantity(int quantity) {
        productDetails.checkProductHasQuantity(quantity);
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasAvailability(String availability) {
        productDetails.checkProductHasInStockAvailability(availability);
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasCondition(String condition) {
        productDetails.checkProductHasCondition(condition);
        return this;
    }

    @Nonnull
    public ProductPage checkProductHasBrand(String brand) {
        productDetails.checkProductHasBrand(brand);
        return this;
    }

    @Nonnull
    public ProductPage checkProductDetailsHasScreenshot(String pathToScreenshot) {
        return checkProductDetailsHasScreenshot(pathToScreenshot, 0, false);
    }

    @Nonnull
    public ProductPage checkProductDetailsHasScreenshot(String pathToScreenshot, boolean rewriteScreenshot) {
        return checkProductDetailsHasScreenshot(pathToScreenshot, 0, rewriteScreenshot);
    }

    @Nonnull
    public ProductPage checkProductDetailsHasScreenshot(String pathToScreenshot, double percentOfTolerance) {
        return checkProductDetailsHasScreenshot(pathToScreenshot, percentOfTolerance, false);
    }

    @Nonnull
    public ProductPage checkProductDetailsHasScreenshot(String pathToScreenshot,
                                                        double percentOfTolerance,
                                                        boolean rewriteScreenshot
    ) {
        productDetails.checkProductDetailsHasScreenshot(pathToScreenshot, percentOfTolerance, rewriteScreenshot);
        return this;
    }

    @Nonnull
    public ProductPage checkReviewSuccessfullySent() {
        reviewForm.checkReviewSuccessfullySent();
        return this;
    }

    @Nonnull
    @Override
    public ProductPage shouldVisiblePage() {
        productDetails.shouldVisibleComponent();
        reviewForm.shouldVisibleComponent();
        return this;
    }

    @Override
    public void shouldNotVisiblePage() {
        productDetails.shouldNotVisibleComponent();
        reviewForm.shouldNotVisibleComponent();
    }

}
