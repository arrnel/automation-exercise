package com.automationexercise.tests.service;

import com.automationexercise.tests.models.BrandDTO;

import javax.annotation.Nonnull;
import java.util.List;

public interface BrandApiService {

    @Nonnull
    List<BrandDTO> getAllBrands();

}
