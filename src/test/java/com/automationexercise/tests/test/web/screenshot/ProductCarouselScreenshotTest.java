package com.automationexercise.tests.test.web.screenshot;

import com.automationexercise.tests.jupiter.anno.meta.ScreenshotTest;
import com.automationexercise.tests.models.allure.AllureTag;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.test.BaseTest;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(AllureTag.PRODUCT_CAROUSEL_TEST)
@ScreenshotTest
@DisplayName("[Screenshot] Product carousel tests")
class ProductCarouselScreenshotTest extends BaseTest {

    private static final String FIRST_SLIDE_IMG = "/carousel/product/slide_0.png",
            SECOND_SLIDE_IMG = "/carousel/product/slide_1.png";

    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should recommended products carousel show next slide")
    void shouldShowNextSlideTest() {
        // Steps
        new MainPage().open()
                .waitForRecommendedProductsCarouselWillHaveActiveSlide(0)
                .showNextRecommendedProductsCarouselSlide()

                // Assertion
                .checkRecommendedProductsCarouselHasActiveSlide(1)
                .checkRecommendedProductsCarouselHasScreenshot(SECOND_SLIDE_IMG);
    }

    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should recommended products carousel show previous slide")
    void shouldShowPreviousSlideTest() {
        // Steps
        new MainPage().open()
                .waitForRecommendedProductsCarouselWillHaveActiveSlide(1)
                .showPreviousRecommendedProductsCarouselSlide()
                // Assertion
                .checkRecommendedProductsCarouselHasActiveSlide(0)
                .checkRecommendedProductsCarouselHasScreenshot(FIRST_SLIDE_IMG);
    }

}
