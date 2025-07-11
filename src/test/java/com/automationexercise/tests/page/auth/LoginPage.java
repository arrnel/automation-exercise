package com.automationexercise.tests.page.auth;

import com.automationexercise.tests.page.BasePage;
import com.automationexercise.tests.page._component.auth.LoginComponent;
import com.automationexercise.tests.page._component.auth.RegisterComponent;
import com.automationexercise.tests.page.products.MainPage;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@ParametersAreNonnullByDefault
public class LoginPage extends BasePage<LoginPage> {

    public static final String URL = CFG.baseUrl() + "/login";

    private final LoginComponent login;
    private final RegisterComponent register;

    public LoginPage() {
        login = new LoginComponent(page.locator(".login-form"));
        register = new RegisterComponent(page.locator(".signup-form"));
    }

    @Nonnull
    @Step("Navigate to the [Login] page")
    public LoginPage open() {
        log.info("Navigate to the [Login] page");
        page.navigate(URL);
        return this;
    }

    @Nonnull
    public MainPage signIn(String email, String password) {
        log.info("Sign in by username = [{}] and password = [{}]", email, password);
        login.signIn(email, password);
        return new MainPage();
    }

    @Nonnull
    public LoginPage signInWithError(String email, String password) {
        login.signIn(email, password);
        return this;
    }

    @Nonnull
    public RegisterPage register(String name, String email) {
        register.register(name, email);
        return new RegisterPage();
    }

    @Nonnull
    public LoginPage registerWithError(String name, String email) {
        register.register(name, email);
        return new LoginPage();
    }

    @Nonnull
    public LoginPage checkSignInErrorHasMessage(String message) {
        login.checkSignInErrorHasMessage(message);
        return this;
    }

    @Nonnull
    public LoginPage checkSignInErrorHasInvalidPasswordMessage() {
        login.checkSignInErrorHasInvalidPasswordMessage();
        return this;
    }

    @Nonnull
    @Override
    public LoginPage shouldVisiblePage() {
        login.shouldVisibleComponent();
        register.shouldVisibleComponent();
        return this;
    }

    @Override
    public void shouldNotVisiblePage() {
        login.shouldNotVisibleComponent();
        register.shouldNotVisibleComponent();
    }

}
