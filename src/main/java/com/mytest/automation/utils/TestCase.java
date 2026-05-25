package com.mytest.automation.utils;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * @author Rajani
 */
public class TestCase {

    protected static WebDriver driver;
    protected static boolean initialized = false;
    Logs logger = Logs.getInstance();
    String destination = "";

    public TestCase(String args) {
        driver = getDriver(args);
    }

    public TestCase() {
    }

    protected WebDriver getDriver(String str) {
        if (!initialized) {
            driver = BrowserDriver.createDriver(str);
            initialized = true;
        }
        return driver;
    }

    protected WebDriver getDriver() {
        return driver;
    }

    public void getScreenshot(WebDriver driver, String screenshotName, String folderName) throws Exception {
        String dt = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        destination = System.getProperty("user.dir") + "/test-screenshots/" + "/" + folderName + "/" + screenshotName + dt + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
    }

    public static String encoder(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
    public static String decoder(String encodedData) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);
        return new String(decodedBytes);
    }

}
