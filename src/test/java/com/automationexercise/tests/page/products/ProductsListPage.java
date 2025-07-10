package com.automationexercise.tests.page.products;

import com.automationexercise.tests.page._component.filter.SearchFilter;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.automationexercise.tests.page._component._type.ProductsListType.FILTERED_PRODUCTS;

@Slf4j
@ParametersAreNonnullByDefault
public class ProductsListPage extends AbstractProductsPage<ProductsListPage> {

    private static final String URL = CFG.baseUrl() + "/products";

    // Components
    private final SearchFilter searchFilter;

    public ProductsListPage() {
        super(FILTERED_PRODUCTS);
        this.searchFilter = new SearchFilter(page.locator("#advertisement .container"));
    }

    @Nonnull
    @Step("Navigate to the [Products] page")
    public ProductsListPage open() {
        log.info("Navigate to the [Products] page");
        page.navigate(URL);
        return this;
    }

    // Actions
    @Nonnull
    @Step("Filter products by query: {query}")
    public ProductsListPage filterByQuery(String query) {
        searchFilter.search(query);
        return this;
    }

    @Nonnull
    @Override
    public ProductsListPage shouldVisiblePage() {
        searchFilter.shouldVisibleComponent();
        productsList.shouldVisibleComponent();
        return this;
    }

    @Override
    public void shouldNotVisiblePage() {
        searchFilter.shouldNotVisibleComponent();
        productsList.shouldNotVisibleComponent();
    }

}
