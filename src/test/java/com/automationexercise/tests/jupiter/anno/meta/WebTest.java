package com.automationexercise.tests.jupiter.anno.meta;

import com.automationexercise.tests.jupiter.extension.ApiLoginExtension;
import com.automationexercise.tests.jupiter.extension.BlockGoogleAdsExtension;
import com.automationexercise.tests.jupiter.extension.BrowserExtension;
import com.automationexercise.tests.models.allure.AllureTag;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag(AllureTag.WEB_TEST)
@Epic("WEB")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith({
        BrowserExtension.class,
        BlockGoogleAdsExtension.class,
        ApiLoginExtension.class
})
public @interface WebTest {

}
