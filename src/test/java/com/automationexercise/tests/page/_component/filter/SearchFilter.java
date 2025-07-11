package com.automationexercise.tests.page._component.filter;

import com.automationexercise.tests.config.test.Config;
import com.automationexercise.tests.page._component.BaseComponent;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.SneakyThrows;

public class SearchFilter extends BaseComponent<SearchFilter> {

    private static final Config CFG = Config.getInstance();

    private final SearchFilterLocator locator;

    public SearchFilter(Locator self) {
        super(self);
        locator = new SearchFilterLocator(self);
    }

    @SneakyThrows
    @Step("Search product by query: {query}")
    public void search(String query) {
        locator.search().fill(query);
        locator.submit().click();
        Thread.sleep(CFG.searchTimeout());
    }

    @Override
    public SearchFilter shouldVisibleComponent() {
        locator.search().waitFor(VISIBLE_CONDITION);
        locator.submit().waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        locator.search().waitFor(DETACHED_CONDITION);
        locator.submit().waitFor(DETACHED_CONDITION);
    }

}
