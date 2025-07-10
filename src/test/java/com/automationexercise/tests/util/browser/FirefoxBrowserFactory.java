package com.automationexercise.tests.util.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public enum FirefoxBrowserFactory implements BrowserFactory {

    INSTANCE;

    @Nonnull
    @Override
    public Browser getBrowser(Playwright playwright) {
        return playwright
                .firefox()
                .launch(getLaunchOptions());
    }

    @Nonnull
    public BrowserType.LaunchOptions getLaunchOptions() {
        return new BrowserType.LaunchOptions()
                .setTimeout(CFG.browserTimeout())
                .setSlowMo(CFG.browserSlowMotion())
                .setHeadless(CFG.browserIsHeadless());
    }

}
