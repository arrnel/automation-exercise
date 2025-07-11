package com.automationexercise.tests.api.impl;

import com.automationexercise.tests.api.AllureApiClient;
import com.automationexercise.tests.api.core.RestClient;
import com.automationexercise.tests.api.core.asertions.AssertableResponse;
import com.automationexercise.tests.models.allure.AllureProject;
import com.automationexercise.tests.models.allure.AllureResults;
import lombok.extern.slf4j.Slf4j;

import static com.automationexercise.tests.api.core.condition.Conditions.statusCode;
import static com.automationexercise.tests.models.api.HttpStatus.*;

@Slf4j
public class AllureApiClientImpl extends RestClient implements AllureApiClient {

    private static final String UPLOAD_RESULTS_URL = "/allure-docker-service/send-results",
            PROJECT_URL_PATTERN = "/allure-docker-service/projects/%s",
            CLEAN_RESULTS_URL = "/allure-docker-service/clean-results",
            GENERATE_REPORT = "/allure-docker-service/generate-report",
            CREATE_PROJECT = "/allure-docker-service/projects";

    public AllureApiClientImpl() {
        super(CFG.allureReportUrl());
    }

    @Override
    public void uploadResults(String projectId,
                              AllureResults results) {
        new AssertableResponse(
                given()
                        .queryParam("project_id", projectId)
                        .body(results)
                        .post(UPLOAD_RESULTS_URL)
        )
                .shouldHave(statusCode(OK));
    }

    @Override
    public void createProjectIfNotExist(String projectId) {
        if (!isProjectExists(projectId)) {
            createProject(new AllureProject(projectId));
        }
    }

    @Override
    public void cleanResults(String projectId) {
        new AssertableResponse(
                given()
                        .get(CLEAN_RESULTS_URL)
        )
                .shouldHave(statusCode(OK));
    }

    @Override
    public void generateReport(String projectId) {
        new AssertableResponse(
                given()
                        .queryParam("project_id", projectId)
                        .queryParam("execution_name", System.getenv("HEAD_COMMIT_MESSAGE"))
                        .queryParam("execution_from", System.getenv("BUILD_URL"))
                        .queryParam("execution_type", System.getenv("EXECUTION_TYPE"))
                        .get(GENERATE_REPORT)
        )
                .shouldHave(statusCode(OK));
    }

    private boolean isProjectExists(String projectId) {
        var response = given()
                .get(PROJECT_URL_PATTERN.formatted(projectId));

        return switch (response.getStatusCode()) {
            case OK -> true;
            case NOT_FOUND -> false;
            default -> throw new UnsupportedOperationException("""
                    Unsupported status code: %d,
                    Returned response body: %s
                    """.formatted(response.getStatusCode(), response.getBody()));
        };
    }

    private void createProject(AllureProject project) {
        new AssertableResponse(
                given()
                        .body(project)
                        .post(CREATE_PROJECT)
        )
                .shouldHave(statusCode(CREATED));
    }

}
