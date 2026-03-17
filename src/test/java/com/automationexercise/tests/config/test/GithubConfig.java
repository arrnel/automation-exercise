package com.automationexercise.tests.config.test;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
enum GithubConfig implements Config {

    INSTANCE;

    @Nonnull
    @Override
    public String baseUrl() {
        return "https://automationexercise.com";
    }

    @Override
    public String remoteUrl() {
        return "ws://localhost:9222/";
    }

    @Nonnull
    @Override
    public String domain() {
        return "www.automationexercise.com";
    }

    @Nonnull
    @Override
    public String baseApiUrl() {
        return "https://automationexercise.com/api";
    }

    @Nonnull
    @Override
    public String pathToScreenshotsDirectory() {
        return System.getProperty("user.dir") + "/src/.screen-output/screenshots/remote";
    }

    @Nonnull
    @Override
    public String pathToVideosDirectory() {
        return System.getProperty("user.dir") + "/src/.screen-output/video/remote";
    }

    @Override
    public String allureReportUrl() {
        return System.getenv("ALLURE_DOCKER_API");
    }

}
