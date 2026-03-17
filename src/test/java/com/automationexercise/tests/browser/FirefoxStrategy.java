package com.automationexercise.tests.browser;

import com.automationexercise.tests.store.ThreadSafeTestNameStore;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.ConnectOptions;
import com.microsoft.playwright.BrowserType.ConnectOverCDPOptions;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.BrowserType.LaunchPersistentContextOptions;
import com.microsoft.playwright.Playwright;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.automationexercise.tests.config.test.CfgInstance.CFG;

@ParametersAreNonnullByDefault
public enum FirefoxStrategy implements BrowserStrategy {

    INSTANCE;

    @Nonnull
    @Override
    public BrowserType getBrowserType(Playwright playwright) {
        return playwright.firefox();
    }

    @Nonnull
    @Override
    public List<String> browserArgs() {
        var browserSize = CFG.browserSize();
        return List.of(
                "--width=" + browserSize[0],
                "--height=1080" + browserSize[1],
                "--disable-dev-shm-usage"
        );
    }

    @Nonnull
    public Map<String, Object> browserPrefs() {
        var browserDownloadFolder = Paths.get(
                CFG.browserDownloadFolder().toAbsolutePath().toString(),
                ThreadSafeTestNameStore.INSTANCE.getCurrentTestTitle()
        );
        var prefs = new HashMap<String, Object>();
        prefs.put("pdfjs.disabled", true);
        prefs.put("browser.download.folderList", 2);
        prefs.put("browser.download.dir", browserDownloadFolder);
        prefs.put("browser.download.manager.showWhenStarting", false);
        prefs.put("browser.download.panel.shown", false);
        prefs.put("dom.webdriver.enabled", false);
        prefs.put("useAutomationExtension", false);
        prefs.put("media.webspeech.synth.enabled", false);
        prefs.put("media.webspeech.recognition.enable", false);
        return prefs;
    }

    @Nonnull
    @Override
    public List<Path> extensions() {
        return List.of();
    }

    @Nullable
    @Override
    public Path profile() {
        return null;
    }

    @Nonnull
    @Override
    public LaunchOptions getLaunchOptions() {

        var testTitle = Optional.ofNullable(ThreadSafeTestNameStore.INSTANCE.getCurrentTestTitle())
                .orElse("test");
        var browserDownloadFolder = Path.of("%s/%s".formatted(CFG.browserProfileFolder(), testTitle));

        var options = new LaunchOptions()
                .setDownloadsPath(browserDownloadFolder)
                .setSlowMo(CFG.browserSlowMotion())
                .setTimeout(CFG.browserTimeout());

        if (!browserArgs().isEmpty())
            options.setArgs(browserArgs());

        if (!browserPrefs().isEmpty())
            options.setFirefoxUserPrefs(browserPrefs());

        return options;
    }

    @Nonnull
    @Override
    public LaunchPersistentContextOptions getLaunchPersistentContextOptions() {

        var testTitle = Optional.ofNullable(ThreadSafeTestNameStore.INSTANCE.getCurrentTestTitle())
                .orElse("test");
        var browserDownloadFolder = Path.of("%s/%s".formatted(CFG.browserProfileFolder(), testTitle));

        var browserSize = CFG.browserSize();

        var options = new LaunchPersistentContextOptions()
                .setBaseURL(CFG.baseUrl())
                .setDownloadsPath(browserDownloadFolder)
                .setTimeout(CFG.browserTimeout())
                .setSlowMo(CFG.browserSlowMotion())
                .setAcceptDownloads(true)
                .setLocale(CFG.browserLocale())
                .setScreenSize(browserSize[0], browserSize[1])
                .setViewportSize(browserSize[0], browserSize[1]);

        if (!browserArgs().isEmpty())
            options.setArgs(browserArgs());

        if (!browserPrefs().isEmpty())
            options.setFirefoxUserPrefs(browserPrefs());

        return options;
    }

    @Nonnull
    @Override
    public ConnectOptions getConnectOptions() {
        return new BrowserType.ConnectOptions();
    }

    @Nonnull
    @Override
    public ConnectOverCDPOptions getConnectOverCDPOptions() {
        throw new UnsupportedOperationException("CDP supports for Chromium");
    }
}