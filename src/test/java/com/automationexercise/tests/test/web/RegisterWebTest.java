package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.meta.DisabledByIssue;
import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.RegisterComponentTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.RegisterPageTag;
import com.automationexercise.tests.jupiter.extension.UsersRemoverExtension;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.page.auth.LoginPage;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@WebTest
@RegisterPageTag
@RegisterComponentTag
@DisplayName("[WEB] Registration tests")
class RegisterWebTest extends BaseTest {

    @Owner("@arrnel")

    @RegisterComponentTag
    @Test
    @DisplayName("Should register with valid data")
    void shouldRegisterWithValidDataTest() {

        // Data
        var user = DataGenerator.generateUser();

        // Steps
        new LoginPage().open()
                .register(user.name(), user.email())
                .checkNameHasText(user.name())
                .checkEmailHasText(user.email())
                .sendRegisterData(user)

                // Assertions
                .checkAccountCreatedMessagesVisible();

        UsersRemoverExtension.addUserToRemove(user);

    }

    @DisabledByIssue(issueId = "2")
    @Owner("@arrnel")
    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#validAnotherSensitiveDataProvider")
    @DisplayName("Should register with valid data")
    void shouldRegisterWithValidSensitiveDataTest(String caseName, UserDTO user) {
        // Steps
        new LoginPage().open()
                .register(user.name(), user.email())
                .checkNameHasText(user.name())
                .checkEmailHasText(user.email())
                .sendRegisterData(user)

                // Assertions
                .checkAccountCreatedMessagesVisible();

        UsersRemoverExtension.addUserToRemove(user);
    }

    @DisabledByIssue(issueId = "2")
    @Owner("@arrnel")
    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("com.automationexercise.tests.test.data.UserDataProvider#invalidAnotherSensitiveDataProvider")
    @DisplayName("Should not register with invalid data")
    void shouldNotRegisterWithInvalidSensitiveDataTest(String caseName, UserDTO user) {
        // Steps
        new LoginPage().open()
                .register(user.name(), user.email())
                .checkNameHasText(user.name())
                .checkEmailHasText(user.email())
                .sendRegisterDataWithError(user)

                // Assertions
                .checkErrorIsVisible("");

        UsersRemoverExtension.addUserToRemove(user); // Remove created users if user created
    }

}
