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

    @Nonnull
    @Override
    public String pathToScreenshotsDirectory() {
        return System.getProperty("user.dir") + "/src/.screen-output/screenshots/docker";
    }

    @Nonnull
    @Override
    public String pathToVideosDirectory() {
        return System.getProperty("user.dir") + "/src/.screen-output/video/docker";
    }

    @Override
    public String allureReportUrl() {
        return "";
    }

}
