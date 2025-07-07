package com.automationexercise.tests.util;

import com.automationexercise.tests.config.service.ServiceConfig;
import com.automationexercise.tests.config.test.Config;
import com.automationexercise.tests.ex.ProductNotFoundException;
import com.automationexercise.tests.models.*;
import com.automationexercise.tests.models.Currency;
import com.automationexercise.tests.models.meta.TestData;
import com.automationexercise.tests.service.ProductApiService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.automationexercise.tests.models.UserType.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataGenerator {

    private static final Faker FAKE = new Faker();
    private static final Config CFG = Config.getInstance();
    private static final ServiceConfig SERVICE_CONFIG = ServiceConfig.getInstance();

    private static final String[] COUNTRIES_LIST = {"India", "United States", "Canada", "Australia", "Israel", "New Zealand", "Singapore"};

    private static final String[] RECOMMENDED_PRODUCTS = {"Stylish Dress", "Winter Top", "Summer White Top", "Blue Top", "Men Tshirt"};

    private static final Map<UserType, List<String>> USER_TYPES_CATEGORIES = Map.of(
            WOMEN, List.of("Dress", "Tops", "Saree"),
            MEN, List.of("Tshirts", "Jeans"),
            KIDS, List.of("Dress", "Tops & Shirts")
    );
    private static final List<String> BRANDS = List.of(
            "Polo", "H&M", "Madame", "Mast & Harbour", "Babyhug", "Allen Solly Junior", "Kookie Kids", "Biba"
    );

    private static final ProductApiService productService = SERVICE_CONFIG.getProductApiService();

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

    public static String generateName() {
        return FAKE.name().fullName();
    }

    public static String randomCountry() {
        return COUNTRIES_LIST[FAKE.random().nextInt(COUNTRIES_LIST.length)];
    }

    public static UserDTO generateUser() {
        var password = CFG.defaultPassword();
        var phoneNumber = FAKE.phoneNumber().phoneNumber();
        var firstName = FAKE.name().firstName();
        var birthDate = randomBirthDate();

        return UserDTO.builder()
                .email(generateEmail())
                .password(password)
                .name(generateName())
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
                .country(randomCountry())
                .state(FAKE.address().state())
                .city(FAKE.address().city())
                .address1(FAKE.address().streetAddress())
                .address2(FAKE.address().secondaryAddress())
                .zipCode(FAKE.address().zipCode())
                .testData(new TestData(null, null, password, phoneNumber))
                .build();

    }

    public static UserType randomUserType() {
        return UserType.random();
    }

    public static String randomUserTypeCategory(UserType userType) {
        var userTypeCategories = USER_TYPES_CATEGORIES.get(userType);
        return userTypeCategories.get(new Random().nextInt(userTypeCategories.size() - 1));
    }

    public static String randomBrand() {
        return BRANDS.get(new Random().nextInt(BRANDS.size()));
    }

    public static ProductDTO randomProduct() {
        var products = productService.getAllProducts();
        return products.get(new Random().nextInt(products.size() - 1));
    }

    public static List<ProductDTO> randomProducts(int count) {
        var products = new ArrayList<>(productService.getAllProducts());
        if (products.size() < count)
            throw new IllegalArgumentException("Invalid products count: %d. Found products count: %d"
                    .formatted(count, products.size()));
        Collections.shuffle(products);
        return products.subList(0, count);
    }

    public static ProductDTO recommendedProduct() {
        var recommendedProductTitle = RECOMMENDED_PRODUCTS[new Random().nextInt(RECOMMENDED_PRODUCTS.length - 1)];
        return productService.getProductByTitle(recommendedProductTitle)
                .orElseThrow(() -> new ProductNotFoundException("Recommended product not found by title: %s".formatted(recommendedProductTitle)));
    }

    public static ProductDTO expectedProduct() {
        return ProductDTO.builder()
                .id(3L)
                .title("Sleeveless Dress")
                .category(CategoryDTO.of(WOMEN, "Dress"))
                .price(new PriceDTO(Currency.RS, new BigDecimal("1000.0")))
                .brand("Madame")
                .build();
    }

    private static final List<String> expectedProductsTitles = List.of(
            "Sleeveless Dress",
            "Beautiful Peacock Blue Cotton Linen Saree",
            "Pure Cotton V-Neck T-Shirt",
            "Cotton Mull Embroidered Dress",
            "Half Sleeves Top Schiffli Detailing - Pink",
            "Summer White Top",
            "Men Tshirt",
            "Frozen Tops For Kids",
            "Green Side Placket Detail T-Shirt",
            "GRAPHIC DESIGN MEN T SHIRT - BLUE"
    );

    private static final List<ProductDTO> expectedProducts;

    static {
        expectedProducts = productService.getAllProducts().stream()
                .filter(product -> expectedProductsTitles.contains(product.title()))
                .toList();
    }

    public static List<String> expectedProductsTitles() {
        return new ArrayList<>(expectedProductsTitles);
    }

    public static List<ProductDTO> expectedProducts() {
        return new ArrayList<>(expectedProducts);
    }

    public static ReviewInfo randomReview() {
        return ReviewInfo.builder()
                .email(FAKE.internet().emailAddress())
                .name(FAKE.name().fullName())
                .message(FAKE.lorem().paragraph(FAKE.random().nextInt(1, 10)))
                .build();
    }

    public static String generateComment() {
        return FAKE.lorem().paragraph(FAKE.random().nextInt(1, 5));
    }

    public static CardInfo generateCreditCard() {
        var expiryDate = FAKE.business().creditCardExpiry().split("-");
        return CardInfo.builder()
                .number(FAKE.business().creditCardNumber())
                .name(FAKE.name().fullName())
                .expiryMonth(expiryDate[1])
                .expiryYear(expiryDate[0])
                .cvc(FAKE.number().digits(3))
                .build();
    }

    public static Path randomFilePath() {
        String[] fileNames = {"bug.pdf", "file.txt", "img.jpg", "img.png", "img.gif"};
        var randomFileName = fileNames[FAKE.random().nextInt(fileNames.length - 1)];
        return Paths.get(CFG.pathToFiles(), randomFileName);
    }

    public static ContactInfo randomContactInfo() {

        return ContactInfo.builder()
                .email(generateEmail())
                .name(generateName())
                .subject(FAKE.lorem().sentence())
                .message(FAKE.lorem().paragraph(FAKE.random().nextInt(4, 10)))
                .pathToFile(randomFilePath())
                .build();
    }

}
