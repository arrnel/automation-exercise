package com.automationexercise.tests.test.web.screenshot;

import com.automationexercise.tests.jupiter.anno.meta.ScreenshotTest;
import com.automationexercise.tests.models.ScreenshotParam;
import com.automationexercise.tests.models.allure.AllureTag;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.page.products.ProductPage;
import com.automationexercise.tests.page.products.ProductsListPage;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(AllureTag.PRODUCT_TEST)
@ScreenshotTest
@DisplayName("[Screenshot] Product screenshot tests")
class ProductScreenshotTest extends BaseTest {

    private static final String PRODUCT_DETAILS_IMG = "/product/product_details.png";
    private static final String PRODUCT_CARD_IMG = "/product/product_card.png";
    private static final String PRODUCT_CARD_OVERLAY_IMG = "/product/product_card_overlay.png";

    @Tag(AllureTag.PRODUCT_DETAILS_TEST)
    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should have product details screenshot")
    void shouldHaveProductDetailsScreenshotTest() {
        // Data
        var product = DataGenerator.expectedProduct();
        new ProductPage().open(product.id())
                .checkProductDetailsHasScreenshot(PRODUCT_DETAILS_IMG);
    }

    @Tag(AllureTag.PRODUCT_CARD_TEST)
    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should have expected screenshot of product card in [Main] page")
    void shouldHaveExpectedProductCardScreenshotInMainPageTest() {
        // Data
        var productTitle = DataGenerator.expectedProduct().title();

        // Steps
        new MainPage().open()

                // Assertions
                .checkProductCardHasScreenshot(productTitle, PRODUCT_CARD_IMG);
    }

    @Tag(AllureTag.PRODUCT_CARD_TEST)
    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should have expected screenshot product card overlay in [Main] page")
    void shouldHaveExpectedProductCardOverlayScreenshotInMainPageTest() {
        // Data
        var productTitle = DataGenerator.expectedProduct().title();

        // Steps
        new MainPage().open()

                // Assertions
                .checkProductCardOverlayHasScreenshot(
                        productTitle,
                        ScreenshotParam.builder()
                                .expectedScreenshotUrl(PRODUCT_CARD_OVERLAY_IMG)
                                .timeout(500)
                                .build()
                );
    }

    @Tag(AllureTag.PRODUCT_CARD_TEST)
    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should have expected screenshot of product card in [Products] page")
    void shouldHaveExpectedProductCardScreenshotInProductsPageTest() {
        // Data
        var productTitle = DataGenerator.expectedProduct().title();

        // Steps
        new ProductsListPage().open()

                // Assertions
                .checkProductCardHasScreenshot(productTitle, PRODUCT_CARD_IMG);
    }

    @Tag(AllureTag.PRODUCT_CARD_TEST)
    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should have expected screenshot product card overlay in [Products] page")
    void shouldHaveExpectedProductCardOverlayScreenshotInProductsPageTest() {
        // Data
        var productTitle = DataGenerator.expectedProduct().title();

        // Steps
        new ProductsListPage().open()

                // Assertions
                .checkProductCardOverlayHasScreenshot(
                        productTitle,
                        ScreenshotParam.builder()
                                .expectedScreenshotUrl(PRODUCT_CARD_OVERLAY_IMG)
                                .timeout(1_000)
                                .rewrite(true)
                                .build()
                );
    }

}
