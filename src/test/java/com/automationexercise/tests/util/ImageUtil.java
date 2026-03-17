package com.automationexercise.tests.util;

import com.automationexercise.tests.ex.ScreenshotException;
import com.automationexercise.tests.models.ScreenshotCheckContext;
import com.automationexercise.tests.util.screenshot.ScreenDiff;
import com.automationexercise.tests.util.screenshot.ScreenDiffResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static com.automationexercise.tests.config.test.CfgInstance.CFG;


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
                                           double tolerance
    ) {

        var diffResult = new ScreenDiffResult(
                ImageUtil.convertBytesToBufferedImage(expectedScreenshot),
                ImageUtil.convertBytesToBufferedImage(actualScreenshot),
                tolerance
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

    public static byte[] imageToBytes(BufferedImage image) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createImage(Path pathToImage, Dimension imageSize, Color color) {
        try {
            Files.createDirectories(pathToImage.getParent());
            BufferedImage image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(color);
            graphics.fillRect(0, 0, imageSize.width, imageSize.height);
            graphics.dispose();

            if (Files.notExists(pathToImage))
                Files.createFile(pathToImage);

            Files.write(pathToImage, ImageUtil.imageToBytes(image));

        } catch (IOException e) {
            throw new ScreenshotException("Unable to create [%s] image at: %s.%n".formatted(color, pathToImage), e);
        }
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

    @SneakyThrows
    public static void performScreenshotCheck(ScreenshotCheckContext ctx) {

        boolean isNew = Files.notExists(ctx.expectedScreenshotPath());

        if (isNew)
            ImageUtil.createImage(ctx.expectedScreenshotPath(), ctx.actualScreenshotSize(), Color.WHITE);

        var diff = ImageUtil.getScreenDiff(
                Files.readAllBytes(ctx.expectedScreenshotPath()),
                ctx.actualScreenshot(),
                ctx.tolerance()
        );

        var rewriteOldOnFailure = diff.isHasDiff() && (CFG.rewriteAllScreenshots() || ctx.rewriteScreenshot());
        if (isNew || rewriteOldOnFailure)
            ImageUtil.rewriteImage(ctx.actualScreenshot(), ctx.expectedScreenshotPath());

        AllureUtil.attachScreenDiff(diff);

        if (diff.isHasDiff())
            throw new ScreenshotException(
                    "Screenshots differ more than %.2f%%".formatted(ctx.tolerance())
            );
    }


}
