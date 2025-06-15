package com.automationexercise.tests.api;

import com.automationexercise.tests.api.core.asertions.AssertableResponse;
import com.automationexercise.tests.models.api.Token;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface AuthApiClient {

    @Nonnull
    AssertableResponse sendGetCsrfTokenRequest();

    @Nonnull
    AssertableResponse sendLoginRequest(String email, String password, Token csrf);

    @Nonnull
    AssertableResponse sendLogoutRequest(Token csrf, Token sessionId);

}
