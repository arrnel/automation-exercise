package com.automationexercise.tests.models;

import lombok.Builder;

import javax.annotation.Nonnull;

import static com.automationexercise.tests.util.ObjectMapperUtil.getBeautifulJSON;

@Builder
public record CartProductInfo(

        String title,
        String category,
        PriceDTO price,
        Integer quantity,
        PriceDTO total

) {

    @Nonnull
    public String toString() {
        return getBeautifulJSON(this);
    }

}
