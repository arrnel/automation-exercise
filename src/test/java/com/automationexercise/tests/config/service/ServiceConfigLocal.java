package com.automationexercise.tests.config.service;

import com.automationexercise.tests.api.*;
import com.automationexercise.tests.api.impl.*;
import com.automationexercise.tests.service.*;
import com.automationexercise.tests.service.impl.*;

import javax.annotation.Nonnull;

enum ServiceConfigLocal implements ServiceConfig {

    INSTANCE;

    @Nonnull
    @Override
    public AuthApiClient getAuthApiClient() {
        return new AuthApiClientImpl();
    }

    @Nonnull
    @Override
    public BrandApiClient getBrandApiClient() {
        return new BrandApiClientImpl();
    }

    @Nonnull
    @Override
    public ProductApiClient getProductApiClient() {
        return new ProductApiClientImpl();
    }

    @Nonnull
    @Override
    public UserApiClient getUserApiClient() {
        return new UserApiClientImpl();
    }

    @Nonnull
    @Override
    public VerifyLoginApiClient getVerifyLoginApiClient() {
        return new VerifyLoginApiClientImpl();
    }

    @Nonnull
    @Override
    public AuthApiService getAuthApiService() {
        return new AuthServiceImpl();
    }

    @Nonnull
    @Override
    public BrandApiService getBrandApiService() {
        return new BrandApiServiceImpl();
    }

    @Nonnull
    @Override
    public ProductApiService getProductApiService() {
        return new ProductServiceImpl();
    }

    @Nonnull
    @Override
    public UserApiService getUserApiService() {
        return new UserServiceImpl();
    }

    @Nonnull
    @Override
    public VerifyLoginApiService getVerifyLoginApiService() {
        return new VerifyLoginServiceImpl();
    }

    @Nonnull
    @Override
    public GithubApiClient getGitHubApiClient() {
        return new GithubApiClientImpl();
    }

}
