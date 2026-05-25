package com.mytest.automation.utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Rajani
 */
public class Report {
    public static ExtentReports extent;
    public static ExtentTest test;
    String destination = "";

    public void setReportPath() {
        extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/Report.html", false);
    }

    public void getScreenshot(WebDriver driver, String screenshotName, String folderName) throws Exception {
        String dt = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        destination = System.getProperty("user.dir") + "/test-screenshots/" + "/" + folderName + "/" + screenshotName + dt + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        test.addScreenCapture(destination);
    }

    public void addScreenshotToTestNGReport(WebDriver driver) {
        String src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);

        String path = "<img src=\"data:image/png;base64, " + src + "\" width=\"300\" height=\"350\" />";

        Logs.info(path);
    }
}
