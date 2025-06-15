package com.automationexercise.tests.api.core.config;

import com.automationexercise.tests.models.Currency;
import com.automationexercise.tests.models.PriceDTO;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.io.Serial;
import java.math.BigDecimal;

public class PriceDeserializer extends StdDeserializer<PriceDTO> {

    @Serial
    private static final long serialVersionUID = 1L;

    protected PriceDeserializer() {
        super(PriceDTO.class);
    }

    @Override
    public PriceDTO deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext
    ) throws IOException, JacksonException {
        var priceData = jsonParser.readValueAs(String.class);
        if (priceData == null) return null;

        var priceDataArray = priceData.split("\\. ");
        return new PriceDTO(
                Currency.getByValue(priceDataArray[0]),
                new BigDecimal(priceDataArray[1])
        );
    }

}
