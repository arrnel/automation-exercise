package com.automationexercise.tests.config.test;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
enum LocalConfig implements Config {

    INSTANCE;

    @Nonnull
    @Override
    public String baseUrl() {
        return "https://automationexercise.com";
    }

    @Nonnull
    @Override
    public String baseApiUrl() {
        return "https://automationexercise.com/api";
    }

    @Nonnull
    @Override
    public String remoteUrl() {
        return "";
    }

}
