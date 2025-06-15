package com.automationexercise.tests.api.impl;

import com.automationexercise.tests.api.BrandApiClient;
import com.automationexercise.tests.api.core.RestClient;
import com.automationexercise.tests.api.core.asertions.AssertableResponse;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

@Slf4j
public class BrandApiClientImpl extends RestClient implements BrandApiClient {

    private static final String GET_ALL_BRANDS_URL = "/brandsList";

    public BrandApiClientImpl() {
        super(CFG.baseApiUrl());
    }

    @Nonnull
    @Override
    @Step("Send request [GET]:/brandsList")
    public AssertableResponse sendGetAllBrandsRequest() {
        log.debug("Send request [GET]:/brandsList");
        return new AssertableResponse(
                given()
                        .get(GET_ALL_BRANDS_URL)
        );
    }

}
