package com.automationexercise.tests.jupiter.anno.tag;

import io.qameta.allure.Story;
import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ComponentTag {

    @Tag("address_component")
    @Story("[WEB] Address component tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AddressComponentTag {

    }

    @Tag("register_component")
    @Story("[WEB] Registration component tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RegisterComponentTag {

    }

    @Tag("login_component")
    @Story("[WEB] Login component tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LoginComponentTag {

    }

    @Tag("carousel_component")
    @Tag("image_carousel_component")
    @Story("[WEB] Image carousel component tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ImageCarouselComponentTag {

    }

    @Tag("carousel_component")
    @Tag("product_carousel_component")
    @Story("[WEB] Product carousel component tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ProductCarouselComponentTag {

    }

    @Tag("breadcrumb_component")
    @Story("[WEB] Breadcrumb component tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BreadcrumbComponentTag {

    }

    @Tag("header_component")
    @Story("[WEB] Header component tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface HeaderComponentTag {

    }

    @Tag("notification_component")
    @Story("[WEB] Notification component tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NotificationComponentTag {

    }

    @Tag("page_scroller_component")
    @Story("[WEB] Page scroller component tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PageScrollerComponentTag {

    }

    @Tag("subscription_component")
    @Story("[WEB] Subscription component tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SubscriptionComponentTag {

    }

    @Tag("contact_us_form")
    @Story("[WEB] Contact us component tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ContactUsFormTag {

    }

    @Tag("search_filter")
    @Story("[WEB] Search filter tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SearchFilterTag {

    }

    @Tag("category_filter")
    @Story("[WEB] Category filter tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CategoryFilterTag {

    }

    @Tag("brand_filter")
    @Story("[WEB] Brand filter tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BrandFilterTag {

    }

    @Tag("payment_form")
    @Story("[WEB] Payment form tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PaymentFormTag {

    }

    @Tag("product_details_component")
    @Story("[WEB] Product details tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ProductDetailsComponentTag {

    }

    @Tag("review_form")
    @Story("[WEB] Review form tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ReviewFormTag {

    }

    @Tag("cart_list_component")
    @Story("[WEB] Cart list tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CartListComponentTag {

    }

    @Tag("product_list_component")
    @Story("[WEB] Product list tests")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ProductListComponentTag {

    }

    @Tag("invoice")
    @Story("[WEB] Invoice")
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface InvoiceTag {

    }

}
