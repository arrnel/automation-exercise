package com.automationexercise.tests.jupiter.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
public @interface Company {

    String title() default "";

    String firstName() default "";

    String lastName() default "";

    String phoneNumber() default "";

    Address address() default @Address();

}
