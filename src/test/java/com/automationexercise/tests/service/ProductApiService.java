package com.automationexercise.tests.service;

import com.automationexercise.tests.models.ProductDTO;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

@ParametersAreNonnullByDefault
public interface ProductApiService {

    @Nonnull
    Optional<ProductDTO> getProductById(int productId);

    Optional<ProductDTO> getProductByTitle(String productTitle);

    @Nonnull
    List<ProductDTO> filterProductsByQuery(String query);

    @Nonnull
    List<ProductDTO> getAllProducts();

}
