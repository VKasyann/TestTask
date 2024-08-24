package library.pageObjects.MonrealCanadiens;

import library.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MonrealMainPage {
    private static MonrealMainPage instance;
    BaseTest baseTest = BaseTest.getInstance();
    WebDriver driver = baseTest.getDriver();

    String pageURL = "https://www.nhl.com/fr/canadiens/";
    private final By languageChangeButton = By.xpath("//button[@aria-controls='language-switch']");
    private final By languageChangeEnglish = By.xpath("//nav[@id='language-switch']/ul/li[contains(., 'English')]");
    private final By languageChangeFrench = By.xpath("//nav[@id='language-switch']/ul/li[contains(., 'Français')]");
    private final By topMenu = By.id("hamburger-menu");

    // singleton pattern for page object class
    public static MonrealMainPage getInstance() {
        if (instance == null) {
            instance = new MonrealMainPage();
        }
        return instance;
    }

    public void navigate() {
        driver.navigate().to(pageURL);
    }

    public void switchLanguage(String languageCode) {
        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(languageChangeButton));

        driver.findElement(languageChangeButton).click();

        switch(languageCode) {
            case "EN":
                driver.findElement(languageChangeEnglish).click();
                break;
            case "FR":
                driver.findElement(languageChangeFrench).click();
                break;
            default:
                throw new IllegalArgumentException("Unknown country code.");
        }
    }

    public void clickMenuItem(String menuName) {
        By elementLocator = By.xpath("//*[contains(text(),'" + menuName + "')]");
        final By menuNameLocator = BaseTest.childOf(topMenu, elementLocator);

        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(menuNameLocator));

        driver.findElement(menuNameLocator).click();
    }
}
