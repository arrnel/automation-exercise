package com.automationexercise.tests.page.products;

import com.automationexercise.tests.page._component._type.ProductsListType;

import javax.annotation.Nonnull;

public class FilteredProductsPage extends AbstractProductsPage<FilteredProductsPage> {

    public FilteredProductsPage() {
        super(ProductsListType.FILTERED_PRODUCTS);
    }

    @Nonnull
    @Override
    public FilteredProductsPage shouldVisiblePage() {
        categoryFilter.shouldVisibleComponent();
        brandFilter.shouldVisibleComponent();
        productsList.shouldVisibleComponent();
        return this;
    }

    @Override
    public void shouldNotVisiblePage() {
        categoryFilter.shouldNotVisibleComponent();
        brandFilter.shouldNotVisibleComponent();
        productsList.shouldNotVisibleComponent();
    }

}
