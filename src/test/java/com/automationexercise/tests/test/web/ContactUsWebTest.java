package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.models.allure.AllureTag;
import com.automationexercise.tests.page.contact.ContactUsPage;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(AllureTag.CONTACT_US_TEST)
@WebTest
@DisplayName("[WEB] Contact us tests")
class ContactUsWebTest extends BaseTest {

    @Owner(AllureTag.OWNER)
    @Test
    @DisplayName("Should send contact form")
    void shouldSendContactUsData() {
        // Data
        var contactInfo = DataGenerator.randomContactInfo();
        // Steps & Assertions
        new ContactUsPage().open()
                .send(contactInfo);
    }

}
