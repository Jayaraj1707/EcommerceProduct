package com.ecommerce.util;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.enums.Timeout;
import com.ecommerce.log.LogUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PageUtil {

    /**
     * Click.
     *
     * @param element the element
     * @param driver  the driver
     */
    public static void click(WebElement element, final WebDriver driver) {

		/*JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", elem);
       */
        JavascriptExecutor executor = (JavascriptExecutor) driver;

        try {
            if (element != null && !isDisplayed(driver, element, Timeout.FIVE_SEC)) {
                executor.executeScript("arguments[0].scrollIntoView(true);", element);
            }

            Assert.assertTrue(isDisplayed(driver, element, Timeout.FIVE_SEC),
                    "Element not found in page for scroll to work.");
            executor.executeScript("arguments[0].click();", element);
        } catch (StaleElementReferenceException ex) {

            LogUtil.log("Stale exception occured, waiting for rebuilding the element.", LogLevel.HIGH);
            new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
            executor.executeScript("arguments[0].scrollIntoView(true);", element);
            Assert.assertTrue(isDisplayed(driver, element, Timeout.FIVE_SEC),
                    "Element not found in page for scroll to work.");

            executor.executeScript("arguments[0].click();", element);
        }
    }


    /**
     * Checks if is displayed.
     *
     * @param driver  the driver
     * @param locator the locator
     * @param timeout the timeout
     * @return true, if is displayed
     */
    public static boolean isDisplayed(final WebDriver driver, final By locator, final Timeout timeout) {

        try {
            WebElement eleToScroll = getElement(driver, locator, timeout);

            if (eleToScroll != null && !eleToScroll.isDisplayed()) {
                scrollIntoView(driver, eleToScroll);
            }
        } catch (StaleElementReferenceException ex) {
            WebElement eleToScroll = getElement(driver, locator, timeout);

            if (eleToScroll != null && !eleToScroll.isDisplayed()) {
                scrollIntoView(driver, eleToScroll);
            }
        }
        String customTimeoutName = System.getProperty("CUSTOM_IS_DISPLAYED_TIMEOUT_NAME");
        final Timeout waitTimeout = customTimeoutName != null && !(customTimeoutName.isEmpty())
                && Timeout.getByName(customTimeoutName) != null ? Timeout.getByName(customTimeoutName) : timeout;
        try {
            return new WebDriverWait(driver, Duration.ofSeconds((long) timeout.getValue()))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * Checks if is displayed.
     *
     * @param driver  the driver
     * @param element the element
     * @param timeout the timeout
     * @return true, if is displayed
     */
    public static boolean isDisplayed(final WebDriver driver, final WebElement element, final Timeout timeout) {

        if (element != null && !element.isDisplayed()) {
            scrollIntoView(driver, element);
        }

        WebElement ele = null;
        try {
            ele = new WebDriverWait(driver, Duration.ofSeconds(timeout.getValue())).until(new ExpectedCondition<WebElement>() {

                public WebElement apply(final WebDriver driver) {

                    if (element != null && element.isDisplayed()) {
                        return element;
                    }
                    return null;
                }
            });
        } catch (Exception e) {

        }

        return ele != null && ele.isDisplayed();

    }


    public static void waitForElementVisibility(final WebDriver driver, final By locator, final Timeout timeout) {

        try {
            //new WebDriverWait(driver, Duration.ofSeconds(timeout.getValue())).until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
            new WebDriverWait(driver, Duration.ofSeconds(timeout.getValue())).until(ExpectedConditions.visibilityOfElementLocated(locator));

        } catch (Exception ex) {
            LogUtil.log(" Catch block :  Exception in getting the Visible of Element", LogLevel.HIGH);

            try {
                LogUtil.log("Catch block is running. Retrying on element located.", LogLevel.HIGH);
                new WebDriverWait(driver, Duration.ofSeconds(timeout.getValue())).until(ExpectedConditions.presenceOfElementLocated(locator));
            } catch (Exception e) {
                LogUtil.log("Exception in presence of element located.", LogLevel.HIGH);
            }
            throw ex;
        }
    }

    /**
     * Gets the element.
     *
     * @param driver  the driver
     * @param locator the locator
     * @param timeout the timeout
     * @return the element
     */
    public static WebElement getElement(final WebDriver driver, final By locator, final Timeout timeout) {

        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout.getValue()))
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (StaleElementReferenceException ex) {

            return getElementOvercomingStale(driver, locator, timeout);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Gets the element overcoming stale.
     *
     * @param driver  the driver
     * @param locator the locator
     * @param timeout the timeout
     * @return the element overcoming stale
     */
    public static WebElement getElementOvercomingStale(final WebDriver driver, final By locator, final Timeout timeout) {

        try {
            LogUtil.log("Get element overcominng stale.", LogLevel.LOW);
            return new WebDriverWait(driver, Duration.ofSeconds(timeout.getValue())).until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(locator)));
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * Gets the visible elements. Returns an array of webElements that are
     * visible. If there are no visible DOM elements that match the criteria,
     * then NULL is returned.
     *
     * @param driver  the driver
     * @param locator the locator
     * @param timeout the timeout
     * @return the visible elements
     */
    public static List<WebElement> getVisibleElements(final WebDriver driver, final By locator, final Timeout timeout) {

        final List<WebElement> visibleElements = new ArrayList<>();
        try {
            for (final WebElement element : getElements(driver, locator, timeout)) {
                if (element.isDisplayed()) {
                    visibleElements.add(element);
                }
            }
        } catch (Exception ex) {
            LogUtil.log("Element either not found or not visible.", LogLevel.HIGH);
        }
        return visibleElements;
    }


    /**
     * Gets the elements.
     *
     * @param driver  the driver
     * @param locator the locator
     * @param timeout the timeout
     * @return the elements
     */
    public static List<WebElement> getElements(final WebDriver driver, final By locator, final Timeout timeout) {

        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout.getValue()))
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * Attribute.
     *
     * @param tagname the tagname
     * @param value   the value
     * @return the by
     */
    public static By attribute(final String tagname, final String value) {

        return By.cssSelector("[" + tagname + "=\"" + value + "\"]");
    }

    /**
     * Switch to next window.
     *
     * @param driver the driver
     */
    public static void switchToNextWindow(WebDriver driver) {

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }


    /**
     * Scroll into view.
     *
     * @param driver  the driver
     * @param element the element
     */
    public static void scrollIntoView(final WebDriver driver, WebElement element) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

    }

    /**
     * Sets the text.
     *
     * @param fieldName the field name
     * @param value     the value
     */
    public static void setText(WebElement fieldName, String value) {
        clearText(fieldName);
        fieldName.clear();
        if (value != null) {
            fieldName.sendKeys(value);
        }
    }

    /**
     * Maximize windows.
     *
     * @param driver the driver
     */
    public static void maximizeWindows(WebDriver driver) {

        if (SystemUtil.isChrome()) {
            driver.manage().window().maximize();
        } else if (SystemUtil.isUnix() && SystemUtil.isChrome()) {
            driver.manage().window().setSize(new Dimension(1400, 768));
        } else if (SystemUtil.isUnix() && ParamUtil.getBrowser().equalsIgnoreCase("chromeEmulator") && ParamUtil.getBrowser().equalsIgnoreCase("android")) {
            LogUtil.log("Browser not maximized", LogLevel.LOW);
        } else if (ParamUtil.getBrowser().equalsIgnoreCase("android")) {
            LogUtil.log("Browser not maximized", LogLevel.LOW);
        } else {
            driver.manage().window().maximize();
        }
    }

    /**
     * Clear text.
     *
     * @param fieldName the field name
     */
    private static void clearText(WebElement fieldName) {

        fieldName.sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
    }

}
