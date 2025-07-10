package com.automationexercise.tests.page.order;

import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.page.BasePage;
import com.automationexercise.tests.page.products.MainPage;
import com.automationexercise.tests.util.matcher.AppMatchers;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
public class OrderPlacedPage extends BasePage<OrderPlacedPage> {

    private static final String ORDER_PLACED_TITLE = "Order Placed!";
    private static final String ORDER_PLACED_MESSAGE = "Congratulations! Your order has been confirmed!";

    private final Locator title;
    private final Locator message;
    private final Locator downloadInvoice;
    private final Locator next;

    public OrderPlacedPage() {
        var container = page.locator("section .container");
        title = container.locator("h2 b");
        message = container.locator("p");
        downloadInvoice = container.locator("text=Download Invoice");
        next = container.locator("text=Continue");
    }

    @Step("Check order placed message is visible")
    public OrderPlacedPage checkOrderPlacedMessageVisible() {
        log.info("Check order placed message is visible");
        assertThat(title).hasText(ORDER_PLACED_TITLE);
        assertThat(message).hasText(ORDER_PLACED_MESSAGE);
        return this;
    }

    @Step("Download invoice")
    public Download downloadInvoice() {
        log.info("Download invoice");
        return page.waitForDownload(downloadInvoice::click);
    }


    @Step("Download invoice")
    private Path downloadAndSafeInvoice() {
        log.info("Download invoice");
        var currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        var pathToFile = Paths.get(CFG.pathToInvoiceFolder(), "invoice_%s.txt".formatted(currentTime));
        page.waitForDownload(downloadInvoice::click)
                .saveAs(pathToFile);
        return pathToFile;
    }

    public OrderPlacedPage checkInvoiceContainsName(String name) {
        var pathToFile = downloadAndSafeInvoice();
        var stepDescription = "Check invoice contains name: %s".formatted(name);
        log.info(stepDescription);
        Allure.step(stepDescription, () ->
                AppMatchers.invoiceContainsName(pathToFile, name));
        return this;
    }

    public OrderPlacedPage checkInvoiceContainsPrice(PriceDTO price) {
        var pathToFile = downloadAndSafeInvoice();
        var stepDescription = "Check invoice contains price: %s".formatted(price.getAmountText());
        log.info(stepDescription);
        Allure.step(stepDescription, () ->
                AppMatchers.invoiceContainsPrice(pathToFile, price));
        return this;
    }

    public OrderPlacedPage checkInvoiceContainsNameAndPrice(String name, PriceDTO price) {
        var pathToFile = downloadAndSafeInvoice();
        var stepDescription = "Check invoice contains name = [%s] and price = [%s]".formatted(name, price.getAmountText());
        log.info(stepDescription);
        Allure.step(stepDescription, () ->
                AppMatchers.invoiceContainsPrice(pathToFile, price));
        return this;
    }

    @Step("End purchase")
    public MainPage next() {
        log.info("End purchase");
        next.click();
        return new MainPage();
    }

    @Nonnull
    @Override
    public OrderPlacedPage shouldVisiblePage() {
        title.waitFor(VISIBLE_CONDITION);
        message.waitFor(VISIBLE_CONDITION);
        downloadInvoice.waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisiblePage() {
        title.waitFor(DETACHED_CONDITION);
        message.waitFor(DETACHED_CONDITION);
        downloadInvoice.waitFor(DETACHED_CONDITION);
    }

}
