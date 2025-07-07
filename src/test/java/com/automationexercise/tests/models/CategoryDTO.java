package com.automationexercise.tests.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import static com.automationexercise.tests.util.ObjectMapperUtil.getBeautifulJSON;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record CategoryDTO(

        @JsonProperty("usertype")
        UserTypeDTO usertype,

        @JsonProperty("category")
        String category

) {

    public static CategoryDTO of(UserType userType, String category) {
        return CategoryDTO.builder()
                .usertype(new UserTypeDTO(userType))
                .category(category)
                .build();
    }

    @Override
    public String toString() {
        return getBeautifulJSON(this);
    }

}
