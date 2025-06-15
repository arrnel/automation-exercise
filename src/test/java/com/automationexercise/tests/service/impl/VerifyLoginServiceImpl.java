package com.automationexercise.tests.service.impl;

import com.automationexercise.tests.api.VerifyLoginApiClient;
import com.automationexercise.tests.config.service.ServiceConfig;
import com.automationexercise.tests.models.CredentialsDTO;
import com.automationexercise.tests.service.VerifyLoginApiService;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@ParametersAreNonnullByDefault
public class VerifyLoginServiceImpl implements VerifyLoginApiService {

    public static final String USER_EXIST_MESSAGE = "User exists!",
            USER_NOT_EXIST_MESSAGE = "User not found!";

    private final VerifyLoginApiClient verifyLoginClient = ServiceConfig.getInstance().getVerifyLoginApiClient();

    @Override
    @Step("Check user with credentials exists")
    public boolean verifyLogin(CredentialsDTO credentials) {
        log.info("Verifying user with credentials exists: {}", credentials);
        try {
            String message = verifyLoginClient.sendVerifyLoginRequest(credentials)
                    .extract()
                    .asValue("message");
            return switch (message) {
                case USER_EXIST_MESSAGE -> true;
                case USER_NOT_EXIST_MESSAGE -> false;
                default -> throw new IllegalStateException("Unexpected response message: %s".formatted(message));
            };
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

}
