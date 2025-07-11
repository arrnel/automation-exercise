package com.automationexercise.tests.test.web.screenshot;

import com.automationexercise.tests.jupiter.anno.meta.ScreenshotTest;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.ProductDetailsComponentTag;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.ProductListComponentTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.MainPageTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.ProductPageTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.ProductsPageTag;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.page.products.ProductPage;
import com.automationexercise.tests.page.products.ProductsListPage;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@ScreenshotTest
@DisplayName("[Screenshot] Product screenshot tests")
class ProductScreenshotTest extends BaseTest {

    private static final String PRODUCT_DETAILS_IMG = "/product/product_details.png";
    private static final String PRODUCT_CARD_IMG = "/product/product_card.png";
    private static final String PRODUCT_CARD_OVERLAY_IMG = "/product/product_card_overlay.png";

    @Owner("@arrnel")
    @ProductPageTag
    @ProductDetailsComponentTag
    @Test
    @DisplayName("Should have product details screenshot")
    void shouldHaveProductDetailsScreenshotTest() {
        // Data
        var product = DataGenerator.expectedProduct();
        new ProductPage().open(product.id())
                .checkProductDetailsHasScreenshot(PRODUCT_DETAILS_IMG, 0.01);
    }


    @Owner("@arrnel")
    @MainPageTag
    @ProductListComponentTag
    @Test
    @DisplayName("Should have expected screenshot of product card in [Main] page")
    void shouldHaveExpectedProductCardScreenshotInMainPageTest() {
        // Data
        var productTitle = DataGenerator.expectedProduct().title();

        // Steps
        new MainPage().open()

                // Assertions
                .checkProductCardHasScreenshot(productTitle, PRODUCT_CARD_IMG, 0.02);
    }

    @Owner("@arrnel")
    @MainPageTag
    @ProductListComponentTag
    @Test
    @DisplayName("Should have expected screenshot product card overlay in [Main] page")
    void shouldHaveExpectedProductCardOverlayScreenshotInMainPageTest() {
        // Data
        var productTitle = DataGenerator.expectedProduct().title();

        // Steps
        new MainPage().open()

                // Assertions
                .checkProductCardOverlayHasScreenshot(productTitle, PRODUCT_CARD_OVERLAY_IMG, 0.02);
    }

    @Owner("@arrnel")
    @ProductsPageTag
    @ProductListComponentTag
    @Test
    @DisplayName("Should have expected screenshot of product card in [Products] page")
    void shouldHaveExpectedProductCardScreenshotInProductsPageTest() {
        // Data
        var productTitle = DataGenerator.expectedProduct().title();

        // Steps
        new ProductsListPage().open()

                // Assertions
                .checkProductCardHasScreenshot(productTitle, PRODUCT_CARD_IMG, 0.02);
    }

    @Owner("@arrnel")
    @ProductsPageTag
    @ProductListComponentTag
    @Test
    @DisplayName("Should have expected screenshot product card overlay in [Products] page")
    void shouldHaveExpectedProductCardOverlayScreenshotInProductsPageTest() {
        // Data
        var productTitle = DataGenerator.expectedProduct().title();

        // Steps
        new ProductsListPage().open()

                // Assertions
                .checkProductCardOverlayHasScreenshot(productTitle, PRODUCT_CARD_OVERLAY_IMG, 0.02);
    }

}
