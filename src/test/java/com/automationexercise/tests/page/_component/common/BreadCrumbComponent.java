package com.automationexercise.tests.page._component.common;

import com.automationexercise.tests.page._component.BaseComponent;
import com.automationexercise.tests.page.products.MainPage;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class BreadCrumbComponent extends BaseComponent<BreadCrumbComponent> {

    private final BreadCrumbComponentLocator locator;

    public BreadCrumbComponent(Locator self) {
        super(self);
        this.locator = new BreadCrumbComponentLocator(self);
    }

    @Nonnull
    @Step("Navigate to [Home] page")
    public MainPage home() {
        log.info("Navigate to [Home] page");
        locator.home().click();
        return new MainPage();
    }

    @Nonnull
    @Step("Navigate to previous page")
    public BreadCrumbComponent previousPage() {
        log.info("Navigate to previous page");
        var breadcrumbs = locator.breadCrumbs().all();
        var previousBreadCrumbIndex = breadcrumbs.size() - 1;

        if (previousBreadCrumbIndex < 0)
            throw new UnsupportedOperationException("Can't navigate to previous page");

        breadcrumbs.get(previousBreadCrumbIndex).click();
        return this;
    }

    @Nonnull
    @Step("Check active breadcrumb title equals: {title}")
    public BreadCrumbComponent checkActiveBreadcrumbTitle(String title) {
        log.info("Check active breadcrumb title equals [{}]", title);
        assertThat(locator.activeBreadCrumb()).hasText(title);
        return this;
    }

    @Override
    public BreadCrumbComponent shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        locator.home().waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
    }

}
