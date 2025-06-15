package com.automationexercise.tests.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

import static com.automationexercise.tests.util.ObjectMapperUtil.getBeautifulJSON;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record BrandDTO(

        @JsonProperty("id")
        Long id,

        @JsonProperty("brand")
        String title

) implements Serializable {

    @Override
    public String toString() {
        return getBeautifulJSON(this);
    }

}
