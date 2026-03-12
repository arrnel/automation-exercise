package com.automationexercise.tests.api.impl;

import com.automationexercise.tests.api.GithubApiClient;
import com.automationexercise.tests.api.core.RestClient;
import com.automationexercise.tests.api.core.asertions.AssertableResponse;
import com.automationexercise.tests.models.api.HttpStatus;
import com.automationexercise.tests.models.github.IssueState;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.automationexercise.tests.api.core.condition.Conditions.statusCode;

@Slf4j
@ParametersAreNonnullByDefault
public class GithubApiClientImpl extends RestClient implements GithubApiClient {

    public GithubApiClientImpl() {
        super(CFG.ghApiUrl());
    }

    @Nonnull
    public IssueState getIssueState(String issueId) {
        var issueUrl = "/repos/%s/%s/issues/%s".formatted(CFG.ghOwner(), CFG.ghRepo(), issueId);
        var stepDescription = "[API] Send get issue state request. [GET]:%s".formatted(issueUrl);
        log.info(stepDescription);
        String state = Allure.step(stepDescription, () ->
                new AssertableResponse(
                        given()
                                .accept("application/vnd.github+json")
                                .header("user-agent", CFG.ghTokenName())
                                .header("authorization", CFG.ghToken())
                                .get(issueUrl)
                )
                        .shouldHave(statusCode(HttpStatus.OK))
                        .extract()
                        .asValue("state")
        );
        return IssueState.valueOf(state.toUpperCase());
    }


}
