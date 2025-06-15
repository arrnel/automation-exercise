package com.automationexercise.tests.api.core.condition;

import io.restassured.response.Response;

public interface Condition {

    void check(Response response);

}