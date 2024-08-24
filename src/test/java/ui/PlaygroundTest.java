package ui;

import library.pageObjects.uiPlayground.ProgressBarPage;
import library.pageObjects.uiPlayground.SampleAppPage;
import library.pageObjects.uiPlayground.UiPlaygroundMainPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PlaygroundTest {
    UiPlaygroundMainPage playgroundPage = UiPlaygroundMainPage.getInstance();
    SampleAppPage sampleAppPage = SampleAppPage.getInstance();
    ProgressBarPage progressBarPage = ProgressBarPage.getInstance();

    @BeforeMethod
    public void setUp() {
        playgroundPage.navigate();
    }

    @Test(description = "from the Home page, navigate to the Sample App page and cover all the " +
            "functionalities of that feature by tests")
    public void sampleAppTests() {
        String email = "email@email.com";
        String incorrectPwd = "Asdcfv23@!";
        String correctPwd = "pwd";

        playgroundPage.clickOnAppByName("Sample App");

        Assert.assertTrue(sampleAppPage.getLoginStatus().contains("User logged out."));

        // click login button with empty fields
        sampleAppPage.clickLoginButton();
        Assert.assertTrue(sampleAppPage.getLoginStatus().contains("Invalid username/password"));

        // click login button with filled email field
        sampleAppPage.typeTextInLoginField(email);
        sampleAppPage.clickLoginButton();
        Assert.assertTrue(sampleAppPage.getLoginStatus().contains("Invalid username/password"));

        // click login button with incorrectly filled password field
        sampleAppPage.typeTextInPasswordField(incorrectPwd);
        sampleAppPage.clickLoginButton();
        Assert.assertTrue(sampleAppPage.getLoginStatus().contains("Invalid username/password"));

        // click login button with correctly filled password field
        sampleAppPage.typeTextInPasswordField(correctPwd);
        sampleAppPage.clickLoginButton();
        Assert.assertTrue(sampleAppPage.getLoginStatus().contains("Invalid username/password"));

        // click login button with filled email and incorrectly filled password field
        sampleAppPage.typeTextInLoginField(email);
        sampleAppPage.typeTextInPasswordField(incorrectPwd);
        sampleAppPage.clickLoginButton();
        Assert.assertTrue(sampleAppPage.getLoginStatus().contains("Invalid username/password"));

        // click login button with correctly filled email and password field
        sampleAppPage.typeTextInLoginField(email);
        sampleAppPage.typeTextInPasswordField(correctPwd);
        sampleAppPage.clickLoginButton();
        Assert.assertTrue(sampleAppPage.getLoginStatus().contains("Welcome, " + email));
    }

    @Test(description = "on the Home page, click on the Load Delay and verify the page will " +
            "get loaded in reasonable time")
    public void loadDelayTest() {
        playgroundPage.clickOnAppByName("Load Delay");

        playgroundPage.validateDelayButtonDisplayed();
    }

    @Test(description = "from the Home page, navigate to the Progress Bar page and follow the " +
            "instructions specified in the Scenario section")
    public void progressBarTest() throws InterruptedException {
        playgroundPage.clickOnAppByName("Progress Bar");

        progressBarPage.clickStartButton();

        // there is no requirements for exact wait time or limitations, so I set maximum wait time to 1 minute with
        // 0.001 seconds step
        for (int i = 0; i < 60000; i++) {
            if (Integer.parseInt(progressBarPage.getProgressBarValue()) >= 75) {
                progressBarPage.clickStopButton();

                Assert.assertTrue(progressBarPage.getResultValue().contains("Result: 0"));
                break;
            }
            Thread.sleep(1);
        }
    }
}
