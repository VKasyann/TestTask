package library.pageObjects.uiPlayground;

import library.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SampleAppPage {
    private static SampleAppPage instance;
    BaseTest baseTest = BaseTest.getInstance();
    WebDriver driver = baseTest.getDriver();

    String pageURL = "http://uitestingplayground.com/sampleapp";
    private final By loginButton = By.id("login");
    private final By loginStatus = By.id("loginstatus");
    private final By loginInput = By.name("UserName");
    private final By passwordInput = By.name("Password");

    public static SampleAppPage getInstance() {
        if (instance == null) {
            instance = new SampleAppPage();
        }
        return instance;
    }

    public void navigate() {
        driver.navigate().to(pageURL);
    }

    public String getLoginStatus() {
        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(loginStatus));

        return driver.findElement(loginStatus).getText();
    }

    public void clickLoginButton() {
        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(loginButton));

        driver.findElement(loginButton).click();
    }

    public void typeTextInLoginField(String text) {
        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(loginInput));

        driver.findElement(loginInput).clear();
        driver.findElement(loginInput).sendKeys(text);
    }

    public void typeTextInPasswordField(String text) {
        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(passwordInput));

        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(text);
    }
}
