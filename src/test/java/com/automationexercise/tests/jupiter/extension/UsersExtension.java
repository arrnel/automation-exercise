package com.automationexercise.tests.jupiter.extension;

import com.automationexercise.tests.jupiter.anno.Users;
import com.automationexercise.tests.mapper.UserMapper;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.util.DataGenerator;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.*;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.max;

public class UsersExtension extends BaseExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UsersExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        var isMethodAnnotated = context.getRequiredTestMethod().isAnnotationPresent(Users.class);
        var methodAnno = context.getRequiredTestMethod().getAnnotation(Users.class);

        Stream.of(context.getRequiredTestMethod().getParameters())
                .filter(parameter -> isMethodAnnotated || parameter.isAnnotationPresent(Users.class))
                .filter(parameter -> isParameterListOfType(parameter, UserDTO.class))
                .forEach(parameter -> {
                    var anno = parameter.isAnnotationPresent(Users.class)
                            ? parameter.getAnnotation(Users.class)
                            : methodAnno;
                    putInUsersMap(parameter.getName(), createUsers(anno));
                });

    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext
    ) throws ParameterResolutionException {

        var isRequiredType = isParameterListOfType(parameterContext.getParameter(), UserDTO.class);
        var isMethodAnnotated = extensionContext.getRequiredTestMethod().isAnnotationPresent(Users.class);
        var isCurrentParameterAnnotated = parameterContext.getParameter().isAnnotationPresent(Users.class);

        return isRequiredType && (isMethodAnnotated || isCurrentParameterAnnotated);

    }

    @Override
    public List<UserDTO> resolveParameter(ParameterContext parameterContext,
                                          ExtensionContext extensionContext
    ) throws ParameterResolutionException {
        return getUsersMap().get(parameterContext.getParameter().getName());
    }

    @Nonnull
    private List<UserDTO> createUsers(Users anno) {

        List<UserDTO> tempUsers = new ArrayList<>();
        Stream.of(anno.value())
                .forEach(annoUser -> tempUsers.add(
                        UserMapper.updateFromAnno(
                                DataGenerator.generateUser(),
                                annoUser))
                );
        int randomUsersCount = max(anno.count(), 0);
        IntStream.range(0, randomUsersCount)
                .forEach(i -> tempUsers.add(DataGenerator.generateUser()));

        return tempUsers.isEmpty()
                ? Collections.emptyList()
                : Allure.step("Create users", () -> tempUsers.stream()
                .map(userService::createUser)
                .toList());

    }

    public static void putInUsersMap(String paramName, List<UserDTO> user) {
        getUsersMap().put(paramName, user);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    static Map<String, List<UserDTO>> getUsersMap() {
        ExtensionContext extensionContext = TestMethodContextExtension.context();
        return (Map<String, List<UserDTO>>) extensionContext.getStore(NAMESPACE)
                .getOrComputeIfAbsent(extensionContext.getUniqueId(), map -> new HashMap<>());
    }

    public static Optional<UserDTO> findUser(String email) {
        return getUsersMap().values().stream()
                .flatMap(List::stream)
                .filter(user -> user.email().equals(email))
                .findFirst();
    }

}
