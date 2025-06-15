package com.automationexercise.tests.api.impl;

import com.automationexercise.tests.api.UserApiClient;
import com.automationexercise.tests.api.core.RestClient;
import com.automationexercise.tests.api.core.asertions.AssertableResponse;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.util.ObjToMapConverter;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@ParametersAreNonnullByDefault
public class UserApiClientImpl extends RestClient implements UserApiClient {

    private static final String REGISTER_USER_URL = "/createAccount";
    private static final String GET_USER_BY_EMAIL_URL = "/getUserDetailByEmail";
    private static final String UPDATE_USER_URL = "/updateAccount";
    private static final String DELETE_USER_URL = "/deleteAccount";

    public UserApiClientImpl() {
        super(CFG.baseApiUrl());
    }

    @Nonnull
    @Step("Send request [POST]:/createAccount")
    public AssertableResponse createNewUser(UserDTO user) {
        log.debug("Send request [POST]:/createAccount with user data: {}", user);
        return new AssertableResponse(
                given()
                        .contentType(ContentType.URLENC)
                        .formParams(ObjToMapConverter.convertObjToMap(user))
                        .post(REGISTER_USER_URL));
    }

    @Nonnull
    @Step("Send request [GET]:/getUserDetailByEmail")
    public AssertableResponse getUserByEmail(String email) {
        log.debug("Send request [GET]:/getUserDetailByEmail email: {}", email);
        return new AssertableResponse(
                given()
                        .param("email", email)
                        .get(GET_USER_BY_EMAIL_URL));
    }

    @Nonnull
    @Step("Send request [PUT]:/updateAccount")
    public AssertableResponse updateUser(UserDTO user) {
        log.debug("Send request [PUT]:/updateAccount with user data: {}", user);
        return new AssertableResponse(
                given()
                        .contentType(ContentType.URLENC)
                        .formParams(ObjToMapConverter.convertObjToMap(user))
                        .put(UPDATE_USER_URL));
    }

    @Nonnull
    @Step("Send request [DELETE]:/deleteAccount")
    public AssertableResponse deleteUser(String email, String password) {
        log.debug("Send request [DELETE]:/deleteAccount with email = [{}] and password = [{}]", email, password);
        return new AssertableResponse(
                given()
                        .contentType(ContentType.URLENC)
                        .formParam("email", email)
                        .formParam("password", password)
                        .delete(DELETE_USER_URL));
    }

}
