package com.automationexercise.tests.api.core.parser;

import io.restassured.response.Response;

public interface Parsing {

    void autoConfigureResponseParser(Response response);

}
