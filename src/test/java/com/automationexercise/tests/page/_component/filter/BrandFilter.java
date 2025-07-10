package com.automationexercise.tests.page._component.filter;

import com.automationexercise.tests.page._component.BaseComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.microsoft.playwright.options.WaitForSelectorState.DETACHED;

@Slf4j
@ParametersAreNonnullByDefault
public class BrandFilter extends BaseComponent<BrandFilter> {

    private final BrandFilterLocator locator;

    public BrandFilter(Locator self) {
        super(self);
        locator = new BrandFilterLocator(self);
    }

    @Step("Filter products by brand: {brand}")
    public void filter(String brand) {
        log.info("Filtering products by brand: {}", brand);
        locator.brand(brand).click();
    }

    @Step("Check brand filter [{brand}] exists")
    public void checkBrandFilterExists(String brand) {
        log.info("Check brand filter contains brand: {}", brand);
        assertThat(locator.brand(brand)).isVisible();
    }

    @Step("Check brand filters exists: [{}]")
    public void checkBrandFiltersExists(List<String> brands) {
        log.info("Check brand filter contains brands: {}", brands);

        if (brands.isEmpty())
            throw new IllegalArgumentException("Expected brands cannot be empty");

        var notFoundBrands = brands.stream()
                .filter(brand -> !locator.brand(brand).isVisible())
                .toList();

        if (!notFoundBrands.isEmpty())
            throw new AssertionError("Not found brands: " + notFoundBrands);
    }

    @Step("Check brand [{brand}] quantity equals: {quantity}")
    public void checkBrandQuantityEquals(String brand, int quantity) {
        log.info("Check brand quantity equals: {}", quantity);
        assertThat(locator.brandQuantity(brand)).hasText("(%d)".formatted(quantity));
    }

    @Override
    public BrandFilter shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        locator.title().waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(new WaitForOptions().setState(DETACHED));
    }

}
