package com.automationexercise.tests.page._component;

import com.automationexercise.tests.config.test.Config;
import com.automationexercise.tests.ex.ScreenshotException;
import com.automationexercise.tests.util.AllureUtil;
import com.automationexercise.tests.util.ImageUtil;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
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
public abstract class BaseComponent<T> {

    protected static final Locator.WaitForOptions VISIBLE_CONDITION = new Locator.WaitForOptions().setState(VISIBLE);
    protected static final Locator.WaitForOptions DETACHED_CONDITION = new Locator.WaitForOptions().setState(DETACHED);

    private static final String DEFAULT_COMPONENT_TITLE = "DEFAULT_COMPONENT_TITLE";
    protected static final Config CFG = Config.getInstance();
    protected final Locator self;
    protected final String componentTitle;

    public BaseComponent(Locator self) {
        this.self = self;
        this.componentTitle = DEFAULT_COMPONENT_TITLE;
    }

    public BaseComponent(Locator self, String componentTitle) {
        this.self = self;
        this.componentTitle = componentTitle;
    }

    @Nonnull
    public T scrollToComponent() {
        var componentType = getClass().getTypeName();
        log.info("Scroll to component: {}", componentType);
        Allure.step("Scroll to component: %s".formatted(componentType), () -> {
            self.scrollIntoViewIfNeeded();
            waitUntilStopScrolling();
        });
        return (T) this;
    }

    protected void waitUntilStopScrolling() {
        self.page().waitForFunction("""
                    () => {
                        return new Promise((resolve) => {
                            let timeout = 150;
                            let lastScrollPos = window.scrollY;
                            let stableCount = 0;
                            const checkScroll = () => {
                                const currentScrollPos = window.scrollY;
                                if (currentScrollPos === lastScrollPos) {
                                    stableCount++;
                                    if (stableCount >= 3) resolve(true);
                                } else {
                                    stableCount = 0;
                                    lastScrollPos = currentScrollPos;
                                }
                                setTimeout(checkScroll, timeout);
                            };
                            checkScroll();
                        });
                    }
                """);
    }

    @Nonnull
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public T checkElementHasScreenshot(Locator locator,
                                       String pathToScreenshot,
                                       double percentOfTolerance,
                                       boolean rewriteScreenshot
    ) {
        var path = Paths.get(CFG.pathToScreenshotsDirectory(), pathToScreenshot);

        var actualScreenshot = locator.screenshot();
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

    public abstract T shouldVisibleComponent();

    public abstract void shouldNotVisibleComponent();

}
