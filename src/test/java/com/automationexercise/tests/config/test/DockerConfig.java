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
    public String domain() {
        return "www.automationexercise.com";
    }

    @Nonnull
    @Override
    public String baseApiUrl() {
        return System.getProperty(
                "test.env.base_api_url",
                "https://www.automationexercise.com/api"
        );
    }

    @Override
    public boolean isRemote() {
        return true;
    }

    @Nonnull
    @Override
    public String remoteUrl() {
        return System.getProperty(
                "test.env.remote_url",
                "ws://playwright-server:8080"
        );
    }

    @Override
    public String pathToScreenshots() {
        return System.getProperty("user.dir") + "/src/.screen-output/screenshots/remote";
    }

}
