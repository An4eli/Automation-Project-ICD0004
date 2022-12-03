import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class weatherApi {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    final static String mainApi = "https://api.openweathermap.org/data/2.5/weather?q=";
    final static String BASE_URL = "https://api.openweathermap.org/data/2.5";
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, ParseException {
        weatherMainAPI("Tallinn");

    }
    public static WeatherMain weatherMainAPI(String city) throws IOException, InterruptedException, URISyntaxException {
        WeatherMain weather = new WeatherMain();
        JsonObject jsonObj = toJsonObject(mainApi,city);
        MainDetails mainDetails = new MainDetails();
        mainDetails.setCity(jsonObj.get("name").getAsString());
        mainDetails.setCoordinates(jsonObj.getAsJsonObject("coord").get("lon").getAsString() + "," + jsonObj.getAsJsonObject("coord").get("lat").getAsString());
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport();
        setElements(currentWeatherReport,jsonObj);
        currentWeatherReport.roundElements();
        currentWeatherReport.setDate();
        weather.setMainDetails(mainDetails);
        weather.setCurrentWeatherReport(currentWeatherReport);
        return weather;
    }
    public static void setElements(CurrentWeatherReport currentWeatherReportObject, JsonObject jsonObject){
        currentWeatherReportObject.setTemperature(jsonObject.getAsJsonObject("main").get("temp").getAsDouble());
        currentWeatherReportObject.setHumidity(jsonObject.getAsJsonObject("main").get("humidity").getAsInt());
        currentWeatherReportObject.setPressure(jsonObject.getAsJsonObject("main").get("pressure").getAsInt());
    }
    public static HttpRequest getRequest(String weatherApi, String city) throws URISyntaxException {
        HttpRequest httpRequest = null;
        httpRequest = HttpRequest.newBuilder()
                .uri(new URI(weatherApi + city + "&appid=ef619cd2265c8cb95d9f7934b5a29b36"))
                .GET().build();

        return httpRequest;
    }
    public static HttpResponse<String> getResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    public static JsonObject toJsonObject(String api, String city) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest getRequestForecast = getRequest(api,city);
        HttpResponse<String> getResponse = getResponse(getRequestForecast);
        JsonElement jsonElement = JsonParser.parseString(getResponse.body());
        String jsonFormat = gson.toJson(jsonElement);
        return new Gson().fromJson(jsonFormat, JsonObject.class);
    }
    public static JsonObject toJsonObject(WeatherMain weather){
        String jsonFormat = gson.toJson(weather);
        return new Gson().fromJson(jsonFormat, JsonObject.class);
    }
}