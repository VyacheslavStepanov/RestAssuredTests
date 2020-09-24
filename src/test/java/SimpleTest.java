import io.restassured.response.Response;
import org.apache.http.util.Asserts;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class SimpleTest {
    private String token = "cefba77339674cc9d76319036c03928e";
    @Test
    public void testApiWeatherByZip(){
        String response =
            given().param("zip", "420000,ru")
                    .param("appid", token)
                    .when()
                        .get("http://api.openweathermap.org/data/2.5/weather")
                    .then()
                        .statusCode(200)
                    .log().body()
                    .extract().response().asString();
        Assert.assertEquals(response.contains("Казань"), true);
    }

    @Test
    public void testApiWeatherByCityMoreZero(){
        Response response =
                given().param("q", "Kazan")
                        .param("appid", token)
                        .when()
                        .get("http://api.openweathermap.org/data/2.5/weather")
                        .then()
                        .statusCode(200)
                        .contentType("application/json")
                        .extract().response();
        Assert.assertEquals(
                Double.parseDouble(response.jsonPath().getString("main.temp")) > 0, true);
    }
}
