package com.automationexercise.tests.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.automationexercise.tests.config.test.CfgInstance.CFG;

@Getter
@Setter
@Builder
@Accessors(chain = true)
public class ScreenshotParam {

    private String expectedScreenshotUrl;

    @Builder.Default
    private double tolerance = CFG.defaultScreenshotTolerance();

    @Builder.Default
    private boolean rewrite = false;

    @Builder.Default
    private boolean hover = false;

    @Builder.Default
    private int timeout = CFG.defaultScreenshotTimeout();

    private Runnable action;

}
