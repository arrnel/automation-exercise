package com.automationexercise.tests.test.api;

import com.automationexercise.tests.jupiter.anno.User;
import com.automationexercise.tests.jupiter.anno.meta.ApiTest;
import com.automationexercise.tests.jupiter.extension.UsersRemoverExtension;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.automationexercise.tests.api.core.condition.Conditions.*;
import static com.automationexercise.tests.models.api.HttpStatus.*;
import static com.automationexercise.tests.util.matcher.UserDTOAssertion.areUserCommonDataEqualWithoutId;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ApiTest
@DisplayName("[API] Users test")
class UserApiTest extends BaseTest {

    private static final String ACCOUNT_NOT_FOUND_MESSAGE_IN_GET_REQUEST = "Account not found with this email, try another email!",
            ACCOUNT_NOT_FOUND_MESSAGE = "Account not found!",
            EMAIL_ALREADY_EXIST_MESSAGE = "Email already exists!",
            SUCCESSFUL_CREATE_MESSAGE = "User created!",
            SUCCESSFUL_UPDATE_MESSAGE = "User updated!",
            SUCCESSFUL_DELETE_MESSAGE = "User deleted!",
            UNABLE_TO_UPDATE_MESSAGE = "Unable to update user!";


    @Test
    @DisplayName("Should create user with valid data")
    void shouldCreateUserWithValidDataTest() {

        // Data
        var user = DataGenerator.generateUser();

        // Add to extension users list for remove after test
        UsersRemoverExtension.addUserToRemove(user);

        // Steps
        userApiClient.createNewUser(user)

                // Assertions
                //.shouldHave(contentType(JSON)) <- Current realization return HTML
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(CREATED))
                .shouldHave(bodyField("message", equalTo(SUCCESSFUL_CREATE_MESSAGE)));

        var result = userApiService.getUserByEmail(user.email())
                .orElse(UserDTO.empty());

