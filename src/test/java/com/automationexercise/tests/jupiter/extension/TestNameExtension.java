package com.automationexercise.tests.jupiter.extension;

import com.automationexercise.tests.store.ThreadSafeTestNameStore;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestNameExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = context.getRequiredTestMethod().getName();
        ThreadSafeTestNameStore.INSTANCE.setCurrentTestTitle("%s.%s".formatted(className, methodName));
    }
}