package com.automationexercise.tests.test.data;

import com.automationexercise.tests.models.meta.Range;
import com.automationexercise.tests.util.EmailHelper;
import net.datafaker.Faker;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.automationexercise.tests.util.DataGenerator.generatePassword;
import static com.automationexercise.tests.util.DataGenerator.generateUser;

public class UserDataProvider {

    private static final Range passwordRange = new Range(8, 20),
            nameRange = new Range(2, 20),
            firstNameRange = new Range(2, 20),
            lastNameRange = new Range(2, 20),
            phoneNumberRange = new Range(8, 12),
            userTitleRange = new Range(2, 5),
            companyRange = new Range(3, 100),
            countryRange = new Range(3, 100),
            stateRange = new Range(3, 100),
            cityRange = new Range(3, 100),
            address1Range = new Range(3, 100),
            address2Range = new Range(3, 100),
            zipCodeRange = new Range(3, 100);

    private static final Faker FAKE = new Faker();


    public static Stream<Arguments> absentDataProvider() {
        return Stream.of(
                Arguments.of("email", generateUser().email(null)),
                Arguments.of("password", generateUser().password(null)),
                Arguments.of("name", generateUser().name(null)),
                Arguments.of("firstname", generateUser().firstName(null)),
                Arguments.of("lastname", generateUser().lastName(null)),
                Arguments.of("mobile_number", generateUser().phoneNumber(null)),
                Arguments.of("country", generateUser().country(null)),
                Arguments.of("state", generateUser().state(null)),
                Arguments.of("city", generateUser().city(null)),
                Arguments.of("address1", generateUser().address1(null)),
                Arguments.of("zipcode", generateUser().zipCode(null))
        );
    }

    public static Stream<Arguments> validEmailProvider() {
        return Stream.of(
                Arguments.of("email has popular email service", EmailHelper.ValidEmail.popularEmailService()),
                Arguments.of("email has not exists email service", EmailHelper.ValidEmail.notExistEmailService()),
                Arguments.of("has subdomain name", EmailHelper.ValidEmail.hasSubdomainName()),
                Arguments.of("email username contains plus", EmailHelper.ValidEmail.usernameContainsPlus()),
                Arguments.of("email username with quotes", EmailHelper.ValidEmail.usernameWithQuotes()),
                Arguments.of("email has numerical username", EmailHelper.ValidEmail.numericalUsername()),
                Arguments.of("email has domain with dash", EmailHelper.ValidEmail.domainContainDash()),
                Arguments.of("email has username only with underscore", EmailHelper.ValidEmail.underscoreUsername()),
                Arguments.of("email domain length equals 2", EmailHelper.ValidEmail.domainLengthEqualTwo()),
                Arguments.of("email has mail service name length equals 2", EmailHelper.ValidEmail.domainNameLengthEqualTwo()),
                Arguments.of("email has username with dash", EmailHelper.ValidEmail.usernameContainsDash())
        );
    }

    public static Stream<Arguments> invalidEmailProvider() {
        return Stream.of(
                Arguments.of("email is empty", ""),
                Arguments.of("email is blank", " ".repeat(11)),
                Arguments.of("email has only letters and digits", EmailHelper.InvalidEmail.withoutSymbols()),
                Arguments.of("email has only special symbols", EmailHelper.InvalidEmail.onlySpecialSymbols()),
                Arguments.of("email without username", EmailHelper.InvalidEmail.withoutUsername()),
                Arguments.of("email has text before email address", EmailHelper.InvalidEmail.textBeforeEmailAddress()),
                Arguments.of("email not contains @ symbol", EmailHelper.InvalidEmail.withoutDogSymbol()),
                Arguments.of("email contains several @ symbols", EmailHelper.InvalidEmail.severalDogSymbols()),
                Arguments.of("email starts with dot", EmailHelper.InvalidEmail.firstSymbolIsDot()),
                Arguments.of("email has dot before @ symbol", EmailHelper.InvalidEmail.dotBeforeDogSymbol()),
                Arguments.of("email contains 2 dots in a row", EmailHelper.InvalidEmail.twoDotSymbolsTogether()),
                Arguments.of("email username contains only hieroglyphs", EmailHelper.InvalidEmail.hieroglyphsInstedOfUsername()),
                Arguments.of("email has text after email address", EmailHelper.InvalidEmail.textAfterEmailAddress()),
                Arguments.of("email not contains domain", EmailHelper.InvalidEmail.withoutDomain()),
                Arguments.of("email domain has 1 symbol", EmailHelper.InvalidEmail.domainHasOneLetter()),
                Arguments.of("email has invalid domain - web", EmailHelper.InvalidEmail.invalidTopLevelDomainWeb()),
                Arguments.of("email has mail server as ip and port", EmailHelper.InvalidEmail.mailServerWithDomainAsIpAndPort()),
                Arguments.of("email has 2 dots together before domain", EmailHelper.InvalidEmail.twoDotTogetherBeforeDomain())
        );
    }

