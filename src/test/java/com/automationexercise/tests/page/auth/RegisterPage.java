package com.automationexercise.tests.page.auth;

import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.models.UserTitle;
import com.automationexercise.tests.page.BasePage;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class RegisterPage extends BasePage<RegisterPage> {

    private final RegisterPageLocator locator;

    public RegisterPage() {
        locator = new RegisterPageLocator(page.locator("section .container"));
    }

    @Step("Send register data")
    public AccountCreatedPage sendRegisterData(UserDTO user) {
        fillUserData(user);
        submit();
        return new AccountCreatedPage();
    }

    @Step("Send register data")
    public RegisterPage sendRegisterDataWithError(UserDTO user) {
        fillUserData(user);
        submit();
        return this;
    }

    @Step("Send register data")
    public RegisterPage checkErrorIsVisible(String text) {
        return this;
    }


    private void fillUserData(UserDTO user) {
        selectUserTitle(user.userTitle());
        fillName(user.name());
        fillPassword(user.password());
        selectBirthDay(user.birthDay());
        selectBirthMonth(user.birthMonth());
        selectBirthYear(user.birthYear());
        selectNewsletter();
        selectSpecialOffers();
        fillFirstName(user.firstName());
        fillLastName(user.lastName());
        fillCompany(user.company());
        fillAddress1(user.address1());
        fillAddress2(user.address2());
        selectCountry(user.country());
        fillState(user.state());
        fillCity(user.city());
        fillZipCode(user.zipCode());
        fillPhoneNumber(user.phoneNumber());
    }

    @Step("Check name has text: {name}")
    public RegisterPage checkNameHasText(String name) {
        log.info("Check name has text: {}", name);
        assertThat(locator.name()).hasValue(name);
        return this;
    }

    @Step("Check email has text: {email}")
    public RegisterPage checkEmailHasText(String email) {
        log.info("Check email has text: {}", email);
        assertThat(locator.email()).hasValue(email);
        return this;
    }

    @Step("Select user title: {userTitle}")
    private void selectUserTitle(UserTitle userTitle) {
        log.info("Select user title: {}", userTitle);
        locator.title(userTitle).click();
    }

    @Step("Fill name: {name}")
    private void fillName(String name) {
        log.info("Fill name: {}", name);
        locator.name().fill(name);
    }

    @Step("Fill email: {email}")
    private void fillEmail(String email) {
        log.info("Fill email: {}", email);
        locator.email().fill(email);
    }

    @Step("Fill password: {password}")
    private void fillPassword(String password) {
        log.info("Fill password: {}", password);
        locator.password().fill(password);
    }

    @Step("Select birth day: {birthDay}")
    private void selectBirthDay(int birthDay) {
        log.info("Select birth day: {}", birthDay);
        locator.birthDay().selectOption(String.valueOf(birthDay));
    }

    @Step("Select birth month: {birthMonth}")
    private void selectBirthMonth(int birthMonth) {
        log.info("Select birth month: {}", birthMonth);
        locator.birthMonth().selectOption(String.valueOf(birthMonth));
    }

    @Step("Select birth year: {birthYear}")
    private void selectBirthYear(int birthYear) {
        log.info("Select birth year: {}", birthYear);
        locator.birthYear().selectOption(String.valueOf(birthYear));
    }

    @Step("Set checked newsletter radio button")
    private void selectNewsletter() {
        log.info("Set checked newsletter radio button");
        locator.newsletter().check();
    }

    @Step("Set checked special offers radio button")
    private void selectSpecialOffers() {
        log.info("Set checked special offers radio button");
        locator.specialOffers().check();
    }

    @Step("Fill first name: {firstName}")
    private void fillFirstName(String firstName) {
        log.info("Fill first name: {}", firstName);
        locator.firstName().fill(firstName);
    }

    @Step("Fill last name: {lastName}")
    private void fillLastName(String lastName) {
        log.info("Fill last name: {}", lastName);
        locator.lastName().fill(lastName);
    }

    @Step("Fill company: {company}")
    private void fillCompany(String company) {
        log.info("Fill company: {}", company);
        locator.company().fill(company);
    }

    @Step("Fill Address 1: {address1}")
    private void fillAddress1(String address1) {
        log.info("Fill Address 1: {}", address1);
        locator.address1().fill(address1);
    }

    @Step("Fill Address 2: {address2}")
    private void fillAddress2(String address2) {
        log.info("Fill Address 2: {}", address2);
        locator.address2().fill(address2);
    }

    @Step("Select country: {country}")
    private void selectCountry(String country) {
        log.info("Select country: {}", country);
        locator.country().selectOption(String.valueOf(country));
    }

    @Step("Fill state: {state}")
    private void fillState(String state) {
        log.info("Fill state: {}", state);
        locator.state().fill(state);
    }

    @Step("Fill city: {city}")
    private void fillCity(String city) {
        log.info("Fill city: {}", city);
        locator.city().fill(city);
    }

    @Step("Fill zip code: {address2}")
    private void fillZipCode(String zipCode) {
        log.info("Fill zip code: {}", zipCode);
        locator.zipCode().fill(zipCode);
    }

    @Step("Fill phone number: {phoneNumber}")
    private void fillPhoneNumber(String phoneNumber) {
        log.info("Fill phone number: {}", phoneNumber);
        locator.phoneNumber().fill(phoneNumber);
    }

    @Step("Click on [Create account] button")
    private void submit() {
        log.info("Click on [Create account] button");
        locator.createAccount().click();
    }

    @Nonnull
    @Override
    public RegisterPage shouldVisiblePage() {
        locator.password().waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisiblePage() {
        locator.password().waitFor(DETACHED_CONDITION);
    }

}
