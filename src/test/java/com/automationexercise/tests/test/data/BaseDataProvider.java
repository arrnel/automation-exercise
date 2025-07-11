package com.automationexercise.tests.test.data;

import com.automationexercise.tests.models.meta.Range;
import net.datafaker.Faker;

public abstract class BaseDataProvider {

    protected static final Faker FAKE = new Faker();

    // @INFO: USER
    protected static final Range passwordRange = new Range(8, 20),
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

    // @INFO: PRODUCT_REVIEW
    protected static final Range reviewMessageRange = new Range(3, 1000);

}
