package com.automationexercise.tests.util.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public enum WebKitBrowserFactory implements BrowserFactory {

    INSTANCE;

    @Nonnull
    @Override
    public Browser getBrowser(Playwright playwright) {
        return playwright
                .webkit()
                .launch(getLaunchOptions());
    }

    @Nonnull
    public LaunchOptions getLaunchOptions() {
        return new LaunchOptions()
                .setTimeout(CFG.browserTimeout())
                .setSlowMo(CFG.browserSlowMotion())
                .setHeadless(CFG.browserIsHeadless());
    }

}
