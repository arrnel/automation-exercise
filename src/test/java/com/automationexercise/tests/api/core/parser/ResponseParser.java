package com.automationexercise.tests.api.core.parser;

import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
public class ResponseParser implements Parsing {

    @Override
    public void autoConfigureResponseParser(Response response) {

        var responseContentType = ContentType.fromContentType(response.getContentType());
        var body = response.getBody().asString();

        if (responseContentType == ContentType.HTML) {
            if (isHtml(body)) {
                setParser(response, ContentType.HTML, Parser.HTML);
            } else if (isJson(body)) {
                setParser(response, ContentType.HTML, Parser.JSON);
            }
        }

    }

    public void setParser(Response response,
                          ContentType contentType,
                          Parser parser
    ) {
        response.then()
                .parser(contentType.toString(), parser);
    }

    private static boolean isHtml(final String body) {
        if (StringUtils.isEmpty(body)) return false;
        String[] htmlStartTags = {"<!DOCTYPE html>", "<html>"};
        return Stream.of(htmlStartTags)
                .anyMatch(htmlTag -> body.toLowerCase().startsWith(htmlTag.toLowerCase()));
    }

    private static boolean isJson(final String body) {
        if (StringUtils.isEmpty(body)) return false;
        var firstChar = body.charAt(0);
        return firstChar == '[' || firstChar == '{';
    }


}
