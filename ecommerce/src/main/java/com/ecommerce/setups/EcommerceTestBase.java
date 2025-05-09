package com.ecommerce.setups;

import com.ecommerce.exception.DriverException;
import com.ecommerce.log.TestCaseLogUtil;
import com.ecommerce.manager.DriverManager;
import com.ecommerce.util.PageUtil;
import com.ecommerce.util.ParamUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;

import static com.ecommerce.manager.ConfigManager.*;

public class EcommerceTestBase {

    private static String propertyFile;

    /** The driver. */
    public static WebDriver driver;

    /**
     * Sets the up method.
     *
     * @param context the new up method
     * @throws DriverException the driver exception
     */
    @BeforeTest(alwaysRun = true)
    public void setupMethod(final ITestContext context) throws DriverException, IOException {
        String name = context.getCurrentXmlTest().getClasses().stream()
                .findFirst().get().getName();
        System.setProperty("testClassName", name.substring(name.lastIndexOf(".")+1));
        propertyFile = "ecommerce_testData.properties";
        loadMyAiEchoConfig(propertyFile);
        TestCaseLogUtil.printAllTestcases(context);
        if (ParamUtil.hasBrowserParam()) {
            driver = DriverManager.getDriver();
            PageUtil.maximizeWindows(driver);
        } else {
            String message = "Mandatory Parameters are not provided";
            throw new DriverException(message);
        }
    }

    /**
     * After test.
     *
     * @param context
     */
    @AfterTest(alwaysRun = true)
    public void afterTest(final ITestContext context) {

        driver.close();
    }
}