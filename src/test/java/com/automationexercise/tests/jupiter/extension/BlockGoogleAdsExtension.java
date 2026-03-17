package com.automationexercise.tests.jupiter.extension;

import com.automationexercise.tests.browser.PlaywrightContextStore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Locale;
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
                                    String urlLower = route.request().url().toLowerCase(Locale.ROOT);
                                    boolean isAd = CFG.browserGoogleAdsPattern().stream().anyMatch(urlLower::contains);

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