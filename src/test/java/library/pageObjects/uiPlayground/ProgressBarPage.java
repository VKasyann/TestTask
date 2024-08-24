package library.pageObjects.uiPlayground;

import library.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProgressBarPage {
    private static ProgressBarPage instance;
    BaseTest baseTest = BaseTest.getInstance();
    WebDriver driver = baseTest.getDriver();

    String pageURL = "http://uitestingplayground.com/progressbar";
    private final By startButton = By.id("startButton");
    private final By stopButton = By.id("stopButton");
    private final By resultText = By.id("result");
    private final By progressBar = By.id("progressBar");

    public static ProgressBarPage getInstance() {
        if (instance == null) {
            instance = new ProgressBarPage();
        }
        return instance;
    }

    public void navigate() {
        driver.navigate().to(pageURL);
    }

    public void clickStartButton() {
        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(startButton));

        driver.findElement(startButton).click();
    }

    public void clickStopButton() {
        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(stopButton));

        driver.findElement(stopButton).click();
    }

    public String getProgressBarValue() {
        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(progressBar));

        return driver.findElement(progressBar).getAttribute("aria-valuenow");
    }

    public String getResultValue() {
        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(progressBar));

        return driver.findElement(resultText).getText();
    }
}