    public static Stream<Arguments> validPasswordProvider() {
        return Stream.of(
                Arguments.of("password length = min", generatePassword(passwordRange.min())),
                Arguments.of("password length = min+1", generatePassword(passwordRange.min() + 1)),
                Arguments.of("password length = max-1", generatePassword(passwordRange.max() - 1)),
                Arguments.of("password length = max", generatePassword(passwordRange.max())),
                Arguments.of("password not contains special symbols", generatePassword(true, true)),
                Arguments.of("password not contains uppercase letters", generatePassword(true))
        );
    }

    public static Stream<Arguments> invalidPasswordProvider() {
        return Stream.of(
                Arguments.of("password is empty", ""),
                Arguments.of("password is blank", "           "),
                Arguments.of("password length = min-1", generatePassword(passwordRange.min() - 1)),
                Arguments.of("password length = max+1", generatePassword(passwordRange.max() + 1)),
                Arguments.of("password length = max+10", generatePassword(passwordRange.max() + 10)),
                Arguments.of("password not contains digits", generatePassword(false)),
                Arguments.of("password not contains letters", FAKE.number().digits(passwordRange.min()))
        );
    }

    public static Stream<Arguments> validAnotherSensitiveDataProvider() {
        return Stream.of(
                // Name
                Arguments.of("name length = min", generateUser().name(FAKE.lorem().characters(nameRange.min()))),
                Arguments.of("name length = min+1", generateUser().name(FAKE.lorem().characters(nameRange.min() + 1))),
                Arguments.of("name length = max-1", generateUser().name(FAKE.lorem().characters(nameRange.max() - 1))),
                Arguments.of("name length = max", generateUser().name(FAKE.lorem().characters(nameRange.max()))),

                // First name
                Arguments.of("first name length = min", generateUser().firstName(FAKE.lorem().characters(firstNameRange.min()))),
                Arguments.of("first name length = min+1", generateUser().firstName(FAKE.lorem().characters(firstNameRange.min() + 1))),
                Arguments.of("first name length = max-1", generateUser().firstName(FAKE.lorem().characters(firstNameRange.max() - 1))),
                Arguments.of("first name length = max", generateUser().firstName(FAKE.lorem().characters(firstNameRange.max()))),

                // Last name
                Arguments.of("last name length = min", generateUser().lastName(FAKE.lorem().characters(lastNameRange.min()))),
                Arguments.of("last name length = min+1", generateUser().lastName(FAKE.lorem().characters(lastNameRange.min() + 1))),
                Arguments.of("last name length = max-1", generateUser().lastName(FAKE.lorem().characters(lastNameRange.max() - 1))),
                Arguments.of("last name length = max", generateUser().lastName(FAKE.lorem().characters(lastNameRange.max()))),

                // Phone number
                Arguments.of("phone length = min", generateUser().phoneNumber(FAKE.number().digits(phoneNumberRange.min()))),
                Arguments.of("phone length = min+1", generateUser().phoneNumber(FAKE.number().digits(phoneNumberRange.min() + 1))),
                Arguments.of("phone length = max-1", generateUser().phoneNumber(FAKE.number().digits(phoneNumberRange.max() - 1))),
                Arguments.of("phone length = max", generateUser().phoneNumber(FAKE.number().digits(phoneNumberRange.max()))),

                // User title
//                Arguments.of("user title length = min", DataGenerator.generateUser().userTitle(FAKE.lorem().characters(userTitleRange.min()))),
//                Arguments.of("user title length = min+1", DataGenerator.generateUser().userTitle(FAKE.lorem().characters(userTitleRange.min() + 1))),
//                Arguments.of("user title length = max-1", DataGenerator.generateUser().userTitle(FAKE.lorem().characters(userTitleRange.max() - 1))),
//                Arguments.of("user title length = max", DataGenerator.generateUser().userTitle(FAKE.lorem().characters(userTitleRange.max()))),

                // Company
                Arguments.of("company length = min", generateUser().company(FAKE.lorem().characters(companyRange.min()))),
                Arguments.of("company length = min+1", generateUser().company(FAKE.lorem().characters(companyRange.min() + 1))),
                Arguments.of("company length = max-1", generateUser().company(FAKE.lorem().characters(companyRange.max() - 1))),
                Arguments.of("company length = max", generateUser().company(FAKE.lorem().characters(companyRange.max()))),

                // Country
                Arguments.of("country length = min", generateUser().country(FAKE.lorem().characters(countryRange.min()))),
                Arguments.of("country length = min+1", generateUser().country(FAKE.lorem().characters(countryRange.min() + 1))),
                Arguments.of("country length = max-1", generateUser().country(FAKE.lorem().characters(countryRange.max() - 1))),
                Arguments.of("country length = max", generateUser().country(FAKE.lorem().characters(countryRange.max()))),

                // State
                Arguments.of("state length = min", generateUser().state(FAKE.lorem().characters(stateRange.min()))),
                Arguments.of("state length = min+1", generateUser().state(FAKE.lorem().characters(stateRange.min() + 1))),
                Arguments.of("state length = max-1", generateUser().state(FAKE.lorem().characters(stateRange.max() - 1))),
                Arguments.of("state length = max", generateUser().state(FAKE.lorem().characters(stateRange.max()))),

                // City
                Arguments.of("city length = min", generateUser().city(FAKE.lorem().characters(cityRange.min()))),
                Arguments.of("city length = min+1", generateUser().city(FAKE.lorem().characters(cityRange.min() + 1))),
                Arguments.of("city length = max-1", generateUser().city(FAKE.lorem().characters(cityRange.max() - 1))),
                Arguments.of("city length = max", generateUser().city(FAKE.lorem().characters(cityRange.max()))),

                // Address 1
                Arguments.of("address1 length = min", generateUser().address1(FAKE.lorem().characters(address1Range.min()))),
                Arguments.of("address1 length = min+1", generateUser().address1(FAKE.lorem().characters(address1Range.min() + 1))),
                Arguments.of("address1 length = max-1", generateUser().address1(FAKE.lorem().characters(address1Range.max() - 1))),
                Arguments.of("address1 length = max", generateUser().address1(FAKE.lorem().characters(address1Range.max()))),

                // Address 2
                Arguments.of("address2 length = min", generateUser().address2(FAKE.lorem().characters(address2Range.min()))),
                Arguments.of("address2 length = min+1", generateUser().address2(FAKE.lorem().characters(address2Range.min() + 1))),
                Arguments.of("address2 length = max-1", generateUser().address2(FAKE.lorem().characters(address2Range.max() - 1))),
                Arguments.of("address2 length = max", generateUser().address2(FAKE.lorem().characters(address2Range.max()))),

                // Zip code
                Arguments.of("zip code length = min", generateUser().zipCode(FAKE.lorem().characters(zipCodeRange.min()))),
                Arguments.of("zip code length = min+1", generateUser().zipCode(FAKE.lorem().characters(zipCodeRange.min() + 1))),
                Arguments.of("zip code length = max-1", generateUser().zipCode(FAKE.lorem().characters(zipCodeRange.max() - 1))),
                Arguments.of("zip code length = max", generateUser().zipCode(FAKE.lorem().characters(zipCodeRange.max())))
        );
    }

