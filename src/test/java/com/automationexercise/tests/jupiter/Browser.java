package com.automationexercise.tests.jupiter;

import com.automationexercise.tests.util.browser.BrowserName;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Browser {

    BrowserName value() default BrowserName.EMPTY;

    String size() default "";

}
