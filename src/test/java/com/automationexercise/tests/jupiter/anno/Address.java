package com.automationexercise.tests.jupiter.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
public @interface Address {

    String country() default "";

    String state() default "";

    String city() default "";

    String address1() default "";

    String address2() default "";

    String zipCode() default "";

}