    public static Stream<Arguments> invalidAnotherSensitiveDataProvider() {
        return Stream.of(
                // Name
                Arguments.of("name is empty", generateUser().name("")),
                Arguments.of("name is blank", generateUser().name(" ".repeat(nameRange.min()))),
                Arguments.of("name length = min-1", generateUser().name(FAKE.lorem().characters(nameRange.min() - 1))),
                Arguments.of("name length = max+1", generateUser().name(FAKE.lorem().characters(nameRange.max()))),
                Arguments.of("name length = max+10", generateUser().name(FAKE.lorem().characters(nameRange.max() + 1))),

                // First name
                Arguments.of("first name is empty", generateUser().firstName("")),
                Arguments.of("first name is blank", generateUser().firstName(" ".repeat(firstNameRange.min()))),
                Arguments.of("first name length = min-1", generateUser().firstName(FAKE.lorem().characters(firstNameRange.min() - 1))),
                Arguments.of("first name length = max+1", generateUser().firstName(FAKE.lorem().characters(firstNameRange.max()))),
                Arguments.of("first name length = max+10", generateUser().firstName(FAKE.lorem().characters(firstNameRange.max() + 1))),

                // Last name
                Arguments.of("last name is empty", generateUser().lastName("")),
                Arguments.of("last name is blank", generateUser().lastName(" ".repeat(lastNameRange.min()))),
                Arguments.of("last name length = min-1", generateUser().lastName(FAKE.lorem().characters(lastNameRange.min() - 1))),
                Arguments.of("last name length = max+1", generateUser().lastName(FAKE.lorem().characters(lastNameRange.max()))),
                Arguments.of("last name length = max+10", generateUser().lastName(FAKE.lorem().characters(lastNameRange.max() + 1))),

                // Phone number
                Arguments.of("phone is empty", generateUser().lastName("")),
                Arguments.of("phone is blank", generateUser().lastName(" ".repeat(phoneNumberRange.min()))),
                Arguments.of("phone length = min-1", generateUser().lastName(FAKE.lorem().characters(phoneNumberRange.min() - 1))),
                Arguments.of("phone length = max+1", generateUser().lastName(FAKE.lorem().characters(phoneNumberRange.max()))),
                Arguments.of("phone length = max+10", generateUser().lastName(FAKE.lorem().characters(phoneNumberRange.max() + 1))),

                // User title
                Arguments.of("user title is empty", generateUser().lastName("")),
                Arguments.of("user title is blank", generateUser().lastName(" ".repeat(userTitleRange.min()))),
                Arguments.of("user title length = min-1", generateUser().lastName(FAKE.lorem().characters(userTitleRange.min() - 1))),
                Arguments.of("user title length = max+1", generateUser().lastName(FAKE.lorem().characters(userTitleRange.max()))),
                Arguments.of("user title length = max+10", generateUser().lastName(FAKE.lorem().characters(userTitleRange.max() + 1))),

                // Company
                Arguments.of("company is empty", generateUser().lastName("")),
                Arguments.of("company is blank", generateUser().lastName(" ".repeat(companyRange.min()))),
                Arguments.of("company length = min-1", generateUser().lastName(FAKE.lorem().characters(companyRange.min() - 1))),
                Arguments.of("company length = max+1", generateUser().lastName(FAKE.lorem().characters(companyRange.max()))),
                Arguments.of("company length = max+10", generateUser().lastName(FAKE.lorem().characters(companyRange.max() + 1))),

                // Country
                Arguments.of("country is empty", generateUser().lastName("")),
                Arguments.of("country is blank", generateUser().lastName(" ".repeat(countryRange.min()))),
                Arguments.of("country length = min-1", generateUser().lastName(FAKE.lorem().characters(countryRange.min() - 1))),
                Arguments.of("country length = max+1", generateUser().lastName(FAKE.lorem().characters(countryRange.max()))),
                Arguments.of("country length = max+10", generateUser().lastName(FAKE.lorem().characters(countryRange.max() + 1))),

                // State
                Arguments.of("state is empty", generateUser().lastName("")),
                Arguments.of("state is blank", generateUser().lastName(" ".repeat(stateRange.min()))),
                Arguments.of("state length = min-1", generateUser().lastName(FAKE.lorem().characters(stateRange.min() - 1))),
                Arguments.of("state length = max+1", generateUser().lastName(FAKE.lorem().characters(stateRange.max()))),
                Arguments.of("state length = max+10", generateUser().lastName(FAKE.lorem().characters(stateRange.max() + 1))),

                // City
                Arguments.of("city is empty", generateUser().lastName("")),
                Arguments.of("city is blank", generateUser().lastName(" ".repeat(cityRange.min()))),
                Arguments.of("city length = min-1", generateUser().lastName(FAKE.lorem().characters(cityRange.min() - 1))),
                Arguments.of("city length = max+1", generateUser().lastName(FAKE.lorem().characters(cityRange.max()))),
                Arguments.of("city length = max+10", generateUser().lastName(FAKE.lorem().characters(cityRange.max() + 1))),

                // Address 1
                Arguments.of("address1 is empty", generateUser().lastName("")),
                Arguments.of("address1 is blank", generateUser().lastName(" ".repeat(address1Range.min()))),
                Arguments.of("address1 length = min-1", generateUser().lastName(FAKE.lorem().characters(address1Range.min() - 1))),
                Arguments.of("address1 length = max+1", generateUser().lastName(FAKE.lorem().characters(address1Range.max()))),
                Arguments.of("address1 length = max+10", generateUser().lastName(FAKE.lorem().characters(address1Range.max() + 1))),

                // Address 2
                Arguments.of("address2 is empty", generateUser().lastName("")),
                Arguments.of("address2 is empty", generateUser().lastName(" ".repeat(address2Range.min()))),
                Arguments.of("address2 length = min-1", generateUser().lastName(FAKE.lorem().characters(address2Range.min() - 1))),
                Arguments.of("address2 length = max+1", generateUser().lastName(FAKE.lorem().characters(address2Range.max()))),
                Arguments.of("address2 length = max+10", generateUser().lastName(FAKE.lorem().characters(address2Range.max() + 1))),

                // Zip code
                Arguments.of("zip code is empty", generateUser().lastName("")),
                Arguments.of("zip code is blank", generateUser().lastName(" ".repeat(zipCodeRange.min()))),
                Arguments.of("zip code length = min-1", generateUser().lastName(FAKE.lorem().characters(zipCodeRange.min() - 1))),
                Arguments.of("zip code length = max+1", generateUser().lastName(FAKE.lorem().characters(zipCodeRange.max()))),
                Arguments.of("zip code length = max+10", generateUser().lastName(FAKE.lorem().characters(zipCodeRange.max() + 1)))
        );
    }

}
