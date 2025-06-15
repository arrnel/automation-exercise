package com.automationexercise.tests.api.core.logger;

import io.restassured.response.Response;

public interface Logging {

    void log(Response response);

}