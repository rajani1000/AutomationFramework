package com.mytest.automation.pages;


import com.mytest.automation.utils.BasePage;
import com.mytest.automation.utils.Environments;
import com.mytest.automation.utils.Logs;
import com.mytest.automation.utils.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * @author Rajani
 */
public class OpenCartHomePage extends BasePage {


    private By searchItem = By.xpath("//input[@name='search']");
    private By title = By.xpath("//title");
    String item = "//a[contains(text(),'<<VARIABLE>>')]";
    private By addToCartButton = By.xpath("//button[contains(text(),'Add to Cart')]");
    private By cartButton = By.xpath("//*[@id = 'header-cart']//button");
    private By checkoutLink = By.xpath("//*[contains(text(),' Checkout')]//parent::a");
    private By totalAmount = By.xpath("(//*[text() ='Total'])[3]//parent::td//following-sibling::td");

    Logs logger = Logs.getInstance();
    TestCase tc = new TestCase();

    public OpenCartHomePage(WebDriver driver, String env) {
        super(driver, env);
    }

    public OpenCartHomePage(WebDriver driver) {
        super(driver);
    }


    public void navigateToPage(String env, String titleTxt) throws InterruptedException {
        navigateTo(new Environments().getProperty(env));
        Logs.info("Navigated to requested page");
        verifyTitle(titleTxt);
        Thread.sleep(2000);
    }

    public void navigateToLink(String link) {
        navigateTo(link);
        Logs.info("Navigated to: "+ link);
    }
    public void verifyTitle(String titleTxt) {
        String txt = getTitle();
        if(txt.equalsIgnoreCase(titleTxt)){
            Logs.info("Expected title displayed: "+txt);
        } else {
            throw new RuntimeException("Expected title was not displayed: "+txt);
        }
    }

    public void searchForItemAndAddToCart(String item) throws InterruptedException {
        getElementWhenClickable(searchItem).click();
        getElementWhenClickable(searchItem).sendKeys(item, Keys.ENTER);
        Logs.info("Searched for item: " + item);
        Thread.sleep(2000);
        By element = By.xpath(item.replaceAll("<<VARIABLE>>", item));
        String itemLink = getAttribute(element, "href");
        navigateToLink(itemLink);
        Logs.info("Navigated to item page: " + itemLink);
        getElementWhenClickable(addToCartButton).click();
        Thread.sleep(2000);
        getElementWhenClickable(cartButton).click();
        Thread.sleep(1000);
        String checkoutUrl = getAttribute(checkoutLink, "href");
        if (checkoutUrl != null) {
            Logs.info("Item added to cart successfully");
            navigateToLink(checkoutUrl);
            String amount= getText(totalAmount);
            Logs.info("Total amount in cart is: " + amount);
        } else {
            Logs.info("Item added to cart failed");
        }
    }

}
