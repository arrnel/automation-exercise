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
import java.util.List;

@ParametersAreNonnullByDefault
interface BrowserStrategy {

    @Nonnull
    BrowserType getBrowserType(Playwright playwright);

    @Nonnull
    List<String> browserArgs();

    @Nonnull
    List<Path> extensions();

    @Nullable
    Path profile();

    @Nonnull
    LaunchOptions getLaunchOptions();

    @Nonnull
    LaunchPersistentContextOptions getLaunchPersistentContextOptions();

    @Nonnull
    ConnectOptions getConnectOptions();

    @Nonnull
    ConnectOverCDPOptions getConnectOverCDPOptions();

}
