package com.automationexercise.tests.models;

import lombok.Builder;

import javax.annotation.Nonnull;

import static com.automationexercise.tests.util.ObjectMapperUtil.getBeautifulJSON;

@Builder
public record ReviewInfo(
        String name,
        String email,
        String message
) {

    public ReviewInfo name(String name) {
        return new ReviewInfo(name, email, message);
    }

    public ReviewInfo email(String email) {
        return new ReviewInfo(name, email, message);
    }

    public ReviewInfo message(String message) {
        return new ReviewInfo(name, email, message);
    }

    @Nonnull
    public String toString() {
        return getBeautifulJSON(this);
    }

}
