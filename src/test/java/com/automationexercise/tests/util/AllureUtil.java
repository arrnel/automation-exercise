package com.automationexercise.tests.util;

import com.automationexercise.tests.util.screenshot.ScreenDiff;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AllureUtil {

    // -------- Page
    private static final String PAGE_MIME_TYPE = "text/html";
    private static final String PAGE_TITLE = "Page html";
    private static final String PAGE_FILE_EXTENSION = "html";

    // -------- Screenshot
    private static final String SCREENSHOT_TITLE = "Screenshot";
    private static final String SCREENSHOT_MIME_TYPE = "image/png";

    // -------- Screen diff
    private static final String SCREEN_DIFF_MIME_TYPE = "application/vnd.allure.image.diff";
    private static final String SCREEN_DIFF_TITLE = "Screenshot diff";

    // -------- Test video
    private static final String VIDEO_MIME_TYPE = "video/webm";
    private static final String VIDEO_EXTENSION = ".webm";
    private static final String VIDEO_TITLE = "Test Video";

    // -------- Text
    private static final String TEXT_MIME_TYPE = "text/plain";


    public static void attachScreenDiff(ScreenDiff screenDiff) {
        try {
            Allure.addAttachment(
                    SCREEN_DIFF_TITLE,
                    SCREEN_DIFF_MIME_TYPE,
                    new ObjectMapper().writeValueAsString(screenDiff)
            );
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void attachPage(String pageContent) {
        try {
            Allure.addAttachment(
                    PAGE_TITLE,
                    PAGE_MIME_TYPE,
                    pageContent,
                    PAGE_FILE_EXTENSION
            );
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void attachVideo(Path path) {
        try (InputStream is = Files.newInputStream(path)) {
            Allure.addAttachment(VIDEO_TITLE, VIDEO_MIME_TYPE, is, VIDEO_EXTENSION);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void attachScreenshot(byte[] screenshot) {
        try {
            Allure.addAttachment(
                    SCREENSHOT_TITLE,
                    new ByteArrayInputStream(screenshot)
            );
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void attachText(String title, String text) {
        try {
            Allure.addAttachment(
                    title,
                    TEXT_MIME_TYPE,
                    text
            );
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
