package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.MainPageTag;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.automationexercise.tests.jupiter.anno.tag.ComponentTag.ProductCarouselComponentTag;

@WebTest
@ProductCarouselComponentTag
@DisplayName("[WEB] Product carousel tests")
class ProductCarouselWebTest extends BaseTest {

    @Owner("@arrnel")
    @MainPageTag
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
