import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;


public class SmokeTests {

    public static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static final String FORECAST_API = "https://api.openweathermap.org/data/2.5/forecast?q=";

    public static final String API_WEATHER_LAT_LON = "https://api.openweathermap.org/data/2.5/weather?";
    public static final String API_FORECAST_LAT_LON = "https://api.openweathermap.org/data/2.5/forecast?";

    public static final String CITY  = "Tallinn";
    public static final String KEY_API = "&appid=ef619cd2265c8cb95d9f7934b5a29b36";

    public static final String LAT = "lat=59.436962";
    public static final String LON = "lon=24.753574";

    @Test
    public void getWeatherWithLATLON () {
        given()
                .contentType(JSON.toString())
                .when()
                .get(API_WEATHER_LAT_LON + LAT + "&" + LON + KEY_API)
                .then()
                .statusCode(200);
    }

    @Test
    public void getWeatherForecastWithLATLON () {
        given()
                .contentType(JSON.toString())
                .when()
                .get(API_FORECAST_LAT_LON + LAT + "&" + LON + KEY_API)
                .then()
                .statusCode(200);
    }
}