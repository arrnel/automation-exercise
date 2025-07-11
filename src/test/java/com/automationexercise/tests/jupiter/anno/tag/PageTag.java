package com.automationexercise.tests.jupiter.anno.tag;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Tag;

import java.lang.annotation.*;

public class PageTag {

    @Tag("login_page")
    @Feature("Login page")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LoginPageTag {

    }

    @Tag("register_page")
    @Feature("Register page")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RegisterPageTag {

    }

    @Tag("account_created_page")
    @Feature("Account created page")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AccountCreatedPageTag {

    }

    @Tag("main_page")
    @Feature("Main page")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface MainPageTag {

    }

    @Tag("products_page")
    @Feature("Products page")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ProductsPageTag {

    }

    @Tag("product_page")
    @Feature("Product page")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ProductPageTag {

    }

    @Tag("cart_page")
    @Feature("Cart page")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CartPageTag {

    }

    @Tag("checkout_page")
    @Feature("Checkout page")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CheckoutPageTag {

    }

    @Tag("payment_page")
    @Feature("Payment page")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PaymentPageTag {

    }

    @Tag("order_placed_page")
    @Feature("Order placed page")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface OrderPlacedPageTag {

    }

    @Tag("contact_us_page")
    @Feature("Contact us page")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ContactUsPageTag {

    }

}
