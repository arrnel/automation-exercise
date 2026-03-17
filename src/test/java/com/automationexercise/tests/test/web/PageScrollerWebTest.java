package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.meta.ScreenshotTest;
import com.automationexercise.tests.models.ScreenshotParam;
import com.automationexercise.tests.models.allure.AllureTag;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.page.products.ProductsListPage;
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

}
