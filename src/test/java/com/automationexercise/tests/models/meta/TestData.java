package com.automationexercise.tests.models.meta;

import com.automationexercise.tests.models.api.Token;

public record TestData(
        Token sessionId,
        Token csrf,
        String password,
        String phone
) {

    public static TestData empty() {
        return new TestData(null, null, null, null);
    }

    public TestData sessionId(Token sessionId) {
        return new TestData(sessionId, csrf, password, phone);
    }

    public TestData csrf(Token csrf) {
        return new TestData(sessionId, csrf, password, phone);
    }

    public TestData password(String password) {
        return new TestData(sessionId, csrf, password, phone);
    }

    public TestData phone(String phone) {
        return new TestData(sessionId, csrf, password, phone);
    }

}
