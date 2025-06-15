package com.automationexercise.tests.api.core.condition;

import io.restassured.http.ContentType;
import org.hamcrest.Matcher;

public class Conditions {

    public static StatusCodeCondition statusCode(int statusCode) {
        return new StatusCodeCondition(statusCode);
    }

    public static BodyStatusCodeCondition bodyStatusCode(int statusCode) {
        return new BodyStatusCodeCondition(statusCode);
    }

    public static BodyFieldCondition bodyField(String path, Matcher<?> matcher) {
        return new BodyFieldCondition(path, matcher);
    }

    public static ContentTypeCondition contentType(ContentType contentType) {
        return new ContentTypeCondition(contentType);
    }

}
