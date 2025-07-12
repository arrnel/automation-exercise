package com.automationexercise.tests.api;

import com.automationexercise.tests.models.allure.AllureResults;

public interface AllureApiClient {

    void uploadResults(String projectId, AllureResults results);

    void createProjectIfNotExist(String projectId);

    void cleanResults(String projectId);

    void generateReport(String projectId);

}
