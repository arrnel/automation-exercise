package com.automationexercise.tests.test.data;

import com.automationexercise.tests.util.EmailHelper.InvalidEmail;
import com.automationexercise.tests.util.EmailHelper.ValidEmail;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.automationexercise.tests.util.DataGenerator.randomReview;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDataProvider extends BaseDataProvider {

    public static Stream<Arguments> validReviewDataProvider() {
        return Stream.of(
                Arguments.of("name length = [min]", randomReview().name(FAKE.lorem().characters(nameRange.min()))),
                Arguments.of("name length = [max]", randomReview().name(FAKE.lorem().characters(nameRange.max()))),
                Arguments.of("email is valid", randomReview().email(ValidEmail.random())),
                Arguments.of("message length = [min]", randomReview().message(FAKE.lorem().characters(reviewMessageRange.min()))),
                Arguments.of("message length = [max]", randomReview().message(FAKE.lorem().characters(reviewMessageRange.max())))
        );
    }

    public static Stream<Arguments> invalidReviewDataProvider() {
        return Stream.of(
                Arguments.of("name is empty", randomReview().name("")),
                Arguments.of("name is blank", randomReview().name(" ")),
                Arguments.of("name length = [min-1]", randomReview().name(FAKE.lorem().characters(nameRange.min() - 1))),
                Arguments.of("name length = [max+1]", randomReview().name(FAKE.lorem().characters(nameRange.max() + 1))),
                Arguments.of("name length = [max+10]", randomReview().name(FAKE.lorem().characters(nameRange.max() + 10))),
                Arguments.of("email is empty", randomReview().email("")),
                Arguments.of("email is blank", randomReview().email(" ")),
                Arguments.of("email is invalid", randomReview().email(InvalidEmail.random())),
                Arguments.of("message is empty", randomReview().message("")),
                Arguments.of("message is blank", randomReview().message(" ")),
                Arguments.of("message length = [min-1]", randomReview().message(FAKE.lorem().characters(reviewMessageRange.min() - 1))),
                Arguments.of("message length = [max+1]", randomReview().message(FAKE.lorem().characters(reviewMessageRange.max() + 1))),
                Arguments.of("message length = [max+10]", randomReview().message(FAKE.lorem().characters(reviewMessageRange.max() + 10)))
        );
    }

}