        // Assertions
        Assertions.assertAll("Check created user are the same",
                () -> assertNotNull(result.id()),
                () -> areUserCommonDataEqualWithoutId(user, result)
        );

    }

    @User
    @Test
    @DisplayName("Should not create user with exists email")
    void shouldCreateUserWithExistsEmailTest(UserDTO user) {

        // Data
        var newUser = DataGenerator.generateUser()
                .email(user.email());

        // Steps
        userApiClient.createNewUser(newUser)

                // Assertions
                //.shouldHave(contentType(JSON)) <- Current realization return HTML
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(BAD_REQUEST))
                .shouldHave(bodyField("message", equalTo(EMAIL_ALREADY_EXIST_MESSAGE)));

    }

    @ParameterizedTest(name = "Case: field [{0}] is not provided")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#absentDataProvider")
    @DisplayName("Should not create user if mandatory param is missing")
    void shouldNotCreateUserIfMandatoryParamIsMissingTest(String absentParam,
                                                          UserDTO user
    ) {
        // Steps
        userApiClient.createNewUser(user)

                // Assertions
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(BAD_REQUEST))
                .shouldHave(bodyField(
                        "message",
                        equalTo("Bad request, %s parameter is missing in POST request.".formatted(absentParam))));
    }

    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#validEmailProvider")
    @DisplayName("Should create user with valid email")
    void shouldCreateUserWithValidEmailTest(String caseTitle,
                                            String email
    ) {
        // Data
        var user = DataGenerator.generateUser()
                .email(email);

        // Add to extension users list for remove after test
        UsersRemoverExtension.addUserToRemove(user);

        // Steps & Assertions
        userApiClient.createNewUser(user)
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(OK))
                .shouldHave(bodyField("message", equalTo(SUCCESSFUL_CREATE_MESSAGE)));
    }

    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#invalidEmailProvider")
    @DisplayName("Should not create user with invalid email")
    void shouldNotCreateUserWithInvalidEmailTest(String caseTitle,
                                                 String email
    ) {
        // Data
        var user = DataGenerator.generateUser()
                .email(email);

        // Add to extension users list for remove after test
        UsersRemoverExtension.addUserToRemove(user); // <- Cause system allow to register user with invalid email

        // Steps
        userApiClient.createNewUser(user)

                // Assertions
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(BAD_REQUEST))
                .shouldHave(bodyField("message", equalTo("Invalid email!")));
    }

    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#validPasswordProvider")
    @DisplayName("Should create user with valid password")
    void shouldCreateUserWithValidPasswordTest(String caseTitle,
                                               String password
    ) {
        // Data
        var user = DataGenerator.generateUser()
                .password(password);

        // Add to extension users list for remove after test
        UsersRemoverExtension.addUserToRemove(user);

        // Steps & Assertions
        userApiClient.createNewUser(user)
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(OK))
                .shouldHave(bodyField("message", equalTo(SUCCESSFUL_CREATE_MESSAGE)));
    }

    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#invalidPasswordProvider")
    @DisplayName("Should not create user with invalid password")
    void shouldNotCreateUserWithInvalidPasswordTest(String caseTitle,
                                                    String password
    ) {
        // Data
        var user = DataGenerator.generateUser()
                .password(password);

        // Add to extension users list for remove after test
        UsersRemoverExtension.addUserToRemove(user); // <- Cause system allow to register user with invalid email

        // Steps
        userApiClient.createNewUser(user)

                // Assertions
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(BAD_REQUEST))
                .shouldHave(bodyField("message", equalTo("Invalid password!")));
    }

    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#validAnotherSensitiveDataProvider")
    @DisplayName("Should create user with valid sensitive data")
    void shouldCreateUserWithValidSensitiveDataTest(String caseTitle,
                                                    String name
    ) {
        // Data
        var user = DataGenerator.generateUser()
                .name(name);

        // Add to extension users list for remove after test
        UsersRemoverExtension.addUserToRemove(user);

        // Steps & Assertions
        userApiClient.createNewUser(user)
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(OK))
                .shouldHave(bodyField("message", equalTo(SUCCESSFUL_CREATE_MESSAGE)));
    }

    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#invalidAnotherSensitiveDataProvider")
    @DisplayName("Should not create user with invalid sensitive data name")
    void shouldNotCreateUserWithInvalidAnotherSensitiveDataTest(String caseTitle,
                                                                String name
    ) {
        // Data
        var user = DataGenerator.generateUser()
                .name(name);

        // Add to extension users list for remove after test
        UsersRemoverExtension.addUserToRemove(user); // <- Cause system allow to register user with invalid email

        // Steps
        userApiClient.createNewUser(user)

                // Assertions
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(BAD_REQUEST))
                .shouldHave(bodyField("message", equalTo("Invalid name!")));
    }

    @User
    @Test
    @DisplayName("Should get user if user exists")
    void shouldGetUserIfUserExistsTest(UserDTO user) {
        // Steps
        var result = userApiClient.getUserByEmail(user.email())

                // Assertions
                //.shouldHave(contentType(JSON)) <- Current realization return HTML
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(OK))
                .extract()
                .asPojo("user", UserDTO.class);

        assertTrue(areUserCommonDataEqualWithoutId(user, result));
    }

    @Test
    @DisplayName("Should return NOT_FOUND if email unknown")
    void shouldReturnNotFoundIfEmailUnknownTest() {
        // Steps
        userApiClient.getUserByEmail(DataGenerator.generateEmail())

                // Assertions
                //.shouldHave(contentType(JSON)) <- Current realization return HTML
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(NOT_FOUND))
                .shouldHave(bodyField("message", equalTo(ACCOUNT_NOT_FOUND_MESSAGE_IN_GET_REQUEST)));
    }

    @User
    @Test
    @DisplayName("Should update user information")
    void shouldUpdateUserTest(UserDTO user) {
        // Data
        UserDTO updatedUser = DataGenerator.generateUser()
                .email(user.email());

        // Steps
        userApiClient.updateUser(updatedUser)
                //.shouldHave(contentType(JSON)) <- Current realization return HTML
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(OK))
                .shouldHave(bodyField("message", equalTo(SUCCESSFUL_UPDATE_MESSAGE)));

        var result = userApiService.getUserByEmail(user.email())
                .orElse(UserDTO.empty());

        // Assertions
        Assertions.assertAll("Check updated user has expected data",
                () -> assertEquals(user.id(), result.id()),
                () -> assertTrue(areUserCommonDataEqualWithoutId(updatedUser, result))
        );
    }

    @Test
    @DisplayName("Should return NOT_FOUND if updating user email unknown")
    void shouldReturnNotFoundIfUpdatingUserEmailUnknownTest() {
        // Data
        UserDTO updatedUser = DataGenerator.generateUser();

        // Steps
        userApiClient.updateUser(updatedUser)

                // Assertions
                //.shouldHave(contentType(JSON)) <- Current realization return HTML
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(NOT_FOUND))
                .shouldHave(bodyField("message", equalTo(ACCOUNT_NOT_FOUND_MESSAGE)));
    }

    @ParameterizedTest(name = "Case: field [{0}] is not provided")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#absentDataProvider")
    @DisplayName("Should not update user if mandatory param is missing")
    void shouldNotUpdateUserIfMandatoryParamIsMissingTest(String absentParam,
                                                          UserDTO userToUpdate,
                                                          @User UserDTO createdUser
    ) {
        // Steps
        userApiClient.updateUser(
                        userToUpdate.email(createdUser.email())
                                .password(createdUser.password())
                )

                // Assertions
                //.shouldHave(contentType(JSON)) < -Current realization return HTML
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(BAD_REQUEST))
                .shouldHave(bodyField(
                        "message",
                        equalTo("Bad request, %s parameter is missing in POST request.".formatted(absentParam))));
    }

    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#validAnotherSensitiveDataProvider")
    @DisplayName("Should update user with valid sensitive data")
    void shouldUpdateUserWithValidSensitiveDataTest(String caseTitle,
                                                    UserDTO userToUpdate,
                                                    @User UserDTO createdUser
    ) {

        // Steps
        userApiClient.updateUser(
                        userToUpdate.email(createdUser.email())
                                .password(createdUser.password())
                )

                // Assertions
                //.shouldHave(contentType(JSON)) <- Current realization return HTML
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(OK))
                .shouldHave(bodyField("message", equalTo(SUCCESSFUL_UPDATE_MESSAGE)));
    }

    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#invalidAnotherSensitiveDataProvider")
    @DisplayName("Should not update user with invalid sensitive data name")
    void shouldNotUpdateUserWithInvalidAnotherSensitiveDataTest(String caseTitle,
                                                                UserDTO userToUpdate,
                                                                @User UserDTO createdUser
    ) {

        // Steps
        userApiClient.updateUser(
                        userToUpdate.email(createdUser.email())
                                .password(createdUser.password())
                )

                // Assertions
                //.shouldHave(contentType(JSON)) <- Current realization return HTML
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(BAD_REQUEST))
                .shouldHave(bodyField("message", equalTo(UNABLE_TO_UPDATE_MESSAGE)));
    }

    @User
    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUserTest(UserDTO user) {
        // Steps
        userApiClient.deleteUser(user.email(), user.password())

                // Assertions
                //.shouldHave(contentType(JSON)) <- Current realization return HTML
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(OK))
                .shouldHave(bodyField("message", equalTo(SUCCESSFUL_DELETE_MESSAGE)));

        assertTrue(userApiService.getUserByEmail(user.email()).isEmpty());
    }

    @Test
    @DisplayName("Should return NOT_FOUND if deleting user email unknown")
    void shouldReturnNotFoundIfDeletingUserEmailUnknownTest() {
        // Data
        UserDTO user = DataGenerator.generateUser();

        // Steps
        userApiClient.deleteUser(user.email(), user.password())

                // Assertions
                //.shouldHave(contentType(JSON)) <- Current realization return HTML
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(NOT_FOUND))
                .shouldHave(bodyField("message", equalTo(ACCOUNT_NOT_FOUND_MESSAGE)));
    }


}
