package com.automationexercise.tests.util;

import com.automationexercise.tests.util.screenshot.ScreenDiff;
import com.automationexercise.tests.util.screenshot.ScreenDiffResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageUtil {

    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    public static BufferedImage convertBytesToBufferedImage(byte[] bytes) {
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] convertBufferedImageToBytes(BufferedImage bufferedImage) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ScreenDiff getScreenDiff(byte[] expectedScreenshot,
                                           byte[] actualScreenshot,
                                           double percentOfTolerance
    ) {

        var diffResult = new ScreenDiffResult(
                ImageUtil.convertBytesToBufferedImage(expectedScreenshot),
                ImageUtil.convertBytesToBufferedImage(actualScreenshot),
                percentOfTolerance
        );

        var diffResultImage = convertBufferedImageToBytes(
                diffResult.getDiff()
                        .getMarkedImage()
        );

        return ScreenDiff.builder()
                .expected("data:image/png;base64," + ENCODER.encodeToString(expectedScreenshot))
                .actual("data:image/png;base64," + ENCODER.encodeToString(actualScreenshot))
                .diff("data:image/png;base64," + ENCODER.encodeToString(diffResultImage))
                .hasDiff(diffResult.getAsBoolean())
                .build();
    }

    @SneakyThrows
    public static void rewriteImage(byte[] actualScreenshot,
                                    Path pathToScreenshot
    ) {
        Path parentDir = pathToScreenshot.getParent();
        if (parentDir != null && !Files.exists(parentDir))
            Files.createDirectories(parentDir);
        Files.write(pathToScreenshot, actualScreenshot);
    }

}
