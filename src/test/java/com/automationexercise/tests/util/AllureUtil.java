package com.automationexercise.tests.util;

import com.automationexercise.tests.util.screenshot.ScreenDiff;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AllureUtil {

    public static void addScreenDiffAttachment(ScreenDiff screenDiff) {
        try {
            Allure.addAttachment(
                    "Screenshot diff",
                    "application/vnd.allure.image.diff",
                    new ObjectMapper().writeValueAsString(screenDiff)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
