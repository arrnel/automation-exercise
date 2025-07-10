package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.ApiLogin;
import com.automationexercise.tests.jupiter.anno.User;
import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.InvoiceTag;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.PaymentFormTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.OrderPlacedPageTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.PaymentPageTag;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.page.auth.LoginPage;
import com.automationexercise.tests.page.products.ProductsListPage;
import com.automationexercise.tests.util.DataGenerator;
import com.automationexercise.tests.util.ProductUtil;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WebTest
@DisplayName("[WEB] Payment tests")
class PaymentWebTest {

    @Owner("@arrnel")
    @PaymentPageTag
    @PaymentFormTag
    @ApiLogin
    @Test
    @DisplayName("Should place order with registration before checkout")
    void shouldPlaceOrderWithRegistrationBeforeCheckoutTest() {
        // Data
        var products = DataGenerator.randomProducts(5);
        var productsTitles = ProductUtil.productsTitles(products);
        var comment = DataGenerator.generateComment();
        var creditCard = DataGenerator.generateCreditCard();

        // Steps
        new ProductsListPage().open()
                .addProductsToCart(productsTitles)
                .header().cart()
                .proceedToCheckout()
                .commentOrder(comment)
                .placeOrder()
                .pay(creditCard)

                // Assertions
                .checkOrderPlacedMessageVisible();
    }

    @Owner("@arrnel")
    @PaymentPageTag
    @PaymentFormTag
    @Test
    @DisplayName("Should place order with registration while checkout")
    void shouldPlaceOrderWithRegistrationWhileCheckoutTest() {
        // Data
        var user = DataGenerator.generateUser();
        var products = DataGenerator.randomProducts(5);
        var productsTitles = ProductUtil.productsTitles(products);
        var comment = DataGenerator.generateComment();
        var creditCard = DataGenerator.generateCreditCard();

        // Steps
        new ProductsListPage().open()
                .addProductsToCart(productsTitles)
                .header().cart()
                .proceedToCheckout()
                .notification().clickOnLink();

        new LoginPage().register(user.name(), user.email())
                .sendRegisterData(user)
                .next()
                .header().cart()
                .proceedToCheckout()
                .commentOrder(comment)
                .placeOrder()
                .pay(creditCard)

                // Assertions
                .checkOrderPlacedMessageVisible();
    }

    @Owner("@arrnel")
    @OrderPlacedPageTag
    @InvoiceTag
    @User(email = "validate_invoice_user@test_user.test")
    @ApiLogin(email = "validate_invoice_user@test_user.test")
    @Test
    @DisplayName("Should have valid data in invoice")
    void shouldHaveValidDataInInvoiceFileTest(UserDTO user) {
        // Data
        var products = DataGenerator.randomProducts(5);
        var productsTitles = ProductUtil.productsTitles(products);
        var productsTotalPrice = ProductUtil.productsTotalPrice(products);
        var creditCard = DataGenerator.generateCreditCard();

        // Steps
        new ProductsListPage().open()
                .addProductsToCart(productsTitles)
                .header().cart()
                .proceedToCheckout()
                .placeOrder()
                .pay(creditCard)

                // Assertions
                .checkInvoiceContainsNameAndPrice(user.name(), productsTotalPrice);
    }

}
