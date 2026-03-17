package com.automationexercise.tests.store;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public enum ThreadSafeTestNameStore {

    INSTANCE;
    private static final ThreadLocal<String> threadSafeTestNameStore = ThreadLocal.withInitial(String::new);

    @Nullable
    public String getCurrentTestTitle() {
        return threadSafeTestNameStore.get();
    }

    public void setCurrentTestTitle(String token) {
        threadSafeTestNameStore.set(token);
    }

}