package com.automationexercise.tests.util.browser;

import com.automationexercise.tests.config.test.Config;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.ConnectOptions;
import com.microsoft.playwright.BrowserType.ConnectOverCDPOptions;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;

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
    BrowserType getBrowserType(Playwright playwright);

    @Nonnull
    default Browser getBrowser(Playwright playwright) {
        // @formatter:off
        var browserType = getBrowserType(playwright);
        return !CFG.isMoon()
                ? browserType.launch(getLaunchOptions())
                : CFG.connectOverCdp()
                    ? browserType.connectOverCDP(getRemoteUrl(), getConnectOverCdpOptions())
                    : browserType.connect(getRemoteUrl(), getConnectOptions());
        // @formatter:on
    }

    @SneakyThrows
    @Nonnull
    default String getRemoteUrl() {
        var url = CFG.connectOverCdp()
                ? "http://%s/playwright/%s"
                : "ws://%s/playwright/%s";
        url = url.formatted(CFG.remoteHost(), CFG.browserName().toString());
        var uriBuilder = new URIBuilder(url)
                .addParameter("headless", String.valueOf(CFG.browserIsHeadless()))
                .addParameter("screenResolution", CFG.browserSize())
                .addParameter("enableVNC", "true")
                .addParameter("enableVideo", String.valueOf(CFG.saveFailedTestsVideo()));
        return uriBuilder.build().toString();
    }

    @Nonnull
    LaunchOptions getLaunchOptions();

    @Nonnull
    ConnectOptions getConnectOptions();

    @Nonnull
    ConnectOverCDPOptions getConnectOverCdpOptions();

}
