package com.automationexercise.tests.api.impl;

import com.automationexercise.tests.api.AuthApiClient;
import com.automationexercise.tests.api.core.RestClient;
import com.automationexercise.tests.api.core.asertions.AssertableResponse;
import com.automationexercise.tests.models.api.Token;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@ParametersAreNonnullByDefault
public class AuthApiClientImpl extends RestClient implements AuthApiClient {

    private static final String CSRF_KEY = "csrfmiddlewaretoken";

    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";

    public AuthApiClientImpl() {
        super(CFG.baseUrl(), ContentType.HTML, ContentType.URLENC);
    }

    @Nonnull
    @Step("Send request [GET]:/")
    public AssertableResponse sendGetCsrfTokenRequest() {
        log.debug("Send request [GET]:/");
        return new AssertableResponse(
                given()
                        .get(LOGIN_URL));
    }

    @Nonnull
    @Step("Send [POST]:/login")
    public AssertableResponse sendLoginRequest(String email, String password, Token csrf) {
        log.debug("Send POST:{}", LOGIN_URL);
        return new AssertableResponse(
                given()
                        .cookie(csrf.type(), csrf.value())
                        .header("referer", CFG.baseUrl() + LOGIN_URL)
                        .formParam(CSRF_KEY, csrf.value())
                        .formParam("email", email)
                        .formParam("password", password)
                        .post(LOGIN_URL));
    }

    @Nonnull
    @Step("Send request [GET]:/login")
    public AssertableResponse sendLoginRequest(String email, String password, Cookie csrf, String csrfFormData) {
        log.debug("Send request GET:{}", LOGIN_URL);
        return new AssertableResponse(
                given()
                        .cookie(csrf)
                        .header("referer", CFG.baseUrl() + LOGIN_URL)
                        .formParam(CSRF_KEY, csrfFormData)
                        .formParam("email", email)
                        .formParam("password", password)
                        .post(LOGIN_URL));
    }

    @Nonnull
    @Override
    @Step("Send request [GET]:/logout")
    public AssertableResponse sendLogoutRequest(Token csrf, Token sessionId) {
        log.debug("Send request GET:{}", LOGOUT_URL);
        return new AssertableResponse(
                given()
                        .cookie(csrf.type(), csrf.value())
                        .cookie(sessionId.type(), sessionId.value())
                        .get(LOGOUT_URL));
    }

}
