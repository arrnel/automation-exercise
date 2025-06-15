package com.automationexercise.tests.service.impl;

import com.automationexercise.tests.api.AuthApiClient;
import com.automationexercise.tests.api.core.store.ThreadSafeCookieStore;
import com.automationexercise.tests.config.service.ServiceConfig;
import com.automationexercise.tests.models.api.Tokens;
import com.automationexercise.tests.service.AuthApiService;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

@Slf4j
public class AuthServiceImpl implements AuthApiService {

    private static final ServiceConfig SERVICE_CFG = ServiceConfig.getInstance();
    private final AuthApiClient authClient;

    public AuthServiceImpl() {
        this.authClient = SERVICE_CFG.getAuthApiClient();
    }

    @Nonnull
    @Step("Sign in by email and password")
    public Tokens signIn(String email, String password) {

        log.info("Sign in by email = [{}] and password = [{}]", email, password);

        authClient.sendGetCsrfTokenRequest();
        var csrf = ThreadSafeCookieStore.INSTANCE.getCsrfToken();

        authClient.sendLoginRequest(email, password, csrf);
        return new Tokens(
                ThreadSafeCookieStore.INSTANCE.getSessionToken(),
                ThreadSafeCookieStore.INSTANCE.getCsrfToken()
        );

    }

    @Override
    @Step("Logout")
    public void logout() {

        log.info("Logout");

        var token = ThreadSafeCookieStore.INSTANCE.getCsrfToken();
        var sessionId = ThreadSafeCookieStore.INSTANCE.getSessionToken();
        authClient.sendLogoutRequest(token, sessionId);
    }

}
