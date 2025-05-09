package com.ecommerce.listener;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.log.LogUtil;
import com.ecommerce.screenshot.TakeScreenshot;
import com.ecommerce.util.DateUtil;
import org.joda.time.DateTimeZone;
import org.testng.*;
import org.testng.log4testng.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.ecommerce.manager.DriverManager.*;


/**
 * The Class AutomationListener.
 */
public class AutomationListener implements ISuiteListener, ITestListener {

    String logStart = "";

    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(AutomationListener.class);

    /*
     * Method override
     */
    @Override
    public void onFinish(ITestContext arg0) {
    }

    /*
     * Method override
     */
    @Override
    public void onStart(ITestContext ctx) {

    }

    /*
     * Method override
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

    }

    /*
     * Method override
     */
    @Override
    public void onTestFailure(ITestResult result) {

        String methodName = result.getMethod().getMethodName();
        LogUtil.log("<b style=\"color:red\">TEST FAILED.</b>", LogLevel.LOW);
        String ids[] = result.getName().split("_");
        String caseId = ids[ids.length - 1];
        if (caseId.contains("C"))
            caseId = caseId.replace("C", "");
        else if (caseId.contains("c"))
            caseId = caseId.replace("c", "");

        if (getDriver() != null) {
            LogUtil.log("Failure URL : <a href=" + getDriver().getCurrentUrl() + ">URL</a>", LogLevel.LOW);
            LogUtil.log("URL : " + getDriver().getCurrentUrl(), LogLevel.LOW);
        }

        String screenshotValue = System.getProperty("screenshot");
        LogUtil.log("Screenshot : " + screenshotValue, LogLevel.LOW);
        String fileName = "";
        boolean takeScreenshot = (screenshotValue == null || screenshotValue.isEmpty() ? true : Boolean.valueOf(screenshotValue));
        if (takeScreenshot) {
            LogUtil.log("Take Screenshot", LogLevel.LOW);
            fileName = takeScreenshot(result);
        }else {
            LogUtil.log("Not Taking Screenshot", LogLevel.LOW);
        }


        String destFile = "testresult/screenshots/" + fileName;
        result.setAttribute("screenshot", fileName);

        Reporter.setEscapeHtml(false);

        if (result.getThrowable() instanceof AssertionError) {
            String errorMessage = result.getThrowable().getMessage();
            populateErrorMessage(errorMessage);
        } else if (result.getThrowable() instanceof Exception) {
            LogUtil.log("<font color=\"red\">Failure Reason  : " + result.getThrowable().getLocalizedMessage() + "</font>",
                    LogLevel.LOW);
        }

        endLog(result);
    }

    /**
     * Populate error message.
     *
     * @param errorMessage the error message
     */
    private void populateErrorMessage(String errorMessage) {
        String message = errorMessage;

        if (message.contains("but")) {
            expectedButScernario(errorMessage);
        } else if (message.contains("not be null")) {
            expectedNotScernario(errorMessage);
        } else if (message.contains("null")) {
            LogUtil.log("<font color=\"red\">Failed Forcefully.</font>", LogLevel.LOW);
        } else {
            LogUtil.log("<font color=\"red\">Failure Reason  : " + errorMessage + "</font>", LogLevel.LOW);
        }
    }

    /**
     * Expected but scernario.
     *
     * @param errorMessage the error message
     */
    public void expectedButScernario(String errorMessage) {
        String message = errorMessage;
        String expected = "<font color=\"red\">Failure Reason  : ";

        int expectedIndex = message.lastIndexOf("but");
        String exp = message.substring(0, expectedIndex);
        exp = exp.replace("expected", "");
        exp = exp.replace("[", "");
        exp = exp.replace("]", "");
        if (exp.contains("null")) {
            expected = "(Null Expected but found value in object.) ";
        }

        exp = exp.replace("true", "");
        exp = exp.replace("false", "");
        exp = exp.replace("null", "");
        exp = exp.trim();
        expected += exp;
        LogUtil.log(expected + "</font>", LogLevel.LOW);
    }

    /**
     * Expected not scernario.
     *
     * @param errorMessage the error message
     */
    public void expectedNotScernario(String errorMessage) {
        errorMessage = errorMessage.replace("expected", "");
        errorMessage = errorMessage.replace("object to not be null", "");
        LogUtil.log("<font color=\"red\">Not Null expected, but found Null. (Message : " + errorMessage + " )</font>",
                LogLevel.LOW);
    }

    /*
     * Method override
     */
    @Override
    public void onTestSkipped(ITestResult result) {

        LogUtil.log("<font color=\"red\"> Test case Skipped in MAC.</font>", LogLevel.LOW);
        result.getThrowable().printStackTrace();
    }

    private String takeScreenshot(ITestResult result) {
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

    /*
     * Method override
     */
    @Override
    public void onTestStart(ITestResult arg0) {

        LogUtil.setNoOfSteps(0);
        LogUtil.setNoOfSections(0);

        LogUtil.commonConsoleLog(new StringBuilder("").toString());
        LogUtil.commonConsoleLog(new StringBuilder("").toString());

        long time = Calendar.getInstance().getTimeInMillis();
        String startTime = DateUtil.formatToZone(time, DateTimeZone.forID("America/Los_Angeles"), DateUtil.LARGE_FORMAT);
        String content = "<p style=\"text-decoration:underline;font-weight:bold\">START TIME : " + startTime + "</p>";
        logStart = content;
        String methodName = arg0.getMethod().getMethodName();
        LogUtil.commonConsoleLog(new StringBuilder("Started Test " + methodName).toString());
        LogUtil.commonConsoleLog(new StringBuilder("START TIME : " + startTime).toString());
    }

    /*
     * Method override
     */
    @Override
    public void onTestSuccess(ITestResult arg0) {
        LogUtil.log("<b style=\"color:green\">TEST PASSED.</b>", LogLevel.LOW);
        endLog(arg0);
    }

    /*
     * Method override
     */
    @Override
    public void onFinish(ISuite arg0) {

    }

    /*
     * Method override
     */
    @Override
    public void onStart(ISuite arg0) {

    }

    /**
     * End log.
     *
     * @param arg0 the arg0
     */
    private void endLog(ITestResult arg0) {
        long time = Calendar.getInstance().getTimeInMillis();
        String startTime = DateUtil.formatToZone(time, DateTimeZone.forID("America/Los_Angeles"), DateUtil.LARGE_FORMAT);
        String content = "<b style=\"text-decoration:underline\">END TIME : " + startTime + "</b>";
        Reporter.log(logStart + "############" + content);
        String methodName = arg0.getMethod().getMethodName();
        LogUtil.commonEndConsoleLog(new StringBuilder("END TIME : " + startTime + "").toString());
        LogUtil.commonEndConsoleLog(new StringBuilder("Ended Test " + methodName + "").toString());
        LogUtil.commonEndConsoleLog(new StringBuilder("").toString());
        LogUtil.setNoOfSteps(0);
        LogUtil.setNoOfSections(0);
    }

}
