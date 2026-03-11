package com.automationexercise.tests.models;

import java.awt.*;
import java.nio.file.Path;

public record ScreenshotCheckContext(
        Path expectedScreenshotPath,
        byte[] actualScreenshot,
        Dimension actualScreenshotSize,
        double tolerance,
        boolean forceRewrite
) {}