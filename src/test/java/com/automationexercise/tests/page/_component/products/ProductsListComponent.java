package com.automationexercise.tests.page._component.products;

import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.page._component.BaseComponent;
import com.automationexercise.tests.page._component._type.ProductsListType;
import com.automationexercise.tests.page.products.ProductPage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ParametersAreNonnullByDefault
public class ProductsListComponent extends BaseComponent<ProductsListComponent> {

    private final ProductListComponentLocator locator;

    public ProductsListComponent(Locator self,
                                 ProductsListType productsListType
    ) {
        super(self, productsListType.getComponentTitle());
        this.locator = new ProductListComponentLocator(self);
    }

    @Nonnull
    @Step("Open product from [{this.componentTitle}]: {productTitle}")
    public ProductPage openProduct(String productTitle) {
        log.info("Open product: {}", productTitle);
        locator.openProduct(productTitle);
        return new ProductPage();
    }

    @Nonnull
    @Step("Add product to cart from [{this.componentTitle}]: {productTitle}")
    public ProductsListComponent addToCart(String productTitle) {
        log.info("Adding product to cart: {}", productTitle);
        locator.addToCart(productTitle).click();
        return this;
    }

    @Nonnull
    @Step("Check product exists in [{this.componentTitle}]: {productTitle}")
    public ProductsListComponent checkProductExists(String productTitle) {
        log.info("Check product exists: {}", productTitle);
        assertThat(locator.productContent(productTitle)).isVisible();
        return this;
    }

    @Nonnull
    @Step("Check product card has expected screenshot in [{this.componentTitle}]: {productTitle}")
    public ProductsListComponent checkProductCardHasScreenshot(String productTitle,
                                                               String pathToScreenshot,
                                                               double percentOfTolerance,
                                                               boolean rewriteScreenshot
    ) {
        checkElementHasScreenshot(locator.productWrapper(productTitle), pathToScreenshot, percentOfTolerance, rewriteScreenshot);
        return this;
    }

    @Nonnull
    @SneakyThrows
    @Step("Check product card overlay has expected screenshot in [{this.componentTitle}]: {productTitle}")
    public ProductsListComponent checkProductCardOverlayHasScreenshot(String productTitle,
                                                                      String pathToScreenshot,
                                                                      double percentOfTolerance,
                                                                      boolean rewriteScreenshot
    ) {
        var productOverlay = locator.productWrapper(productTitle).locator("//*[@class='product-overlay']");
        locator.productContent(productTitle).hover();
        self.page().waitForFunction(
                "([overlay, content]) => { " +
                        "const overlayDisplay = getComputedStyle(overlay, null).getPropertyValue(\"display\"); " +
                        "const overlayHeight = getComputedStyle(overlay, null).getPropertyValue(\"height\"); " +
                        "const contentHeight = getComputedStyle(content, null).getPropertyValue(\"height\"); " +
                        "return overlayDisplay === 'block' && overlayHeight === contentHeight; " +
                        "}",
                new Object[]{productOverlay.elementHandle(), locator.productContent(productTitle).elementHandle()},
                new Page.WaitForFunctionOptions().setTimeout(CFG.browserTimeout())
        );
        Thread.sleep(100);
        checkElementHasScreenshot(locator.productWrapper(productTitle), pathToScreenshot, percentOfTolerance, rewriteScreenshot);
        return this;
    }

    @Nonnull
    @Step("Check [{this.componentTitle}] is empty")
    public ProductsListComponent checkProductsListIsEmpty() {
        log.info("Check product list is empty");
        assertEquals(0, locator.products().count(), "Check products list is empty");
        return this;
    }

    @Nonnull
    @Step("Check product [{productTitle}] in [{this.componentTitle}] has price: {productPrice.currency}. {productPrice.amount}")
    public ProductsListComponent checkProductHasPrice(String productTitle,
                                                      PriceDTO productPrice
    ) {
        log.info("Check product [{}] has price: {}", productTitle, productPrice);
        assertThat(locator.productPrice(productTitle)).hasText(productPrice.getPriceText());
        return this;
    }

    @Override
    public ProductsListComponent shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
    }

}
