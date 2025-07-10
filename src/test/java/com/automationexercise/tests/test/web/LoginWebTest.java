package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.ApiLogin;
import com.automationexercise.tests.jupiter.anno.User;
import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.LoginComponentTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.LoginPageTag;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.page.auth.LoginPage;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WebTest
@LoginPageTag
@LoginComponentTag
@DisplayName("[WEB] Login tests")
class LoginWebTest extends BaseTest {

    @User
    @Test
    @DisplayName("Should sign in with valid credentials")
    void shouldSignInWithValidCredentialsTest(UserDTO user) {
        new LoginPage().open()
                .signIn(user.email(), user.password())
                .header()
                .checkUserIsLoggedIn();
    }

    @User
    @Test
    @DisplayName("Should not sign in with invalid password")
    void shouldNotSignInWithInvalidPasswordTest(UserDTO user) {
        new LoginPage().open()
                .signInWithError(user.email(), DataGenerator.generatePassword())
                .checkSignInErrorHasInvalidPasswordMessage();
    }

    @User
    @Test
    @DisplayName("Should not sign in with invalid email")
    void shouldNotSignInWithInvalidEmailTest(UserDTO user) {
        new LoginPage().open()
                .signInWithError(DataGenerator.generateEmail(), user.password())
                .checkSignInErrorHasInvalidPasswordMessage();
    }

    @User
    @Test
    @DisplayName("Should not sign in with removed user credentials")
    void shouldNotSignInWithRemovedUserCredentialsTest(UserDTO user) {
        userApiService.deleteUser(user);
        new LoginPage().open()
                .signInWithError(user.email(), user.password())
                .checkSignInErrorHasInvalidPasswordMessage();
    }

    @ApiLogin
    @Test
    @DisplayName("Should logout")
    void shouldLogoutTest() {
        // Steps
        new LoginPage().open()
                .header().logout()
                // Assertions
                .header().checkUserIsNotAuthorized();
    }

    @User(email = "removed_user@test.test")
    @ApiLogin(email = "removed_user@test.test")
    @Test
    @DisplayName("Should logout with removed user")
    void shouldLogoutRemovedUserTest(UserDTO user) {
        // Steps
        var loginPage = new LoginPage();
        loginPage.open();
        userApiService.deleteUser(user);
        loginPage.header().logout()

                // Assertions
                .header().checkUserIsNotAuthorized();
    }

}
