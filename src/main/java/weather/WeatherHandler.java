package weather;

import logs.LogsAppender;
import api.WeatherApi;
import api.dto.ForecastReport;
import api.dto.WeatherDTO;
import com.google.gson.JsonObject;
import exceptions.FileNotFoundException;
import exceptions.InvalidFileFormatException;
import weather.utils.JsonFormatter;

import java.io.*;
import java.net.ConnectException;
import java.util.ArrayList;

public class WeatherHandler {
    public String city = "";
    public static BufferedReader openFile(String filename) throws InvalidFileFormatException, FileNotFoundException {
        try {
            String fileFormat = filename.split("\\.")[1];
            if(!fileFormat.equals("txt")){
                throw new InvalidFileFormatException();
            }

            File file = new File(filename);
            BufferedReader br
                    = new BufferedReader(new FileReader(file));
            return br;
        }catch (IOException ex){
            throw new FileNotFoundException();
        }
    }
    public static void readFile(BufferedReader br) throws IOException {
        String city;
        while ((city = br.readLine()) != null)
            try {
                WeatherApi weatherApi = new WeatherApi();
                WeatherDTO weatherDTO = weatherApi.weatherMainAPI(city);
                ArrayList<ForecastReport> forecastReportList = weatherApi.forecastAPI(city);
                for(ForecastReport forecastReportObject: forecastReportList){
                    weatherDTO.setForecastReport(forecastReportObject);
                }
                createWeatherFile(weatherDTO, city);
            }catch (ConnectException e){
                System.out.println("Cant connect to api");
            }catch (Exception e){
                LogsAppender.invalidCityLog(city);
            }
    }

    public static void createWeatherFile(WeatherDTO weather, String city){
        JsonObject jsonObject = JsonFormatter.toJsonObject(weather);
        String fileName = city + ".json";
        File jsonFile = new File(fileName);
        String jsonString = JsonFormatter.toJsonString(weather);
        if (jsonFile.exists()) {
            LogsAppender.overWriteFileLog(city);
        }
        try (FileWriter myWriter = new FileWriter(jsonFile)) {
            myWriter.write(jsonString);
            System.out.println(jsonObject);
            LogsAppender.successfullyCreatedFileLog(fileName);
        }catch (IOException e){
            LogsAppender.ioExceptionLog(e);
        }
    }
}
