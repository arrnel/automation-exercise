package com.automationexercise.tests.test;

import com.automationexercise.tests.api.*;
import com.automationexercise.tests.config.service.ServiceConfig;
import com.automationexercise.tests.config.test.Config;
import com.automationexercise.tests.service.*;
import net.datafaker.Faker;

public abstract class BaseTest {

    protected static final Config CFG = Config.getInstance();
    protected static final ServiceConfig SERVICE_CONFIG = ServiceConfig.getInstance();
    protected static final Faker FAKE = new Faker();

    // Client
    protected final AuthApiClient authApiClient;
    protected final BrandApiClient brandApiClient;
    protected final ProductApiClient productApiClient;
    protected final UserApiClient userApiClient;
    protected final VerifyLoginApiClient verifyLoginApiClient;

    // Service
    protected final AuthApiService authApiService;
    protected final BrandApiService brandApiService;
    protected final ProductApiService productApiService;
    protected final UserApiService userApiService;
    protected final VerifyLoginApiService verifyLoginApiService;

    public BaseTest() {

        // Client
        authApiClient = SERVICE_CONFIG.getAuthApiClient();
        brandApiClient = SERVICE_CONFIG.getBrandApiClient();
        productApiClient = SERVICE_CONFIG.getProductApiClient();
        userApiClient = SERVICE_CONFIG.getUserApiClient();
        verifyLoginApiClient = SERVICE_CONFIG.getVerifyLoginApiClient();

        // Service
        authApiService = SERVICE_CONFIG.getAuthApiService();
        brandApiService = SERVICE_CONFIG.getBrandApiService();
        productApiService = SERVICE_CONFIG.getProductApiService();
        userApiService = SERVICE_CONFIG.getUserApiService();
        verifyLoginApiService = SERVICE_CONFIG.getVerifyLoginApiService();

    }

}
