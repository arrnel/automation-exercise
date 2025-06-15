package com.automationexercise.tests.api.core.config;

import com.automationexercise.tests.models.UserTitle;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.io.Serial;

public class UserTitleSerializer extends StdSerializer<UserTitle> {

    @Serial
    private static final long serialVersionUID = 1L;

    protected UserTitleSerializer() {
        super(UserTitle.class);
    }

    @Override
    public void serialize(UserTitle userTitle,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeString(userTitle != null
                ? userTitle.getValue()
                : null
        );
    }

}
