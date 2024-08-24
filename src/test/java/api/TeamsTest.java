package api;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static library.requests.Teams.getTeams;

public class TeamsTest {
    JsonPath teams;

    // before each test we make an API request to get teams
    @BeforeMethod
    public void setUp() {
        teams = getTeams();
    }

    @Test(description = "Verify the response returned expected count of teams")
    public void verifyCountOfTeams() {
        int expectedTeamSize = 32;

        // validate that teams size is equal to expected
        Assert.assertEquals(teams.getList("teams").size(), expectedTeamSize);
    }

    @Test(description = "Verify the oldest team is Montreal Canadiens")
    public void verifyOldestTeam() {
        String expectedOldestTeam = "Montreal Canadiens";

        List<Map<String, Object>> teamsList = teams.getList("teams");

        // Find the team with the lowest "founded" value using lambda function
        Map<String, Object> oldestTeam = teamsList.stream()
                .min(Comparator.comparingInt(team -> (int) team.get("founded")))
                .orElse(null);

        // as soon as we get team with the lowest number in founded year, we extract team name and make an assertion
        assert oldestTeam != null;
        Assert.assertEquals(oldestTeam.get("name"), expectedOldestTeam);
    }

    @Test(description = "verify there's a city with more than 1 team and verify names of those teams")
    public void verifyNumberOfTeamsFromCity() {
        int expectedTeamSize = 2;
        String repeatedLocation = "New York";
        String firstTeamName = "New York Islanders";
        String secondTeamName = "New York Rangers";
        int tmp = 0;

        List<Map<String, Object>> teamsList = teams.getList("teams");

        // Sort the list of teams by the "location" attribute
        teamsList.sort(Comparator.comparing(team -> (String) team.get("location")));

        // Find and store teams with repeated "location" attributes
        Set<String> repeatedLocations = new HashSet<>();
        List<Map<String, Object>> repeatedTeams = new ArrayList<>();

        for (int i = 0; i < teamsList.size() - 1; i++) {
            String currentLocation = (String) teamsList.get(i).get("location");
            String nextLocation = (String) teamsList.get(i + 1).get("location");

            if (currentLocation.equals(nextLocation)) {
                if (!repeatedLocations.contains(currentLocation)) {
                    repeatedTeams.add(teamsList.get(i));  // Add the current team
                }
                repeatedTeams.add(teamsList.get(i + 1));  // Add the next team
                repeatedLocations.add(currentLocation);   // Add location
            }
        }

        boolean firstTeamFound = false;
        boolean secondTeamFound = false;

        for (Map<String, Object> team : repeatedTeams) {
            String teamName = (String) team.get("name");

            if (teamName.equals(firstTeamName)) {
                firstTeamFound = true;
            }
            if (teamName.equals(secondTeamName)) {
                secondTeamFound = true;
            }
        }

        // Validate that we found both teams
        Assert.assertTrue(firstTeamFound);
        Assert.assertTrue(secondTeamFound);

        // Validate that we found exactly 2 teams
        Assert.assertEquals(repeatedTeams.size(), expectedTeamSize);

        // Validate that we found only "New York"
        Assert.assertEquals(repeatedLocations.size(), 1);
        Assert.assertTrue(repeatedLocations.contains(repeatedLocation));
    }

    @Test(description = "verify there are 8 teams in the Metropolitan division and verify them by their names")
    public void verifyTeamsInDivision() {
        int expectedTeamSize = 8;
        List<String> expectedTeamNames = new ArrayList<>();
        List<String> actualTeamNames = new ArrayList<>();
        expectedTeamNames.add("Carolina Hurricanes");
        expectedTeamNames.add("Columbus Blue Jackets");
        expectedTeamNames.add("New Jersey Devils");
        expectedTeamNames.add("New York Islanders");
        expectedTeamNames.add("New York Rangers");
        expectedTeamNames.add("Philadelphia Flyers");
        expectedTeamNames.add("Pittsburgh Penguins");
        expectedTeamNames.add("Washington Capitals");

        List<Map<String, Object>> teamsList = teams.getList("teams");

        // Find teams that belong to the "Metropolitan" division and save team names
        for (Map<String, Object> team : teamsList) {
            String currentTeamDivision = ((Map<String, String>) team.get("division")).get("name");
            if ("Metropolitan".equals(currentTeamDivision)) {
                actualTeamNames.add((String) team.get("name"));
            }
        }

        // Validate that we found exactly 8 teams with "Metropolitan" division
        Assert.assertEquals(actualTeamNames.size(), expectedTeamSize);

        for (String expectedName : expectedTeamNames) {
            Assert.assertTrue(actualTeamNames.contains(expectedName));
        }
    }
}
