package com.automationexercise.tests.service.impl;

import com.automationexercise.tests.api.UserApiClient;
import com.automationexercise.tests.config.service.ServiceConfig;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.models.api.HttpStatus;
import com.automationexercise.tests.service.UserApiService;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

import static com.automationexercise.tests.api.core.condition.Conditions.*;
import static com.automationexercise.tests.models.api.HttpStatus.CREATED;
import static com.automationexercise.tests.models.api.HttpStatus.OK;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.URLENC;

@Slf4j
@ParametersAreNonnullByDefault
public class UserServiceImpl implements UserApiService {

    private static final ServiceConfig SERVICE_CONFIG = ServiceConfig.getInstance();

    private final UserApiClient userClient = SERVICE_CONFIG.getUserApiClient();

    @Nonnull
    @Override
    @Step("Create new user: {user.email}")
    public UserDTO createUser(UserDTO user) {
        log.info("Creating new user: {}", user);
        userClient.createNewUser(user)
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(CREATED));
        var userId = userClient.getUserByEmail(user.email())
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(OK))
                .extract()
                .asPojo("user.id", Long.class);
        return user.id(userId);
    }

    @Nonnull
    @Override
    @Step("Get user by email: {email}")
    public Optional<UserDTO> getUserByEmail(String email) {
        log.info("Get user by email: {}", email);
        return getUser(email);
    }

    @Nonnull
    @Override
    @Step("Update user: {user.email}")
    public UserDTO updateUser(UserDTO user) {
        log.info("Update user: {}", user);
        userClient.updateUser(user)
                .shouldHave(statusCode(OK))
                .shouldHave(contentType(URLENC));
        return userClient.getUserByEmail(user.email())
                .shouldHave(statusCode(OK))
                .shouldHave(contentType(JSON))
                .extract()
                .asPojo("user", UserDTO.class);
    }

    @Override
    @Step("Remove user with email: {user.email}")
    public void deleteUser(UserDTO user) {
        log.info("Delete user: {}", user);
        getUser(user.email())
                .ifPresentOrElse(
                        u -> userClient
                                .deleteUser(user.email(), user.testData().password())
                                .shouldHave(statusCode(OK)),
                        () -> Allure.step("User not exists or is already removed")
                );
    }

    private Optional<UserDTO> getUser(String email) {
        var response = userClient.getUserByEmail(email);
        try {
            return Optional.of(
                    response
                            .shouldHave(statusCode(OK))
                            .shouldHave(bodyStatusCode(OK))
                            .extract()
                            .asPojo("user", UserDTO.class));
        } catch (AssertionError e) {
            log.error("Error: {}", e.getMessage());
            Integer responseCode = response.extract().asValue("responseCode");
            if (HttpStatus.NOT_FOUND == responseCode)
                return Optional.empty();

            throw e;
        }
    }

}
