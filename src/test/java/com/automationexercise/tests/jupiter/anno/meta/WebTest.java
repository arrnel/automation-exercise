package com.automationexercise.tests.jupiter.anno.meta;

import com.automationexercise.tests.jupiter.extension.BrowserExtension;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag("web")
@Epic("WEB")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(BrowserExtension.class)
public @interface WebTest {

}
