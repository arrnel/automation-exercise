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

}
