package com.automationexercise.tests.util;

import com.automationexercise.tests.models.Currency;
import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.models.ProductDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductUtil {

    public static List<String> productsTitles(List<ProductDTO> products) {
        return products.stream()
                .map(ProductDTO::title)
                .toList();
    }

    public static PriceDTO productsTotalPrice(List<ProductDTO> products) {
        return new PriceDTO(
                Currency.RS,
                products.stream()
                        .map(product -> product.price().amount())
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

}
