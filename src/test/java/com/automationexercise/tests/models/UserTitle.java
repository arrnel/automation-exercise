package com.automationexercise.tests.models;

import com.automationexercise.tests.ex.UserTitleNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum UserTitle {

    EMPTY(""),
    MR("Mr"),
    MRS("Mrs"),
    MS("Ms"),
    MISS("Miss");

    private final String value;

    public static UserTitle getByValue(String value) {
        return Stream.of(UserTitle.values())
                .filter(g -> g.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new UserTitleNotFoundException("User title with value = [%s] not found".formatted(value)));
    }

}
