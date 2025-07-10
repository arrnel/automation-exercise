package com.automationexercise.tests.page._component.filter;

import com.automationexercise.tests.models.UserType;
import com.automationexercise.tests.page._component.BaseComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.microsoft.playwright.options.WaitForSelectorState.DETACHED;
import static org.apache.commons.lang3.StringUtils.capitalize;

@Slf4j
@ParametersAreNonnullByDefault
public class CategoryFilter extends BaseComponent<CategoryFilter> {

    private final CategoryFilterLocator locator;

    public CategoryFilter(Locator self) {
        super(self);
        locator = new CategoryFilterLocator(self);
    }

    @Step("Filter products by {userType} category: {category}")
    public void filter(UserType userType, String category) {
        log.info("Filtering products by {} category {}", userType, category);
        if (userType == UserType.EMPTY)
            throw new IllegalArgumentException("Unable to filter products by UserType.EMPTY");
        locator.userType(userType.getValue()).click();
        locator.category(userType.getValue(), capitalize(category)).click();
    }

    @Step("Check category filter has user type: {userType}")
    public void checkContainsUserType(UserType userType) {
        if (userType == UserType.EMPTY)
            throw new IllegalArgumentException("Unable to filter products by UserType.EMPTY");
        assertThat(locator.userType(userType.getValue())).isVisible();
    }

    @Step("Check categories list contains expected user types")
    public void checkContainsUserTypes(List<UserType> userTypes) {
        log.info("Check categories list contains user types: {}", userTypes);
        if (userTypes.isEmpty())
            throw new IllegalArgumentException("Expected user types cannot be empty");

        var notFoundUserTypes = userTypes.stream()
                .filter(userType -> !locator.userType(userType.getValue()).isVisible())
                .toList();

        if (!notFoundUserTypes.isEmpty())
            throw new AssertionError("Not found user types: " + notFoundUserTypes);
    }

    @Step("Check user type contains expected category")
    public void checkUserTypeContainsCategory(UserType userType, String category) {
        if (userType == UserType.EMPTY)
            throw new IllegalArgumentException("Unable to filter products for user type = UserType.EMPTY");
        assertThat(locator.category(userType.getValue(), category)).isVisible();
    }

    @Step("Check categories list contains expected categories")
    public void checkUserTypeContainsCategory(UserType userType, List<String> categories) {
        log.info("Check userType list contains category: {}", categories);

        if (userType == UserType.EMPTY)
            throw new IllegalArgumentException("Unavailable userType: UserType.EMPTY");
        if (categories.isEmpty())
            throw new IllegalArgumentException("Expected category cannot be empty");

        var notFoundCategories = categories.stream()
                .filter(category -> !locator.category(userType.getValue(), category).isVisible())
                .toList();

        if (!notFoundCategories.isEmpty())
            throw new AssertionError("Not found category: " + notFoundCategories);
    }


    @Override
    public CategoryFilter shouldVisibleComponent() {
        self.waitFor(VISIBLE_CONDITION);
        return this;
    }

    @Override
    public void shouldNotVisibleComponent() {
        self.waitFor(new WaitForOptions().setState(DETACHED));
    }

}
