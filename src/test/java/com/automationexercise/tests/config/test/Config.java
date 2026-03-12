package com.automationexercise.tests.config.test;

import com.automationexercise.tests.models.meta.TestEnv;
import com.automationexercise.tests.util.EnvUtil;
import com.automationexercise.tests.util.browser.BrowserName;
import io.restassured.filter.log.LogDetail;
import org.apache.commons.lang3.EnumUtils;

import javax.annotation.Nonnull;
import java.nio.file.Path;

public interface Config {

    String CSRF_COOKIE_TITLE = "csrftoken";
    String SESSION_ID_COOKIE_TITLE = "sessionid";
    TestEnv TEST_ENV = EnumUtils.getEnumIgnoreCase(
            TestEnv.class,
            EnvUtil.envVar("TEST_ENV", "docker"),
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
        return EnvUtil.envVar("TEST_IGNORE_DISABLED_BY_ISSUE", false);
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
    default String ghApiUrl() {
        return "https://api.github.com";
    }

    @Nonnull
    default String ghToken() {
        return System.getenv("GH_TOKEN");
    }

    @Nonnull
    default String ghTokenName() {
        return System.getenv("GH_TOKEN_NAME");
    }

    @Nonnull
    default String githubOwner() {
        return System.getenv("GH_OWNER");
    }

    @Nonnull
    default String gitHubRepo() {
        return System.getenv("GH_REPO");
    }

    @Nonnull
    default BrowserName browserName() {
        return EnumUtils.getEnumIgnoreCase(BrowserName.class,
                EnvUtil.envVar("PLAYWRIGHT_BROWSER_NAME", "chromium"),
                BrowserName.CHROMIUM);
    }

    @Nonnull
    default String browserSize() {
        return EnvUtil.envVar("TEST_BROWSER_SIZE", "1920x1080");
    }

    default double browserTimeout() {
        return EnvUtil.envVar("PLAYWRIGHT_BROWSER_TIMEOUT", 10000.0);
    }

    default double animationDuration() {
        return EnvUtil.envVar("TEST_ANIMATION_DURATION", 200.0);
    }

    default boolean browserIsHeadless() {
        return EnvUtil.envVar("PLAYWRIGHT_HEADLESS", false);
    }

    default double browserSlowMotion() {
        return EnvUtil.envVar("PLAYWRIGHT_SLOW_MOTION", 50.0);
    }

    default boolean saveFailedTestsVideo() {
        return EnvUtil.envVar("TEST_SAVE_FAILED_TESTS_VIDEO", false);
    }

    default double maxScreenshotDiff() {
        return EnvUtil.envVar("TEST_MAX_SCREENSHOT_DIFF", 0.2);
    }

    default double defaultScreenshotTolerance() {
        return EnvUtil.envVar("TEST_DEFAULT_SCREENSHOT_TOLERANCE", 0.01);
    }

    default int defaultScreenshotTimeout() {
        return EnvUtil.envVar("TEST_DEFAULT_SCREENSHOT_TIMEOUT", 200);
    }

    default long searchTimeout() {
        return 200;
    }

    default boolean rewriteAllScreenshots() {
        return EnvUtil.envVar("TEST_REWRITE_ALL_SCREENSHOTS", false);
    }

    @Nonnull
    String pathToScreenshotsDirectory();

    @Nonnull
    String pathToVideosDirectory();

    @Nonnull
    default String defaultPassword() {
        return EnvUtil.envVar("TEST_DEFAULT_USER_PASSWORD", "12345");
    }

    @Nonnull
    default LogDetail defaultRestLogLevel() {
        return EnumUtils.getEnumIgnoreCase(
                LogDetail.class,
                EnvUtil.envVar("TEST_REST_LOGS", "headers"),
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
                        ghApiUrl(),
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

        var ghToken = ghToken();
        ghToken = ghToken.contains("github_pat_")
                ? ghToken.substring(0, 20) + "***" + ghToken.substring(ghToken.length() - 4)
                : "WRONG_GITHUB_PAT_TOKEN";

        var githubData = """
                {
                    "ghApiUrl": "%s",
                    "ghOwner": "%s",
                    "ghRepo": "%s",
                    "ghToken": "%s",
                    "ghTokenName": "%s",
                }""".formatted(ghApiUrl(), ghToken, ghTokenName(), gitHubRepo(), githubOwner());

        return """
                {
                    "test" : %s,
                    "playwright" : %s,
                    "github" : %s,
                }""".formatted(testData, pwData, githubData);
    }

    default Path pathToAllureResults() {
        return Path.of("build/allure-results");
    }

    String allureReportUrl();

}
