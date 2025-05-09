package com.ecommerce.adapter;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.enums.Timeout;
import com.ecommerce.log.LogUtil;
import com.ecommerce.util.PageUtil;
import com.ecommerce.util.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.lang.reflect.Method;

public abstract class AbstractPageAdapter {

    protected static WebDriver driver;

    protected WebElement getElementLocator(Enum<?> enums, Boolean doAssert) {

        By by = null;
        String description = "";
        Enum<?> xpathEnum = enums.getClass().getEnumConstants()[0];

        try {

            @SuppressWarnings({ "unchecked", "static-access" })
            Enum<?> pageEnum = xpathEnum.valueOf(enums.getClass(), enums.name());
            Method byMethod = null;
            Method descriptionMethod = null;

            byMethod = pageEnum.getClass().getMethod("getByLocator");
            descriptionMethod = pageEnum.getClass().getMethod("getDescription");

            by = (By) byMethod.invoke(pageEnum);

            description = (String) descriptionMethod.invoke(pageEnum);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtil.log("Error in getting the locator : " + ex.getMessage(), LogLevel.HIGH);
        }

        PageUtil.waitForElementVisibility(driver, by, Timeout.SHORT_TIMEOUT);
        WebElement element = PageUtil.getElement(driver, by, Timeout.FIVE_SEC);
        if (doAssert) {
            Assert.assertTrue(element != null && element.isDisplayed(), description + " field is not displayed");
        }

        return element;
    }

    protected <T> void fillTextBoxValue(Enum<?> enums, String value, Boolean doAssert) {

        By by = null;
        String description = "";
        Enum<?> xpathEnum = enums.getClass().getEnumConstants()[0];

        try {

            @SuppressWarnings({ "unchecked", "static-access" })
            Enum<?> pageEnum = xpathEnum.valueOf(enums.getClass(), enums.name());
            Method byMethod = null;
            Method descriptionMethod = null;

            byMethod = pageEnum.getClass().getMethod("getByLocator");
            descriptionMethod = pageEnum.getClass().getMethod("getDescription");

            by = (By) byMethod.invoke(pageEnum);

            description = (String) descriptionMethod.invoke(pageEnum);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtil.log("Error in Filling Text box values : " + ex.getMessage(), LogLevel.HIGH);
        }

        if (value != null && !value.isEmpty()) {
            PageUtil.waitForElementVisibility(driver, by, Timeout.SHORT_TIMEOUT);
            WebElement element = PageUtil.getElement(driver, by, Timeout.FIVE_SEC);
            if (doAssert) {
                Assert.assertTrue(element != null && element.isDisplayed(), description + " field is not displayed");
            }
            PageUtil.setText(element, value);

        }
    }

    protected <T> void click(Enum<?> enums, Boolean doAssert) {

        By by = null;
        String description = "";
        Enum<?> xpathEnum = enums.getClass().getEnumConstants()[0];

        try {

            @SuppressWarnings({ "unchecked", "static-access" })
            Enum<?> pageEnum = xpathEnum.valueOf(enums.getClass(), enums.name());
            Method byMethod = null;
            Method descriptionMethod = null;

            byMethod = pageEnum.getClass().getMethod("getByLocator");
            descriptionMethod = pageEnum.getClass().getMethod("getDescription");

            by = (By) byMethod.invoke(pageEnum);

            description = (String) descriptionMethod.invoke(pageEnum);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtil.log("Error in Clicking the button : " + ex.getMessage(), LogLevel.HIGH);
        }
        PageUtil.waitForElementVisibility(driver, by, Timeout.SHORT_TIMEOUT);
        WebElement element = PageUtil.getElement(driver, by, Timeout.THIRTY_SEC);
        if (doAssert) {
            Assert.assertTrue(element != null && element.isDisplayed(), description + " field is not displayed");
        }
        PageUtil.scrollIntoView(driver, element);
        WaitUtil.waitUntil(1);
        PageUtil.click(element, driver);

    }

    public abstract boolean isElementLoaded(Enum<?> enums);

    public abstract boolean isElementDisplayed(Enum<?> enums);
}
