package com.automationexercise.tests.config.test;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

public interface Config {

    String CSRF_COOKIE_TITLE = "csrftoken";
    String SESSION_ID_COOKIE_TITLE = "sessionid";

    static Config getInstance() {
        var env = System.getProperty("test.env", "docker").toLowerCase();
        return switch (env) {
            case "local" -> LocalConfig.INSTANCE;
            case "docker" -> DockerConfig.INSTANCE;
            default -> throw new IllegalArgumentException("Unknown env: " + env);
        };
    }

    @Nonnull
    String baseUrl();

    String baseApiUrl();

    @Nonnull
    String remoteUrl();

    @Nonnull
    default String defaultPassword() {
        var defaultPassword = System.getProperty("test.user.default_password");
        return StringUtils.isNotEmpty(defaultPassword)
                ? defaultPassword
                : "12345";
    }

}
