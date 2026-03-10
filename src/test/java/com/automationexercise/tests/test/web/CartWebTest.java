package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.ApiLogin;
import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.models.allure.AllureTag;
import com.automationexercise.tests.page.order.CartPage;
import com.automationexercise.tests.page.products.ProductsListPage;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(AllureTag.CART_TEST)
@WebTest
@DisplayName("[WEB] Cart page tests")
class CartWebTest extends BaseTest {

    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should visible empty cart list if products not added to cart")
    void shouldVisibleEmptyListIfProductsNotAddedToCartTest() {
        // Steps
        new CartPage().open()

                // Assertions
                .checkCartPageIsEmpty();
    }

    @Tag(AllureTag.PRODUCT_ITEM)
    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should visible empty cart list if all products was removed from cart")
    void shouldVisibleEmptyListIfAllProductsRemovedTest() {
        // Data
        var product = DataGenerator.randomProduct();

        // Steps
        new ProductsListPage().open()
                .addProductToCartAndCloseNotification(product.title())
                .header().cart()
                .removeProductFromCart(product.title())

                // Assertions
                .checkCartPageIsEmpty();
    }

    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should remove product position if product quantity greater than 1")
    void shouldRemoveProductPositionIfProductQuantityGreaterThanOneTest() {
        // Data
        var product = DataGenerator.randomProduct();

        // Steps
        new ProductsListPage().open()
                .addProductToCartAndCloseNotification(product.title())
                .addProductToCartAndCloseNotification(product.title())
                .header().cart()
                .removeProductFromCart(product.title())

                // Assertions
                .checkCartPageIsEmpty();
    }

    @Tag(AllureTag.PRODUCT_ITEM)
    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should increase product quantity if added same product")
    void shouldIncreaseProductQuantityIfAddedSameProductTest() {
        // Data
        var product = DataGenerator.randomProduct();

        // Steps
        new ProductsListPage().open()
                .addProductToCartAndCloseNotification(product.title())
                .addProductToCartAndCloseNotification(product.title())
                .header().cart()

                // Assertions
                .checkProductQuantity(product.title(), 2);
    }

    @Owner(AllureTag.OWNER)
    @ApiLogin
    @Test
    @DisplayName("Should visible [Checkout] page after click on [Proceed To Checkout] button if user authorized")
    void shouldGoToCheckoutPageAfterProceedToCheckoutIfUserAuthorizedTest() {
        // Data
        var product = DataGenerator.randomProduct();

        // Steps
        new ProductsListPage().open()
                .addProductToCartAndCloseNotification(product.title())
                .header().cart()
                .proceedToCheckout()

                // Assertions
                .shouldVisiblePage();
    }

    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should visible [Register/Login] notification after click on [Proceed To Checkout] button if user unauthorized")
    void shouldVisibleRegisterAuthorizeNotificationAfterProceedToCheckoutIfUserUnauthorizedTest() {
        // Data
        var product = DataGenerator.randomProduct();

        // Steps
        new ProductsListPage().open()
                .addProductToCartAndCloseNotification(product.title())
                .header().cart()
                .proceedToCheckout()

                // Assertions
                .notification().checkNotificationHasNotAuthorizedMessage();
    }

}
