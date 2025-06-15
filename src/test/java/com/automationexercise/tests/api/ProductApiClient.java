package com.automationexercise.tests.api;

import com.automationexercise.tests.api.core.asertions.AssertableResponse;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface ProductApiClient {

    @Nonnull
    AssertableResponse sendSearchProductsByQueryRequest(String query);

    @Nonnull
    AssertableResponse sendGetAllProductsRequest();

}
