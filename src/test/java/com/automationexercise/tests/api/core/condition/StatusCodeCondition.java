package com.automationexercise.tests.api.core.condition;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusCodeCondition implements Condition {

    private final int statusCode;

    @Override
    public void check(Response response) {
        response.then()
                .assertThat()
                .statusCode(statusCode);
    }

    @Override
    public String toString() {
        return "Status code is %s".formatted(statusCode);
    }

}
