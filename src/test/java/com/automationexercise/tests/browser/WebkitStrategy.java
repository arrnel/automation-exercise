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
import java.util.List;
import java.util.Optional;

import static com.automationexercise.tests.config.test.CfgInstance.CFG;

@ParametersAreNonnullByDefault
enum WebkitStrategy implements BrowserStrategy {
    INSTANCE;

    @Nonnull
    @Override
    public BrowserType getBrowserType(Playwright playwright) {
        return playwright.webkit();
    }

    @Nonnull
    @Override
    public List<String> browserArgs() {
        return List.of(
        );
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

        return new LaunchOptions()
                .setDownloadsPath(browserDownloadFolder)
                .setSlowMo(CFG.browserSlowMotion())
                .setTimeout(CFG.browserTimeout());
    }

    @Nonnull
    @Override
    public LaunchPersistentContextOptions getLaunchPersistentContextOptions() {
        var options = new BrowserType.LaunchPersistentContextOptions()
                .setSlowMo(CFG.browserSlowMotion())
                .setTimeout(CFG.browserTimeout())
                .setHeadless(false);

        if (!browserArgs().isEmpty())
            options.setArgs(browserArgs());

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
        throw new UnsupportedOperationException("CDP supports only in Chromium");
    }
}