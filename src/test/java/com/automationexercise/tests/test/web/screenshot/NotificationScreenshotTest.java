package com.automationexercise.tests.test.web.screenshot;

import com.automationexercise.tests.jupiter.anno.meta.ScreenshotTest;
import com.automationexercise.tests.models.allure.AllureTag;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(AllureTag.NOTIFICATION_TEST)
@ScreenshotTest
@DisplayName("[Screenshot] Product screenshot tests")
class NotificationScreenshotTest {

    private static final String ADD_TO_CART_NOTIFICATION_IMG = "/notification/successfully_added_to_cart.png";

    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should have add to cart notification expected screenshot")
    void shouldHaveAddToCartNotificationHasScreenshot() {
        // Data
        var product = DataGenerator.randomProduct();

        // Steps
        new MainPage().open()
                .addProductToCart(product.title())

                // Assertions
                .checkNotificationHasScreenshot(ADD_TO_CART_NOTIFICATION_IMG, 0.01);
    }

}
