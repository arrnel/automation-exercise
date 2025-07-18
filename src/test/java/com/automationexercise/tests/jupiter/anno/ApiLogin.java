package com.automationexercise.tests.jupiter.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ApiLogin {

    String email() default "";

    String password() default "";

    User value() default @User;

}
