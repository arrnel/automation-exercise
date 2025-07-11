package com.automationexercise.tests.jupiter.extension;

import com.automationexercise.tests.jupiter.anno.ApiLogin;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.util.DataGenerator;
import com.automationexercise.tests.util.browser.PageStore;
import com.microsoft.playwright.options.Cookie;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class ApiLoginExtension extends BaseExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), ApiLogin.class)
                .ifPresent(anno -> {

                            var hasApiLoginEmail = !(anno.email().isEmpty());
                            var hasUserEmail = !(anno.value().email().isEmpty());

                            String email;
                            String password;
                            UserDTO user;

                            if (hasUserEmail) {
                                email = anno.value().email();
                                password = anno.value().password();
                            } else if (hasApiLoginEmail) {
                                email = anno.email();
                                password = anno.password();
                            } else {
                                email = DataGenerator.generateEmail();
                                password = DataGenerator.generatePassword();
                            }

                            var userFromUserExtension = UserExtension.findUser(email);
                            var userFromUsersExtension = UsersExtension.findUser(email);
                            var foundUser = userFromUserExtension.isPresent()
                                    ? userFromUserExtension
                                    : userFromUsersExtension;

                            if (password.isEmpty()) {
                                if (foundUser.isEmpty())
                                    throw new IllegalStateException("Can not do api login. Password is empty and user with" +
                                            " email [%s] not found in store".formatted(email));
                                password = foundUser.get().testData().password();
                            } else {
                                var userPassword = password;
                                foundUser = userService.getUserByEmail(email)
                                        .map(u -> u.withPassword(userPassword));
                            }

                            if (foundUser.isPresent()) {
                                user = foundUser.get();
                            } else {
                                user = userService.createUser(
                                        DataGenerator.generateUser()
                                                .email(email)
                                                .password(password));
                                UsersRemoverExtension.addUserToRemove(user);
                            }

                            var tokens = authService.signIn(email, password);

                            var userWithTestData = user.testData(
                                    user.testData()
                                            .sessionId(tokens.sessionId())
                                            .csrf(tokens.csrf())
                            );

                            foundUser.ifPresent(u ->
                                    userFromUserExtension.ifPresentOrElse(
                                            u1 -> updateUserFromUserExtension(userWithTestData),
                                            () -> updateUserFromUsersExtension(userWithTestData)
                                    ));

                            Allure.step("Set sessionId and csrf cookies", () -> {
                                var browserContext = PageStore.INSTANCE.getOrCreateNewPage().context();
                                var csrf = tokens.csrf();
                                var sessionId = tokens.sessionId();
                                browserContext.addCookies(List.of(
                                        new Cookie(csrf.type(), csrf.value()).setUrl(CFG.baseUrl()),
                                        new Cookie(sessionId.type(), sessionId.value()).setUrl(CFG.baseUrl())
                                ));
                            });
                        }
                );
    }

    private void updateUserFromUserExtension(UserDTO user) {
        UserExtension.getUserMap().entrySet().stream()
                .filter(entry ->
                        entry.getValue().email().equals(user.email()))
                .findFirst()
                .ifPresent(entry -> UserExtension.getUserMap().put(entry.getKey(), user));
    }

    private void updateUserFromUsersExtension(UserDTO user) {
        var usersListMap = UsersExtension.getUsersMap();
        usersListMap.entrySet().stream()
                .filter(entry ->
                        entry.getValue().stream()
                                .anyMatch(u -> u.email().equals(user.email())))
                .findFirst()
                .ifPresent(entry -> {
                    var usersList = usersListMap.get(entry.getKey());
                    IntStream.range(0, usersList.size())
                            .filter(i -> usersList.get(i).email().equals(user.email()))
                            .findFirst()
                            .ifPresent(i -> usersList.set(i, user));
                });
    }

}
