package com.automationexercise.tests.service.impl;

import com.automationexercise.tests.api.ProductApiClient;
import com.automationexercise.tests.config.service.ServiceConfig;
import com.automationexercise.tests.models.ProductDTO;
import com.automationexercise.tests.service.ProductApiService;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

import static com.automationexercise.tests.api.core.condition.Conditions.bodyStatusCode;
import static com.automationexercise.tests.api.core.condition.Conditions.statusCode;
import static com.automationexercise.tests.models.api.HttpStatus.OK;

@Slf4j
@ParametersAreNonnullByDefault
public class ProductServiceImpl implements ProductApiService {

    private static final ServiceConfig serviceConfig = ServiceConfig.getInstance();

    private final ProductApiClient productClient = serviceConfig.getProductApiClient();

    @Nonnull
    @Override
    @Step("Find product by id")
    public Optional<ProductDTO> getProductById(int productId) {
        log.info("Find product by id: {}", productId);
        return products().stream()
                .filter(product -> product.id().equals(productId))
                .findFirst();
    }

    @Nonnull
    @Override
    @Step("Find product by title")
    public Optional<ProductDTO> getProductByTitle(String productTitle) {
        log.info("Find product by title: {}", productTitle);
        return filteredProducts(productTitle).stream()
                .filter(product -> productTitle.equals(product.title()))
                .findFirst();
    }

    @Nonnull
    @Override
    @Step("Search products by query")
    public List<ProductDTO> filterProductsByQuery(String query) {
        log.info("Search products by query: {}", query);
        return filteredProducts(query);
    }

    @Nonnull
    @Override
    public List<ProductDTO> getAllProducts() {
        log.info("Get all products");
        return products();
    }

    @Nonnull
    public List<ProductDTO> products() {
        return productClient.sendGetAllProductsRequest()
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(OK))
                .extract()
                .asList("products", ProductDTO.class);
    }

    @Nonnull
    public List<ProductDTO> filteredProducts(String query) {
        return productClient.sendSearchProductsByQueryRequest(query)
                .shouldHave(statusCode(OK))
                .shouldHave(bodyStatusCode(OK))
                .extract()
                .asList("products", ProductDTO.class);
    }

}
