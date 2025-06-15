package com.automationexercise.tests.api.core.condition;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matcher;

@RequiredArgsConstructor
public class BodyFieldCondition implements Condition {

    private final String path;
    private final Matcher<?> matcher;


    @Override
    public void check(Response response) {
        response.then()
                .assertThat()
                .body(path, matcher);
    }

    @Override
    public String toString() {
        return "Body field [%s] %s".formatted(path, matcher);
    }

}
