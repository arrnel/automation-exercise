package com.automationexercise.tests.page._component.address;

import com.automationexercise.tests.models.AddressInfo;
import com.automationexercise.tests.page._component.BaseComponent;
import com.automationexercise.tests.page._component._type.AddressType;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.automationexercise.tests.util.matcher.AppMatchers.matchAddressInfo;

@Slf4j
@ParametersAreNonnullByDefault
public class AddressDetailsComponent extends BaseComponent<AddressDetailsComponent> {

    private static final String DELIVERY_ADDRESS_TITLE = "Your delivery address";
    private static final String BILLING_ADDRESS_TITLE = "Your billing address";

    private final AddressDetailsComponentLocator locator;
    private final AddressType addressType;

    public AddressDetailsComponent(Locator self,
                                   AddressType addressType
    ) {
        super(self, addressType.getComponentTitle());
        this.locator = new AddressDetailsComponentLocator(self);
        this.addressType = addressType;
    }

    @Step("Check [{this.componentTitle}] has address info")
    public void shouldHaveAddressInfo(AddressInfo addressInfo) {
        var actualAddressInfo = AddressInfo.builder()
                .title(locator.title().innerText())
                .company(locator.company().innerText())
                .address1(locator.address1().innerText())
                .address2(locator.address2().innerText())
                .cityStateZip(locator.cityStateZip().innerText())
                .country(locator.country().innerText())
                .phoneNumber(locator.phoneNumber().innerText())
                .build();

        log.info("Check [{}] has address info", this.componentTitle);
        matchAddressInfo(
                addressInfo.title(addressType == AddressType.DELIVERY
                        ? DELIVERY_ADDRESS_TITLE
                        : BILLING_ADDRESS_TITLE),
                actualAddressInfo);
    }

    @Nonnull
    @Override
    public AddressDetailsComponent shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        locator.title().waitFor(VISIBLE_CONDITION);
        locator.fullName().waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(DETACHED_CONDITION);
        locator.title().waitFor(DETACHED_CONDITION);
        locator.fullName().waitFor(DETACHED_CONDITION);
    }

}
