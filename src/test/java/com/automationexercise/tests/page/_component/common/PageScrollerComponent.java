package com.automationexercise.tests.page._component.common;

import com.automationexercise.tests.page._component.BaseComponent;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageScrollerComponent extends BaseComponent<PageScrollerComponent> {

    public PageScrollerComponent(Locator self) {
        super(self);
    }

    @Step("Scroll page to the top")
    public void scrollToTop() {
        log.info("Scroll page to the top");
        Allure.step("Click on [Scroll to top] button", () -> self.click());
        Allure.step("Wait until stop scrolling", this::waitUntilStopScrolling);
    }

    @Override
    public PageScrollerComponent shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
    }

}
