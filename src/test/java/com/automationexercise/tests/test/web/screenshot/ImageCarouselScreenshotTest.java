package com.automationexercise.tests.test.web.screenshot;

import com.automationexercise.tests.jupiter.anno.meta.ScreenshotTest;
import com.automationexercise.tests.models.allure.AllureTag;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.test.BaseTest;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(AllureTag.CAROUSEL_TEST)
@Tag(AllureTag.IMAGE_CAROUSEL_TEST)
@ScreenshotTest
@DisplayName("[Screenshot] Image carousel tests")
class ImageCarouselScreenshotTest extends BaseTest {

    private static final String FIRST_SLIDE_IMG = "/carousel/image/slide_0.png",
            SECOND_SLIDE_IMG = "/carousel/image/slide_1.png";

    @Owner(AllureTag.OWNER)
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

    @Owner(AllureTag.OWNER)
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
