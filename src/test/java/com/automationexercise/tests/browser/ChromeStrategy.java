package com.automationexercise.tests.browser;

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
import java.util.ArrayList;
import java.util.List;

import static com.automationexercise.tests.config.test.CfgInstance.CFG;

@ParametersAreNonnullByDefault
enum ChromeStrategy implements BrowserStrategy {

    INSTANCE;

    @Nonnull
    @Override
    public BrowserType getBrowserType(Playwright playwright) {
        return playwright.chromium();
    }

    @Nonnull
    @Override
    public List<String> browserArgs() {

        var browserSize = CFG.browserSize();

        var baseArgs = List.of(
                "--window-size=%d,%d".formatted(browserSize[0], browserSize[1]),
                "--disable-infobars",
                "--disable-notifications",
                "--disable-blink-features=AutomationControlled",
                "--start-maximized",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu"
        );

        return new ArrayList<>(baseArgs);
    }

    @Nonnull
    @Override
    public List<Path> extensions() {
        List<String> extensions = List.of(

        );
        return extensions.stream()
                .map(ext -> CFG.browserExtensionFolder().resolve(ext))
                .toList();
    }

    @Nullable
    @Override
    public Path profile() {
        return null;
    }

    @Nonnull
    @Override
    public LaunchOptions getLaunchOptions() {

        List<String> args = new ArrayList<>(browserArgs());

        extensions().forEach(path ->
                args.add("--load-extension=%s".formatted(path.toAbsolutePath().toString())));

        return new BrowserType.LaunchOptions()
                .setArgs(args)
                .setSlowMo(CFG.browserSlowMotion())
                .setTimeout(CFG.browserTimeout())
                .setHeadless(false);

    }

    @Nonnull
    @Override
    public LaunchPersistentContextOptions getLaunchPersistentContextOptions() {

        List<String> args = new ArrayList<>(browserArgs());

        extensions().forEach(path ->
                args.add("--load-extension=%s".formatted(path.toAbsolutePath().toString())));

        return new BrowserType.LaunchPersistentContextOptions()
                .setArgs(args)
                .setSlowMo(CFG.browserSlowMotion())
                .setTimeout(CFG.browserTimeout())
                .setHeadless(false);
    }

    @Nonnull
    @Override
    public ConnectOptions getConnectOptions() {
        return new BrowserType.ConnectOptions();
    }

    @Nonnull
    @Override
    public ConnectOverCDPOptions getConnectOverCDPOptions() {
        return new BrowserType.ConnectOverCDPOptions();
    }
}