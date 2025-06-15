package com.automationexercise.tests.models;

import com.automationexercise.tests.ex.UserTypeNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum UserType {

    EMPTY(""),
    WOMEN("Women"),
    MEN("Men"),
    KIDS("Kids");

    private final String value;

    public static UserType getByValue(String value) {
        return Stream.of(UserType.values())
                .filter(g -> g.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new UserTypeNotFoundException("UserType with value = [%s] not found".formatted(value)));
    }
}
