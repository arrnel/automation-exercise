package com.automationexercise.tests.config.test;

import com.automationexercise.tests.models.meta.TestEnv;
import com.automationexercise.tests.util.browser.BrowserName;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

public interface Config {

    String CSRF_COOKIE_TITLE = "csrftoken";
    String SESSION_ID_COOKIE_TITLE = "sessionid";
    TestEnv TEST_ENV = TestEnv.valueOf(System.getProperty("test.env", "docker").toUpperCase());

    static Config getInstance() {
        return switch (TEST_ENV) {
            case LOCAL -> LocalConfig.INSTANCE;
            case DOCKER, CI -> DockerConfig.INSTANCE;
        };
    }

    @Nonnull
    String baseUrl();

    @Nonnull
    String domain();

    @Nonnull
    String baseApiUrl();

    boolean isRemote();

    @Nonnull
    String remoteUrl();

    default String pathToFiles() {
        return "src/test/resources/files";
    }

    default String pathToInvoiceFolder() {
        return pathToFiles() + "/invoice";
    }

    default String gitHubApiUrl() {
        return "https://api.github.com";
    }

    default String githubToken() {
        return System.getenv("GITHUB_TOKEN");
    }

    default String githubTokenName() {
        return System.getenv("GITHUB_TOKEN_NAME");
    }

    default String gitHubAccountName() {
        return System.getenv("GITHUB_ACCOUNT_NAME");
    }

    default String gitHubRepoName() {
        return System.getenv("GITHUB_REPO_NAME");
    }

    default BrowserName browserName() {
        return BrowserName.valueOf(
                System.getProperty(
                                "test.browser.name",
                                "chrome")
                        .toUpperCase());
    }

    default String browserVersion() {
        return System.getProperty(
                "test.browser.version",
                "127.0"
        );
    }

    default double browserTimeout() {
        return Double.parseDouble(
                System.getProperty("test.browser.timeout", "10000.0")
        );
    }

    default boolean browserIsHeadless() {
        return "true".equalsIgnoreCase(
                System.getProperty("test.browser.headless")
        );
    }

    default double playwrightSlowMotion() {
        return Double.parseDouble(
                System.getProperty("test.playwright.slow_motion", "50.0")
        );
    }

    default boolean playwrightVideo() {
        return "true".equalsIgnoreCase(
                System.getProperty("test.playwright.video", "false")
        );
    }

    default double maxScreenshotDiff() {
        return Double.parseDouble(
                System.getProperty("test.screenshot.max_available_diff", "0.2")
        );
    }

    default long searchTimeout() {
        return Long.parseLong(
                System.getProperty("test.search_timeout", "200")
        );
    }

    default boolean rewriteAllScreenshots() {
        return "true".equalsIgnoreCase(System.getProperty("test.screenshots.rewrite_all", "false"));
    }

    String pathToScreenshots();

    @Nonnull
    default String defaultPassword() {
        var defaultPassword = System.getProperty("test.user.default_password");
        return StringUtils.isNotEmpty(defaultPassword)
                ? defaultPassword
                : "12345";
    }

    default boolean closeBrowserAfterTests() {
        return "true".equalsIgnoreCase(System.getProperty("test.playwright.close_browser_after_test", "false"));
    }


}
