package com.ecommerce.screenshot;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.log.LogUtil;
import com.ecommerce.manager.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TakeScreenshot {

    /**
     * Take screenshot.
     *
     * @param fileName the file name
     */
    public static void takeScreenshot(String fileName) {

        try {

            WebDriver driver = DriverManager.getDriver();

            final Path screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath();
            final Path screenShotDir = getTargetScreenshotPath(fileName);
            Files.copy(screenshot, screenShotDir, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtil.log("Error in taking screenshot :" + ex.getMessage(), LogLevel.HIGH);
        }
    }

    /**
     * Gets the target screenshot path.
     *
     * @param fileName the file name
     * @return the target screenshot path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static Path getTargetScreenshotPath(final String fileName) throws IOException {

        final String dir = System.getProperty("user.dir");
        final Path baseDir = Paths.get(dir, fileName);
        Files.createDirectories(baseDir);
        return baseDir;
    }

    /** Take screenshot for TOTP pwd reset
     * @param driver
     * @param fileName
     */
    public static void takeScreenshot(WebDriver driver, String fileName) {

        try {

            final Path screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath();
            final Path screenShotDir = getTargetScreenshotPath(fileName);
            Files.copy(screenshot, screenShotDir, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtil.log("Error in taking screenshot :" + ex.getMessage(), LogLevel.HIGH);
        }
    }

}
