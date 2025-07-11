package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.ApiLogin;
import com.automationexercise.tests.jupiter.anno.meta.ScreenshotTest;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.PageScrollerComponentTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.CheckoutPageTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.MainPageTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.ProductsPageTag;
import com.automationexercise.tests.page.order.CheckoutPage;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.page.products.ProductsListPage;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@ScreenshotTest
@PageScrollerComponentTag
@DisplayName("[Screenshot] Page scroller test")
class PageScrollerWebTest {

    @Owner("@arrnel")
    @MainPageTag
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
        mainPage.checkPageHasScreenshot("/page/main.png", 0.005);
    }

    @Owner("@arrnel")
    @ProductsPageTag
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
        productsPage.checkPageHasScreenshot("/page/products.png", 0.01);
    }

    @Owner("@arrnel")
    @CheckoutPageTag
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

    @Owner("@arrnel")
    @CheckoutPageTag
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
