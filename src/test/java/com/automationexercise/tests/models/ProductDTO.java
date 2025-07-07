package com.automationexercise.tests.models;

import com.automationexercise.tests.api.core.config.PriceDeserializer;
import com.automationexercise.tests.api.core.config.PriceSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

import java.util.Objects;

import static com.automationexercise.tests.util.ObjectMapperUtil.getBeautifulJSON;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductDTO(

        @JsonProperty("id")
        Long id,

        @JsonProperty("name")
        String title,

        @JsonProperty("category")
        CategoryDTO category,

        @JsonProperty("brand")
        String brand,

        @JsonProperty("price")
        @JsonDeserialize(using = PriceDeserializer.class)
        @JsonSerialize(using = PriceSerializer.class)
        PriceDTO price

) {

    @Override
    public String toString() {
        return getBeautifulJSON(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(brand, that.brand) && Objects.equals(price, that.price) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, category, brand, price);
    }

}
