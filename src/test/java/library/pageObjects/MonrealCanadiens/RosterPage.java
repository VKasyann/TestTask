package library.pageObjects.MonrealCanadiens;

import library.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class RosterPage {
    private static RosterPage instance;
    BaseTest baseTest = BaseTest.getInstance();
    WebDriver driver = baseTest.getDriver();

    String pageURL = "https://www.nhl.com/canadiens/roster/";
    private final By pageRoot = By.id("root");


    // singleton pattern for page object class
    public static RosterPage getInstance() {
        if (instance == null) {
            instance = new RosterPage();
        }
        return instance;
    }

    public int getNumberOfPlayersByCountryShortcut(String country) {
        final By tableElement = BaseTest.childOf(pageRoot, By.xpath("//table"));
        final By countryTagElement = BaseTest.childOf(tableElement, By.xpath("//span[text()='" + country + "']"));

        baseTest.getWait().until(ExpectedConditions.visibilityOfElementLocated(countryTagElement));

        List<WebElement> elements = driver.findElements(countryTagElement);

        return elements.size();
    }
}