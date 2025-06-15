package com.automationexercise.tests.service;

import com.automationexercise.tests.models.UserDTO;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
public interface UserApiService {

    @Nonnull
    UserDTO createUser(UserDTO user);

    @Nonnull
    Optional<UserDTO> getUserByEmail(String email);

    @Nonnull
    UserDTO updateUser(UserDTO user);

    void deleteUser(UserDTO user);

}
