package com.automationexercise.tests.page._component.carousel;

import com.automationexercise.tests.ex.ProductNotFoundException;
import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.page._component._type.CarouselType;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
@ParametersAreNonnullByDefault
public class ProductCarouselComponent extends BaseCarouselComponent<ProductCarouselComponent> {

    public ProductCarouselComponent(Locator self,
                                    CarouselType carouselType
    ) {
        super(self, carouselType);
    }

    @Nonnull
    @Step("Add to cart product [{productTitle}] on [{this.componentTitle}]")
    public ProductCarouselComponent addToCart(String productTitle) {
        log.info("Adding product [{}] on [{}]", productTitle, this.componentTitle);
        scrollToProduct(productTitle)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with title = [%s] not found in carousel"
                                .formatted(productTitle)));
        clickOnAddToCart(productTitle);
        return this;
    }

    @SneakyThrows
    @Step("Click on button [Add to cart] of product = [{productTitle}] in [{this.componentTitle}]")
    private void clickOnAddToCart(String productTitle) {
        log.info("Click on button [Add to cart] of product = [{}] in [{}]", productTitle, this.componentTitle);
        self.locator("//div[contains(@class,'productinfo') and ./p[text()='%s']]".formatted(productTitle))
                .locator(".add-to-cart")
                .click();
    }

    @Nonnull
    @Step("Check [{this.componentTitle}] contains product with title: {productTitle}")
    public ProductCarouselComponent checkContainsProduct(String productTitle) {
        log.info("Check [{}] contains product with title: [{}]", this.componentTitle, productTitle);
        foundProduct(productTitle)
                .orElseThrow(() -> new AssertionError("Product with title = [%s] not found in carousel".formatted(productTitle)));
        return this;
    }

    @Nonnull
    @Step("Check [{this.componentTitle}] contains expected products")
    public ProductCarouselComponent checkContainsProducts(List<String> productTitles) {
        var notFoundProducts = productTitles.stream()
                .filter(productTitle -> foundProduct(productTitle).isEmpty())
                .toList();

        if (!notFoundProducts.isEmpty())
            throw new AssertionError("Product carousel expected products not found: " + notFoundProducts);
        return this;
    }

    @Nonnull
    @Step("Check product [{productTitle}] has price in [{this.componentTitle}]: {price.currency}. {price.amount}")
    public ProductCarouselComponent checkProductHasPrice(String productTitle, PriceDTO price) {
        log.info("Check product [{}] has price in [{}]: {}", productTitle, this.componentTitle, price.getPriceText());
        foundProduct(productTitle)
                .ifPresentOrElse(
                        productItem ->
                                assertThat(productItem.locator("h2")).hasText(price.getPriceText()),
                        () -> {
                            throw new ProductNotFoundException("Product with title = [%s] not found in carousel"
                                    .formatted(productTitle));
                        });
        return this;
    }

    @Nonnull
    @Step("Scroll [{this.componentTitle}] to product: {productTitle}")
    public Optional<Locator> scrollToProduct(String productTitle) {
        log.info("Scroll [{}] to product: {}", this.componentTitle, productTitle);
        return foundProduct(productTitle);
    }

    @SneakyThrows
    private Optional<Locator> foundProduct(String productTitle) {
        var carouselSlides = locator.carouselSlides().all();
        for (int i = 0; i < carouselSlides.size(); i++) {

            Thread.sleep(200);

            var productItem = locator.activeCarouselSlide().locator("p").all().stream()
                    .filter(item -> item.innerText().equals(productTitle))
                    .findFirst();

            if (productItem.isPresent())
                return productItem;
            next();
        }
        return Optional.empty();
    }

}
