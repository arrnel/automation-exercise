package com.automationexercise.tests.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class SystemUtil {

    public static Path getAbsolutePathFromResources(String resourcePath) {

        if (resourcePath.startsWith("/"))
            resourcePath = resourcePath.substring(1);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null)
            classLoader = ClassLoader.getSystemClassLoader();

        URL resourceUrl = classLoader.getResource(resourcePath);
        if (resourceUrl == null) {
            throw new IllegalArgumentException(
                    "Not found resource: " + resourcePath +
                            "\nCheck folder or file exists in src/main/resources or src/test/resources"
            );
        }

        try {
            URI uri = resourceUrl.toURI();
            return Paths.get(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable convert URI to Path: " + resourceUrl, e);
        }
    }

    public static void copyDirIntoDir(Path source, Path target) {
        try {
            FileUtils.copyDirectoryToDirectory(source.toFile(), target.toFile());
        } catch (IOException ex) {
            log.warn("Unable to copy directory {} to {}", source, target, ex);
        }
    }

}
