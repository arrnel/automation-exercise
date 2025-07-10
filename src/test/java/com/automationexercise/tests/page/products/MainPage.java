package com.automationexercise.tests.page.products;

import com.automationexercise.tests.ex.ProductNotFoundException;
import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.page._component._type.ProductsListType;
import com.automationexercise.tests.page._component.carousel.ImageCarouselComponent;
import com.automationexercise.tests.page._component.carousel.ProductCarouselComponent;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.opentest4j.AssertionFailedError;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static com.automationexercise.tests.page._component._type.CarouselType.IMAGE;
import static com.automationexercise.tests.page._component._type.CarouselType.RECOMMENDED_PRODUCTS;

@Slf4j
@ParametersAreNonnullByDefault
public class MainPage extends AbstractProductsPage<MainPage> {

    public static final String URL = CFG.baseUrl();

    // Components
    private final ImageCarouselComponent imageCarousel;
    private final ProductCarouselComponent recommendedProductsCarousel;

    public MainPage() {
        super(ProductsListType.ALL_PRODUCTS);
        this.imageCarousel = new ImageCarouselComponent(page.locator("#slider-carousel"), IMAGE);
        this.recommendedProductsCarousel = new ProductCarouselComponent(page.locator(".recommended_items"), RECOMMENDED_PRODUCTS);
    }

    @Nonnull
    @Step("Navigate to the [Main] page")
    public MainPage open() {
        log.info("Navigate to the [Main] page");
        page.navigate(URL);
        return this;
    }

    // COMPONENT: IMAGE CAROUSEL
    @Nonnull
    public MainPage showNextImageCarouselSlide() {
        imageCarousel.next();
        return this;
    }

    @Nonnull
    public MainPage showPreviousImageCarouselSlide() {
        imageCarousel.previous();
        return this;
    }

    @Nonnull
    public MainPage waitForImageCarouselWillHaveActiveSlide(int slideNumber) {
        imageCarousel.waitUntilSlideWillBeActive(slideNumber);
        return this;
    }

    @Nonnull
    public MainPage checkImageCarouselHasActiveSlide(int slideNumber) {
        imageCarousel.checkActiveSlideNumberEquals(slideNumber);
        return this;
    }

    @Nonnull
    public MainPage checkImageCarouselHasScreenshot(String pathToScreenshot) {
        return checkImageCarouselHasScreenshot(pathToScreenshot, 0, false);
    }

    @Nonnull
    public MainPage checkImageCarouselHasScreenshot(String pathToScreenshot,
                                                    double percentOfTolerance
    ) {
        return checkImageCarouselHasScreenshot(pathToScreenshot, percentOfTolerance, false);
    }

    @Nonnull
    public MainPage checkImageCarouselHasScreenshot(String pathToScreenshot,
                                                    boolean rewriteScreenshot
    ) {
        return checkImageCarouselHasScreenshot(pathToScreenshot, 0, rewriteScreenshot);
    }

    @Nonnull
    @Step("Check image carousel has expected screenshot")
    public MainPage checkImageCarouselHasScreenshot(String pathToScreenshot,
                                                    double percentOfTolerance,
                                                    boolean rewriteScreenshot
    ) {
        imageCarousel.checkCarouselHasScreenshot(pathToScreenshot, percentOfTolerance, rewriteScreenshot);
        return this;
    }

    // COMPONENT: RECOMMENDED PRODUCTS CAROUSEL
    @Nonnull
    @Step("Add recommended product to cart: {productTitle}")
    public MainPage addRecommendedProductToCart(String productTitle) {
        Allure.step("Add recommended product to cart: %s".formatted(productTitle), () -> recommendedProductsCarousel.addToCart(productTitle));
        Allure.step("Check alert has success add product message", notification()::checkNotificationHasSuccessAddedProductMessage);
        Allure.step("Close alert", notification()::close);
        return this;
    }

    @Nonnull
    @Step("Add recommended product to cart: {productTitle}")
    public MainPage addRecommendedProductToCartWithError(String productTitle) {
        Allure.step("Add recommended product to cart: %s".formatted(productTitle), () -> recommendedProductsCarousel.addToCart(productTitle));
        return this;
    }

