import api.WeatherApi;
import api.dto.CurrentWeatherReport;
import api.dto.ForecastReport;
import api.dto.MainDetails;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockTests {

    @Mock
    static WeatherApi weatherApi;
    static CurrentWeatherReport currentWeatherReport;
    static WeatherMain weatherMain;
    static MainDetails mainDetails;
    static ForecastReport forecastReportObject;
    static CurrentWeatherReport currentWeatherReportObject;
    static WeatherMain weatherMainObject;


    private static final String city = "Tallinn";

    @BeforeAll
    public static void weatherForecastMainTest() {
        weatherMainObject = new WeatherMain();
        forecastReportObject = new ForecastReport();
        currentWeatherReportObject = new CurrentWeatherReport();
        currentWeatherReportObject.setPressure(100);
        currentWeatherReportObject.setHumidity(30);
        currentWeatherReportObject.setTemperature(300.00);
        forecastReportObject.setWeather(currentWeatherReportObject);
        weatherMainObject.setForecastReport(forecastReportObject);
    }

    @BeforeAll
    public static void weatherMainTest() {
        currentWeatherReport = new CurrentWeatherReport();
        mainDetails = new MainDetails();
        mainDetails.setCity(city);
        mainDetails.setCoordinates("42,32");
        currentWeatherReport.setPressure(50);
        currentWeatherReport.setTemperature(300.00);
        currentWeatherReport.setHumidity(100);
        weatherMain = new WeatherMain();
        weatherMain.setMainDetails(mainDetails);
        weatherMain.setCurrentWeatherReport(currentWeatherReport);
    }

    @Test
    public void testGetTemperature() throws IOException, InterruptedException {
        when(weatherApi.weatherMainAPI(anyString())).thenReturn(weatherMain);
        WeatherMain weatherGetTemperature = weatherApi.weatherMainAPI(city);
        assertThat(weatherMain.getCurrentWeatherReport().getTemperature(), equalTo(weatherGetTemperature.getCurrentWeatherReport().getTemperature()));
    }

    @Test
    public void testGetPressure() throws IOException, InterruptedException {
        when(weatherApi.weatherMainAPI(anyString())).thenReturn(weatherMain);
        WeatherMain weatherGetPressure = weatherApi.weatherMainAPI(city);
        assertThat(weatherMain.getCurrentWeatherReport().getPressure(), equalTo(weatherGetPressure.getCurrentWeatherReport().getPressure()));
    }

    @Test
    public void testGetHumidity() throws IOException, InterruptedException {
        when(weatherApi.weatherMainAPI(anyString())).thenReturn(weatherMain);
        WeatherMain weatherGetHumidity = weatherApi.weatherMainAPI(city);
        assertThat(weatherMain.getCurrentWeatherReport().getHumidity(), equalTo(weatherGetHumidity.getCurrentWeatherReport().getHumidity()));
    }
    @Test
    public void testGetHumidityForecast() throws IOException, InterruptedException {
        ArrayList<ForecastReport> forecastReportsList = weatherMainObject.getForecastReport();
        when(weatherApi.forecastAPI(anyString())).thenReturn(forecastReportsList);
        ArrayList<ForecastReport> forecastAPIGetHumidity = weatherApi.forecastAPI(city);
        for(ForecastReport forecastReport: forecastAPIGetHumidity){
            for(ForecastReport forecastReportWeatherHumidity : forecastReportsList){
                assertThat(forecastReportWeatherHumidity.getWeather().getHumidity(), equalTo(forecastReport.getWeather().getHumidity()));
            }
        }
    }

    @Test
    public void testGetPressureForecast() throws IOException, InterruptedException {
        ArrayList<ForecastReport> forecastReportsList = weatherMainObject.getForecastReport();
        when(weatherApi.forecastAPI(anyString())).thenReturn(forecastReportsList);
        ArrayList<ForecastReport> forecastAPIGetPressure = weatherApi.forecastAPI(city);
        for(ForecastReport forecastReport: forecastAPIGetPressure){
            for(ForecastReport forecastReportWeatherPressure : forecastReportsList){
                assertThat(forecastReportWeatherPressure.getWeather().getPressure(), equalTo(forecastReport.getWeather().getPressure()));
            }
        }
    }

    @Test
    public void testGetTemperatureForecast() throws IOException, InterruptedException {
        ArrayList<ForecastReport> forecastReportsList = weatherMainObject.getForecastReport();
        when(weatherApi.forecastAPI(anyString())).thenReturn(forecastReportsList);
        ArrayList<ForecastReport> forecastAPIGetTemperature = weatherApi.forecastAPI(city);
        for(ForecastReport forecastReport: forecastAPIGetTemperature){
            for(ForecastReport forecastReportWeatherTemperature : forecastReportsList){
                assertThat(forecastReportWeatherTemperature.getWeather().getTemperature(), equalTo(forecastReport.getWeather().getTemperature()));
            }
        }
    }
}
