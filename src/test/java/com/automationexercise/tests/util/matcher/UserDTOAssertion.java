package com.automationexercise.tests.util.matcher;

import com.automationexercise.tests.models.UserDTO;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.AssertionFailureBuilder.assertionFailure;

@Slf4j
public class UserDTOAssertion {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final List<String> FIELD_NAMES = List.of(
            "email", "name", "firstName", "lastName", "userTitle",
            "phoneNumber", "birthDay", "birthMonth", "birthYear", "company",
            "country", "state", "city", "address1", "address2", "zipCode");

    public static boolean areUserResponsesEqual(UserDTO expected, UserDTO actual) {
        log.info("Check actual user has expected data: {}", actual);
        return matchesUsers(expected, actual, true);
    }

    public static boolean areUserCommonDataEqualWithoutId(UserDTO expected, UserDTO actual) {
        log.info("Check actual user has expected data (id not included): {}", actual);
        return matchesUsers(expected, actual, false);
    }

    private static boolean matchesUsers(UserDTO expected, UserDTO actual, boolean includeId) {

        var isEqual = new AtomicBoolean(true);
        var mismatchDescription = new StringBuilder()
                .append("Expected and actual users has difference:");

        validateExpectedAndActualUsers(expected, actual);

        if (includeId)
            assertIsFieldsEquals(expected, actual, "id", isEqual, mismatchDescription);

        FIELD_NAMES.forEach(fieldName ->
                assertIsFieldsEquals(expected, actual, fieldName, isEqual, mismatchDescription)
        );

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

    private static void assertIsFieldsEquals(UserDTO expected,
                                             UserDTO actual,
                                             String fieldName,
                                             AtomicBoolean isEqual,
                                             StringBuilder mismatchDescription
    ) {
        try {
            Method getter = UserDTO.class.getMethod(fieldName);
            Object expectedValue = getter.invoke(expected);
            Object actualValue = getter.invoke(actual);

            if (expectedValue == null && actualValue == null) return;
            if (expectedValue == null || actualValue == null || !Objects.equals(expectedValue, actualValue)) {
                appendMismatch(mismatchDescription, fieldName, expectedValue, actualValue);
                isEqual.set(false);
            }
        } catch (Exception e) {
            mismatchDescription.append("Could not compare field ").append(fieldName).append(": ").append(e.getMessage());
        }
    }

    private static void validateExpectedAndActualUsers(UserDTO expected, UserDTO actual) {
        if (expected == null && actual == null) return;
        if (expected == null || actual == null) {
            assertionFailure()
                    .message("One of expected or actual users can not equals null")
                    .expected(expected)
                    .actual(actual)
                    .buildAndThrow();
        }
    }

    private static void appendMismatch(StringBuilder mismatchDescription, String fieldName, Object expectedValue, Object actualValue) {
        if (!mismatchDescription.isEmpty())
            mismatchDescription.append(LINE_SEPARATOR);

        mismatchDescription.append(fieldName)
                .append(": expected <").append(expectedValue)
                .append("> but was <").append(actualValue).append(">");
    }

}
