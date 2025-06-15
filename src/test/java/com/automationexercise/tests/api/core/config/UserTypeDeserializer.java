package com.automationexercise.tests.api.core.config;

import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.models.UserType;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.io.Serial;

public class UserTypeDeserializer extends StdDeserializer<UserType> {

    @Serial
    private static final long serialVersionUID = 1L;

    protected UserTypeDeserializer() {
        super(PriceDTO.class);
    }

    @Override
    public UserType deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext
    ) throws IOException, JacksonException {
        var userTypeText = jsonParser.readValueAs(String.class);
        return userTypeText != null
                ? UserType.getByValue(userTypeText)
                : null;
    }

}
