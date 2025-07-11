package com.automationexercise.tests.jupiter.extension;

import com.automationexercise.tests.jupiter.anno.meta.DisabledByIssue;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.junit.platform.commons.support.SearchOption;

import static com.automationexercise.tests.models.github.IssueState.OPEN;

public class IssueExtension extends BaseExtension implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        return AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                DisabledByIssue.class
        ).or(
                () -> AnnotationSupport.findAnnotation(
                        context.getRequiredTestClass(),
                        DisabledByIssue.class,
                        SearchOption.INCLUDE_ENCLOSING_CLASSES
                )
        ).map(
                byIssue -> {

                    if (CFG.ignoreDisabledByIssue())
                        return ConditionEvaluationResult.enabled("Ignoring annotation @DisabledByIssue");

                    return OPEN.equals(gitHubApiClient.getIssueState(byIssue.issueId()))
                            ? ConditionEvaluationResult.disabled("Disabled by issue #" + byIssue.issueId())
                            : ConditionEvaluationResult.enabled("Issue closed");

                }
        ).orElseGet(
                () -> ConditionEvaluationResult.enabled("Annotation @DisabledByIssue not found")
        );
    }

}
