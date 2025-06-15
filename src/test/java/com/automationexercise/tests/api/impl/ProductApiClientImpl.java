package com.automationexercise.tests.api.impl;

import com.automationexercise.tests.api.ProductApiClient;
import com.automationexercise.tests.api.core.RestClient;
import com.automationexercise.tests.api.core.asertions.AssertableResponse;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@ParametersAreNonnullByDefault
public class ProductApiClientImpl extends RestClient implements ProductApiClient {

    private static final String GET_ALL_PRODUCTS_URL = "/productsList";
    private static final String SEARCH_PRODUCTS_URL = "/searchProduct";

    public ProductApiClientImpl() {
        super(CFG.baseApiUrl());
    }

    @Nonnull
    @Override
    @Step("Send request [GET]:/searchProduct")
    public AssertableResponse sendSearchProductsByQueryRequest(String query) {
        log.debug("Send request [GET]:/searchProduct");
        return new AssertableResponse(
                given()
                        .contentType(ContentType.URLENC)
                        .formParam("search_product", query)
                        .post(SEARCH_PRODUCTS_URL));
    }


    @Nonnull
    @Override
    @Step("Send request [GET]:/productsList")
    public AssertableResponse sendGetAllProductsRequest() {
        log.info("Send request [GET]:/productsList");
        return new AssertableResponse(
                given()
                        .get(GET_ALL_PRODUCTS_URL));
    }

}
