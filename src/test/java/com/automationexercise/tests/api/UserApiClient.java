package com.automationexercise.tests.api;

import com.automationexercise.tests.api.core.asertions.AssertableResponse;
import com.automationexercise.tests.models.UserDTO;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface UserApiClient {

    @Nonnull
    AssertableResponse createNewUser(UserDTO user);

    @Nonnull
    AssertableResponse getUserByEmail(String email);

    @Nonnull
    AssertableResponse updateUser(UserDTO user);

    @Nonnull
    AssertableResponse deleteUser(String email, String password);

}
