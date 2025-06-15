package com.automationexercise.tests.api.impl;

import com.automationexercise.tests.api.VerifyLoginApiClient;
import com.automationexercise.tests.api.core.RestClient;
import com.automationexercise.tests.api.core.asertions.AssertableResponse;
import com.automationexercise.tests.models.CredentialsDTO;
import com.automationexercise.tests.util.ObjToMapConverter;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@ParametersAreNonnullByDefault
public class VerifyLoginApiClientImpl extends RestClient implements VerifyLoginApiClient {

    private static final String VERIFY_LOGIN_URL = "/verifyLogin";

    public VerifyLoginApiClientImpl() {
        super(CFG.baseApiUrl(), ContentType.ANY, ContentType.URLENC);
    }

    @Nonnull
    @Override
    @Step("Send [POST]:/api/verifyLogin")
    public AssertableResponse sendVerifyLoginRequest(CredentialsDTO credentials) {
        log.debug("Send [POST]:/api/verifyLogin with credentials: {}", credentials);
        return new AssertableResponse(
                given()
                        .contentType(ContentType.URLENC)
                        .formParams(ObjToMapConverter.convertObjToMap(credentials))
                        .post(VERIFY_LOGIN_URL));
    }

}
