package com.automationexercise.tests.page._component.carousel;

import com.automationexercise.tests.page._component.BaseComponent;
import com.automationexercise.tests.page._component._type.CarouselType;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ParametersAreNonnullByDefault
@SuppressWarnings("unchecked")
abstract class BaseCarouselComponent<T> extends BaseComponent<T> {

    private static final long SCROLL_TIMEOUT = 1_000L;

    protected final CarouselComponentLocator locator;

    public BaseCarouselComponent(Locator self,
                                 CarouselType carouselType
    ) {
        super(self, carouselType.getComponentTitle());
        this.locator = new CarouselComponentLocator(self);
    }

    @SneakyThrows
    @Step("Show previous [{this.componentTitle}] slide")
    public T previous() {
        log.info("Show previous [{}] slide", this.componentTitle);
        locator.previous().click();
        Thread.sleep(SCROLL_TIMEOUT);
        return (T) this;
    }

    @SneakyThrows
    @Step("Show next [{this.componentTitle}] slide")
    public T next() {
        log.info("Show next [{}] slide", this.componentTitle);
        locator.next().click();
        Thread.sleep(SCROLL_TIMEOUT);
        return (T) this;
    }

    @SneakyThrows
    @Step("Waiting for expected [{this.componentTitle}] slide will be active")
    public T waitUntilSlideWillBeActive(int slideNumber) {

        log.info("Waiting for expected [{}] slide will be active", this.componentTitle);

        var allSlidesCount = locator.carouselSlides().all().size();
        if (slideNumber < 0 || slideNumber > allSlidesCount - 1)
            throw new IllegalArgumentException("""
                    Slide number can not be negative or be greater then all slides count - 1.
                    Slide number: %d,
                    Available slide numbers: 0,..,%d,
                    """.formatted(slideNumber, allSlidesCount - 1));

        var slides = allSlidesCount;

        while (slides > 0) {
            if (slideNumber == getActiveSlideNumber()) {
                return (T) this;
            }
            if (slides <= allSlidesCount - slides) {
                previous();
            } else {
                next();
            }
            slides = slides - 1;
            Thread.sleep(SCROLL_TIMEOUT);
        }

        throw new IllegalStateException("Slide number " + slideNumber + " not found");

    }

    @Nonnull
    @Step("Check active slide number of [{this.componentTitle}] equals")
    public T checkActiveSlideNumberEquals(int slideNumber) {
        log.info("Check active slide number of [{}] equals", this.componentTitle);
        if (slideNumber < 0)
            throw new IllegalArgumentException("Slide number cannot be negative");
        assertEquals(slideNumber, getActiveSlideNumber(), "Check carousel active slide number equals");
        return (T) this;
    }

    @Nonnull
    @Step("Check [{this.componentTitle}] has expected screenshot")
    public T checkCarouselHasScreenshot(String pathToScreenshot,
                                        double percentOfTolerance,
                                        boolean rewriteScreenshot
    ) {
        log.info("Check [{}] has expected screenshot", this.componentTitle);
        return checkElementHasScreenshot(self, pathToScreenshot, percentOfTolerance, rewriteScreenshot);
    }

    protected int getActiveSlideNumber() {
        var slideIndicators = locator.carouselSlides().all();
        return IntStream.range(0, slideIndicators.size())
                .filter(i -> slideIndicators.get(i).getAttribute("class").contains("active"))
                .findFirst()
                .orElse(-1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        locator.previous().waitFor(VISIBLE_CONDITION);
        locator.next().waitFor(VISIBLE_CONDITION);
        locator.carouselInner().waitFor(VISIBLE_CONDITION);
        return (T) this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
    }

}
