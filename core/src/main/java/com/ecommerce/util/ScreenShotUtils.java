package com.ecommerce.util;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.log.LogUtil;
import com.ecommerce.screenshot.TakeScreenshot;
import lombok.experimental.UtilityClass;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Class is responsible for taking screenshots of failed/skipped tests having a UI component
 */
@UtilityClass
public class ScreenShotUtils {
    private static final Logger LOGGER = Logger.getLogger(ScreenShotUtils.class.getName());

    /**
     * Takes screenshot of WebUI execution
     *
     * @param result {@link ITestResult}
     * @return String screenshot file name
     */
    public static String takeWebUiScreenShot(ITestResult result) {
        DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
        String fileName = result.getMethod().getMethodName() + "_" + dateFormat.format(new Date()) + ".png";
        String destFile = "testresult/screenshots/" + fileName;
        result.setAttribute("screenshot", fileName);
        TakeScreenshot.takeScreenshot(destFile);
        final String dir = System.getProperty("user.dir");
        final Path baseDir = Paths.get(dir, destFile);
        File screenshot = baseDir.toFile();
        LogUtil.log("Is Screenshot exists : " + screenshot.exists(), LogLevel.LOW);
        if (screenshot.exists()) {
            LogUtil.log("Screenshot Absolute Path : " + screenshot.getAbsolutePath(), LogLevel.LOW);
        }
        LogUtil.log("Screenshot URL : " + destFile, LogLevel.LOW);
        return fileName;
    }


    private void printUnimplementedWarning() {
        LOGGER.warning("ENCOUNTERED UNIMPLEMENTED METHOD CALL !! PLEASE IMPLEMENT THE METHOD.");
    }
}
