package com.automationexercise.tests.models;

import com.automationexercise.tests.ex.CurrencyNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Currency {

    RS("Rs");

    private final String title;

    public static Currency getByValue(String value) {
        return Stream.of(Currency.values())
                .filter(g -> g.getTitle().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new CurrencyNotFoundException("Currency with value = [%s] not found".formatted(value)));
    }

}
