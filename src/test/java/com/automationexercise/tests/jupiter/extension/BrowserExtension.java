package com.automationexercise.tests.jupiter.extension;

import com.automationexercise.tests.jupiter.anno.meta.Browser;
import com.automationexercise.tests.jupiter.anno.meta.ScreenshotTest;
import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.util.browser.ChromeBrowserFactory;
import com.automationexercise.tests.util.browser.FirefoxBrowserFactory;
import com.automationexercise.tests.util.browser.PageStore;
import com.automationexercise.tests.util.browser.WebKitBrowserFactory;
import com.microsoft.playwright.Browser.NewContextOptions;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
public class BrowserExtension extends BaseExtension implements BeforeEachCallback, AfterEachCallback, TestExecutionExceptionHandler, LifecycleMethodExecutionExceptionHandler {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        var clazz = context.getRequiredTestClass();
        var method = context.getRequiredTestMethod();
        if (!isClassHaveUiAnno(context.getRequiredTestClass()))
            return;

        Optional<Browser> browserAnno = Optional.empty();
        if (isMethodHaveBrowserAnno(method)) {
            browserAnno = Optional.of(method.getAnnotation(Browser.class));
        } else if (isClassHaveBrowserAnno(clazz)) {
            browserAnno = Optional.of(clazz.getAnnotation(Browser.class));
        }

        var playwright = PageStore.INSTANCE.createNewPlaywright();

        var browser = browserAnno
                .map(value ->
                        switch (value.value()) {
                            case CHROMIUM -> ChromeBrowserFactory.INSTANCE.getBrowser(playwright);
                            case FIREFOX -> FirefoxBrowserFactory.INSTANCE.getBrowser(playwright);
                            case WEBKIT -> WebKitBrowserFactory.INSTANCE.getBrowser(playwright);
                            default ->
                                    throw new IllegalStateException("Browser [%s] not supported".formatted(value.value()));
                        })
                .orElse(PageStore.INSTANCE.getOrCreateNewBrowser());

        var screenResolution = (browserAnno.isEmpty() || browserAnno.get().size().isBlank()
                ? CFG.browserSize()
                : browserAnno.get().size()
        ).split("x");

        var width = Integer.parseInt(screenResolution[0]);
        var height = Integer.parseInt(screenResolution[1]);

        var contextOptions = new NewContextOptions()
                .setViewportSize(width, height);

        if (CFG.saveFailedTestsVideo()) {
            contextOptions
                    .setRecordVideoSize(width, height)
                    .setRecordVideoDir(Path.of(CFG.pathToVideosDirectory()).toAbsolutePath());
        }

        PageStore.INSTANCE.setBrowserContext(browser.newContext(contextOptions));

    }


    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if (!isClassHaveUiAnno(context.getRequiredTestClass()))
            return;
        PageStore.INSTANCE.closeThreadPlaywrightData();
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        log.info("TEST EXCEPTION: {}", throwable.getMessage());
        if (!isClassHaveUiAnno(context.getRequiredTestClass()))
            throw throwable;
        attachPage();
        attachScreenshot();
        attachVideo();
        throw throwable;
    }

    @Override
    public void handleBeforeEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (!isClassHaveUiAnno(context.getRequiredTestClass()))
            throw throwable;
        attachPage();
        attachScreenshot();
        attachVideo();
        throw throwable;
    }

    @Override
    public void handleAfterEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (!isClassHaveUiAnno(context.getRequiredTestClass()))
            throw throwable;
        attachPage();
        attachScreenshot();
        attachVideo();
        throw throwable;
    }

    private static void attachScreenshot() {
        var page = PageStore.INSTANCE.getPage();
        if (!page.isClosed()) {
            Allure.addAttachment(
                    "Screen on fail",
                    new ByteArrayInputStream(page.screenshot())
            );
        }
    }

    private static void attachPage() {
        var page = PageStore.INSTANCE.getPage();
        if (!page.isClosed()) {
            Allure.addAttachment(
                    "Page html",
                    "text/html",
                    page.content(),
                    "html"
            );
        }
    }

    @SneakyThrows
    private static void attachVideo() {
        var page = PageStore.INSTANCE.getPage();
        if (CFG.saveFailedTestsVideo() && !page.isClosed()) {
            Allure.addByteAttachmentAsync(
                    "Test video",
                    "video/webm",
                    getVideo(page)
            );
        }
    }

    private static Supplier<byte[]> getVideo(Page page) {
        return () -> {
            try {
                return Files.readAllBytes(page.video().path());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private boolean isClassHaveUiAnno(Class<?> clazz) {
        return clazz.isAnnotationPresent(WebTest.class) || clazz.isAnnotationPresent(ScreenshotTest.class);
    }

    private static boolean isClassHaveBrowserAnno(Class<?> clazz) {
        return clazz.isAnnotationPresent(Browser.class);
    }

    private static boolean isMethodHaveBrowserAnno(Method method) {
        return method.isAnnotationPresent(Browser.class);
    }

}
