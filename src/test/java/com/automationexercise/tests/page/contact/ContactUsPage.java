package com.automationexercise.tests.page.contact;

import com.automationexercise.tests.models.ContactInfo;
import com.automationexercise.tests.page.BasePage;
import com.automationexercise.tests.page._component.contact.ContactUsForm;
import com.automationexercise.tests.page.products.MainPage;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@ParametersAreNonnullByDefault
public class ContactUsPage extends BasePage<ContactUsPage> {

    private static final String URL = CFG.baseUrl() + "/contact_us";

    private final ContactUsForm contactUsForm;

    public ContactUsPage() {
        this.contactUsForm = new ContactUsForm(page.locator(".contact-form"));
    }

    @Nonnull
    @Step("Navigate to [Contact us] page")
    public ContactUsPage open() {
        page.navigate(URL);
        return this;
    }

    @Nonnull
    public ContactUsPage send(ContactInfo contactInfo) {
        contactUsForm.sendFormWithAcceptAlert(contactInfo);
        contactUsForm.checkStatusMessageIsSuccess();
        return this;
    }

    @Step("Go on [Main] page")
    public MainPage goHome() {
        log.info("Go on [Main] page");
        return contactUsForm.home();
    }

    @Nonnull
    public ContactUsPage sendWithError(ContactInfo contactInfo) {
        contactUsForm.sendFormWithAcceptAlert(contactInfo);
        return this;
    }

    @Nonnull
    public ContactUsPage sendWithAlertDismiss(ContactInfo contactInfo) {
        log.info("Sending contact us form. With browser alert cancellation. ContactInfo: {}", contactInfo);
        contactUsForm.sendFormWithDismissAlert(contactInfo);
        return this;
    }

    @Nonnull
    @Override
    public ContactUsPage shouldVisiblePage() {
        contactUsForm.shouldVisibleComponent();
        return this;
    }

    @Override
    public void shouldNotVisiblePage() {
        contactUsForm.shouldNotVisibleComponent();
    }

}
