package com.automationexercise.tests.jupiter.anno.meta;

import com.automationexercise.tests.models.allure.AllureTag;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag(AllureTag.API_TEST)
@Epic("API")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ApiTest {

}
