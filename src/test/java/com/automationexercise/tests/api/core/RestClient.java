package com.automationexercise.tests.api.core;

import com.automationexercise.tests.api.core.store.CookieManagerFilter;
import com.automationexercise.tests.config.test.Config;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.EnumUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
public abstract class RestClient {

    protected static final Config CFG = Config.getInstance();

    private static final LogDetail DEFAULT_LOG_LEVEL = EnumUtils.getEnumIgnoreCase(
            LogDetail.class,
            System.getProperty("test.api.log.level", "headers"));
    private static final ContentType DEFAULT_ACCEPT = ContentType.JSON;
    private static final ContentType DEFAULT_CONTENT_TYPE = ContentType.JSON;

    private static final String ALLURE_RESTASSURED_REQUEST_TPL = "request-attachment.ftl";
    private static final String ALLURE_RESTASSURED_RESPONSE_TPL = "response-attachment.ftl";

    protected final RequestSpecification requestSpec;

    public RestClient(String baseUrl) {
        this(baseUrl, true, DEFAULT_ACCEPT, DEFAULT_CONTENT_TYPE, DEFAULT_LOG_LEVEL, new Filter[0]);
    }

    public RestClient(String baseUrl, ContentType accept, ContentType contentType) {
        this(baseUrl, true, accept, contentType, DEFAULT_LOG_LEVEL);
    }

    public RestClient(String baseUrl, boolean followRedirect) {
        this(baseUrl, followRedirect, DEFAULT_ACCEPT, DEFAULT_CONTENT_TYPE, DEFAULT_LOG_LEVEL);
    }

    public RestClient(String baseUrl, LogDetail loggingLevel) {
        this(baseUrl, true, DEFAULT_ACCEPT, DEFAULT_CONTENT_TYPE, loggingLevel);
    }

    public RestClient(String baseUrl, boolean followRedirect, LogDetail loggingLevel) {
        this(baseUrl, followRedirect, DEFAULT_ACCEPT, DEFAULT_CONTENT_TYPE, loggingLevel);
    }

    public RestClient(String baseUrl, boolean followRedirect, ContentType accept, ContentType contentType, @Nullable LogDetail loggingLevel, @Nullable Filter... filters) {

        RequestSpecBuilder specBuilder = new RequestSpecBuilder()
                .setConfig(RestAssuredConfig.config().redirect(
                        RedirectConfig.redirectConfig().followRedirects(followRedirect)
                ))
                .setBaseUri(baseUrl)
                .setAccept(accept)
                .setContentType(contentType)
                .addFilter(new CookieManagerFilter())
                .addFilter(new AllureRestAssured()
                        .setRequestTemplate(ALLURE_RESTASSURED_REQUEST_TPL)
                        .setResponseTemplate(ALLURE_RESTASSURED_RESPONSE_TPL));

        if (filters != null && filters.length > 0) {
            Stream.of(filters)
                    .forEach(specBuilder::addFilter);
        }

        Optional.ofNullable(loggingLevel)
                .ifPresentOrElse(
                        ll -> specBuilder
                                .addFilter(new RequestLoggingFilter(ll))
                                .addFilter(new ResponseLoggingFilter(ll)),
                        () -> specBuilder
                                .addFilter(new RequestLoggingFilter(DEFAULT_LOG_LEVEL))
                                .addFilter(new ResponseLoggingFilter(DEFAULT_LOG_LEVEL))
                );

        this.requestSpec = specBuilder.build();

    }

    @Nonnull
    protected RequestSpecification given() {
        return RestAssured.given()
                .spec(requestSpec);
    }

}
