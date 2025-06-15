package com.automationexercise.tests.api.core.asertions;

import com.automationexercise.tests.api.core.condition.Condition;
import com.automationexercise.tests.api.core.extractor.Extractor;
import com.automationexercise.tests.api.core.logger.Logging;
import com.automationexercise.tests.api.core.logger.ResponseLogger;
import com.automationexercise.tests.api.core.parser.Parsing;
import com.automationexercise.tests.api.core.parser.ResponseParser;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AssertableResponse {

    private final Response response;
    private final Logging logging;
    private final Parsing parsing;

    private boolean isParserConfigured = false;

    public AssertableResponse(Response response) {
        this.response = response;
        this.parsing = new ResponseParser();
        this.logging = new ResponseLogger();
        parsing.autoConfigureResponseParser(response);
    }

    public AssertableResponse shouldHave(Condition condition) {
        autoConfigureResponseParserOnFirstRun();

        log.info("Check status of: {}", condition);
        condition.check(response);
        return this;
    }

    public AssertableResponse logAll() {
        logging.log(response);
        return this;
    }

    public Extractor extract() {
        return new Extractor(response);
    }

    private void autoConfigureResponseParserOnFirstRun() {
        if (!isParserConfigured) {
            parsing.autoConfigureResponseParser(response);
            isParserConfigured = true;
        }
    }

}
