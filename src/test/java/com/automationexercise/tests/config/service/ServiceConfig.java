package com.automationexercise.tests.config.service;

import com.automationexercise.tests.api.*;
import com.automationexercise.tests.service.*;

import javax.annotation.Nonnull;

public interface ServiceConfig {

    static ServiceConfig getInstance() {
        return ServiceConfigLocal.INSTANCE;
    }

    // Client
    @Nonnull
    AuthApiClient getAuthApiClient();

    @Nonnull
    BrandApiClient getBrandApiClient();

    @Nonnull
    ProductApiClient getProductApiClient();

    @Nonnull
    UserApiClient getUserApiClient();

    @Nonnull
    VerifyLoginApiClient getVerifyLoginApiClient();

    // Service
    @Nonnull
    AuthApiService getAuthApiService();

    @Nonnull
    BrandApiService getBrandApiService();

    @Nonnull
    ProductApiService getProductApiService();

    @Nonnull
    UserApiService getUserApiService();

    @Nonnull
    VerifyLoginApiService getVerifyLoginApiService();

    @Nonnull
    GithubApiClient getGitHubApiClient();

}
