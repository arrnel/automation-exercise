package com.automationexercise.tests.page._component._type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CarouselType {
    IMAGE("Image carousel"),
    RECOMMENDED_PRODUCTS("Recommended products carousel");
    private final String componentTitle;
}
