package com.automationexercise.tests.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;
import java.util.regex.Pattern;

@Slf4j
@ParametersAreNonnullByDefault
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailHelper {

    private static final String VALID_EMAIL_PATTERN = "^(?!.*\\.{2})(?!.*[-.]{2})(?!.*@[-.]|[-.]@)(?!.*\\.@)(?!.*@\\.)(?!.*\\.$)(?!^\\.)" +
            "(\"[a-zA-Z0-9._%+-]+\"|[a-zA-Z0-9._%+-]+)@[a-zA-Z0-9.-]+\\.(?i)(?!web$)[a-zA-Z]{2,}(?:\\[[0-9]+])?$";
    private static final Faker FAKE = new Faker();
    private static final Random RANDOM = new Random();

    public static boolean isEmailValid(String email) {
        boolean isValid = Pattern.compile(VALID_EMAIL_PATTERN)
                .matcher(email)
                .matches();
        log.info(String.format("Email [%s] - %s", email, ((isValid) ? "correct" : "not correct")));
        return isValid;
    }

    public static class InvalidEmail {

        private static final String[] domains = {"com", "org", "ru", "en", "io", "me", "info", "tech"};
        private static final String[] domainNames = {"mail", "gmail", "yahoo", "vk", "outlook", "drawbacks", "zoho"};

        /**
         * @return Invalid email
         */
        public static String random() {

            int cases = RANDOM.nextInt(17);

            return switch (cases) {
                case 0 -> withoutSymbols();
                case 1 -> onlySpecialSymbols();
                case 2 -> withoutUsername();
                case 3 -> textBeforeEmailAddress();
                case 4 -> withoutDogSymbol();
                case 5 -> severalDogSymbols();
                case 6 -> firstSymbolIsDot();
                case 7 -> dotBeforeDogSymbol();
                case 8 -> twoDotSymbolsTogether();
                case 9 -> hieroglyphsInstedOfUsername();
                case 10 -> textAfterEmailAddress();
                case 11 -> withoutDomain();
                case 12 -> domainHasOneLetter();
                case 13 -> dashAfterDogSymbol();
                case 14 -> invalidTopLevelDomainWeb();
                case 15 -> mailServerWithDomainAsIpAndPort();
                default -> twoDotTogetherBeforeDomain();
            };

        }

        /**
         * @return Example, "exampleexamplecom"
         */
        public static String withoutSymbols() {
            return FAKE.lorem().characters(5, 20, false, true);
        }

        /**
         * @return Example, "%$@#%.com"
         */
        public static String onlySpecialSymbols() {

            return String.format("%s@%s.%s"
                    , FAKE.lorem().characters(5, 20, false, true, true)
                    , FAKE.lorem().characters(4, 10, false, true, true)
                    , FAKE.lorem().characters(2, 6, false, true, true)
            );

        }

        /**
         * @return Example, "@example.com"
         */
        public static String withoutUsername() {

            return String.format("@%s.%s"
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , domains[RANDOM.nextInt(domains.length)]
            );

        }

        /**
         * @return @literal Example, "Joe Smith &lt;email.@example.com&gt;"
         */
        public static String textBeforeEmailAddress() {

            return String.format("%s %s <%s@%s.%s>"
                    , FAKE.name().firstName().toLowerCase()
                    , FAKE.name().lastName()
                    , FAKE.lorem().characters(5, 20, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , domains[RANDOM.nextInt(domains.length)]
            );

        }

        /**
         * @return Example, "email.example.com"
         */
        public static String withoutDogSymbol() {
            return String.format("%s.%s.%s"
                    , FAKE.lorem().characters(5, 20, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , domains[RANDOM.nextInt(domains.length)]
            );
        }

        /**
         * @return Example, "email@example@example.com"
         */
        public static String severalDogSymbols() {

            return String.format("%s@%s@%s"
                    , FAKE.lorem().characters(5, 20, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , domains[RANDOM.nextInt(domains.length)]
            );

        }

        /**
         * @return Example,".email@example.com"
         */
        public static String firstSymbolIsDot() {

            return String.format(".%s@%s.%s"
                    , FAKE.lorem().characters(5, 20, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , domains[RANDOM.nextInt(domains.length)]
            );

        }

        /**
         * @return Example, "email.@example.com"
         */
        public static String dotBeforeDogSymbol() {

            return String.format("%s.@%s.%s"
                    , FAKE.lorem().characters(5, 20, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , domains[RANDOM.nextInt(domains.length)]
            );

        }

        /**
         * @return Example, "email..example@example.com"
         */
        public static String twoDotSymbolsTogether() {

            return String.format("%s..@%s.%s"
                    , FAKE.lorem().characters(5, 20, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , domains[RANDOM.nextInt(domains.length)]
            );

        }

        /**
         * @return Example, "あいうえお@example.com"
         */
        public static String hieroglyphsInstedOfUsername() {

            return String.format("%s@%s.%s"
                    , "あいうえお"
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , domains[RANDOM.nextInt(domains.length)]
            );

        }

        /**
         * @return Example, email@example.com (Text)
         */
        public static String textAfterEmailAddress() {

            return String.format("%s@%s.%s %s"
                    , FAKE.lorem().characters(5, 20, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , domains[RANDOM.nextInt(domains.length)]
                    , FAKE.lorem().characters(1, 10)
            );

        }

        /**
         * @return Example, email@example
         */
        public static String withoutDomain() {

            return String.format("%s@%s"
                    , FAKE.lorem().characters(5, 20, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
            );

        }

        /**
         * @return Example, "email@example.r"
         */
        public static String domainHasOneLetter() {

            return String.format("%s@%s.%s"
                    , FAKE.lorem().characters(5, 20, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , FAKE.lorem().characters(1, false, false, false)
            );
        }

        /**
         * @return Example, "email@-example.com"
         */
        public static String dashAfterDogSymbol() {

            return String.format("%s@-%s.%s"
                    , FAKE.lorem().characters(5, 20, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , domains[RANDOM.nextInt(domains.length)]
            );

        }

        /**
         * @return Example, "email@example.web"
         */
        public static String invalidTopLevelDomainWeb() {

            return String.format("%s@%s.web"
                    , FAKE.lorem().characters(5, 20, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
            );

        }

        /**
         * @return Example, "email@123.123.123.123:123"
         */
        public static String mailServerWithDomainAsIpAndPort() {

            return String.format("%s.%s@%s:%s"
                    , FAKE.lorem().characters(5, 10, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , FAKE.internet().ipV4Address()
                    , FAKE.internet().port()
            );

        }

        /**
         * @return Example, "email@example..com"
         */
        public static String twoDotTogetherBeforeDomain() {

            return String.format("%s@%s..%s"
                    , FAKE.lorem().characters(5, 10, false)
                    , domainNames[RANDOM.nextInt(domainNames.length)]
                    , domains[RANDOM.nextInt(domains.length)]
            );

        }

    }

    public static class ValidEmail {

        private static final Faker FAKE = new Faker();

        private static final String[] domains = {"com", "org", "ru", "en", "io", "me", "info", "tech"};
        private static final String[] domainNames = {"mail", "gmail", "yahoo", "vk", "outlook", "drawbacks", "zoho"};
        private static final String[] popularEmailServices = {"gmail.com", "proton.me", "outlook.com", "main.ru", "yahoo.com"};
        private static final String[] domainsWith2Letters = {"ru", "me", "io", "jp", "co", "en", "us", "by", "es"};

        private static String getRandomPopularEmailService() {
            return popularEmailServices[RANDOM.nextInt(popularEmailServices.length)];
        }

        private static String getRandomDomainName() {
            return domainNames[RANDOM.nextInt(domainNames.length)];
        }

        private static String getRandomDomain() {
            return domains[RANDOM.nextInt(domains.length)];
        }

        private static String getRandomDomainWith2Letters() {
            return domainsWith2Letters[RANDOM.nextInt(domainsWith2Letters.length)];
        }

        /**
         * @return Valid email
         */
        public String random() {

            int cases = RANDOM.nextInt(11);

            return switch (cases) {
                case 0 -> notExistEmailService();
                case 1 -> usernameWithDot();
                case 2 -> hasSubdomainName();
                case 3 -> usernameContainsPlus();
                case 4 -> usernameWithQuotes();
                case 5 -> numericalUsername();
                case 6 -> domainContainDash();
                case 7 -> underscoreUsername();
                case 8 -> domainLengthEqualTwo();
                case 9 -> domainNameLengthEqualTwo();
                case 10 -> popularEmailService();
                default -> usernameContainsDash();
            };

        }

        /**
         * @return Example, "name3214username342@gmail.com"
         */
        public static String popularEmailService() {
            return String.format("%s@%s.%s"
                    , FAKE.name().firstName().toLowerCase() + FAKE.number().digits(4) + FAKE.lorem().characters(5, 10, false) + FAKE.number().digits(3)
                    , getRandomDomainName()
                    , getRandomDomain()
            );
        }

        /**
         * @return Example, "nasty@hotfix.com"
         */
        public static String notExistEmailService() {
            return FAKE.internet().emailAddress();
        }

        /**
         * @return Example, "firstname.lastname@example.com"
         */
        public static String usernameWithDot() {
            return String.format("%s@%s.%s"
                    , FAKE.name().firstName().toLowerCase() + FAKE.number().digits(4) + "." + FAKE.lorem().characters(5, 10, false) + FAKE.number().digits(3)
                    , getRandomDomainName()
                    , getRandomDomain()
            );
        }

        /**
         * @return Example, "email@subdomain.example.com"
         */
        public static String hasSubdomainName() {
            return String.format("%s@%s.%s.%s"
                    , FAKE.name().firstName().toLowerCase() + FAKE.number().digits(4) + "." + FAKE.lorem().characters(5, 10, false) + FAKE.number().digits(3)
                    , getRandomDomainName()
                    , getRandomDomainName()
                    , getRandomDomain()
            );

        }

        /**
         * @return Example, "firstname+lastname@example.com"
         */
        public static String usernameContainsPlus() {
            return String.format("%s@%s.%s"
                    , FAKE.name().firstName().toLowerCase() + FAKE.number().digits(4) + "+" + FAKE.lorem().characters(5, 10, false) + FAKE.number().digits(3)
                    , getRandomDomainName()
                    , getRandomDomain()
            );
        }

        /**
         * @return Example, "\"email\"@example.com"
         */
        public static String usernameWithQuotes() {
            return String.format("\"%s\"@%s.%s"
                    , FAKE.name().firstName().toLowerCase() + FAKE.number().digits(4) + FAKE.lorem().characters(5, 10, false) + FAKE.number().digits(3)
                    , getRandomDomainName()
                    , getRandomDomain()
            );
        }

        /**
         * @return Example, "452136523456@example.com"
         */
        public static String numericalUsername() {
            return String.format("%s@%s.%s"
                    , FAKE.number().digits(RANDOM.nextInt(15) + 5)
                    , getRandomDomainName()
                    , getRandomDomain()
            );
        }

        /**
         * @return Example,"my.main@do-main.com"
         */
        public static String domainContainDash() {
            return String.format("%s@%s.%s"
                    , FAKE.name().firstName().toLowerCase() + FAKE.number().digits(4) + FAKE.lorem().characters(5, 10, false) + FAKE.number().digits(3)
                    , getRandomDomainName()
                    , getRandomDomain()
            );
        }

        /**
         * @return Example, "_______@example.com"
         */
        public static String underscoreUsername() {
            return String.format("%s@%s.%s"
                    , "_".repeat(RANDOM.nextInt(15) + 5)
                    , getRandomDomainName()
                    , getRandomDomain()
            );
        }

        /**
         * @return Example, "example@domain.ru"
         */
        public static String domainLengthEqualTwo() {
            return String.format("%s@%s.%s"
                    , FAKE.name().firstName().toLowerCase() + FAKE.number().digits(4) + FAKE.lorem().characters(5, 10, false) + FAKE.number().digits(3)
                    , getRandomDomainName()
                    , getRandomDomainWith2Letters()
            );
        }

        /**
         * @return Example, "example@co.jp"
         */
        public static String domainNameLengthEqualTwo() {
            return String.format("%s@%s.%s"
                    , FAKE.name().firstName().toLowerCase() + FAKE.number().digits(4) + FAKE.lorem().characters(5, 10, false) + FAKE.number().digits(3)
                    , getRandomDomainWith2Letters()
                    , getRandomDomainWith2Letters()
            );
        }

        /**
         * @return Example, "my-email@domain.com"
         */
        public static String usernameContainsDash() {
            return String.format("%s@%s.%s"
                    , FAKE.name().firstName().toLowerCase() + FAKE.number().digits(4) + "-" + FAKE.lorem().characters(5, 10, false) + FAKE.number().digits(3)
                    , getRandomDomainWith2Letters()
                    , getRandomDomain()
            );
        }

    }

}
