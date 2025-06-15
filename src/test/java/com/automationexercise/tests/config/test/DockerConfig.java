package com.automationexercise.tests.config.test;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
enum DockerConfig implements Config {

    INSTANCE;

    @Nonnull
    @Override
    public String baseUrl() {
        return System.getProperty(
                "test.env.base_url",
                "https://www.automationexercise.com"
        );
    }

    @Nonnull
    @Override
    public String baseApiUrl() {
        return System.getProperty(
                "test.env.base_api_url",
                "https://www.automationexercise.com/api"
        );
    }

    @Nonnull
    @Override
    public String remoteUrl() {
        return System.getProperty(
                "test.env.remote_url",
                "http://localhost:4444/wd/hub"
        );
    }

}
