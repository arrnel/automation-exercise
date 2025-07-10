package com.automationexercise.tests.page;

import com.automationexercise.tests.config.test.Config;
import com.automationexercise.tests.ex.ScreenshotException;
import com.automationexercise.tests.page._component.common.HeaderComponent;
import com.automationexercise.tests.page._component.common.NotificationComponent;
import com.automationexercise.tests.page._component.common.PageScrollerComponent;
import com.automationexercise.tests.page._component.common.SubscriptionComponent;
import com.automationexercise.tests.util.AllureUtil;
import com.automationexercise.tests.util.ImageUtil;
import com.automationexercise.tests.util.browser.PageStore;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.microsoft.playwright.options.WaitForSelectorState.DETACHED;
import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;

@Slf4j
@SuppressWarnings("unchecked")
@ParametersAreNonnullByDefault
public abstract class BasePage<T> {

    protected static final Config CFG = Config.getInstance();
    protected static final WaitForOptions VISIBLE_CONDITION = new Locator.WaitForOptions().setState(VISIBLE);
    protected static final WaitForOptions DETACHED_CONDITION = new Locator.WaitForOptions().setState(DETACHED);

    protected final Page page;
    private final HeaderComponent header;
    private final PageScrollerComponent pageScroller;
    private final NotificationComponent notification;
    private final SubscriptionComponent subscription;

    public BasePage() {
        this.page = PageStore.INSTANCE.getOrCreateNewPage();
        header = new HeaderComponent(page.locator("header"));
        pageScroller = new PageScrollerComponent(page.locator("#scrollUp"));
        notification = new NotificationComponent(page.locator(".modal-content"));
        subscription = new SubscriptionComponent(page.locator("#footer .single-widget"));
    }

    @Step("Reload page")
    public T reloadPage() {
        log.info("Reload page");
        page.reload();
        return (T) this;
    }

    @Nonnull
    public HeaderComponent header() {
        return header;
    }

    @Nonnull
    public PageScrollerComponent pageScroller() {
        return pageScroller;
    }

    @Nonnull
    public SubscriptionComponent subscription() {
        return subscription;
    }

    @Nonnull
    public NotificationComponent notification() {
        return notification;
    }

    @Nonnull
    public T checkNotificationHasTitle(String title) {
        notification.checkNotificationHasTitle(title);
        return (T) this;
    }

    @Nonnull
    public T checkNotificationHasDescription(String description) {
        notification.checkNotificationHasDescription(description);
        return (T) this;
    }

    @Nonnull
    public T checkNotificationHasDescriptionLink(String descriptionLink) {
        notification.checkNotificationHasDescriptionLink(descriptionLink);
        return (T) this;
    }

    @Nonnull
    public T checkNotificationHasDescriptionLinkText(String descriptionLinkText) {
        notification.checkNotificationHasDescriptionLinkText(descriptionLinkText);
        return (T) this;
    }

    @Nonnull
    public T checkNotificationHasSuccessAddedProductMessage() {
        notification.checkNotificationHasSuccessAddedProductMessage();
        return (T) this;
    }

    @Nonnull
    public T checkNotificationHasScreenshot(String pathToScreenshot) {
        return checkNotificationHasScreenshot(pathToScreenshot, 0.0, false);
    }

    @Nonnull
    public T checkNotificationHasScreenshot(String pathToScreenshot, boolean rewriteScreenshot) {
        return checkNotificationHasScreenshot(pathToScreenshot, 0.0, rewriteScreenshot);
    }

    @Nonnull
    public T checkNotificationHasScreenshot(String pathToScreenshot, double percentOfTolerance) {
        return checkNotificationHasScreenshot(pathToScreenshot, percentOfTolerance, false);
    }

    @Nonnull
    public T checkNotificationHasScreenshot(String pathToScreenshot,
                                            double percentOfTolerance,
                                            boolean rewriteScreenshot
    ) {
        notification.checkNotificationHasScreenshot(pathToScreenshot, percentOfTolerance, rewriteScreenshot);
        return (T) this;
    }

    @Nonnull
    public T closeNotification() {
        notification.close();
        return (T) this;
    }

    @Nonnull
    public T checkPageHasScreenshot(String pathToScreenshot) {
        return checkPageHasScreenshot(pathToScreenshot, 0.0, false);
    }

    @Nonnull
    public T checkPageHasScreenshot(String pathToScreenshot, double percentOfTolerance) {
        return checkPageHasScreenshot(pathToScreenshot, percentOfTolerance, false);
    }

    @Nonnull
    public T checkPageHasScreenshot(String pathToScreenshot, boolean rewriteScreenshot) {
        return checkPageHasScreenshot(pathToScreenshot, 0.0, rewriteScreenshot);
    }

    @Nonnull
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public T checkPageHasScreenshot(String pathToScreenshot,
                                    double percentOfTolerance,
                                    boolean rewriteScreenshot
    ) {
        var path = Paths.get(CFG.pathToScreenshotsDirectory(), pathToScreenshot);
        var actualScreenshot = page.screenshot();
        var screenDiff = ImageUtil.getScreenDiff(
                Files.readAllBytes(path),
                actualScreenshot,
                percentOfTolerance
        );

        if (CFG.rewriteAllScreenshots()) {
            ImageUtil.rewriteImage(actualScreenshot, path);
        }

        if (screenDiff.isHasDiff()) {
            if (rewriteScreenshot && !CFG.rewriteAllScreenshots())
                ImageUtil.rewriteImage(actualScreenshot, path);
            AllureUtil.addScreenDiffAttachment(screenDiff);
            throw new ScreenshotException(
                    "Expected and actual screenshots has difference greater then: %s".formatted(percentOfTolerance)
            );
        }

        return (T) this;

    }

    @Nonnull
    public abstract T shouldVisiblePage();

    public abstract void shouldNotVisiblePage();

}
