package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.ApiLogin;
import com.automationexercise.tests.jupiter.anno.meta.ScreenshotTest;
import com.automationexercise.tests.models.ScreenshotParam;
import com.automationexercise.tests.models.allure.AllureTag;
import com.automationexercise.tests.page.order.CheckoutPage;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.page.products.ProductsListPage;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(AllureTag.PAGE_SCROLLER_TEST)
@ScreenshotTest
@DisplayName("[Screenshot] Page scroller test")
class PageScrollerWebTest {

    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should scroll to top on [Main] page")
    void shouldScrollToTopOnMainPageTest() {
        // Steps
        var mainPage = new MainPage();
        mainPage.open()
                .subscription()
                .scrollToComponent();
        mainPage.pageScroller()
                .scrollToTop();

        // Assertions
        mainPage.checkPageHasScreenshot(
                ScreenshotParam.builder()
                        .expectedScreenshotUrl("/page/main.png")
                        .tolerance(0.005)
                        .build()
        );
    }

    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should scroll to top on [Products] page")
    void shouldScrollToTopOnProductsPageTest() {
        // Steps
        var productsPage = new ProductsListPage();
        productsPage.open()
                .subscription().scrollToComponent();
        productsPage.pageScroller()
                .scrollToTop();

        // Assertions
        productsPage.checkPageHasScreenshot("/page/products.png");
    }

    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should scroll to top on [Cart] page")
    void shouldScrollToTopOnCartPageTest() {
        // Data
        var productsTitles = DataGenerator.expectedProductsTitles();

        // Steps
        var checkoutPage = new CheckoutPage();
        new ProductsListPage().open()
                .addProductsToCart(productsTitles)
                .header().cart()
                .subscription().scrollToComponent();
        checkoutPage.pageScroller().scrollToTop();
        // Assertions
        checkoutPage.checkPageHasScreenshot("/page/cart.png");
    }

    @Owner(AllureTag.OWNER)
    @ApiLogin(
            email = "expected_user@test.test",
            password = "12345"
    )
    @Test
    @DisplayName("Should scroll to top on [Checkout] page")
    void shouldScrollToTopOnCheckoutPageTest() {
        // Data
        var productsTitles = DataGenerator.expectedProductsTitles();

        // Steps
        var checkoutPage = new CheckoutPage();
        new ProductsListPage().open()
                .addProductsToCart(productsTitles)
                .header().cart()
                .proceedToCheckout()
                .subscription().scrollToComponent();
        checkoutPage.pageScroller().scrollToTop();
        // Assertions
        checkoutPage.checkPageHasScreenshot("/page/checkout.png");
    }

}
