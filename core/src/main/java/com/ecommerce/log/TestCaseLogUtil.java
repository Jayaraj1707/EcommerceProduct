package com.ecommerce.log;

import com.ecommerce.data.TestMethodDetails;
import com.ecommerce.enums.Color;
import com.ecommerce.enums.Style;
import com.ecommerce.util.ColorUtil;
import com.ecommerce.util.FileUtil;
import com.ecommerce.util.ParamUtil;
import com.ecommerce.util.StringUtil;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.Reporter;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class TestCaseLogUtil {


    public static void printAllTestcases(final ITestContext context) {

        System.out.println("SuiteName ==" + context.getSuite().getName());
        final ITestNGMethod[] allMethods = context.getAllTestMethods();
        Reporter.log(ColorUtil.style("--------------------------------------------", Color.FOREGROUND_RED,Style.NO_STYLE), true);
        Reporter.log(ColorUtil.style("Automation Test methods (" + allMethods.length + ")",Color.FOREGROUND_RED,Style.NO_STYLE), true);
        Reporter.log(ColorUtil.style("--------------------------------------------",Color.FOREGROUND_RED,Style.NO_STYLE), true);
        Reporter.log("", true);
        TreeMap<Integer, TestMethodDetails> testCaseMap = new TreeMap<Integer, TestMethodDetails>();
        int priority = 1;
        for (ITestNGMethod testNGMethod : allMethods) {
            TestMethodDetails testMethod = new TestMethodDetails();
            testMethod.setTestCaseName(testNGMethod.getMethodName());
            testMethod.setClassName(testNGMethod.getTestClass().getName());
            testCaseMap.put(priority, testMethod);
            priority++;
        }
        printGrid(context, testCaseMap);
    }

    /**
     * Prints the grid.
     *
     * @param testCaseMap the test case map
     */
    private static void printGrid(final ITestContext context, TreeMap<Integer, TestMethodDetails> testCaseMap) {

        FileUtil fileUtil = new FileUtil();
        Properties mproperties = new Properties();
        String suiteName = ParamUtil.getTestSuiteName();
        if (suiteName != null) {
            mproperties = fileUtil.getMethodNameOfProperty(suiteName);
        }
        String threadLine = "====================================================================";
        try {
            Reporter.log("Test Case Grid", true);
            Reporter.log(threadLine, true);
            Reporter.log(
                    "|   S.No.  |                   Test Case Name                      |",
                    true);
            Reporter.log(threadLine, true);
            Iterator<Map.Entry<Integer, TestMethodDetails>> testCaseIterator = testCaseMap.entrySet().iterator();
            Integer serialNo = 1;
            while (testCaseIterator.hasNext()) {
                Map.Entry<Integer, TestMethodDetails> pairs = testCaseIterator.next();
                TestMethodDetails testNgDetails = pairs.getValue();
                String testMethod = getTestMethodName(testNgDetails.getTestCaseName());


                Reporter.log("| " + StringUtil.addSpace("   " + serialNo.toString(), 9) + "| " + StringUtil.addSpace("                  " + testMethod, 54)
                        + "| ", true);
                serialNo++;
            }
            Reporter.log(threadLine, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getTestMethodName(String testMethod) {

        if (testMethod != null && (testMethod.length() > 58)) {

            testMethod = testMethod.substring(0, 58).concat("..");
        }
        return testMethod;
    }
}
