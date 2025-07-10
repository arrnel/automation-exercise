package com.automationexercise.tests.test.web.screenshot;

import com.automationexercise.tests.jupiter.anno.meta.ScreenshotTest;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.ProductCarouselComponentTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.MainPageTag;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.test.BaseTest;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@ScreenshotTest
@ProductCarouselComponentTag
@DisplayName("[Screenshot] Image carousel tests")
class ImageCarouselScreenshotTest extends BaseTest {

    private static final String FIRST_SLIDE_IMG = "/carousel/image/slide_0.png",
            SECOND_SLIDE_IMG = "/carousel/image/slide_1.png";

    @Owner("@arrnel")
    @MainPageTag
    @Test
    @DisplayName("Should image carousel show next slide")
    void shouldShowNextSlideTest() {
        // Steps
        new MainPage().open()
                .waitForImageCarouselWillHaveActiveSlide(0)
                .showNextImageCarouselSlide()

                // Assertion
                .checkImageCarouselHasActiveSlide(1)
                .checkImageCarouselHasScreenshot(SECOND_SLIDE_IMG);
    }

    @Owner("@arrnel")
    @MainPageTag
    @Test
    @DisplayName("Should image carousel show previous slide")
    void shouldShowPreviousSlideTest() {
        // Steps
        new MainPage().open()
                .waitForImageCarouselWillHaveActiveSlide(1)
                .showPreviousImageCarouselSlide()
                // Assertion
                .checkImageCarouselHasActiveSlide(0)
                .checkImageCarouselHasScreenshot(FIRST_SLIDE_IMG);
    }

}
