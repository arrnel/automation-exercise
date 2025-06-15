package com.automationexercise.tests.service;

import com.automationexercise.tests.models.CredentialsDTO;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface VerifyLoginApiService {

    boolean verifyLogin(CredentialsDTO credentials);

}
