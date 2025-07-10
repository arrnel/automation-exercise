package com.automationexercise.tests.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ParametersAreNonnullByDefault
public class FileUtil {

    @Nonnull
    public static String fileContent(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException ex) {
            log.error("Error reading file content", ex);
        }
        return "";
    }

}
