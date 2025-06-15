package com.automationexercise.tests.models.api;

public record Token(

        String type,

        String value

) {

    public static Token empty() {
        return new Token(null, null);
    }

    public String toString() {
        return "%s %s".formatted(type, value);
    }

}
