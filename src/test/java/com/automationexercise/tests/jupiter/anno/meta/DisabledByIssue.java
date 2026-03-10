package com.automationexercise.tests.jupiter.anno.meta;

import com.automationexercise.tests.jupiter.extension.IssueExtension;
import com.automationexercise.tests.models.allure.AllureTag;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag(AllureTag.DISABLED_BY_ISSUE)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(IssueExtension.class)
public @interface DisabledByIssue {

    String issueId() default "";

}
