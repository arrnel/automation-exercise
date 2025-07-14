package com.automationexercise.tests.test.api;

import com.automationexercise.tests.jupiter.anno.User;
import com.automationexercise.tests.jupiter.anno.meta.ApiTest;
import com.automationexercise.tests.models.CredentialsDTO;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.models.api.HttpStatus;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.automationexercise.tests.api.core.condition.Conditions.bodyField;
import static com.automationexercise.tests.api.core.condition.Conditions.bodyStatusCode;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ApiTest
@Feature("[API] Verify login tests")
@DisplayName("[API] Verify Login test")
class VerifyLoginApiTest extends BaseTest {

    private static final String INVALID_CREDENTIALS_MESSAGE = "Bad request, email or password parameter is missing in POST request.";

    @User
    @Test
    @DisplayName("Should return TRUE if user exists with expected credentials")
    void shouldReturnTrueIfUserExistsWithValidCredentialsTest(UserDTO user) {

        // Data
        var credentials = new CredentialsDTO(user.email(), user.password());

        // Steps
        var result = verifyLoginApiService.verifyLogin(credentials);

        // Assertions
        assertTrue(result, "Should exist user with credentials");

    }

    @User
    @Test
    @DisplayName("Should return FALSE if password is invalid")
    void shouldReturnFalseIfUserExistsWithInvalidPasswordTest(UserDTO user) {

        // Data
        var credentials = new CredentialsDTO(
                user.email(),
                DataGenerator.generatePassword()
        );

        // Steps
        var result = verifyLoginApiService.verifyLogin(credentials);

        // Assertions
        assertFalse(result, "Should not exist user with credentials");

    }

    @Test
    @DisplayName("Should return FALSE if user with expected email not exists")
    void shouldReturnFalseIfUserExistsWithValidCredentialsTest() {

        // Data
        var credentials = new CredentialsDTO(
                DataGenerator.generateEmail(),
                DataGenerator.generatePassword()
        );

        // Steps
        var result = verifyLoginApiService.verifyLogin(credentials);

        // Assertions
        assertFalse(result, "Should not exist user with credentials");

    }

    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#invalidCredentialsProvider")
    @DisplayName("Should return BAD_REQUEST if provide invalid data")
    void shouldReturnBadRequestIfCredentialsIsInvalidTest(String caseName, String email, String password) {

        // Data
        var credentials = new CredentialsDTO(email, password);

        // Steps
        verifyLoginApiClient.sendVerifyLoginRequest(credentials)

                // Assertions
                .shouldHave(bodyStatusCode(HttpStatus.BAD_REQUEST))
                .shouldHave(bodyField("message", equalTo(INVALID_CREDENTIALS_MESSAGE)));

    }

}
