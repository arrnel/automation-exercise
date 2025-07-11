package com.automationexercise.tests.config.test;

import com.automationexercise.tests.models.meta.TestEnv;
import com.automationexercise.tests.util.browser.BrowserName;
import io.restassured.filter.log.LogDetail;
import org.apache.commons.lang3.EnumUtils;

import javax.annotation.Nonnull;
import java.nio.file.Path;

import static com.automationexercise.tests.util.EnvDataUtil.*;

public interface Config {

    String CSRF_COOKIE_TITLE = "csrftoken";
    String SESSION_ID_COOKIE_TITLE = "sessionid";
    TestEnv TEST_ENV = EnumUtils.getEnumIgnoreCase(
            TestEnv.class,
            getStringEnv("TEST_ENV", "docker"),
            TestEnv.DOCKER
    );
    String PROJECT_NAME = System.getenv("ALLURE_PROJECT_NAME");

    static Config getInstance() {
        return switch (TEST_ENV) {
            case LOCAL -> LocalConfig.INSTANCE;
            case DOCKER -> DockerConfig.INSTANCE;
            case CI -> GithubConfig.INSTANCE;
        };
    }

    @Nonnull
    String baseUrl();

    @Nonnull
    String domain();

    @Nonnull
    String baseApiUrl();

    default boolean ignoreDisabledByIssue() {
        return getBooleanEnv("TEST_IGNORE_DISABLED_BY_ISSUE", false);
    }

    @Nonnull
    default String pathToFiles() {
        return "src/test/resources/files";
    }

    @Nonnull
    default String pathToInvoiceFolder() {
        return pathToFiles() + "/invoice";
    }

    @Nonnull
    default String gitHubApiUrl() {
        return "https://api.github.com";
    }

    @Nonnull
    default String githubToken() {
        return System.getenv("GITHUB_TOKEN");
    }

    @Nonnull
    default String githubTokenName() {
        return System.getenv("GITHUB_TOKEN_NAME");
    }

    @Nonnull
    default String gitHubAccountName() {
        return System.getenv("GITHUB_ACCOUNT_NAME");
    }

    @Nonnull
    default String gitHubRepoName() {
        return System.getenv("GITHUB_REPO_NAME");
    }

    @Nonnull
    default BrowserName browserName() {
        return EnumUtils.getEnumIgnoreCase(BrowserName.class,
                getStringEnv("PLAYWRIGHT_BROWSER_NAME", "chromium"),
                BrowserName.CHROMIUM);
    }

    @Nonnull
    default String browserSize() {
        return getStringEnv("TEST_BROWSER_SIZE", "1920x1080");
    }

    default double browserTimeout() {
        return getDoubleEnv("PLAYWRIGHT_BROWSER_TIMEOUT", 10000.0);
    }

    default double animationDuration() {
        return getDoubleEnv("TEST_ANIMATION_DURATION", 200.0);
    }

    default boolean browserIsHeadless() {
        return getBooleanEnv("PLAYWRIGHT_HEADLESS", false);
    }

    default double browserSlowMotion() {
        return getDoubleEnv("PLAYWRIGHT_SLOW_MOTION", 50.0);
    }

    default boolean saveFailedTestsVideo() {
        return getBooleanEnv("TEST_SAVE_FAILED_TESTS_VIDEO", false);
    }

    default double maxScreenshotDiff() {
        return getDoubleEnv("TEST_MAX_SCREENSHOT_DIFF", 0.2);
    }

    default long searchTimeout() {
        return getLongProperty("test.search_timeout", 200);
    }

    default boolean rewriteAllScreenshots() {
        return getBooleanEnv("TEST_REWRITE_ALL_SCREENSHOTS", false);
    }

    @Nonnull
    String pathToScreenshotsDirectory();

    @Nonnull
    String pathToVideosDirectory();

    @Nonnull
    default String defaultPassword() {
        return getStringEnv("TEST_DEFAULT_USER_PASSWORD", "12345");
    }

    @Nonnull
    default LogDetail defaultRestLogLevel() {
        return EnumUtils.getEnumIgnoreCase(
                LogDetail.class,
                getStringEnv("TEST_REST_LOGS", "headers"),
                LogDetail.HEADERS);
    }

    default String envData() {

        var testData = """
                {
                    "env": "%s",
                    "baseUrl": %s"
                    "baseApiUrl": "%s",
                    "defaultPassword": %s
                    "defaultRestLogLevel": %s
                    "domain": "%s",
                    "gitHubApiUrl": "%s"
                    "ignoreDisabledByIssue": %s,
                    "maxScreenshotDiff": %s,
                    "pathToFiles": "%s",
                    "pathToInvoiceFolder": "%s",
                    "pathToScreenshotsDirectory": "%s",
                    "pathToVideoDirectory": "%s",
                    "saveFailedTestsVideo": %s,
                    "searchTimeout": %s
                }"""
                .formatted(
                        TEST_ENV,
                        baseUrl(),
                        baseApiUrl(),
                        domain(),
                        defaultPassword(),
                        defaultRestLogLevel(),
                        gitHubApiUrl(),
                        ignoreDisabledByIssue(),
                        maxScreenshotDiff(),
                        pathToFiles(),
                        pathToInvoiceFolder(),
                        pathToScreenshotsDirectory(),
                        pathToVideosDirectory(),
                        saveFailedTestsVideo(),
                        searchTimeout()
                );


        var pwData = """
                {
                    "browserIsHeadless": %s,
                    "browserName": "%s",
                    "browserSize": "%s",
                    "browserTimeout": %s,
                    "playwrightSlowMotion": %s
                }""".formatted(browserIsHeadless(), browserName(), browserSize(), browserTimeout(), browserSlowMotion());

        var githubData = """
                {
                    "githubAccountName": "%s"
                    "githubApiUrl": "%s",
                    "githubRepoName": "%s",
                    "githubToken": "%s",
                    "githubTokenName": "%s",
                }""".formatted(gitHubApiUrl(), githubToken(), githubTokenName(), gitHubRepoName(), gitHubAccountName());

        return """
                {
                    "test" : %s,
                    "playwright" : %s,
                    "github" : %s,
                }""".formatted(testData, pwData, githubData);
    }

    default Path pathToAllureResults() {
        return Path.of("./rococo-tests/build/allure-results");
    }

    String allureReportUrl();

}
