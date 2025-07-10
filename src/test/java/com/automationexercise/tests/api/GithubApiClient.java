package com.automationexercise.tests.api;

import com.automationexercise.tests.models.github.IssueState;

public interface GithubApiClient {

    IssueState getIssueState(String issueId);

}
