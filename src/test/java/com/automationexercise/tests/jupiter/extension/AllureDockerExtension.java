package com.automationexercise.tests.jupiter.extension;

import com.automationexercise.tests.api.AllureApiClient;
import com.automationexercise.tests.config.service.ServiceConfig;
import com.automationexercise.tests.config.test.Config;
import com.automationexercise.tests.models.allure.AllureResults;
import com.automationexercise.tests.models.allure.DecodedAllureFile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

/**
 * EXTENSION HAS GLOBAL REGISTRATION TYPE
 */
@Slf4j
@ParametersAreNonnullByDefault
public class AllureDockerExtension extends BaseExtension implements SuiteExtension {

    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final boolean IS_CI = "github".equalsIgnoreCase(System.getenv("EXECUTION_TYPE"));
    private static final String PROJECT_NAME = Config.PROJECT_NAME;
    private static final Path allureResultsDirectory = CFG.pathToAllureResults();
    private static final AllureApiClient allureApiClient = ServiceConfig.getInstance().getAllureApiClient();

    @Override
    public void beforeSuite(ExtensionContext context) {
        if (!IS_CI)
            return;

        log.info("Create new project if not exists and clean previous results: {}", PROJECT_NAME);
        allureApiClient.createProjectIfNotExist(PROJECT_NAME);
        allureApiClient.cleanResults(PROJECT_NAME);
    }

    @Override
    public void afterSuite() {

        if (!IS_CI)
            return;

        log.info("Upload allure results and generate report: {}", allureResultsDirectory);

        if (!Files.exists(allureResultsDirectory) || !Files.isDirectory(allureResultsDirectory))
            throw new IllegalStateException("Allure results directory does not exist or is not a directory");

        try (var paths = Files.walk(allureResultsDirectory).filter(Files::isRegularFile)) {
            paths.forEach(path -> {
                var sendFile = new DecodedAllureFile(
                        path.getFileName().toString(),
                        encodeFileToBase64(path.toFile())
                );
                allureApiClient.uploadResults(
                        PROJECT_NAME,
                        new AllureResults(List.of(sendFile)));
            });
            allureApiClient.generateReport(PROJECT_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String encodeFileToBase64(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return encoder.encodeToString(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
