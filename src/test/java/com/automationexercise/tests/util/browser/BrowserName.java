package com.automationexercise.tests.util.browser;

import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;

@RequiredArgsConstructor
public enum BrowserName {

    EMPTY(""),
    CHROMIUM("chrome"),
    FIREFOX("firefox"),
    WEBKIT("safari");

    private final String value;

    @Nonnull
    @Override
    public String toString() {
        return value;
    }

}
