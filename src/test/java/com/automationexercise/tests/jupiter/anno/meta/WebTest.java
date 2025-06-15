package com.automationexercise.tests.jupiter.anno.meta;

import com.automationexercise.tests.jupiter.extension.UserExtension;
import com.automationexercise.tests.jupiter.extension.UsersExtension;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag("Web")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith({
        AllureJunit5.class,
        UserExtension.class,
        UsersExtension.class
})
public @interface WebTest {

}
