package com.automationexercise.tests.util.browser;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.ConnectOptions;
import com.microsoft.playwright.BrowserType.ConnectOverCDPOptions;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public enum ChromeBrowserFactory implements BrowserFactory {

    INSTANCE;

    @Nonnull
    @Override
    public BrowserType getBrowserType(Playwright playwright) {
        return playwright
                .chromium();
    }

    @Nonnull
    public LaunchOptions getLaunchOptions() {
        return new LaunchOptions()
                .setChannel("chrome")
                .setTimeout(CFG.browserTimeout())
                .setSlowMo(CFG.browserSlowMotion())
                .setHeadless(CFG.browserIsHeadless());
    }

    @Nonnull
    @Override
    public ConnectOptions getConnectOptions() {
        return new ConnectOptions()
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
