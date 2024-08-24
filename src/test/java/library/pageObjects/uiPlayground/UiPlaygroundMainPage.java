package library.pageObjects.uiPlayground;

import library.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UiPlaygroundMainPage {
    private static UiPlaygroundMainPage instance;
    BaseTest baseTest = BaseTest.getInstance();
    WebDriver driver = baseTest.getDriver();

    String pageURL = "http://uitestingplayground.com/";
    private final By loadDelayButton = By.xpath("//button[@class='btn btn-primary']");


    public static UiPlaygroundMainPage getInstance() {
        if (instance == null) {
            instance = new UiPlaygroundMainPage();
        }
        return instance;
    }

    public void navigate() {
        driver.navigate().to(pageURL);
    }

    public void clickOnAppByName(String name) {
        final By appLink = By.xpath("//a[contains(., '" + name + "')]");

        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(appLink));

        driver.findElement(appLink).click();
    }

    public void validateDelayButtonDisplayed() {
        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(loadDelayButton));

        driver.findElement(loadDelayButton).isDisplayed();
    }
}