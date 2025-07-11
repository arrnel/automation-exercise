package com.automationexercise.tests.jupiter.extension;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@ParametersAreNonnullByDefault
public class EnvLoggerExtension extends BaseExtension implements SuiteExtension {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        var prettyEnvData = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(CFG.envData());
        log.info("Env data: {}", prettyEnvData);
        Allure.step("Env data", () -> Allure.addAttachment("Env data", prettyEnvData));
    }

}
