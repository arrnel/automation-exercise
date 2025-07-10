package com.automationexercise.tests.test.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDataProvider extends BaseDataProvider {

    private static final Faker FAKE = new Faker();

    /**
     * @return Stream of Arguments: caseName, query, expected products
     */
    private static Stream<Arguments> queryProvider() {
        return Stream.of(
                Arguments.of("query is empty", ""),
                Arguments.of("query has partial product title", "placket"),
                Arguments.of("query has full product title", "Sleeveless Dress"),
                Arguments.of("query has partial category title", "thir"),
                Arguments.of("query has full category title", "TSHIRTS"),
                Arguments.of("query has full brand title", "Allen Solly"),
                Arguments.of("query has not exist title of product, brand, category", FAKE.lorem().sentence())
        );
    }

}
