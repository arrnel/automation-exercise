package com.automationexercise.tests.jupiter.anno;

import com.automationexercise.tests.models.UserTitle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
public @interface User {

    String email() default "";

    String password() default "";

    String name() default "";

    UserTitle userTitle() default UserTitle.EMPTY;

    Company company() default @Company();

    Date birthday() default @Date();

}
