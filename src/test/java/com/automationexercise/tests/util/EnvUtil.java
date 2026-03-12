package com.automationexercise.tests.util;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
public class EnvUtil {

    @Nonnull
    public static String envVar(String title, String defaultValue) {
        return Optional.ofNullable(System.getenv(title))
                .filter(v -> !v.isBlank())
                .orElse(defaultValue);
    }

    @Nonnull
    public static Integer envVar(String title, int defaultValue) {
        return Optional.ofNullable(
                        System.getenv(title)
                )
                .map(Integer::parseInt)
                .orElse(defaultValue);
    }

    @Nonnull
    public static Boolean envVar(String title, boolean defaultValue) {
        return Optional.ofNullable(
                        System.getenv(title)
                )
                .filter(v -> !v.trim().isEmpty())
                .map("true"::equalsIgnoreCase)
                .orElse(defaultValue);
    }

    @Nonnull
    public static Double envVar(String title, double defaultValue) {
        return Optional.ofNullable(
                        System.getenv(title)
                )
                .map(Double::parseDouble)
                .orElse(defaultValue);
    }

}