package com.automationexercise.tests.util.screenshot;

import io.qameta.allure.Allure;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.function.BooleanSupplier;

import static com.automationexercise.tests.config.test.CfgInstance.CFG;

@Slf4j
@ParametersAreNonnullByDefault
public class ScreenDiffResult implements BooleanSupplier {

    private static final double MAX_PERCENT = CFG.maxScreenshotDiff();

    private final BufferedImage actual;
    private final double tolerance;
    private final boolean hasDiff;

    @Getter
    private final ImageDiff diff;

    public ScreenDiffResult(BufferedImage expected, BufferedImage actual, double tolerance) {

        if (tolerance < 0.0 || tolerance > MAX_PERCENT)
            throw new IllegalArgumentException("Illegal percent of tolerance value. Allowed between [0, %f]".formatted(MAX_PERCENT));

        this.actual = actual;
        this.tolerance = tolerance;
        this.diff = new ImageDiffer().makeDiff(expected, actual);
        this.hasDiff = hasDiff();

    }

    private boolean hasDiff() {

        int totalPixels = actual.getWidth() * actual.getHeight();
        long expectedDiffSize = Math.round(totalPixels * tolerance);

        double diffPercent = (double) diff.getDiffSize() / totalPixels;
        var roundedActualPercentage = Double.parseDouble(new DecimalFormat("#.###").format(diffPercent));
        var hasDiff = roundedActualPercentage > tolerance;
        if (hasDiff) {
            var table = ScreenDiffTable.builder()
                    .expectedDiffSize(expectedDiffSize)
                    .actualDiffSize(diff.getDiffSize())
                    .expectedDiffPercent(tolerance)
                    .actualDiffPercent(diffPercent)
                    .build()
                    .getDiffTable();
            log.warn("\n{}", table);
            Allure.addAttachment("screen diff data", table);
        }
        return hasDiff;
    }

    @Override
    public boolean getAsBoolean() {
        return hasDiff;
    }

}
