package com.automationexercise.tests.util;

public class EnvDataUtil {

    public static boolean getBooleanEnv(String env, boolean defaultValue) {
        var value = System.getenv(env);
        return isEmptyOrBlank(value)
                ? defaultValue
                : "true".equalsIgnoreCase(value);
    }

    public static boolean getBooleanProperty(String property, boolean defaultValue) {
        var value = System.getProperty(property);
        return isEmptyOrBlank(value)
                ? defaultValue
                : "true".equalsIgnoreCase(value);
    }

    public static double getDoubleEnv(String env, double defaultValue) {
        var value = System.getenv(env);
        return isEmptyOrBlank(value)
                ? defaultValue
                : Double.parseDouble(value);
    }

    public static double getDoubleProperty(String property, double defaultValue) {
        var value = System.getProperty(property);
        return isEmptyOrBlank(value)
                ? defaultValue
                : Double.parseDouble(value);
    }

    public static long getLongEnv(String env, long defaultValue) {
        var value = System.getenv(env);
        return isEmptyOrBlank(value)
                ? defaultValue
                : Long.parseLong(value);
    }

    public static long getLongProperty(String property, long defaultValue) {
        var value = System.getProperty(property);
        return isEmptyOrBlank(value)
                ? defaultValue
                : Long.parseLong(value);
    }

    public static String getStringEnv(String env, String defaultValue) {
        var value = System.getenv(env);
        return isEmptyOrBlank(value)
                ? defaultValue
                : value;
    }

    public static String getStringProperty(String property, String defaultValue) {
        var value = System.getProperty(property);
        return isEmptyOrBlank(value)
                ? defaultValue
                : value;
    }

    private static boolean isEmptyOrBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

}
