package com.automationexercise.tests.page._component.carousel;

import com.automationexercise.tests.page._component._type.CarouselType;
import com.microsoft.playwright.Locator;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.ParametersAreNonnullByDefault;

@Slf4j
@ParametersAreNonnullByDefault
public class ImageCarouselComponent extends BaseCarouselComponent<ImageCarouselComponent> {

    public ImageCarouselComponent(Locator self) {
        super(self, CarouselType.IMAGE);
    }

    public ImageCarouselComponent(Locator self, CarouselType carouselType) {
        super(self, carouselType);
    }

}
