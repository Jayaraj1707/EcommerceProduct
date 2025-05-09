package com.ecommerce.util;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.enums.Timeout;
import com.ecommerce.log.LogUtil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BooleanSupplier;

public class WaitUtil {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(WaitUtil.class);

    private static String logLevel = "";

    /**
     * Wait until.
     *
     * @param seconds
     *            the seconds
     */
    public static void waitUntil(int seconds) {

        wait(seconds);
    }

    /**
     * Wait until.
     *
     * @param timeout
     *            the timeout
     */
    public static void waitUntil(Timeout timeout) {

        wait(timeout.getValue());
    }

    /**
     * Wait default.
     *
     * @param timeout
     *            the timeout
     */
    public static void waitDefault(Timeout timeout) {

        wait(timeout.getValue());
    }

    /**
     * Wait.
     *
     * @param seconds
     *            the seconds
     */
    private static void wait(int seconds) {

        try {
            if (logLevel == null || logLevel.isEmpty()) {
                logLevel = ParamUtil.getLogLevel().toString();
            }
            if (logLevel != null && !logLevel.isEmpty() && !logLevel.equalsIgnoreCase("low")) {
                LogUtil.debugLog("Wait for " + seconds + " seconds.");
            }
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            LOGGER.error("Error Waiting ", e);
        }
    }

    /**
     * Waits for javascript and EXTJS to be ready.
     *
     * @param driver the web driver.
     */
    public static void waitForAll(WebDriver driver){
        if(driver==null) {
            return;
        }
        waitForJS(driver);
        waitForAjax(driver);
    }

    /**
     * Waits for JS to be ready.
     *
     * @param driver the web driver.
     */
    private static void waitForJS(WebDriver driver){

        BooleanSupplier condition=()-> {
            JavascriptExecutor jsDriver = (JavascriptExecutor)driver;
            Object isJSReady = jsDriver.executeScript("return document.readyState");
            return (String.valueOf(isJSReady.toString()).contains("complete"));
        };

        waitForCondition(condition);
    }

    /**
     * Waits until the Ext.Ajax requests are done.
     *
     * @param driver the web driver.
     */
    private static void waitForAjax(WebDriver driver){

        BooleanSupplier condition=()-> {
            JavascriptExecutor jsDriver = (JavascriptExecutor)driver;
            Object numberOfAjaxConnections = jsDriver.executeScript("return Ext.Ajax.isLoading();");
            if (numberOfAjaxConnections instanceof Boolean) {
                return (!Boolean.valueOf((Boolean)numberOfAjaxConnections));
            }
            return false;
        };

        waitForCondition(condition);
    }

    /**
     * Wait for the condition.
     *
     * @param conditionToCheck
     */
    private static void waitForCondition(BooleanSupplier conditionToCheck){
        try{
            int millisecondsWaitInterval = 750;
            TimeoutHelper timeOutHelper=new TimeoutHelper(Timeout.THIRTY_SEC.getValue(),
                    millisecondsWaitInterval);
            timeOutHelper.waitForCondition(()-> conditionToCheck.getAsBoolean());
        }catch(Exception ex){
            LogUtil.log("Error waiting condition: " + ex.getMessage(), LogLevel.LOW);
        }
    }
}
