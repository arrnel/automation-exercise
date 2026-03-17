package com.automationexercise.tests.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@ParametersAreNonnullByDefault
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum PlaywrightContextStore {

    INSTANCE;

    private final ThreadLocal<Playwright> playwrightStore = new ThreadLocal<>();
    private final ThreadLocal<Browser> browserStore = new ThreadLocal<>();
    private final ThreadLocal<BrowserContext> browserContextStore = new ThreadLocal<>();
    private final ThreadLocal<Page> pageStore = new ThreadLocal<>();
    private final ThreadLocal<Path> profilePathStore = new ThreadLocal<>();

    @Nonnull
    public Playwright getOrCreatePlaywright() {
        return Optional.ofNullable(playwrightStore.get())
                .orElseGet(() -> {
                    var pw = Playwright.create();
                    playwrightStore.set(pw);
                    return pw;
                });
    }

    @Nullable
    public Browser getBrowser() {
        return browserStore.get();
    }

    @Nonnull
    public PlaywrightContextStore setBrowser(Browser browser) {
        browserStore.set(browser);
        return this;
    }

    @Nullable
    public BrowserContext getBrowserContext() {
        return browserContextStore.get();
    }

    @Nonnull
    public BrowserContext getOrCreateNewBrowserContext() {
        return Optional.ofNullable(browserContextStore.get())
                .orElseGet(() -> {
                    var browser = browserStore.get();
                    if (browser == null)
                        throw new RuntimeException("Browser not found in store");
                    var ctx = browser.newContext();
                    setBrowserContext(ctx);
                    return ctx;
                });
    }

    @Nonnull
    public PlaywrightContextStore setBrowserContext(BrowserContext ctx) {
        browserContextStore.set(ctx);
        return this;
    }

    @Nonnull
    public Page getOrCreateNewPage() {
        return Optional.ofNullable(pageStore.get())
                .orElseGet(() -> {
                    var page = getOrCreateNewBrowserContext().newPage();
                    pageStore.set(page);
                    return page;
                });
    }

    @Nullable
    public Page getPage() {
        return pageStore.get();
    }

    @Nonnull
    public PlaywrightContextStore setPage(Page page) {
        pageStore.set(page);
        return this;
    }

    @Nullable
    public Path getProfilePath() {
        return profilePathStore.get();
    }

    @Nonnull
    public PlaywrightContextStore setProfilePath(Path profilePath) {
        profilePathStore.set(profilePath);
        return this;
    }

    public void cleanCurrentThreadData() {
        cleanUpPage();
        cleanUpBrowserContext();
        cleanUpBrowser();
        cleanUpPlaywright();
        cleanUpProfileDir();
    }

    private void cleanUpPlaywright() {
        log.info("Cleanup playwright");
        Optional.ofNullable(playwrightStore.get())
                .ifPresent(pw -> {
                    pw.close();
                    playwrightStore.remove();
                });
    }

    private void cleanUpBrowser() {
        log.info("Cleanup browser");
        Optional.ofNullable(getBrowser())
                .ifPresent(browser -> {
                    if (browser.isConnected()) {
                        browser.close();
                        browserStore.remove();
                    }
                });
    }

    private void cleanUpBrowserContext() {
        log.info("Cleanup browser context");
        Optional.ofNullable(getBrowserContext())
                .ifPresent(ctx -> {
                    ctx.close();
                    browserContextStore.remove();
                });
    }

    private void cleanUpPage() {
        log.info("Cleanup page");
        Optional.ofNullable(getPage())
                .ifPresent(page -> {
                    page.close();
                    pageStore.remove();
                });
    }

    private void cleanUpProfileDir() {
        log.info("Cleanup profile dir");
        Optional.ofNullable(getProfilePath())
                .ifPresent(profile -> {
                    try {
                        FileUtils.cleanDirectory(profile.toFile());
                    } catch (IOException ex) {
                        // NOP
                    }
                    profilePathStore.remove();
                });
    }

}
