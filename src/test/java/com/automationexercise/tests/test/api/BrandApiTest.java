package com.automationexercise.tests.test.api;

import com.automationexercise.tests.jupiter.anno.meta.ApiTest;
import com.automationexercise.tests.models.BrandDTO;
import com.automationexercise.tests.models.api.HttpStatus;
import com.automationexercise.tests.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.automationexercise.tests.api.core.condition.Conditions.bodyStatusCode;
import static com.automationexercise.tests.api.core.condition.Conditions.statusCode;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ApiTest
@DisplayName("[API] Brand test")
class BrandApiTest extends BaseTest {

    private static final BrandDTO EXPECTED_BRAND = BrandDTO.builder()
            .id(13L)
            .title("Allen Solly Junior")
            .build();

    @Test
    @DisplayName("Should get all brands")
    void shouldGetAllBrandsTest() {
        // Steps
        var brands = brandApiClient.sendGetAllBrandsRequest()

                // Assertions
                //.shouldHave(contentType(ContentType.JSON)) <- current realization returns HTML
                .shouldHave(statusCode(HttpStatus.OK))
                .shouldHave(bodyStatusCode(HttpStatus.OK))
                .extract()
                .asList("brands", BrandDTO.class);

        assertAll(
                () -> assertFalse(brands.isEmpty(), "Check brands list not empty"),
                () -> assertTrue(brands.contains(EXPECTED_BRAND), "Check brands contains expected brand")
        );

    }

}
