package com.automationexercise.tests.api;

import com.automationexercise.tests.api.core.asertions.AssertableResponse;
import com.automationexercise.tests.models.CredentialsDTO;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface VerifyLoginApiClient {

    @Nonnull
    AssertableResponse sendVerifyLoginRequest(CredentialsDTO credentials);

}
