package com.automationexercise.tests.util.matcher;

import com.automationexercise.tests.models.AddressInfo;
import com.automationexercise.tests.models.PriceDTO;
import com.automationexercise.tests.models.UserDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@ParametersAreNonnullByDefault
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppMatchers {

    private static final List<String> USER_FIELD_NAMES_WITHOUT_ID = List.of(
            "email", "name", "firstName", "lastName", "userTitle",
            "phoneNumber", "birthDay", "birthMonth", "birthYear", "company",
            "country", "state", "city", "address1", "address2", "zipCode");

    private static final List<String> USER_FIELD_NAMES = List.of(
            "id", "email", "name", "firstName", "lastName", "userTitle",
            "phoneNumber", "birthDay", "birthMonth", "birthYear", "company",
            "country", "state", "city", "address1", "address2", "zipCode");

    public static boolean matchUserRequestDataWithoutId(UserDTO expectedAddress, UserDTO actualAddress) {
        return ObjectsMatcher.compare(UserDTO.class, expectedAddress, actualAddress, USER_FIELD_NAMES_WITHOUT_ID);
    }

    public static boolean matchUserRequestData(UserDTO expectedAddress, UserDTO actualAddress) {
        return ObjectsMatcher.compare(UserDTO.class, expectedAddress, actualAddress, USER_FIELD_NAMES);
    }

    public static boolean matchAddressInfo(AddressInfo expectedAddress, AddressInfo actualAddress) {
        return ObjectsMatcher.compare(AddressInfo.class, expectedAddress, actualAddress);
    }

    public static boolean invoiceContainsPrice(Path path, PriceDTO price) {
        return InvoiceDataMatcher.invoiceContainsPrice(path, price);
    }

    public static boolean invoiceContainsName(Path path, String name) {
        return InvoiceDataMatcher.invoiceContainsName(path, name);
    }

    public static boolean invoiceContainsNameAndPrice(Path path,
                                                      String name,
                                                      PriceDTO price
    ) {
        return InvoiceDataMatcher.invoiceContainsNameAndPrice(path, name, price);
    }

}
