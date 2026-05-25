package com.mytest.automation.pages;


import com.mytest.automation.utils.BasePage;
import com.mytest.automation.utils.Environments;
import com.mytest.automation.utils.Logs;
import com.mytest.automation.utils.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Rajani
 */
public class SaucedemoHomePage extends BasePage {

    private By name = By.xpath("//input[@id='user-name']");
    private By pwd = By.xpath("//input[@id='password']");
    private By login = By.xpath("//input[@id='login-button']");
    private By sortingDropdown = By.xpath("//select[@class='product_sort_container']");
    String item = "//button[contains(@id,'<<VARIABLE>>')]";
    private By cart = By.xpath("//span[@class='shopping_cart_badge']/parent::a");
    private By price = By.xpath("//*[@class='inventory_item_price']");


    Logs logger = Logs.getInstance();
    TestCase tc = new TestCase();

    public SaucedemoHomePage(WebDriver driver, String env) {
        super(driver, env);
    }

    public SaucedemoHomePage(WebDriver driver) {
        super(driver);
    }

    public void loginToSaucedemo(String userName, String password) throws Exception {
        getElementWhenClickable(name).sendKeys(userName);
        Logs.info("Entered user name : " + userName);
        getElementWhenClickable(pwd).sendKeys(password);
        Logs.info("Entered password");
        getElementWhenClickable(login).click();
        Logs.info("Clicked on login button");

    }

    public void navigateToPage(String env, String titleTxt) throws InterruptedException {
        navigateTo(new Environments().getProperty(env));
        Logs.info("Navigated to requested page");
        verifyTitle(titleTxt);
        handlePasswordChangePopup();
        Thread.sleep(2000);
    }


    public void handlePasswordChangePopup() {
        try {
            // Try ENTER key (common for OK buttons)
            performAction();
            Logs.info("Popup dismissed with ENTER key");
            Thread.sleep(500);
        } catch (Exception e) {
            Logs.info("ENTER key approach failed, trying TAB + ENTER");
            try {
                // Try TAB to focus OK, then ENTER
                //   new Actions(getDriver()).sendKeys(Keys.TAB, Keys.ENTER).perform();
                Logs.info("Popup dismissed with TAB + ENTER");
            } catch (Exception e2) {
                Logs.info("Keyboard approach failed");
            }
        }
    }


    public void verifyTitle(String titleTxt) {
        String txt = getTitle();
        if (txt.equalsIgnoreCase(titleTxt)) {
            Logs.info("Expected title displayed: " + txt);
        } else {
            throw new RuntimeException("Expected title was not displayed: " + txt);
        }
    }


    public void verifyItemCost(String sortingOption, String product) throws InterruptedException {

        selectDropdownByText(getElementWhenClickable(sortingDropdown), sortingOption);
        String xpath = item.replaceAll("<<VARIABLE>>", product);
        getElementWhenClickable(By.xpath(xpath)).click();
        Logs.info("Selected product : " + product);
        Thread.sleep(2000);
        getElementWhenClickable(cart).click();
        Logs.info("Clicked on cart button");
        String itemPrice = getText(price);
        Logs.info("Price of the item added to cart is : " + itemPrice);
    }
}
