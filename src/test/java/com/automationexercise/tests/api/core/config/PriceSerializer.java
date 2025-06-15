package com.automationexercise.tests.api.core.config;

import com.automationexercise.tests.models.PriceDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.io.Serial;

public class PriceSerializer extends StdSerializer<PriceDTO> {

    @Serial
    private static final long serialVersionUID = 1L;

    protected PriceSerializer() {
        super(PriceDTO.class);
    }

    @Override
    public void serialize(PriceDTO price,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeString(price != null
                ? price.getPriceText()
                : null
        );
    }

}
