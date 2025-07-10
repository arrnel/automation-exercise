package com.automationexercise.tests.models;

import lombok.Builder;

import javax.annotation.Nonnull;
import java.nio.file.Path;

import static com.automationexercise.tests.util.ObjectMapperUtil.getBeautifulJSON;

@Builder
public record ContactInfo(

        String name,

        String email,

        String subject,

        String message,

        Path pathToFile

) {

    @Nonnull
    public String toString() {
        return getBeautifulJSON(this);
    }

}
