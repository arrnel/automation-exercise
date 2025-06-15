package com.automationexercise.tests.service.impl;

import com.automationexercise.tests.api.BrandApiClient;
import com.automationexercise.tests.config.service.ServiceConfig;
import com.automationexercise.tests.models.BrandDTO;
import com.automationexercise.tests.models.api.HttpStatus;
import com.automationexercise.tests.service.BrandApiService;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.util.List;

import static com.automationexercise.tests.api.core.condition.Conditions.bodyStatusCode;

@Slf4j
public class BrandApiServiceImpl implements BrandApiService {

    private static final ServiceConfig SERVICE_CONFIG = ServiceConfig.getInstance();

    private final BrandApiClient brandClient = SERVICE_CONFIG.getBrandApiClient();

    @Override
    @Step("Get all brands")
    public @Nonnull List<BrandDTO> getAllBrands() {
        log.info("Get all brands");
        return brandClient.sendGetAllBrandsRequest()
                .shouldHave(bodyStatusCode(HttpStatus.OK))
                .extract()
                .asList("brands", BrandDTO.class);
    }

}
