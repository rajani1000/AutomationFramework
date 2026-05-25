package com.mytest.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.io.File;

/**
 * @author Rajani
 */
public class BrowserDriver {
    static String geckoDriverPath = "src/main/resources/drivers/geckodriver.exe";
    static String chromeDriverPath = "src/main/resources/drivers/chromedriver.exe";
    static String ieDriverPath = "src/main/resources/drivers/IEDriverServer.exe";
    static String edgeDriverPath = "src/main/resources/drivers/msedgedriver.exe";


    public static WebDriver createDriver(String browserName) {
        if (browserName.equalsIgnoreCase("FIREFOX"))
            return firefoxDriver();
        if (browserName.equalsIgnoreCase("CHROME"))
            return chromeDriver();
        if (browserName.equalsIgnoreCase("IE"))
            return ieDriver();
        if (browserName.equalsIgnoreCase("EDGE"))
            return edgeDriver();
        if (browserName.equalsIgnoreCase("HEADLESS"))
            return headLessDriver();
        throw new RuntimeException("Invalid Browser Name");
    }

    public static WebDriver chromeDriver() {
        if (!new File(chromeDriverPath).exists())
            throw new RuntimeException("chromedriver.exe does not exists");
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            //options.setPageLoadStrategy(PageLoadStrategy.NONE);
            options.addArguments("start-maximized");
            options.addArguments("enable-automation");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-browser-side-navigation");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-popup-blocking");
            return new ChromeDriver(options);
        } catch (Exception ex) {
            throw new RuntimeException("couldn't create chrome driver, check the chrome version");
        }
    }

    public static WebDriver headLessDriver() {
        if (!new File(chromeDriverPath).exists())
            throw new RuntimeException("chromedriver.exe does not exists");
        try {
           // System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
           /* options.addArguments("--start-maximized");
            options.addArguments("disable-infobars");
            options.addArguments("--disable-extentions");*/
            options.addArguments("--headless");
            return new ChromeDriver(options);
        } catch (Exception ex) {
            throw new RuntimeException("couldn't create chrome driver");
        }
    }

    public static WebDriver ieDriver() {
        if (!new File(ieDriverPath).exists())
            throw new RuntimeException("IEDriverServer.exe does not exists");
        try {
            System.setProperty("webdriver.ie.driver", ieDriverPath);
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.setCapability("nativeEvents", false);
            options.setCapability("unexpectedAlertBehaviour", "accept");
            options.setCapability("ignoreProtectedModeSettings", true);
            options.setCapability("disable-popup-blocking", true);
            options.setCapability("enablePersistentHover", true);
            options.setCapability("ignoreZoomSetting", true);
            options = options.withInitialBrowserUrl("http://www.google.ca");
            return new InternetExplorerDriver(options);
        } catch (Exception ex) {
            throw new RuntimeException("couldn't create ie driver");
        }
    }

    public static WebDriver firefoxDriver() {
        if (!new File(geckoDriverPath).exists())
            throw new RuntimeException("geckoDriver.exe does not exists");
        try {
            System.setProperty("webdriver.gecko.driver", geckoDriverPath);
            WebDriver driver = new FirefoxDriver();
            driver.manage().window().maximize();
            return driver;
        } catch (Exception ex) {
            throw new RuntimeException("couldn't create firefox driver");
        }
    }

    public static WebDriver edgeDriver() {
        if (!new File(edgeDriverPath).exists())
            throw new RuntimeException("msedgedriver.exe does not exists");
        try {
            System.setProperty("webdriver.edge.driver", edgeDriverPath);
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();
            return driver;
        } catch (Exception ex) {
            throw new RuntimeException("couldn't create Edge driver");
        }
    }
}

