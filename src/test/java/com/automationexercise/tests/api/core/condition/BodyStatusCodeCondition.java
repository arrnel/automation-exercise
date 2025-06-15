package com.automationexercise.tests.api.core.condition;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import static org.hamcrest.Matchers.equalTo;

@RequiredArgsConstructor
public class BodyStatusCodeCondition implements Condition {

    private final int statusCode;

    @Override
    public void check(Response response) {
        response.then()
                .assertThat()
                .body("responseCode", equalTo(statusCode));
    }

    @Override
    public String toString() {
        return "Body status code is %s".formatted(statusCode);
    }

}
