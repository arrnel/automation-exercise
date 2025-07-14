package com.automationexercise.tests.util.browser;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.ConnectOverCDPOptions;
import com.microsoft.playwright.Playwright;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public enum FirefoxBrowserFactory implements BrowserFactory {

    INSTANCE;

    @Nonnull
    @Override
    public BrowserType getBrowserType(Playwright playwright) {
        return playwright
                .firefox();
    }

    @Nonnull
    public BrowserType.LaunchOptions getLaunchOptions() {
        return new BrowserType.LaunchOptions()
                .setTimeout(CFG.browserTimeout())
                .setSlowMo(CFG.browserSlowMotion())
                .setHeadless(CFG.browserIsHeadless());
    }

    @Nonnull
    public BrowserType.ConnectOptions getConnectOptions() {
        return new BrowserType.ConnectOptions()
                .setTimeout(CFG.browserTimeout())
                .setSlowMo(CFG.browserSlowMotion());
    }

    @Nonnull
    @Override
    public ConnectOverCDPOptions getConnectOverCdpOptions() {
        return new ConnectOverCDPOptions()
                .setTimeout(CFG.browserTimeout())
                .setSlowMo(CFG.browserSlowMotion());
    }

}
