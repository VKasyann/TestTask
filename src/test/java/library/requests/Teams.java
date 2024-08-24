package library.requests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class Teams {

    private static final String URL = "https://qa-assignment.dev1.whalebone.io/api/teams";

    public static JsonPath getTeams() {

        return
                given()
                        .contentType(ContentType.JSON)
                .when()
                        .get(URL)
                .then()
                        .assertThat().statusCode(200)
                .extract()
                        .response().getBody().jsonPath();
    }
}
