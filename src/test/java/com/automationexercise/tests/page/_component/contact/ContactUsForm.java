package com.automationexercise.tests.page._component.contact;

import com.automationexercise.tests.models.ContactInfo;
import com.automationexercise.tests.page._component.BaseComponent;
import com.automationexercise.tests.page.products.MainPage;
import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class ContactUsForm extends BaseComponent<ContactUsForm> {

    private final ContactUsFormLocator locator;

    public ContactUsForm(Locator self) {
        super(self);
        this.locator = new ContactUsFormLocator(self);
    }

    public void sendFormWithAcceptAlert(ContactInfo contactInfo) {
        log.info("Sending contact us form with accept alert");
        Allure.step("Send contact us form", () -> {
            fillContactUsForm(contactInfo);
            self.page().onDialog(Dialog::accept);
            submit();
        });
        Allure.step("Accept alert");
    }

    public void sendFormWithDismissAlert(ContactInfo contactInfo) {
        log.info("Sending contact us form with dismiss alert");
        Allure.step("Send contact us form", () -> {
            fillContactUsForm(contactInfo);
            self.page().onDialog(Dialog::dismiss);
            submit();
        });
        Allure.step("Dismiss alert");
    }

    private void fillContactUsForm(ContactInfo contactInfo) {
        fillName(contactInfo.name());
        fillEmail(contactInfo.email());
        fillSubject(contactInfo.subject());
        fillMessage(contactInfo.message());
        uploadFile(contactInfo.pathToFile());
    }

    @Step("Fill name field with value: {name}")
    private void fillName(String name) {
        log.info("Fill name: {}", name);
        locator.name().fill(name);
    }

    @Step("Fill email field with value: {email}")
    private void fillEmail(String email) {
        log.info("Fill email: {}", email);
        locator.email().fill(email);
    }

    @Step("Fill subject field with value: {subject}")
    private void fillSubject(String subject) {
        log.info("Fill subject: {}", subject);
        locator.subject().fill(subject);
    }

    @Step("Fill message field with value: {message}")
    private void fillMessage(String message) {
        log.info("Fill message: {}", message);
        locator.message().fill(message);
    }

    @Step("Upload file: {filePath}")
    private void uploadFile(Path pathToFile) {
        log.info("Uploading file from path: {}", pathToFile.toAbsolutePath());
        locator.file().setInputFiles(pathToFile.toAbsolutePath());
    }

    @Step("Click submit button")
    private void submit() {
        log.info("Clicking submit button");
        locator.submit().click();
    }

    @Step("Click submit button")
    public MainPage home() {
        log.info("Clicking home button");
        locator.home().click();
        return new MainPage();
    }

    @Step("Check success status is displayed")
    public void checkStatusMessageIsSuccess() {
        log.info("Check success status message");
        assertThat(locator.statusMessage()).isVisible();
        assertThat(locator.statusMessage()).hasClass("status alert alert-success");
    }

    @Step("Check status message has text: {expectedText}")
    public void checkStatusMessageHasText(String expectedText) {
        log.info("Check status message contains text: {}", expectedText);
        assertThat(locator.statusMessage()).isVisible();
        assertThat(locator.statusMessage()).hasText(expectedText);
    }

    @Override
    public ContactUsForm shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        locator.name().waitFor(VISIBLE_CONDITION);
        locator.email().waitFor(VISIBLE_CONDITION);
        locator.submit().waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
    }

}
