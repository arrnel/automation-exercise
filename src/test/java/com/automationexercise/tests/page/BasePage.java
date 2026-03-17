package com.automationexercise.tests.page;

import com.automationexercise.tests.browser.PlaywrightContextStore;
import com.automationexercise.tests.models.ScreenshotCheckContext;
import com.automationexercise.tests.models.ScreenshotParam;
import com.automationexercise.tests.page._component.common.HeaderComponent;
import com.automationexercise.tests.page._component.common.NotificationComponent;
import com.automationexercise.tests.page._component.common.PageScrollerComponent;
import com.automationexercise.tests.page._component.common.SubscriptionComponent;
import com.automationexercise.tests.util.ImageUtil;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.nio.file.Paths;

import static com.automationexercise.tests.config.test.CfgInstance.CFG;
import static com.microsoft.playwright.options.WaitForSelectorState.DETACHED;
import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;

@Slf4j
@SuppressWarnings("unchecked")
@ParametersAreNonnullByDefault
public abstract class BasePage<T> {

    protected static final WaitForOptions VISIBLE_CONDITION = new Locator.WaitForOptions().setState(VISIBLE);
    protected static final WaitForOptions DETACHED_CONDITION = new Locator.WaitForOptions().setState(DETACHED);

    protected final Page page;
    private final HeaderComponent header;
    private final PageScrollerComponent pageScroller;
    private final NotificationComponent notification;
    private final SubscriptionComponent subscription;

    public BasePage() {
        this.page = PlaywrightContextStore.INSTANCE.getOrCreateNewPage();
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
    public T checkNotificationHasScreenshot(String expectedScreenshotUrl) {
        notification.checkNotificationHasScreenshot(expectedScreenshotUrl);
        return (T) this;
    }

    @Nonnull
    public T checkNotificationHasScreenshot(ScreenshotParam screenshotParam) {
        notification.checkNotificationHasScreenshot(screenshotParam);
        return (T) this;
    }

    @Nonnull
    public T closeNotification() {
        notification.close();
        return (T) this;
    }

    @Nonnull
    public T checkPageHasScreenshot(String expectedScreenshotUrl) {
        return checkPageHasScreenshot(
                ScreenshotParam.builder()
                        .expectedScreenshotUrl(expectedScreenshotUrl)
                        .build()
        );
    }


    @Nonnull
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public T checkPageHasScreenshot(ScreenshotParam screenshotParam) {
        var size = CFG.browserSize();

        var ctx = new ScreenshotCheckContext(
                Paths.get(CFG.pathToScreenshotsDirectory(), screenshotParam.getExpectedScreenshotUrl()),
                page.screenshot(),
                new Dimension(size[0], size[1]),
                screenshotParam.getTolerance(),
                screenshotParam.isRewrite()
        );

        ImageUtil.performScreenshotCheck(ctx);
        return (T) this;
    }

    @Nonnull
    public abstract T shouldVisiblePage();

    public abstract void shouldNotVisiblePage();

}
