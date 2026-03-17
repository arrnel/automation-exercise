package com.automationexercise.tests.browser;

import com.automationexercise.tests.util.SystemUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Browser.NewContextOptions;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import static com.automationexercise.tests.config.test.CfgInstance.CFG;

@ParametersAreNonnullByDefault
@NoArgsConstructor(access = AccessLevel.NONE)
public class DriverManager {

    private static final Map<BrowserName, BrowserStrategy> STRATEGIES = Map.of(
            BrowserName.CHROMIUM, ChromeStrategy.INSTANCE,
            BrowserName.FIREFOX, FirefoxStrategy.INSTANCE,
            BrowserName.WEBKIT, WebkitStrategy.INSTANCE
    );

    private static final Integer[] BROWSER_SIZE = CFG.browserSize();


    public static void launch(BrowserName browserName) {
        Playwright pw = PlaywrightContextStore.INSTANCE.getOrCreatePlaywright();
        BrowserStrategy strategy = getStrategy(browserName);

        Browser browser;
        BrowserContext browserContext;

        if (strategy.profile() == null) {
            browser = strategy.getBrowserType(pw).launch(strategy.getLaunchOptions());
            browserContext = browser.newContext(browserContextOptions());
        } else {
            Path profileTempDir = copyProfileToTemp(
                    profileTempFolderPrefix(browserName),
                    strategy.profile()
            );
            PlaywrightContextStore.INSTANCE.setProfilePath(profileTempDir);
            browser = strategy.getBrowserType(pw).launch();
            browserContext = strategy.getBrowserType(pw)
                    .launchPersistentContext(
                            profileTempDir,
                            strategy.getLaunchPersistentContextOptions()
                    );
        }

        PlaywrightContextStore.INSTANCE
                .setBrowser(browser)
                .setBrowserContext(browserContext);
    }


    public static void connect(BrowserName browserName) {
        Playwright pw = PlaywrightContextStore.INSTANCE.getOrCreatePlaywright();
        BrowserStrategy strategy = getStrategy(browserName);

        BrowserType browserType = strategy.getBrowserType(pw);
        BrowserType.ConnectOptions options = strategy.getConnectOptions();

        Browser browser = browserType.connect(CFG.remoteUrl(), options);
        BrowserContext context = browser.newContext();

        PlaywrightContextStore.INSTANCE
                .setBrowser(browser)
                .setBrowserContext(context);
    }

    public static void connectOverCDP(BrowserName browserName) {
        if (browserName != BrowserName.CHROMIUM)
            throw new UnsupportedOperationException("ConnectOverCDP supports only in Chromium");

        Playwright pw = PlaywrightContextStore.INSTANCE.getOrCreatePlaywright();
        BrowserStrategy strategy = getStrategy(browserName);

        BrowserType.ConnectOverCDPOptions options = strategy.getConnectOverCDPOptions();
        Browser browser = pw.chromium().connectOverCDP(CFG.remoteUrl(), options);
        BrowserContext context = browser.newContext();

        PlaywrightContextStore.INSTANCE
                .setBrowser(browser)
                .setBrowserContext(context);
    }

    private static BrowserStrategy getStrategy(BrowserName name) {
        return Optional.ofNullable(STRATEGIES.get(name))
                .orElseThrow(() -> new IllegalArgumentException("Browser strategy [%s] not found".formatted(name)));
    }

    private static Path copyProfileToTemp(String prefix, Path source) {
        try {
            Path tempDir = Files.createTempDirectory(prefix + System.currentTimeMillis());
            SystemUtil.copyDirIntoDir(source, tempDir);
            return tempDir;
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy profile to temp directory", e);
        }
    }

    private static NewContextOptions browserContextOptions() {
        return new NewContextOptions()
                .setBaseURL(CFG.baseUrl())
                .setScreenSize(BROWSER_SIZE[0], BROWSER_SIZE[1])
                .setViewportSize(BROWSER_SIZE[0], BROWSER_SIZE[1])
                .setRecordVideoSize(BROWSER_SIZE[0], BROWSER_SIZE[1])
                .setAcceptDownloads(true)
                .setLocale(CFG.browserLocale())
                .setRecordVideoDir(CFG.browserVideoFolder());
    }

    private static String profileTempFolderPrefix(BrowserName browserName) {
        if (browserName == BrowserName.EMPTY)
            throw new IllegalArgumentException("Browser name cannot be BrowserName.EMPTY");
        return "playwright-%s-profile-".formatted(browserName.name());
    }


}
