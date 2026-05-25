package com.mytest.ui;


import com.mytest.automation.controller.UIModule;
import com.mytest.automation.utils.*;
import org.testng.SkipException;
import org.testng.annotations.*;

/**
 * @author Rajani
 */
public class UITests extends TestCase {

    UIModule oc;
    Logs logger = Logs.getInstance();
    Datapool dp = new Datapool();

    @Parameters("dataFile")
    @BeforeClass
    public void loadData(String dataFile) throws Throwable {
        dp.loadDatapool(TestProps.get("databookUI"), getClass().getSimpleName());
    }

    @BeforeMethod
    public void loadBrowser() {
        try {
            if (System.getProperty("browser") != null) {
                oc = new UIModule(System.getProperty("browser"));
            } else {
                oc = new UIModule(new Environments().getProperty("browser"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void loginToSaucedemo() throws Throwable {
        try {
            Logs.info("*************************Login To Saucedemo Web Page **********************");
            dp.setDataRow("Login To Saucedemo Web Page");
            checkExecutionStatus();
            String env = dp.getData("Environment") + "_UI";
            String userName = dp.getData("ParamVal1");
            String password = dp.getData("ParamVal2");
            String title = dp.getData("ParamVal3");
            oc.loginToSaucedemo(env,title,userName,password);
            getScreenshot(driver, "Login to Saucedemo", getClass().getSimpleName());

        } catch (Exception e) {
            getScreenshot(driver, "Login Failed", getClass().getSimpleName());
            throw e;
        }
    }

    @Test(dependsOnMethods = {"loginToSaucedemo"})
    public void verifyProductPrice() throws Throwable {
        try {
            Logs.info("************************* Verify Product Price **********************");
            dp.setDataRow("Verify Total Cost");
            checkExecutionStatus();
            String sortingOption = dp.getData("ParamVal1");
            String product = dp.getData("ParamVal2");
            oc.verifyProductPrice(sortingOption, product);
            getScreenshot(driver, "Verify Price", getClass().getSimpleName());

        } catch (Exception e) {
            getScreenshot(driver, "Price Verification Failed", getClass().getSimpleName());
            throw e;
        }
    }

    @AfterClass
    public void closeBrowser() throws Throwable {
        oc.quitBrowser();
    }

    private void checkExecutionStatus() throws Throwable {
        if (!(dp.getData("execute").equalsIgnoreCase("yes"))) {
            throw new SkipException("Skipped the test");
        }
    }
}