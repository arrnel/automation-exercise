package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.ContactUsFormTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.ContactUsPageTag;
import com.automationexercise.tests.page.contact.ContactUsPage;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WebTest
@ContactUsPageTag
@ContactUsFormTag
@DisplayName("[WEB] Contact us tests")
class ContactUsWebTest extends BaseTest {

    @Owner("@arrnel")
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
