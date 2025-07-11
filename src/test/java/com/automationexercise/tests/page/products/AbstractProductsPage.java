package com.automationexercise.tests.page.products;

import com.automationexercise.tests.ex.ProductNotFoundException;
import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.models.UserType;
import com.automationexercise.tests.page.BasePage;
import com.automationexercise.tests.page._component._type.ProductsListType;
import com.automationexercise.tests.page._component.filter.BrandFilter;
import com.automationexercise.tests.page._component.filter.CategoryFilter;
import com.automationexercise.tests.page._component.products.ProductsListComponent;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.opentest4j.AssertionFailedError;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Slf4j
@SuppressWarnings("unchecked")
@ParametersAreNonnullByDefault
public abstract class AbstractProductsPage<T> extends BasePage<T> {

    protected final ProductsListComponent productsList;
    protected final CategoryFilter categoryFilter;
    protected final BrandFilter brandFilter;

    public AbstractProductsPage(ProductsListType productsListType) {
        this.productsList = new ProductsListComponent(page.locator(".features_items"), productsListType);
        this.categoryFilter = new CategoryFilter(page.locator(".category-products"));
        this.brandFilter = new BrandFilter(page.locator(".brands_products"));
    }

    public AbstractProductsPage(ProductsListComponent productsList,
                                CategoryFilter categoryFilter,
                                BrandFilter brandFilter
    ) {
        this.productsList = productsList;
        this.categoryFilter = categoryFilter;
        this.brandFilter = brandFilter;
    }

    public ProductPage openProduct(String productTitle) {
        productsList.openProduct(productTitle);
        return new ProductPage();
    }

    // COMPONENT: CATEGORY_FILTER
    @Nonnull
    public T filterByCategory(UserType userType,
                              String categoryTitle
    ) {
        categoryFilter.filter(userType, categoryTitle);
        return (T) this;
    }

    @Nonnull
    public T checkCategoryFilterContainsUserTypes(List<UserType> userTypes) {
        categoryFilter.checkContainsUserTypes(userTypes);
        return (T) this;
    }

    @Nonnull
    public T checkCategoryFilterContainsCategory(UserType userType,
                                                 String category
    ) {
        categoryFilter.checkUserTypeContainsCategory(userType, category);
        return (T) this;
    }

    @Nonnull
    public T checkCategoryFilterContainsCategories(UserType userType,
                                                   List<String> categories
    ) {
        categoryFilter.checkUserTypeContainsCategory(userType, categories);
        return (T) this;
    }

    // COMPONENT: BRAND_FILTER
    @Nonnull
    public T filterByBrand(String brandTitle) {
        brandFilter.filter(brandTitle);
        return (T) this;
    }

    @Nonnull
    public T checkCategoryFilterContainsUserType(UserType userType) {
        categoryFilter.checkContainsUserType(userType);
        return (T) this;
    }

    @Nonnull
    public T checkBrandFilterContainsBrand(String brandTitle) {
        brandFilter.checkBrandFilterExists(brandTitle);
        return (T) this;
    }

    @Nonnull
    public T checkBrandFilterContainsBrands(List<String> brandsTitles) {
        brandFilter.checkBrandFiltersExists(brandsTitles);
        return (T) this;
    }

    @Nonnull
    public T checkBrandFilterQuantityEquals(String brandTitle, int brandQuantity) {
        brandFilter.checkBrandQuantityEquals(brandTitle, brandQuantity);
        return (T) this;
    }

    // COMPONENT: PRODUCT_LIST
    @Nonnull
    public T addProductToCart(String productTitle) {
        productsList.addToCart(productTitle);
        return (T) this;
    }

    @Nonnull
    @Step("Add product [{productTitle}] and close success notification")
    public T addProductToCartAndCloseNotification(String productTitle) {
        addProductToCart(productTitle);
        notification().checkNotificationHasSuccessAddedProductMessage();
        notification().close();
        return (T) this;
    }

    @Nonnull
    @Step("Add products to cart: {productsTitles}")
    public T addProductsToCart(List<String> productsTitles) {
        log.info("Add products to cart: {}", productsTitles);
        shouldVisiblePage();
        productsTitles.forEach(
                this::addProductToCartAndCloseNotification
        );
        return (T) this;
    }

    // Assertions
    @Nonnull
    public T checkProductsListContainsProduct(String productTitle) {
        productsList.checkProductExists(productTitle);
        return (T) this;
    }

    @Nonnull
    public T checkProductsListContainsProducts(List<String> productsTitles) {

        if (productsTitles.isEmpty()) {
            productsList.checkProductsListIsEmpty();
            return (T) this;
        }

        var stepDescription = "Check products list contains expected products";
        log.info(stepDescription);
        return Allure.step(stepDescription, () -> {
            var notFoundProducts = productsTitles.stream()
                    .filter(productTitle -> {
                        try {
                            checkProductsListContainsProduct(productTitle);
                            return false;
                        } catch (AssertionFailedError ex) {
                            return true;
                        }
                    })
                    .toList();

            if (!notFoundProducts.isEmpty())
                throw new ProductNotFoundException("""
                        Expected products titles: %s
                        Not found products: %s
                        """.formatted(productsTitles, notFoundProducts));
            return (T) this;
        });


    }

    public T checkProductHasPrice(String productTitle, PriceDTO price) {
        productsList.checkProductHasPrice(productTitle, price);
        return (T) this;
    }

    public T checkProductCardHasScreenshot(String productTitle, String pathToScreenshot) {
        return checkProductCardHasScreenshot(productTitle, pathToScreenshot, 0.0);
    }

    public T checkProductCardHasScreenshot(String productTitle,
                                           String pathToScreenshot,
                                           boolean rewriteScreenshot
    ) {
        return checkProductCardHasScreenshot(productTitle, pathToScreenshot, 0.0, rewriteScreenshot);
    }

    public T checkProductCardHasScreenshot(String productTitle,
                                           String pathToScreenshot,
                                           double percentOfTolerance
    ) {
        return checkProductCardHasScreenshot(productTitle, pathToScreenshot, percentOfTolerance, false);
    }

    public T checkProductCardHasScreenshot(String productTitle,
                                           String pathToScreenshot,
                                           double percentOfTolerance,
                                           boolean rewriteScreenshot
    ) {
        productsList.checkProductCardHasScreenshot(productTitle, pathToScreenshot, percentOfTolerance, rewriteScreenshot);
        return (T) this;
    }

    @Nonnull
    public T checkProductCardOverlayHasScreenshot(String productTitle, String pathToScreenshot) {
        return checkProductCardOverlayHasScreenshot(productTitle, pathToScreenshot, 0.0);
    }

    @Nonnull
    public T checkProductCardOverlayHasScreenshot(String productTitle,
                                                  String pathToScreenshot,
                                                  boolean rewriteScreenshot
    ) {
        return checkProductCardOverlayHasScreenshot(productTitle, pathToScreenshot, 0.0, rewriteScreenshot);
    }

    @Nonnull
    public T checkProductCardOverlayHasScreenshot(String productTitle,
                                                  String pathToScreenshot,
                                                  double percentOfTolerance
    ) {
        return checkProductCardOverlayHasScreenshot(productTitle, pathToScreenshot, percentOfTolerance, false);
    }

    @Nonnull
    public T checkProductCardOverlayHasScreenshot(String productTitle,
                                                  String pathToScreenshot,
                                                  double percentOfTolerance,
                                                  boolean rewriteScreenshot
    ) {
        productsList.checkProductCardOverlayHasScreenshot(productTitle, pathToScreenshot, percentOfTolerance, rewriteScreenshot);
        return (T) this;
    }

}
