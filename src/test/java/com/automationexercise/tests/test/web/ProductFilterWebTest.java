package com.automationexercise.tests.test.web;

import com.automationexercise.tests.jupiter.anno.meta.WebTest;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.BrandFilterTag;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.CategoryFilterTag;
import com.automationexercise.tests.jupiter.anno.tag.ComponentTag.SearchFilterTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.MainPageTag;
import com.automationexercise.tests.jupiter.anno.tag.PageTag.ProductsPageTag;
import com.automationexercise.tests.models.ProductDTO;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.page.products.ProductsListPage;
import com.automationexercise.tests.test.BaseTest;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@WebTest
@DisplayName("[WEB] Product filter tests")
class ProductFilterWebTest extends BaseTest {

    @Owner("@arrnel")
    @ProductsPageTag
    @SearchFilterTag
    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("com.automationexercise.tests.test.data.ProductDataProvider#queryProvider")
    @DisplayName("Should filter products by query")
    void shouldFilterProductByQueryInProductsPageTest(String caseName, String query) {

        // Data
        var expectedProductsTitles = productApiService.filterProductsByQuery(query).stream()
                .map(ProductDTO::title)
                .toList();

        // Steps
        new ProductsListPage().open()
                .filterByQuery(query)

                // Assertions
                .checkProductsListContainsProducts(expectedProductsTitles);
    }

    @Owner("@arrnel")
    @MainPageTag
    @CategoryFilterTag
    @Test
    @DisplayName("Should filter products by category in main page")
    void shouldFilterProductsByCategoryInMainPageTest() {

        // Data
        var userType = DataGenerator.randomUserType();
        var category = DataGenerator.randomUserTypeCategory(userType);
        var expectedProductsTitles = productApiService.getAllProducts().stream()
                .filter(product -> product.category().usertype().userType().equals(userType))
                .filter(product -> product.category().category().equals(category))
                .map(ProductDTO::title)
                .toList();

        // Steps
        new MainPage().open()
                .filterByCategory(userType, category)

                // Assertions
                .checkProductsListContainsProducts(expectedProductsTitles);
    }

    @Owner("@arrnel")
    @ProductsPageTag
    @CategoryFilterTag
    @Test
    @DisplayName("Should filter products by category in products page")
    void shouldFilterProductsByCategoryInProductsPageTest() {

        // Data
        var userType = DataGenerator.randomUserType();
        var category = DataGenerator.randomUserTypeCategory(userType);
        var expectedProductsTitles = productApiService.getAllProducts().stream()
                .filter(product -> product.category().usertype().userType().equals(userType))
                .filter(product -> product.category().category().equals(category))
                .map(ProductDTO::title)
                .toList();

        // Steps
        new ProductsListPage().open()
                .filterByCategory(userType, category)

                // Assertions
                .checkProductsListContainsProducts(expectedProductsTitles);
    }

    @Owner("@arrnel")
    @MainPageTag
    @BrandFilterTag
    @Test
    @DisplayName("Should filter products by brand in main page")
    void shouldFilterProductsByBrandInMainPageTest() {

        // Data
        var brand = DataGenerator.randomBrand();
        var expectedProductsTitles = productApiService.getAllProducts().stream()
                .filter(product -> product.brand().equals(brand))
                .map(ProductDTO::title)
                .toList();

        // Steps
        new MainPage().open()
                .filterByBrand(brand)

                // Assertions
                .checkProductsListContainsProducts(expectedProductsTitles);
    }

    @Owner("@arrnel")
    @ProductsPageTag
    @BrandFilterTag
    @Test
    @DisplayName("Should filter products by brand in products page")
    void shouldFilterProductsByBrandInProductsPageTest() {

        // Data
        var brand = DataGenerator.randomBrand();
        var expectedProductsTitles = productApiService.getAllProducts().stream()
                .filter(product -> product.brand().equals(brand))
                .map(ProductDTO::title)
                .toList();

        // Steps
        new ProductsListPage().open()
                .filterByBrand(brand)

                // Assertions
                .checkProductsListContainsProducts(expectedProductsTitles);
    }

}
