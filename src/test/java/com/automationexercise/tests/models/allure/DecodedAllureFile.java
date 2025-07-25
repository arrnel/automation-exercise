package com.automationexercise.tests.models.allure;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DecodedAllureFile(

        @JsonProperty("file_name")
        String fileName,

        @JsonProperty("content_base64")
        String contentBase64

) {

}
