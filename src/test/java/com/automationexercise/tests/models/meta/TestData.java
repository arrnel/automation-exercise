package com.automationexercise.tests.models.meta;

import com.automationexercise.tests.models.api.Token;

public record TestData(
        Token token,
        String password,
        String phone
) {

    public static TestData empty() {
        return new TestData(null, null, null);
    }

    public TestData token(Token token) {
        return new TestData(token, password, phone);
    }

    public TestData password(String password) {
        return new TestData(token, password, phone);
    }

    public TestData phone(String phone) {
        return new TestData(token, password, phone);
    }

}
