package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.meta.DisabledByIssue;
import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.ProductDetailsComponentTag;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.ProductListComponentTag;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.ReviewFormTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.MainPageTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.ProductPageTag;
import com.automationexercise.tests.models.ReviewInfo;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.page.products.ProductPage;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@WebTest
@DisplayName("[WEB] Products tests")
class ProductWebTest extends BaseTest {

    @Owner("@arrnel")
    @ProductPageTag
    @ProductDetailsComponentTag
    @Test
    @DisplayName("Should have valid product data in product page")
    void shouldHaveValidProductDataInProductPageTest() {
        // Data
        var product = DataGenerator.randomProduct();

        // Steps
        new ProductPage().open(product.id())

                // Assertions
                .checkProductHasTitle(product.title())
                .checkProductHasUserTypeAndCategory(product.category().usertype().userType(), product.category().category())
                .checkProductHasPrice(product.price())
                .checkProductHasAvailability("In Stock")
                .checkProductHasRating(9, true)
                .checkProductHasCondition("New")
                .checkProductHasBrand(product.brand());
    }

    @Owner("@arrnel")
    @MainPageTag
    @ProductListComponentTag
    @Test
    @DisplayName("Should have valid product price in main page")
    void shouldHaveValidPriceTest() {
        // Data
        var product = DataGenerator.expectedProduct();

        // Steps
        new MainPage().open()

                // Assertions
                .checkProductHasPrice(product.title(), product.price());
    }

    @Owner("@arrnel")
    @ProductPageTag
    @ProductDetailsComponentTag
    @Test
    @DisplayName("Should add product to cart in product page")
    void shouldAddProductToCartInProductPageTest() {
        // Data
        var product = DataGenerator.randomProduct();

        // Steps
        new ProductPage().open(product.id())
                .addToCartAndCloseNotification(2)

                // Assertions
                .header().cart()
                .checkProductQuantity(product.title(), 2);
    }

    @Owner("@arrnel")
    @ProductPageTag
    @ReviewFormTag
    @Test
    @DisplayName("Should send review")
    void shouldSendProductReviewTest() {
        // Data
        var product = DataGenerator.randomProduct();
        var review = DataGenerator.randomReview();

        // Steps & Assertions
        new ProductPage().open(product.id())
                .sendReview(review);
    }

    @DisabledByIssue(issueId = "3")
    @Owner("arrnel")
    @ProductPageTag
    @ReviewFormTag
    @ParameterizedTest
    @MethodSource("com.automationexercise.tests.test.data.ReviewDataProvider#validReviewDataProvider")
    @DisplayName("Should send product review with valid data")
    void shouldSendProductReviewWithValidDataTest(String caseName, ReviewInfo review) {
        // Data
        var product = DataGenerator.randomProduct();

        // Steps & Assertions
        new ProductPage().open(product.id())
                .sendReview(review);
    }

    @DisabledByIssue(issueId = "3")
    @Owner("arrnel")
    @ProductPageTag
    @ReviewFormTag
    @ParameterizedTest
    @MethodSource("com.automationexercise.tests.test.data.ReviewDataProvider#invalidReviewDataProvider")
    @DisplayName("Should not send product review with invalid data")
    void shouldNotSendProductReviewWithInvalidDataTest(String caseName, ReviewInfo review) {
        // Data
        var product = DataGenerator.randomProduct();

        // Steps & Assertions
        new ProductPage().open(product.id())
                .sendReviewWithError(review);
    }

}
