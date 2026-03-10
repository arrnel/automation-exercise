package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.Browser;
import com.automationexercise.tests.jupiter.anno.ApiLogin;
import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.models.allure.AllureTag;
import com.automationexercise.tests.page.products.ProductsListPage;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import com.automationexercise.tests.util.ProductUtil;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.automationexercise.tests.util.browser.BrowserName.FIREFOX;

@Tag(AllureTag.CHECKOUT_TEST)
@WebTest
@DisplayName("[WEB] Checkout page test")
class CheckoutWebTest extends BaseTest {

    @Browser(FIREFOX)
    @Owner(AllureTag.OWNER)
    @ApiLogin
    @Test
    @DisplayName("Should have expected products total price in [Checkout] page")
    void shouldHaveExpectedProductsTotalPriceInCheckoutPage() {
        // Data
        var products = DataGenerator.randomProducts(5);
        var productsTitles = ProductUtil.productsTitles(products);
        var productsTotal = ProductUtil.productsTotalPrice(products);

        // Steps
        new ProductsListPage().open()
                .addProductsToCart(productsTitles)
                .header().cart()
                .proceedToCheckout()

                // Assertions
                .checkTotalPrice(productsTotal);
    }

    @Owner(AllureTag.OWNER)
    @ApiLogin
    @Test
    @DisplayName("Should not exist removed position from cart in [Checkout] page")
    void shouldExcludeProductPositionInCheckoutPageIfProductWasRemovedFromCartTest() {
        // Data
        var products = DataGenerator.randomProducts(5);
        var removedProduct = products.get(FAKE.random().nextInt(0, products.size() - 1));
        var productsTitles = ProductUtil.productsTitles(products);
        var productsTotal = ProductUtil.productsTotalPrice(products);
        var remainingProducts = new ArrayList<>(products);
        remainingProducts.remove(removedProduct);
        var remainingProductsTotal = ProductUtil.productsTotalPrice(remainingProducts);

        // Steps
        new ProductsListPage().open()
                .addProductsToCart(productsTitles)
                .header().cart()
                .proceedToCheckout()
                .checkProductExistsInCheckout(removedProduct.title())
                .checkTotalPrice(productsTotal)
                .header().cart()
                .removeProductFromCart(removedProduct.title())
                .proceedToCheckout()

                // Assertions
                .checkProductNotExistsInCart(removedProduct.title())
                .checkTotalPrice(remainingProductsTotal);
    }

}
