package com.automationexercise.tests.util.screenshot;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScreenDiff {

    private String expected;
    private String actual;
    private String diff;
    private boolean hasDiff;

}
