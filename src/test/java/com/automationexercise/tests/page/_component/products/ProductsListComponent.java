package com.automationexercise.tests.page._component.products;

import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.models.ScreenshotParam;
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
                                                               String pathToScreenshot
    ) {
        checkElementHasScreenshot(locator.productWrapper(productTitle), pathToScreenshot);
        return this;
    }

    @Nonnull
    @Step("Check product card has expected screenshot in [{this.componentTitle}]: {productTitle}")
    public ProductsListComponent checkProductCardHasScreenshot(String productTitle,
                                                               ScreenshotParam screenshotParam
    ) {
        checkElementHasScreenshot(locator.productWrapper(productTitle), screenshotParam);
        return this;
    }

    public ProductsListComponent checkProductCardOverlayHasScreenshot(String productTitle,
                                                                      String expectedScreenshotUrl
    ) {
        return checkProductCardOverlayHasScreenshot(
                productTitle,
                ScreenshotParam.builder()
                        .expectedScreenshotUrl(expectedScreenshotUrl)
                        .build()
        );
    }

    @Nonnull
    @SneakyThrows
    @Step("Check product card overlay has expected screenshot in [{this.componentTitle}]: {productTitle}")
    public ProductsListComponent checkProductCardOverlayHasScreenshot(String productTitle,
                                                                      ScreenshotParam screenshotParam
    ) {
        var productOverlay = locator.productWrapper(productTitle).locator("//*[@class='product-overlay']");
        var parentHandle = productOverlay.locator("..").elementHandle(); // или page.locator(".single-products").elementHandle()
        locator.productContent(productTitle).hover();

        Runnable action = () -> {
            self.page().waitForFunction(
                    """
                            ([overlay, parent]) => {
                                const style = getComputedStyle(overlay);
                                if (style.display !== 'block') return false;
                    
                                const overlayHeight = parseFloat(style.height);
                                const parentHeight = parseFloat(getComputedStyle(parent).height);
                                const isFullyOpened = Math.abs(overlayHeight - parentHeight) < 1;
                    
                                if (isFullyOpened) {
                                    overlay.style.transition = 'none';
                                    overlay.style.height = parentHeight + 'px';
                                    return true;
                                }
                                return false;
                            }""",
                    new Object[]{productOverlay.elementHandle(), parentHandle},
                    new Page.WaitForFunctionOptions().setTimeout(CFG.browserTimeout())
            );

            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
                // NOP
            }
        };

        checkElementHasScreenshot(
                locator.productWrapper(productTitle),
                screenshotParam.setAction(action)
        );

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
