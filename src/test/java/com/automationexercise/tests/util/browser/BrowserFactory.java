package com.automationexercise.tests.util.browser;

import com.automationexercise.tests.config.test.Config;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface BrowserFactory {

    Config CFG = Config.getInstance();

    static BrowserFactory getInstance() {
        var browserName = Config.getInstance().browserName();
        return switch (browserName) {
            case CHROMIUM -> ChromeBrowserFactory.INSTANCE;
            case FIREFOX -> FirefoxBrowserFactory.INSTANCE;
            case WEBKIT -> WebKitBrowserFactory.INSTANCE;
            default -> throw new RuntimeException("Browser [%s] not supported".formatted(browserName));
        };
    }

    @Nonnull
    Browser getBrowser(Playwright playwright);

    @Nonnull
    LaunchOptions getLaunchOptions();

}
