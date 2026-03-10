package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.models.allure.AllureTag;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(AllureTag.CAROUSEL_TEST)
@Tag(AllureTag.PRODUCT_CAROUSEL_TEST)
@WebTest
@DisplayName("[WEB] Product carousel tests")
class ProductCarouselWebTest extends BaseTest {

    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should add recommended product to cart")
    void shouldAddRecommendedProductToCartTest() {
        // Data
        var randomProduct = DataGenerator.recommendedProduct();
        // Steps
        new MainPage().open()
                .addRecommendedProductToCart(randomProduct.title())
                .header()
                .cart()
                .checkProductExistsInCart(randomProduct.title());
    }

}
