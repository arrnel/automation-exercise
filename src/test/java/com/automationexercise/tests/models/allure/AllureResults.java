package com.automationexercise.tests.models.allure;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AllureResults(

        @JsonProperty("results")
        List<DecodedAllureFile> results

) {

}
