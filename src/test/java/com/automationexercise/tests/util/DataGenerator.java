package com.automationexercise.tests.util;

import com.automationexercise.tests.config.test.Config;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.models.UserTitle;
import com.automationexercise.tests.models.meta.TestData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataGenerator {

    private static final Faker FAKE = new Faker();
    private static final Config CFG = Config.getInstance();

    public static LocalDate randomBirthDate() {

        var now = LocalDate.now();
        var min = now.minusYears(18);
        var max = now.minusYears(60);

        var duration = ChronoUnit.DAYS.between(max, min);
        return min.minusDays(new Random().nextLong(duration));

    }

    public static String generatePassword() {
        return FAKE.internet().password();
    }

    public static String generatePassword(int length) {
        return generatePassword(length, length);
    }

    public static String generatePassword(int minLength, int maxLength) {
        return FAKE.internet().password(minLength, maxLength, true, true, true);
    }

    public static String generatePassword(boolean includeDigits) {
        return generatePassword(includeDigits, false);
    }

    public static String generatePassword(boolean includeDigits, boolean includeUppercase) {
        return generatePassword(includeDigits, includeUppercase, false);
    }

    public static String generatePassword(boolean includeDigits, boolean includeUppercase, boolean includeSpecial) {
        return generatePassword(8, 20, includeDigits, includeUppercase, includeSpecial);
    }

    public static String generatePassword(int length, boolean includeDigits, boolean includeUppercase, boolean includeSpecial) {
        return generatePassword(length, length, includeDigits, includeUppercase, includeSpecial);
    }

    public static String generatePassword(int minLength, int maxLength, boolean includeDigits, boolean includeUppercase, boolean includeSpecial) {
        return FAKE.internet().password(minLength, maxLength, includeUppercase, includeSpecial, includeDigits);
    }

    public static String generateEmail() {
        return FAKE.internet().emailAddress();
    }

    public static UserDTO generateUser() {
        var password = CFG.defaultPassword();
        var phoneNumber = FAKE.phoneNumber().phoneNumber();
        var firstName = FAKE.name().firstName();
        var birthDate = randomBirthDate();

        return UserDTO.builder()
                .email(FAKE.internet().emailAddress())
                .password(CFG.defaultPassword())
                .name(firstName)
                .firstName(firstName)
                .lastName(FAKE.name().lastName())
                .phoneNumber(phoneNumber)
                .userTitle(
                        new Random().nextBoolean()
                                ? UserTitle.MR
                                : UserTitle.MRS
                )
                .birthDay(birthDate.getDayOfMonth())
                .birthDay(birthDate.getDayOfMonth())
                .birthMonth(birthDate.getMonthValue())
                .birthYear(birthDate.getYear())
                .company(FAKE.company().name())
                .country(FAKE.address().country())
                .state(FAKE.address().state())
                .city(FAKE.address().city())
                .address1(FAKE.address().streetAddress())
                .address2(FAKE.address().secondaryAddress())
                .zipCode(FAKE.address().zipCode())
                .testData(new TestData(null, password, phoneNumber))
                .build();

    }

}