    @Nonnull
    @Step("Add recommended products to cart: {productsTitles}")
    public MainPage addRecommendedProductsToCart(List<String> productsTitles) {
        if (productsTitles.isEmpty()) return this;
        var stepDescription = "Add recommended products to cart: %s".formatted(productsTitles);
        log.info(stepDescription);
        productsTitles.forEach(this::addRecommendedProductToCart);
        return this;
    }

    @Nonnull
    @Step("Show next recommended products carousel slide")
    public MainPage showNextRecommendedProductsCarouselSlide() {
        recommendedProductsCarousel.next();
        return this;
    }

    @Nonnull
    @Step("Show previous recommended products carousel slide")
    public MainPage showPreviousRecommendedProductsCarouselSlide() {
        recommendedProductsCarousel.previous();
        return this;
    }

    @Nonnull
    @Step("Wait for recommended products carousel will have active slide: {slideNumber}")
    public MainPage waitForRecommendedProductsCarouselWillHaveActiveSlide(int slideNumber) {
        recommendedProductsCarousel.waitUntilSlideWillBeActive(slideNumber);
        return this;
    }

    @Nonnull
    public MainPage checkRecommendedProductsCarouselHasActiveSlide(int slideNumber) {
        recommendedProductsCarousel.checkActiveSlideNumberEquals(slideNumber);
        return this;
    }

    public MainPage checkRecommendedProductHasPrice(String productTitle, PriceDTO price) {
        var stepDescription = "Check recommended product [%s] has price: %s".formatted(productTitle, price.getPriceText());
        log.info(stepDescription);

        Allure.step(stepDescription, () ->
                recommendedProductsCarousel.checkProductHasPrice(productTitle, price));

        return this;
    }

    @Nonnull
    @Step("Check recommended products carousel contains expected product: {productTitle}")
    public MainPage checkRecommendedProductsCarouselContainsProduct(String productTitle) {
        recommendedProductsCarousel.checkContainsProduct(productTitle);
        return this;
    }

    @Nonnull
    @Step("Check recommended products carousel contains expected products: {productsTitles}")
    public MainPage checkRecommendedProductsCarouselContainsProducts(List<String> productsTitles) {
        if (productsTitles.isEmpty()) return this;
        var stepDescription = "Check products list contains products: %s".formatted(productsTitles);
        log.info(stepDescription);

        var notFoundProducts = productsTitles.stream()
                .map(productTitle -> {
                    try {
                        checkRecommendedProductsCarouselContainsProduct(productTitle);
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

        return this;
    }

    @Nonnull
    public MainPage checkRecommendedProductsCarouselHasScreenshot(String pathToScreenshot) {
        return checkRecommendedProductsCarouselHasScreenshot(pathToScreenshot, 0, false);
    }

    @Nonnull
    public MainPage checkRecommendedProductsCarouselHasScreenshot(String pathToScreenshot,
                                                                  double percentOfTolerance
    ) {
        return checkRecommendedProductsCarouselHasScreenshot(pathToScreenshot, percentOfTolerance, false);
    }

    @Nonnull
    public MainPage checkRecommendedProductsCarouselHasScreenshot(String pathToScreenshot,
                                                                  boolean rewriteScreenshot
    ) {
        return checkRecommendedProductsCarouselHasScreenshot(pathToScreenshot, 0, rewriteScreenshot);
    }

    @Nonnull
    public MainPage checkRecommendedProductsCarouselHasScreenshot(String pathToScreenshot,
                                                                  double percentOfTolerance,
                                                                  boolean rewriteScreenshot
    ) {
        recommendedProductsCarousel.checkCarouselHasScreenshot(pathToScreenshot, percentOfTolerance, rewriteScreenshot);
        return this;
    }

    // Assertions
    @Nonnull
    @Override
    public MainPage shouldVisiblePage() {
        imageCarousel.shouldVisibleComponent();
        return this;
    }

    @Override
    public void shouldNotVisiblePage() {
        imageCarousel.shouldNotVisibleComponent();
    }

}
