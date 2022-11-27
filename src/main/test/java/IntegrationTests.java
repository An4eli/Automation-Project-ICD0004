import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Before;;
import org.junit.Test;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IntegrationTests {
    String fileWithWrongCity;
    String rightApi;
    String wrongApi;
    String city = "Tallinn";
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Date currentDate = new Date();
    String currentDateFormated = sdf.format(currentDate);
    String lastDate;
    JsonObject weatherMainJsonObject;
    JsonObject foreCastJsonObject;
    JsonArray foreCastJsonArray;

    @Before
    public void setUp() {
        fileWithWrongCity = "src/test/java/cities.txt";
        rightApi = "https://api.openweathermap.org/data/2.5/weather?q=";
        wrongApi = "https://api.openweathermap.org/dataa/2.5/wrong!!";
    }

    @Before
    public void setUpLastForecastDate() throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(currentDateFormated));
        c.add(Calendar.DATE, 3);  // number of days to add
        lastDate = sdf.format(c.getTime());
    }

    @Before
    public void setUpWeatherMain() throws IOException, InterruptedException {
        WeatherMain weatherMain = new WeatherMain();
        WeatherApi.weatherMainAPI(weatherMain, city);
        weatherMainJsonObject = WeatherApi.toJsonObject(weatherMain);
    }

    @Before
    public void setUpForecastReport() throws IOException, InterruptedException {
        WeatherMain weatherMain = new WeatherMain();
        WeatherApi weatherApi = new WeatherApi();
        weatherApi.forecastAPI(weatherMain, city);
        foreCastJsonObject = WeatherApi.toJsonObject(weatherMain);
        foreCastJsonArray = foreCastJsonObject.getAsJsonArray("forecastReport");
    }

    @Test
    public void checkApi() throws IOException, InterruptedException {
        HttpRequest request = WeatherApi.getRequest(rightApi, city);
        HttpResponse<String> response = WeatherApi.getResponse(request);
        Assert.assertEquals(200, response.statusCode());
    }

    @Test
    public void checkNotWorkingApi() throws IOException, InterruptedException {
        HttpRequest request = WeatherApi.getRequest(wrongApi, city);
        HttpResponse<String> response = WeatherApi.getResponse(request);
        Assert.assertEquals(401, response.statusCode());
    }

    @Test
    public void testWeatherMainContainsAllElements() {
        Assert.assertNotEquals(null, weatherMainJsonObject.getAsJsonObject("currentWeatherReport").get("date"));
        Assert.assertNotEquals(null, weatherMainJsonObject.getAsJsonObject("currentWeatherReport").has("temperature"));
        Assert.assertNotEquals(null, weatherMainJsonObject.getAsJsonObject("currentWeatherReport").has("humidity"));
        Assert.assertNotEquals(null, weatherMainJsonObject.getAsJsonObject("currentWeatherReport").has("pressure"));
    }
}