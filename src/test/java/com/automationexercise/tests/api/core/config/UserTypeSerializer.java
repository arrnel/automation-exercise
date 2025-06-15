package com.automationexercise.tests.api.core.config;

import com.automationexercise.tests.models.UserType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.io.Serial;

public class UserTypeSerializer extends StdSerializer<UserType> {

    @Serial
    private static final long serialVersionUID = 1L;

    protected UserTypeSerializer() {
        super(UserType.class);
    }

    @Override
    public void serialize(UserType userType,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeString(userType != null
                ? userType.getValue()
                : null
        );
    }

}
