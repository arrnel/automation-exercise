package com.automationexercise.tests.util.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Browser.NewContextOptions;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum PageStore {

    INSTANCE;

    private final ThreadLocal<Playwright> playwrightStore = new ThreadLocal<>();
    private final ThreadLocal<Browser> browserStore = new ThreadLocal<>();
    private final ThreadLocal<BrowserContext> browserContextStore = new ThreadLocal<>();
    private final ThreadLocal<Page> pageStore = new ThreadLocal<>();

    @Nonnull
    public Playwright createNewPlaywright() {
        playwrightStore.set(Playwright.create());
        return playwrightStore.get();
    }

    @Nonnull
    public Browser createNewBrowser(NewContextOptions contextOptions) {
        var browser = BrowserFactory.getInstance()
                .getBrowser(getOrCreateNewPlaywright());
        var browserContext = browser.newContext(contextOptions);
        browserStore.set(browser);
        browserContextStore.set(browserContext);
        return browser;
    }

    @Nonnull
    public Browser createNewBrowser() {
        var browser = BrowserFactory.getInstance()
                .getBrowser(getOrCreateNewPlaywright());
        browserContextStore.set(browser.newContext());
        browserStore.set(browser);
        return browser;
    }

    @Nonnull
    public Page createNewPage() {
        if (browserContextStore.get() == null)
            createNewBrowser();
        pageStore.set(browserContextStore.get().newPage());
        return pageStore.get();
    }

    @Nonnull
    public Playwright getOrCreateNewPlaywright() {
        playwrightStore.set(Playwright.create());
        return playwrightStore.get() != null
                ? playwrightStore.get()
                : createNewPlaywright();
    }

    @Nonnull
    public Browser getOrCreateNewBrowser() {
        return browserStore.get() != null
                ? browserStore.get()
                : createNewBrowser();
    }

    @Nonnull
    public Page getOrCreateNewPage() {
        return pageStore.get() != null
                ? pageStore.get()
                : createNewPage();
    }

    @Nullable
    public Playwright getPlaywright() {
        return playwrightStore.get();
    }

    @Nullable
    public Browser getBrowser() {
        return browserStore.get();
    }

    @Nullable
    public BrowserContext getBrowserContext() {
        return browserContextStore.get();
    }

    @Nullable
    public Page getPage() {
        return pageStore.get();
    }

    public void closeThreadPlaywrightData() {
        cleanUpPage();
        cleanUpBrowserContext();
        cleanUpBrowser();
        cleanUpPlaywright();
    }

    private void cleanUpPlaywright() {
        try {
            log.info("Cleanup playwright");
            getPlaywright().close();
            playwrightStore.remove();
        } catch (Exception e) {
            // NOP
        }
    }

    private void cleanUpBrowser() {
        try {
            log.info("Cleanup browser");
            if (getBrowser() != null && getBrowser().isConnected()) {
                getBrowser().close();
                browserStore.remove();
            }
        } catch (Exception e) {
            // NOP
        }
    }

    private void cleanUpBrowserContext() {
        try {
            log.info("Cleanup browser context");
            if (getBrowserContext() != null) {
                getBrowserContext().close();
                browserContextStore.remove();
            }
        } catch (Exception e) {
            // NOP
        }
    }

    private void cleanUpPage() {
        try {
            log.info("Cleanup page");
            if (getPage() != null || !getPage().isClosed()) {
                getPage().close();
                pageStore.remove();
            }
        } catch (Exception e) {
            // NOP
        }
    }

    public void setBrowserContext(BrowserContext browserContext) {
        browserContextStore.set(browserContext);
    }
}
