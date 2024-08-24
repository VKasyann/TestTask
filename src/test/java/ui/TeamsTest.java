package ui;

import library.pageObjects.MonrealCanadiens.MonrealMainPage;
import library.pageObjects.MonrealCanadiens.RosterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TeamsTest {
    MonrealMainPage mainPage = MonrealMainPage.getInstance();
    RosterPage rosterPage = RosterPage.getInstance();

    @Test(description = "open web browser and scrape roster of the oldest team and verify there are more Canadian " +
            "players than players from USA")
    public void verifyCountOfTeams() {
        mainPage.navigate();
        mainPage.switchLanguage("EN");

        // navigate to "Roster" page
        mainPage.clickMenuItem("Team");
        mainPage.clickMenuItem("Roster");

        int canadianPlayers = rosterPage.getNumberOfPlayersByCountryShortcut("CAN");
        int usaPlayers = rosterPage.getNumberOfPlayersByCountryShortcut("USA");

        Assert.assertTrue(canadianPlayers > usaPlayers);
    }
}
