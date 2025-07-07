package com.automationexercise.tests.test.api;

import com.automationexercise.tests.jupiter.anno.meta.ApiTest;
import com.automationexercise.tests.models.*;
import com.automationexercise.tests.models.api.HttpStatus;
import com.automationexercise.tests.test.BaseTest;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.automationexercise.tests.api.core.condition.Conditions.bodyStatusCode;
import static com.automationexercise.tests.api.core.condition.Conditions.statusCode;
import static org.junit.jupiter.api.Assertions.*;

@ApiTest
@Feature("[API] Products tests")
@DisplayName("[API] Products test")
class ProductApiTest extends BaseTest {

    private static final ProductDTO EXPECTED_PRODUCT = ProductDTO.builder()
            .id(8L)
            .title("Fancy Green Top")
            .price(PriceDTO.builder()
                    .currency(Currency.RS)
                    .amount(BigDecimal.valueOf(700))
                    .build())
            .brand("Polo")
            .category(CategoryDTO.builder()
                    .category("Tops")
                    .usertype(new UserTypeDTO(UserType.WOMEN))
                    .build())
            .build();

    private static final ProductDTO EXCLUDED_BY_FILTER_PRODUCT = ProductDTO.builder()
            .id(2L)
            .title("Men Tshirt")
            .price(PriceDTO.builder()
                    .currency(Currency.RS)
                    .amount(BigDecimal.valueOf(400))
                    .build())
            .brand("H&amp;M")
            .category(CategoryDTO.builder()
                    .category("Tshirts")
                    .usertype(new UserTypeDTO(UserType.MEN))
                    .build())
            .build();

    @Test
    @DisplayName("Should get all products")
    void shouldGetAllProductsTest() {
        // Steps
        var products = productApiClient.sendGetAllProductsRequest()

                // Assertions
                //.shouldHave(contentType(ContentType.JSON)) <- current realization returns HTML
                .shouldHave(statusCode(HttpStatus.OK))
                .shouldHave(bodyStatusCode(HttpStatus.OK))
                .extract()
                .asList("products", ProductDTO.class);

        // Assertions
        assertAll(
                () -> assertFalse(products.isEmpty(), "Check products list not empty"),
                () -> assertTrue(products.contains(EXPECTED_PRODUCT), "Check products contains expected product")
        );
    }

    @Test
    @DisplayName("Should filter products")
    void shouldFilterProductsTest() {
        // Data
        var query = "Top";

        // Steps
        var products = productApiClient.sendSearchProductsByQueryRequest(query)

                // Assertions
                //.shouldHave(contentType(ContentType.JSON)) <- current realization returns HTML
                .shouldHave(statusCode(HttpStatus.OK))
                .shouldHave(bodyStatusCode(HttpStatus.OK))
                .extract()
                .asList("products", ProductDTO.class);

        assertAll(
                () -> assertFalse(products.isEmpty(), "Check products list not empty"),
                () -> assertTrue(products.contains(EXPECTED_PRODUCT), "Check products contains expected product"),
                () -> assertFalse(products.contains(EXCLUDED_BY_FILTER_PRODUCT))
        );
    }

    @Test
    @DisplayName("Should return empty list if products not found")
    void shouldReturnEmptyProductsListTest() {
        // Data
        var query = FAKE.company().catchPhrase();

        // Steps
        var products = productApiClient.sendSearchProductsByQueryRequest(query)

                // Assertions
                //.shouldHave(contentType(ContentType.JSON)) <- current realization returns HTML
                .shouldHave(statusCode(HttpStatus.OK))
                .shouldHave(bodyStatusCode(HttpStatus.OK))
                .extract()
                .asList("products", ProductDTO.class);

        assertAll(
                () -> assertTrue(products.isEmpty(), "Check products list not empty")
        );
    }


}
