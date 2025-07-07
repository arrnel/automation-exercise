package com.automationexercise.tests.mapper;

import com.automationexercise.tests.jupiter.anno.User;
import com.automationexercise.tests.models.AddressInfo;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.models.UserTitle;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class UserMapper {

    private UserMapper() {
    }

    public static UserDTO updateFromAnno(UserDTO user, User anno) {
        return UserDTO.builder()
                .id(user.id())
                .email(isNotEmpty(anno.email())
                        ? anno.email()
                        : user.email())
                .password(isNotEmpty(anno.password())
                        ? anno.password()
                        : user.password())
                .name(isNotEmpty(anno.name())
                        ? anno.name()
                        : user.name())
                .firstName(isNotEmpty(anno.company().firstName())
                        ? anno.company().firstName()
                        : user.firstName())
                .lastName(isNotEmpty(anno.company().lastName())
                        ? anno.company().lastName()
                        : user.lastName())
                .phoneNumber(isNotEmpty(anno.company().phoneNumber())
                        ? anno.company().phoneNumber()
                        : user.phoneNumber())
                .userTitle(anno.userTitle() != UserTitle.EMPTY
                        ? anno.userTitle()
                        : user.userTitle())
                .birthDay(anno.birthday().day() != 0
                        ? anno.birthday().day()
                        : user.birthDay())
                .birthMonth(anno.birthday().month() != 0
                        ? anno.birthday().month()
                        : user.birthMonth())
                .birthYear(anno.birthday().year() != 0
                        ? anno.birthday().year()
                        : user.birthYear())
                .company(isNotEmpty(anno.company().title())
                        ? anno.company().title()
                        : user.company())
                .country(isNotEmpty(anno.company().address().country())
                        ? anno.company().address().country()
                        : user.country())
                .state(isNotEmpty(anno.company().title())
                        ? anno.company().address().state()
                        : user.state())
                .city(isNotEmpty(anno.company().address().city())
                        ? anno.company().address().city()
                        : user.city())
                .address1(isNotEmpty(anno.company().address().address1())
                        ? anno.company().address().address1()
                        : user.address1())
                .address2(isNotEmpty(anno.company().address().address2())
                        ? anno.company().address().address2()
                        : user.address2())
                .zipCode(isNotEmpty(anno.company().address().zipCode())
                        ? anno.company().address().zipCode()
                        : user.zipCode())
                .testData(user.testData())
                .build();
    }

    public static AddressInfo toAddress(UserDTO user) {
        return AddressInfo.builder()
                .fullName("%s %s".formatted(user.firstName(), user.lastName()))
                .company(user.company())
                .address1(user.address1())
                .address1(user.address2())
                .cityStateZip("%s %s %s".formatted(user.city(), user.state(), user.zipCode()))
                .country(user.country())
                .phoneNumber(user.phoneNumber())
                .build();
    }

}
