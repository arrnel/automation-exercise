package com.automationexercise.tests.util.matcher;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.AssertionFailureBuilder.assertionFailure;

@ParametersAreNonnullByDefault
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectsMatcher {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    public static <T> boolean compare(Class<T> clazz, T expected, T actual) {
        return compareObjects(clazz, expected, actual, null);
    }

    public static <T> boolean compare(Class<T> clazz, T expected, T actual, List<String> fieldNames) {
        return compareObjects(clazz, expected, actual, fieldNames);
    }

    private static <T> boolean compareObjects(Class<T> clazz, T expected, T actual, @Nullable List<String> fieldNames) {

        var isEqual = new AtomicBoolean(true);
        var mismatchDescription = new StringBuilder()
                .append("Expected and actual %s has difference:".formatted(clazz.getSimpleName()));

        var fields = clazz.getDeclaredFields();
        var fieldNamesList = fieldNames != null && !fieldNames.isEmpty()
                ? fieldNames
                : Arrays.stream(fields).map(Field::getName).toList();

        Arrays.stream(fields)
                .filter(field -> fieldNamesList.contains(field.getName()))
                .forEach(field -> {

                    try {
                        Method getter = clazz.getMethod(field.getName());
                        assertIsFieldsEquals(
                                getter.invoke(expected),
                                getter.invoke(actual),
                                field.getName(),
                                isEqual,
                                mismatchDescription
                        );
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                });

        if (!isEqual.get()) {
            assertionFailure()
                    .message(mismatchDescription.toString())
                    .expected(expected)
                    .actual(actual)
                    .buildAndThrow();
            return false;
        }
        return true;
    }

    private static void assertIsFieldsEquals(@Nullable Object expectedField,
                                             @Nullable Object actualField,
                                             String fieldName,
                                             AtomicBoolean isEqual,
                                             StringBuilder mismatchDescription
    ) {
        try {
            if (expectedField == null && actualField == null) return;
            if (expectedField == null || actualField == null || !Objects.equals(expectedField, actualField)) {
                appendMismatch(mismatchDescription, fieldName, expectedField, actualField);
                isEqual.set(false);
            }
        } catch (Exception e) {
            mismatchDescription.append("Could not compare field ").append(fieldName).append(": ").append(e.getMessage());
        }
    }

    private static void appendMismatch(StringBuilder mismatchDescription,
                                       String fieldName,
                                       @Nullable Object expectedValue,
                                       @Nullable Object actualValue
    ) {
        if (!mismatchDescription.isEmpty())
            mismatchDescription.append(LINE_SEPARATOR);

        mismatchDescription.append(fieldName)
                .append(": expected <").append(expectedValue)
                .append("> but was <").append(actualValue).append(">");
    }


}
