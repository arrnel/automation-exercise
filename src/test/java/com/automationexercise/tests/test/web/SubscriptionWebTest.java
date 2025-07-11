package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.SubscriptionComponentTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.MainPageTag;
import com.automationexercise.tests.page.contact.ContactUsPage;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WebTest
@SubscriptionComponentTag
@DisplayName("[WEB] Subscription test")
class SubscriptionWebTest {

    @Owner("@arrnel")
    @MainPageTag
    @Test
    @DisplayName("Should subscribe")
    void shouldSubscribeTest() {
        // Data
        var email = DataGenerator.generateEmail();

        // Steps & Assertions
        new MainPage().open()
                .subscription().subscribe(email)
                .checkSuccessSubscribeStatusMessageHasCorrectText();
    }

    @Owner("@arrnel")
    @MainPageTag
    @Test
    @DisplayName("Success subscribe message should have expected text after sending contact form")
    void shouldVisibleCorrectSuccessSubscriptionStatusAfterSendingContactFormTest() {
        // Data
        var contactInfo = DataGenerator.randomContactInfo();

        // Steps & Assertions
        new ContactUsPage().open()
                .send(contactInfo)
                .subscription().subscribe(contactInfo.email())
                .checkSuccessSubscribeStatusMessageHasCorrectText();
    }

}
