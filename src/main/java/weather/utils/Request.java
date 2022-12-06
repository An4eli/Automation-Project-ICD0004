package weather.utils;

import logs.LogsAppender;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

public class Request {

    public static HttpRequest getRequest(String weatherApi, String city){
        HttpRequest httpRequest = null;
        try {
            httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(weatherApi + city + "&appid=ef619cd2265c8cb95d9f7934b5a29b36"))
                    .GET().build();
        }catch (URISyntaxException e){
            LogsAppender.exceptionLog(e);
        }
        return httpRequest;
    }
}
