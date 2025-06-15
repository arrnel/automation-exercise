package com.automationexercise.tests.models;

import com.automationexercise.tests.api.core.config.UserTypeDeserializer;
import com.automationexercise.tests.api.core.config.UserTypeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

public record UserTypeDTO(

        @JsonProperty("usertype")
        @JsonDeserialize(using = UserTypeDeserializer.class)
        @JsonSerialize(using = UserTypeSerializer.class)
        UserType userType

) implements Serializable {

}
