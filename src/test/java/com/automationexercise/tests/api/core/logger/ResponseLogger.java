package com.automationexercise.tests.api.core.logger;

import io.restassured.response.Response;

public class ResponseLogger implements Logging {

    @Override
    public void log(Response response) {
        response.then().log().all();
    }

}
