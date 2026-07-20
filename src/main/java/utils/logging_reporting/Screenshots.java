package utils.logging_reporting;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Screenshots {

    public static void captureSuccess(WebDriver driver, String testName) {
        try {
            // set the file name of the screenshot
            String fileName = "Successful Screenshot for [%s]".formatted(testName);

            // take the screenshot by driver
            var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            //Resize the Screenshot before upload
            InputStream screenshot_resized = resizeScreenshot(screenshot, 300);

            //upload the screenshot into allure report
            Allure.addAttachment(fileName, screenshot_resized);

            //log the action into console
            LogHelper.logInfoStep("Capturing Screenshot for Succeeded Scenario");

        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Capture Screenshot for Successes Scenario", e);
        }
    }

    public static void captureFailure(WebDriver driver, String testName) {
        try {
            // set the file name of the screenshot
            String fileName = "Failed Screenshot for [%s]".formatted(testName);

            // take the screenshot by driver
            var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            //Resize the Screenshot before upload
            InputStream screenshot_resized = resizeScreenshot(screenshot, 300);

            //upload the screenshot into allure report
            Allure.addAttachment(fileName, screenshot_resized);

            //log the action into console
            LogHelper.logInfoStep("Capturing Screenshot for Failed Scenario");

        } catch (Exception e) {
            LogHelper.logErrorStep("Failed to Capture Screenshot for Failed Scenario", e);
        }
    }

    public static InputStream resizeScreenshot(byte[] original, int targetWidth) throws IOException {
        BufferedImage src = ImageIO.read(new ByteArrayInputStream(original));
        int targetHeight = (int) (src.getHeight() * (targetWidth / (double) src.getWidth()));

        BufferedImage scaled = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = scaled.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(src, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(scaled, "png", out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
