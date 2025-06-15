package com.automationexercise.tests.service;

import com.automationexercise.tests.models.api.Tokens;

public interface AuthApiService {

    Tokens signIn(String email, String password);

    void logout();

}
