package com.automationexercise.tests.models;

import com.automationexercise.tests.util.ObjectMapperUtil;
import lombok.Builder;

import javax.annotation.Nonnull;

@Builder
public record CardInfo(

        String name,
        String number,
        String cvc,
        String expiryMonth,
        String expiryYear

) {

    @Nonnull
    @Override
    public String toString() {
        return ObjectMapperUtil.getBeautifulJSON(this);
    }

}
