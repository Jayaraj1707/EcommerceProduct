package com.ecommerce.setups;

import com.ecommerce.adapter.PageAdapter;
import org.openqa.selenium.WebDriver;

public abstract class EcommerceBasePage {

    /**
     * The driver.
     */
    protected WebDriver driver;

    /**
     * The pageAdapter
     */
    protected PageAdapter pageAdapter;

    /**
     * Instantiates a new MyAiEcho Base Page.
     *
     * @param driver the driver
     */
    public EcommerceBasePage(WebDriver driver) {

        this.driver = driver;
        this.pageAdapter = PageAdapter.getInstance(driver);
    }


    /**
     * Gets the driver.
     *
     * @return the driver
     */
    protected WebDriver getDriver() {

        return driver;
    }

}