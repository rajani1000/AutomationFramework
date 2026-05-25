package com.mytest.automation.controller;

import com.mytest.automation.pages.OpenCartHomePage;
import com.mytest.automation.pages.SaucedemoHomePage;
import com.mytest.automation.utils.TestCase;


/**
 * @author Rajani
 */
public class UIModule extends TestCase {

    public UIModule(String arg) {
        super(arg);
    }

    public void navigateToOpenCart(String env, String title) throws Exception {
        OpenCartHomePage oc = new OpenCartHomePage(driver);
        oc.navigateToPage(env, title);
    }
    public void searchForItemAndAddToCart(String item) throws Exception {
        OpenCartHomePage oc = new OpenCartHomePage(driver);
        oc.searchForItemAndAddToCart(item);
    }

    public void loginToSaucedemo(String env, String title, String userName, String password) throws Exception {
        SaucedemoHomePage sd = new SaucedemoHomePage(driver);
        sd.navigateToPage(env,title);
        sd.loginToSaucedemo(userName, password);
    }

    public void verifyProductPrice(String sortingOption, String product) throws Exception {
        SaucedemoHomePage sd = new SaucedemoHomePage(driver);
        sd.verifyItemCost(sortingOption, product);
    }

    public void quitBrowser() {
        if (initialized) {
            driver.quit();
            initialized = false;
        } else {
            driver.quit();
        }
    }

    public void closeBrowser() {
        driver.close();
    }

}
