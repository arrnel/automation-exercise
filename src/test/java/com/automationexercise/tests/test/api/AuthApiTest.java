package com.automationexercise.tests.test.api;

import com.automationexercise.tests.api.core.store.ThreadSafeCookieStore;
import com.automationexercise.tests.jupiter.anno.User;
import com.automationexercise.tests.jupiter.anno.meta.ApiTest;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTest
@DisplayName("[API] Auth test")
class AuthApiTest extends BaseTest {

    @User
    @Test
    @DisplayName("Check should sign in with correct credentials")
    void shouldSignInWithCorrectCredentialsTest(UserDTO user) {

        // Steps
        var token = authApiService.signIn(user.email(), user.testData().password());

        // Assertions
        assertNotNull(token, "Check token not equals null");

    }

    @User
    @Test
    @DisplayName("Check should not sign in with invalid credentials")
    void shouldNotSignInWithInvalidCredentialsTest(UserDTO user) {

        // Steps
        authApiClient.sendGetCsrfTokenRequest();
        var token = ThreadSafeCookieStore.INSTANCE.getCsrfToken();

        authApiClient.sendLoginRequest(user.email(), DataGenerator.generatePassword(), token);

        // Assertions
    }

    @User
    @Test
    @DisplayName("Check should sign in with correct credentials")
    void shouldLogoutWithAuthorizedUserTest(UserDTO user) {

        // Steps
        authApiService.signIn(user.email(), user.testData().password());
        authApiService.logout();

    }

}
