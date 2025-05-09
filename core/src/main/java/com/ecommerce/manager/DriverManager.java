package com.ecommerce.manager;

import com.ecommerce.browser.ChromeBrowser;
import com.ecommerce.enums.LogLevel;
import com.ecommerce.log.LogUtil;
import com.ecommerce.util.ParamUtil;
import com.ecommerce.util.TerminalUtil;
import com.ecommerce.util.WaitUtil;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

    private static WebDriver webDriver;

    /**
     * Gets the browser driver.
     *
     * @param browser the browser
     * @return the browser driver
     */
    public static WebDriver getBrowserDriver(final String browser) {

        switch (browser) {
            case "chrome":
                if (SystemUtils.IS_OS_LINUX) {
                    LogUtil.log("Chrome Driver for Linux ", LogLevel.HIGH);
                    webDriver = ChromeBrowser.createDriver();
                } else {
                    webDriver = ChromeBrowser.createDriver();
                }
                break;
            case "firefox":
                if (webDriver == null) {
                    webDriver = new FirefoxDriver();
                }
                break;
            case "edge":
                if (webDriver == null) {
                    webDriver = new EdgeDriver();
                    break;
                }
            default:
                break;
        }
//         PageUtil.maximizeWindows(webDriver);
        return webDriver;
    }

    /**
     * Reload.
     */
    public static void reload() {

        if (getDriver() != null) {
            String url = getDriver().getCurrentUrl();
            getDriver().get(url);
            WaitUtil.waitUntil(10);
        }
    }

    /**
     * Close.
     */
    public static void close() {

        if (getDriver() != null) {
            close(getDriver());
        }
    }

    /**
     * Close.
     *
     * @param webDriver the web driver
     */
    public static void close(final WebDriver webDriver) {
        // FirefoxBrowser.generateHarFile();
        webDriver.manage().deleteAllCookies();
        webDriver.quit();
//		webDriverThread = new ThreadLocal<EventFiringWebDriver>();
        TerminalUtil.revertTimeZone();
    }

    /**
     * Gets the driver.
     *
     * @return the driver
     */
    public static WebDriver getDriver() {

        return webDriver == null ? getBrowserDriver(ParamUtil.getBrowser()) : webDriver;
    }
}
