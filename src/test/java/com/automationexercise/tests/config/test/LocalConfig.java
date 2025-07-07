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
    public String domain() {
        return "www.automationexercise.com";
    }

    @Nonnull
    @Override
    public String baseApiUrl() {
        return "https://automationexercise.com/api";
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    @Nonnull
    @Override
    public String remoteUrl() {
        return "";
    }

    @Override
    public String pathToScreenshots() {
        return System.getProperty("user.dir") + "/src/.screen-output/screenshots/local";
    }

}
