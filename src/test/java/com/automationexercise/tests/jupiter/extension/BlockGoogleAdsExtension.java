package com.automationexercise.tests.jupiter.extension;

import com.automationexercise.tests.browser.PlaywrightContextStore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;
import java.util.Optional;

import static com.automationexercise.tests.config.test.CfgInstance.CFG;

@Slf4j
public class BlockGoogleAdsExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        if (CFG.playwrightBlockGoogleAds())
            return;

        Optional.ofNullable(PlaywrightContextStore.INSTANCE.getBrowserContext())
                .ifPresentOrElse(
                        ctx ->
                                ctx.route("**/*", route -> {

                                    var request = route.request();
                                    var url = request.url().toLowerCase();
                                    var type = request.resourceType();
                                    var adTypes = List.of("script", "image", "xhr");

                                    boolean isAd = CFG.browserGoogleAdsPattern().stream()
                                                    .anyMatch(pattern -> pattern.contains(url));

                                    if (isAd) {
                                        route.abort();
                                    } else {
                                        route.resume();
                                    }
                                }),
                        () -> log.warn("[BlockGoogleAdsExtension] BrowserContext not found in store → skipping ad block")
                );


    }

}