package com.automationexercise.tests.api.core.config;

import com.automationexercise.tests.models.UserTitle;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.io.Serial;

public class UserTitleDeserializer extends StdDeserializer<UserTitle> {

    @Serial
    private static final long serialVersionUID = 1L;

    protected UserTitleDeserializer() {
        super(UserTitle.class);
    }

    @Override
    public UserTitle deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext
    ) throws IOException, JacksonException {
        var userTitleText = jsonParser.readValueAs(String.class);
        return userTitleText != null
                ? UserTitle.getByValue(userTitleText)
                : null;
    }

}
