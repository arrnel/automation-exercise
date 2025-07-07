package com.automationexercise.tests.models.api;

import javax.annotation.Nonnull;

public record Token(

        String type,

        String value

) {

    public static Token empty() {
        return new Token(null, null);
    }

    @Nonnull
    public String toString() {
        return "%s %s".formatted(type, value);
    }

}
