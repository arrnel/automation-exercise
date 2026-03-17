package com.automationexercise.tests.jupiter.extension;

import com.automationexercise.tests.browser.BrowserName;
import com.automationexercise.tests.browser.DriverManager;
import com.automationexercise.tests.browser.PlaywrightContextStore;
import com.automationexercise.tests.jupiter.Browser;
import com.automationexercise.tests.jupiter.anno.meta.ScreenshotTest;
import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.util.AllureUtil;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

import static com.automationexercise.tests.config.test.CfgInstance.CFG;

@Slf4j
public class BrowserExtension extends BaseExtension implements BeforeEachCallback, AfterEachCallback, TestExecutionExceptionHandler, LifecycleMethodExecutionExceptionHandler {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        if (!isUiTest(context.getRequiredTestMethod())) return;

        Optional<Browser> anno = getBrowserAnnotation(context);
        var browserName = anno.isPresent() && anno.get().value() != BrowserName.EMPTY
                ? anno.get().value()
                : CFG.browserName();

        DriverManager.launch(browserName);

    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if (!isUiTest(context.getRequiredTestMethod()))
            return;
        PlaywrightContextStore.INSTANCE.cleanCurrentThreadData();
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        handleUiTestException(context, throwable);
    }

    @Override
    public void handleBeforeEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        handleUiTestException(context, throwable);
    }

    @Override
    public void handleAfterEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        handleUiTestException(context, throwable);
    }


    private Optional<Browser> getBrowserAnnotation(ExtensionContext context) {
        var method = context.getRequiredTestMethod();
        if (method.isAnnotationPresent(Browser.class)) {
            return Optional.of(method.getAnnotation(Browser.class));
        }
        var clazz = context.getRequiredTestClass();
        if (clazz.isAnnotationPresent(Browser.class)) {
            return Optional.of(clazz.getAnnotation(Browser.class));
        }
        return Optional.empty();
    }

    private void handleUiTestException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (!isUiTest(context.getRequiredTestMethod())) {
            throw throwable;
        }
        attachTestArtifacts();
        throw throwable;
    }

    private static void attachTestArtifacts() {

        var page = PlaywrightContextStore.INSTANCE.getPage();
        var isPageClosed = Optional.ofNullable(page)
                .map(Page::isClosed)
                .orElse(true);

        if (!isPageClosed) {
            AllureUtil.attachPage(page.content());
            AllureUtil.attachScreenshot(page.screenshot());
            AllureUtil.attachVideo(page.video().path());
        }

    }

    private boolean isUiTest(Method method) {
        return isAnnoExists(method, WebTest.class) || isAnnoExists(method, ScreenshotTest.class);
    }

    private boolean isAnnoExists(Method method, Class<? extends Annotation> anno) {
        return method.isAnnotationPresent(anno) || method.getDeclaringClass().isAnnotationPresent(anno);
    }

}
