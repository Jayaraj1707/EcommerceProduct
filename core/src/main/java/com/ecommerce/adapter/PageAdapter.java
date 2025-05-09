package com.ecommerce.adapter;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.enums.Timeout;
import com.ecommerce.log.LogUtil;
import com.ecommerce.util.PageUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

public class PageAdapter extends AbstractPageAdapter {

    private static PageAdapter pageAdapter  = null;

    public PageAdapter(WebDriver driver) {
        AbstractPageAdapter.driver = driver;
    }

    public static PageAdapter getInstance(WebDriver driver) {
        if(pageAdapter ==null)
            pageAdapter = new PageAdapter(driver);
        return pageAdapter;
    }

    public static PageAdapter reset(WebDriver driver) {
        pageAdapter = new PageAdapter(driver);
        return pageAdapter;
    }

    @Override
    public boolean isElementLoaded(Enum<?> enums) {

        return false;
    }

    public boolean triggerTextBoxEvents(Enum<?> setEnum, final String value, Boolean doAssert, Boolean authenticate,
                                        Enum<?> getEnum) {

        fillTextBoxValue(setEnum, value, doAssert);

        if (authenticate) {
            return isElementLoaded(getEnum);
        }

        return false;
    }

    public boolean triggerClickEvents(Enum<?> setEnum, final Boolean doAssert, Boolean authenticate,
                                      Enum<?> getEnum) {

        click(setEnum, doAssert);

        if (authenticate) {
            return isElementDisplayed(getEnum);
        }
        return false;
    }

    @Override
    public boolean isElementDisplayed(Enum<?> enums) {
        By by = null;
        Enum<?> xpathEnum = enums.getClass().getEnumConstants()[0];
        WebElement element = null;
        try {
            @SuppressWarnings({ "unchecked", "static-access" })
            Enum<?> pageEnum = xpathEnum.valueOf(enums.getClass(), enums.name());
            Method byMethod = null;
            byMethod = pageEnum.getClass().getMethod("getByLocator");
            by = (By) byMethod.invoke(pageEnum);
            element = PageUtil.getElement(driver, by, Timeout.FIVE_SEC);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtil.log("Exception in display element: " + ex.getMessage(), LogLevel.HIGH);
        }

        return element != null && element.isDisplayed();
    }

    /**
     * Select Option from list.
     *
     * @param selectBox
     * @param doAssert
     * @param optionLocator
     */
    public void selectOptionFromList(Enum<?> selectBox, final Boolean doAssert, By optionLocator) {

        click(selectBox, doAssert);
        if(PageUtil.isDisplayed(driver, optionLocator, Timeout.FIVE_SEC)) {
            WebElement option = PageUtil.getElement(driver, optionLocator, Timeout.FIVE_SEC);
            PageUtil.click(option, driver);
        }
    }

    /**
     * Gets the element text.
     *
     * @param enums the enums
     * @param doAssert the do assert
     * @return the element text
     */
    public String getElementText(Enum<?> enums, final Boolean doAssert) {

        return getElementLocator(enums, doAssert).getText().trim();
    }

}
