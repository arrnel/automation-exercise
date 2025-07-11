package com.automationexercise.tests.jupiter.anno.meta;

import com.automationexercise.tests.jupiter.extension.ApiLoginExtension;
import com.automationexercise.tests.jupiter.extension.BrowserExtension;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag("screenshot")
@Epic("Screenshot")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ExtendWith({
        BrowserExtension.class,
        ApiLoginExtension.class
})
public @interface ScreenshotTest {

}
