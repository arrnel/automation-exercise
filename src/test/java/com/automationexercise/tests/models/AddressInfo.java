package com.automationexercise.tests.models;

import lombok.Builder;

import javax.annotation.Nonnull;

import static com.automationexercise.tests.util.ObjectMapperUtil.getBeautifulJSON;

@Builder
public record AddressInfo(

        String title,
        String fullName,
        String company,
        String address1,
        String address2,
        String cityStateZip,
        String country,
        String phoneNumber

) {

    public AddressInfo title(String title) {
        return new AddressInfo(title, fullName, company, address1, address2, cityStateZip, country, phoneNumber);
    }

    @Nonnull
    @Override
    public String toString() {
        return getBeautifulJSON(this);
    }

}
