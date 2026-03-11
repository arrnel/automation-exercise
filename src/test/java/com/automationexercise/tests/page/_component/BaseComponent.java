package com.automationexercise.tests.page._component;

import com.automationexercise.tests.config.test.Config;
import com.automationexercise.tests.models.ScreenshotCheckContext;
import com.automationexercise.tests.util.ImageUtil;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
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
    public T checkElementHasScreenshot(
            Locator locator,
            String path,
            double percentOfTolerance,
            boolean rewriteScreenshot
    ) {

        var box = locator.boundingBox();
        if (box == null) {
            throw new IllegalStateException("Элемент невидим");
        }

        var ctx = new ScreenshotCheckContext(
                Paths.get(CFG.pathToScreenshotsDirectory(), path),
                locator.screenshot(),
                new Dimension((int) box.width, (int) box.height),
                percentOfTolerance,
                rewriteScreenshot
        );

        ImageUtil.performScreenshotCheck(ctx);
        return (T) this;
    }

    public abstract T shouldVisibleComponent();

    public abstract void shouldNotVisibleComponent();

}
