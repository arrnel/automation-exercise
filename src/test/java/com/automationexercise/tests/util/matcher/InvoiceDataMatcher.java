package com.automationexercise.tests.util.matcher;

import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.util.FileUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AssertionFailureBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;

@Slf4j
@ParametersAreNonnullByDefault
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvoiceDataMatcher {

    public static boolean invoiceContainsName(Path path, String name) {
        var text = FileUtil.fileContent(path);
        String actualName = extractName(text);
        if (!actualName.equals(name)) {
            AssertionFailureBuilder.assertionFailure()
                    .message("Name mismatch: expected <" + name + "> but was <" + actualName + ">")
                    .buildAndThrow();
        }
        return true;
    }

    public static boolean invoiceContainsPrice(Path path, PriceDTO price) {
        var text = FileUtil.fileContent(path);
        String actualPrice = extractPrice(text);
        if (!actualPrice.equals(price.getAmountText())) {
            AssertionFailureBuilder.assertionFailure()
                    .message("Price mismatch: expected <" + price + "> but was <" + actualPrice + ">")
                    .buildAndThrow();
        }
        return true;
    }

    public static boolean invoiceContainsNameAndPrice(Path path, String name, PriceDTO price) {
        var text = FileUtil.fileContent(path);
        String actualName = extractName(text);
        String actualPrice = extractPrice(text);

        StringBuilder mismatchDescription = new StringBuilder("Expected and actual invoices data has difference:\n");
        boolean hasError = false;

        if (!actualName.equals(name)) {
            mismatchDescription.append("Name: expected <").append(name)
                    .append("> but was <").append(actualName).append(">\n");
            hasError = true;
        }

        if (!actualPrice.equals(price.getAmountText())) {
            mismatchDescription.append("Total price: expected <").append(price)
                    .append("> but was <").append(actualPrice).append(">\n");
            hasError = true;
        }

        if (hasError) {
            AssertionFailureBuilder.assertionFailure()
                    .message(mismatchDescription.toString())
                    .buildAndThrow();
        }

        return true;

    }

    private static String extractName(String text) {
        var leftSideText = "Hi ";
        var rightSideText = ", Your";
        var leftSide = text.indexOf(leftSideText) + leftSideText.length();
        var rightSide = text.lastIndexOf(rightSideText);
        return text.substring(leftSide, rightSide);
    }

    private static String extractPrice(String text) {
        var leftSideText = "amount is ";
        var rightSideText = ". Thank you";
        var leftSide = text.lastIndexOf(leftSideText) + leftSideText.length();
        var rightSide = text.lastIndexOf(rightSideText);
        return text.substring(leftSide, rightSide);
    }

}
