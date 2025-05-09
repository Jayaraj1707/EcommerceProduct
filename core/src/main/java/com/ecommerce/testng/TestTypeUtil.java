package com.ecommerce.testng;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.enums.TestType;
import com.ecommerce.log.LogUtil;
import lombok.experimental.UtilityClass;
import org.testng.ITestContext;

import java.util.Arrays;
import java.util.List;

import static com.ecommerce.enums.TestType.*;

@UtilityClass
@SuppressWarnings("all")
public class TestTypeUtil {
    private static final String
            KEY = "testType";

    /**
     * Sets the test type to TestNG test context
     *
     * @param context  {@link ITestContext}
     * @param testType {@link TestType} - Restricted to ENUM Constants
     */
    public static void setTestType(ITestContext context, TestType testType) {
        context.setAttribute(testType.getKey(), testType.getValue());
    }

    /**
     * Gets test type for current TC from TestNG test context
     *
     * @param testContext - {@link ITestContext}
     * @return String - Test type
     * @see TestType
     */
    public static String getTestType(ITestContext testContext) {
        String testType = (String) testContext.getAttribute(KEY);
        LogUtil.log("Current TC is of the type: '" + testType + "'", LogLevel.LOW);
        return testType;
    }

    /**
     * Gets TC types that user a UI
     *
     * @return List of String - valid test types that uses a UI
     * @see TestType
     */
    public static List<String> getTypesWithUi() {
        List<String> uiTypes = Arrays.asList(UI.getValue());
        return uiTypes;
    }

    /**
     * Gets TC types that does not use a UI
     *
     * @return List of String - valid test types that does not use a UI
     * @see TestType
     */
    public static List<String> getTypesWithNoUi() {
        List<String> noUiList = Arrays.asList(API.getValue());
        return noUiList;
    }
}
