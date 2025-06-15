package com.automationexercise.tests.jupiter.extension;

import com.automationexercise.tests.jupiter.anno.User;
import com.automationexercise.tests.mapper.UserMapper;
import com.automationexercise.tests.models.UserDTO;
import com.automationexercise.tests.util.DataGenerator;
import org.junit.jupiter.api.extension.*;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class UserExtension extends BaseExtension implements BeforeEachCallback, ParameterResolver {

    static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UserExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        var isMethodAnnotated = context.getRequiredTestMethod().isAnnotationPresent(User.class);
        var methodAnno = context.getRequiredTestMethod().getAnnotation(User.class);

        Stream.of(context.getRequiredTestMethod().getParameters())
                .filter(parameter -> isMethodAnnotated || parameter.isAnnotationPresent(User.class))
                .filter(parameter -> parameter.getType().equals(UserDTO.class))
                .forEach(parameter -> {
                    var anno = parameter.isAnnotationPresent(User.class)
                            ? parameter.getAnnotation(User.class)
                            : methodAnno;
                    putInUserMap(parameter.getName(), createUser(anno));
                });
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext
    ) throws ParameterResolutionException {

        var isRequiredType = parameterContext.getParameter().getType().isAssignableFrom(UserDTO.class);
        var isMethodAnnotated = extensionContext.getRequiredTestMethod().isAnnotationPresent(User.class);
        var isCurrentParameterAnnotated = parameterContext.getParameter().isAnnotationPresent(User.class);

        return isRequiredType && (isMethodAnnotated || isCurrentParameterAnnotated);

    }

    @Nonnull
    @Override
    public UserDTO resolveParameter(ParameterContext parameterContext,
                                    ExtensionContext extensionContext
    ) throws ParameterResolutionException {
        return getUserMap().get(parameterContext.getParameter().getName());
    }

    @Nonnull
    private UserDTO createUser(User anno) {
        var tempUser = UserMapper.updateFromAnno(
                DataGenerator.generateUser(),
                anno);
        return userService.createUser(tempUser)
                .password(tempUser.password())
                .phoneNumber(tempUser.phoneNumber())
                .testData(tempUser.testData());
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    static Map<String, UserDTO> getUserMap() {
        var context = TestMethodContextExtension.context();
        return (Map<String, UserDTO>) context.getStore(NAMESPACE)
                .getOrComputeIfAbsent(context.getUniqueId(), map -> new HashMap<>());
    }

    private static void putInUserMap(String paramName, UserDTO user) {
        getUserMap().put(paramName, user);
    }

}
