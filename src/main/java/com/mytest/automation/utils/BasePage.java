package com.mytest.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * @author Rajani
 */
public class BasePage {
    WebDriver driver;
    Environments env;
    private String url;
    Duration timeout = Duration.ofSeconds(10);
    TestCase tc = new TestCase();

    private WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public BasePage(WebDriver driver, String env) {
        this.url = new Environments().getProperty(env);
        this.driver = driver;
    }

    public void hardWait(int sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec));
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public void navigateTo() {
        driver.get(this.url);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public WebElement getElementWhenClickable(final By locator) {
        return new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement getElementWhenVisible(final By locator) {
        return new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement getElementWhenPresented(final By locator) {
        return new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> getElementListWhenPresented(final By locator) {
        return new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public String getText(final By locator) {
        return driver.findElement(locator).getText();
    }

    public boolean isTextVisible(final By locator, String text) {
        return new WebDriverWait(driver, timeout).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public String getAttribute(final By locator, String attributeName) {
        return getElementWhenClickable(locator).getAttribute(attributeName);
    }

    public void webScrollToElement(WebElement e, int offset) {
        JavascriptExecutor jsx = (JavascriptExecutor) driver;
        jsx.executeScript("window.scrollTo(" + e.getLocation().getX() + "," + (e.getLocation().getY() - offset) + ");");
    }

    public void webScrollIntoView(WebElement e) {
        JavascriptExecutor jsx = (JavascriptExecutor) driver;
        jsx.executeScript("arguments[0].scrollIntoView(true);", e);
    }

    public void jsClick(WebElement e) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", e);
    }

    public void highlightElement(WebElement e) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].setAttribute('style','border: solid 2px red')", e);
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void selectDropdownByIndex(WebElement e, int index) {
        new Select(e).selectByIndex(index);
    }

    public void selectDropdownByValue(WebElement e, String val) {
        new Select(e).selectByValue(val);
    }

    public void selectDropdownByText(WebElement e, String text) {
        new Select(e).selectByVisibleText(text);
    }

    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }
    public void performAction() {
        ((JavascriptExecutor) driver).executeScript("window.focus();");
        new Actions(driver).sendKeys(Keys.TAB).perform();
        new Actions(driver).sendKeys(Keys.ENTER).perform();
        Logs.info("Password change popup handled: TAB + ENTER");
    }

    public WebElement switchToActiveElement() {
        WebElement e = driver.switchTo().activeElement();
        return e;
    }

  }
