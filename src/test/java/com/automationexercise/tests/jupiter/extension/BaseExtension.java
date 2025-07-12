package com.automationexercise.tests.jupiter.extension;

import com.automationexercise.tests.api.GithubApiClient;
import com.automationexercise.tests.config.service.ServiceConfig;
import com.automationexercise.tests.config.test.Config;
import com.automationexercise.tests.service.AuthApiService;
import com.automationexercise.tests.service.UserApiService;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@ParametersAreNonnullByDefault
public abstract class BaseExtension {

    private static final ServiceConfig SERVICE_CONFIG = ServiceConfig.getInstance();
    protected static final Config CFG = Config.getInstance();

    protected final GithubApiClient gitHubApiClient = SERVICE_CONFIG.getGithubApiClient();

    protected final UserApiService userService = SERVICE_CONFIG.getUserApiService();
    protected final AuthApiService authService = SERVICE_CONFIG.getAuthApiService();

    protected static boolean isParameterListOfType(Parameter parameter, Class<?> clazz) {
        if (parameter.getType().isAssignableFrom(List.class)) {
            ParameterizedType type = (ParameterizedType) parameter.getParameterizedType();
            Type[] typeArgs = type.getActualTypeArguments();
            return typeArgs.length == 1 && typeArgs[0].equals(clazz);
        }
        return false;
    }

}
