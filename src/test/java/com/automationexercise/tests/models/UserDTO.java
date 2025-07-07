package com.automationexercise.tests.models;

import com.automationexercise.tests.api.core.config.UserTitleDeserializer;
import com.automationexercise.tests.api.core.config.UserTitleSerializer;
import com.automationexercise.tests.models.meta.TestData;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Objects;

import static com.automationexercise.tests.util.ObjectMapperUtil.getBeautifulJSON;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDTO(

        @JsonProperty("id")
        Long id,

        @JsonProperty("email")
        String email,

        @JsonProperty("password")
        String password,

        @JsonProperty("name")
        String name,

        @JsonAlias("first_name")
        @JsonProperty("firstname")
        String firstName,

        @JsonAlias("last_name")
        @JsonProperty("lastname")
        String lastName,

        @JsonProperty("mobile_number")
        String phoneNumber,

        @JsonProperty("title")
        @JsonDeserialize(using = UserTitleDeserializer.class)
        @JsonSerialize(using = UserTitleSerializer.class)
        UserTitle userTitle,

        @JsonProperty("birth_day")
        Integer birthDay,

        @JsonProperty("birth_month")
        Integer birthMonth,

        @JsonProperty("birth_year")
        Integer birthYear,

        @JsonProperty("company")
        String company,

        @JsonProperty("country")
        String country,

        @JsonProperty("state")
        String state,

        @JsonProperty("city")
        String city,

        @JsonProperty("address1")
        String address1,

        @JsonProperty("address2")
        String address2,

        @JsonProperty("zipcode")
        String zipCode,

        @JsonIgnore
        TestData testData


) implements Serializable {

    @Builder
    @JsonCreator
    public UserDTO {
        if (testData == null)
            testData = TestData.empty();
    }

    public UserDTO id(Long id) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO email(String email) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO password(String password) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO withPassword(String password) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData.password(password));
    }

    public UserDTO name(String name) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO firstName(String firstName) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO lastName(String lastName) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO phoneNumber(String phoneNumber) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO userTitle(UserTitle userTitle) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO birthDay(Integer birthDay) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO birthMonth(Integer birthMonth) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO birthYear(Integer birthYear) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO company(String company) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO country(String country) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO state(String state) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO city(String city) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO address1(String address1) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO address2(String address2) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public UserDTO zipCode(String zipCode) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }


    public UserDTO testData(TestData testData) {
        return new UserDTO(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode, testData);
    }

    public static UserDTO empty() {
        return new UserDTO(null, null, null, null, null, null, null, null, 0, 0, 0, null, null, null, null, null, null, null, TestData.empty());
    }

    @Nonnull
    @Override
    public String toString() {
        return getBeautifulJSON(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) && Objects.equals(name, userDTO.name) && Objects.equals(city, userDTO.city) && Objects.equals(email, userDTO.email) && Objects.equals(state, userDTO.state) && Objects.equals(company, userDTO.company) && Objects.equals(country, userDTO.country) && Objects.equals(zipCode, userDTO.zipCode) && Objects.equals(password, userDTO.password) && Objects.equals(lastName, userDTO.lastName) && Objects.equals(address1, userDTO.address1) && Objects.equals(address2, userDTO.address2) && Objects.equals(firstName, userDTO.firstName) && Objects.equals(birthDay, userDTO.birthDay) && Objects.equals(birthYear, userDTO.birthYear) && Objects.equals(phoneNumber, userDTO.phoneNumber) && Objects.equals(birthMonth, userDTO.birthMonth) && userTitle == userDTO.userTitle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, firstName, lastName, phoneNumber, userTitle, birthDay, birthMonth, birthYear, company, country, state, city, address1, address2, zipCode);
    }

}
