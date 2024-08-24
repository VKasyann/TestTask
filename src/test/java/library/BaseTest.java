package library;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {
    private static BaseTest instance;
    private WebDriver driver;
    WebDriverWait wait;

    public static BaseTest getInstance() {
        if (instance == null) {
            instance = new BaseTest();
        }
        return instance;
    }

    // with this implementation, we can add a parameter to set up different browsers, but for now I use only Chrome
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        if(wait == null)
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void closeBrowser() {
        driver.quit();
        driver = null;
    }

    public WebDriver getDriver() {
        if(driver == null)
            setUp();
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    // method to combine two By locators into one Xpath locator
    public static By childOf(By parent, By child) {
        String parentXpath = getXpathFromBy(parent);
        String childXpath = getXpathFromBy(child);
        return By.xpath(parentXpath + childXpath);
    }

    // Method to extract XPath expression from By locators
    private static String getXpathFromBy(By by) {
        String byString = by.toString();
        if (byString.startsWith("By.xpath: ")) {
            return byString.replace("By.xpath: ", "");
        } else if (byString.startsWith("By.id: ")) {
            return "//*[@id='" + byString.replace("By.id: ", "") + "']";
        } else if (byString.startsWith("By.className: ")) {
            return "//*[@class='" + byString.replace("By.className: ", "") + "']";
        } else if (byString.startsWith("By.cssSelector: ")) {
            return "//*[contains(@class, '" + byString.replace("By.cssSelector: ", "") + "')]";
        } else if (byString.startsWith("By.name: ")) {
            return "//*[@name='" + byString.replace("By.name: ", "") + "']";
        }
        throw new UnsupportedOperationException("Locator type not supported: " + by);
    }
}
