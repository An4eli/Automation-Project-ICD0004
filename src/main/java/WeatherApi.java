import com.google.gson.*;
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
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, ParseException {
        weatherJson("Tallinn");

    }
    public static void weatherJson(String city) throws URISyntaxException, IOException, InterruptedException, ParseException {
        HttpRequest getRequest1 = HttpRequest.newBuilder()
                .uri(new URI("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=ef619cd2265c8cb95d9f7934b5a29b36"))
                .GET().build();
        HttpClient httpClient1 = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient1.send(getRequest1, HttpResponse.BodyHandlers.ofString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(getResponse.body());
        String jsonFormat = gson.toJson(je);
        JsonObject jobj = new Gson().fromJson(jsonFormat, JsonObject.class); // 1 request

        MainDetails object = new MainDetails(jobj.get("name").getAsString(), jobj.getAsJsonObject("coord").get("lon").getAsString() + "," + jobj.getAsJsonObject("coord").get("lat").getAsString(), "Celcius");
        CurrentWeatherReport object2 = new CurrentWeatherReport();
        object2.setDate();
        object2.setTemperature(jobj.getAsJsonObject("main").get("temp").getAsDouble());
        object2.setHumidity(jobj.getAsJsonObject("main").get("humidity").getAsInt());
        object2.setPressure(jobj.getAsJsonObject("main").get("pressure").getAsInt());

        WeatherMain object4 = new WeatherMain();
        object4.setMainDetails(object);
        object4.setCurrentWeatherReport(object2);

        Forecast(object4,city);


    }
}