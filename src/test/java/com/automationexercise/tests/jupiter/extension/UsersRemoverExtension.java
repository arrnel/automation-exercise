package com.automationexercise.tests.jupiter.extension;

import com.automationexercise.tests.models.UserDTO;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * EXTENSION REGISTERED GLOBALLY
 */
@Slf4j
@ParametersAreNonnullByDefault
public class UsersRemoverExtension extends BaseExtension implements AfterEachCallback {

    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UsersRemoverExtension.class);

    @Override
    public void afterEach(ExtensionContext context) {

        getUsersToRemove().addAll(UserExtension.getUserMap().values());
        getUsersToRemove().addAll(
                UsersExtension.getUsersMap().values().stream()
                        .flatMap(Collection::stream)
                        .toList());

        if (!getUsersToRemove().isEmpty()) {
            log.info("Remove created users after test");
            Allure.step("Removing created users after tests", () ->
                    getUsersToRemove()
                            .forEach(user -> {
                                try {
                                    userService.deleteUser(user);
                                } catch (Exception ex) {
                                    log.error("Unable to delete user: {}\n.Exception: {}", user, ex.getMessage());
                                }
                            }));
        }

        // Clear users list after test
        UserExtension.getUserMap().clear();
        UsersExtension.getUsersMap().clear();
        getUsersToRemove().clear();

    }

    @Nonnull
    @SuppressWarnings("unchecked")
    private static List<UserDTO> getUsersToRemove() {
        var context = TestMethodContextExtension.context();
        var id = "%s_%s".formatted(context.getUniqueId(), "remove_list");
        return (List<UserDTO>) context.getStore(NAMESPACE)
                .getOrComputeIfAbsent(id, list -> new ArrayList<UserDTO>());
    }

    public static void addUserToRemove(@Nonnull UserDTO user) {
        getUsersToRemove().add(user);
    }

    public static void addUsersToRemove(Collection<UserDTO> users) {
        getUsersToRemove().addAll(users);
    }

}
