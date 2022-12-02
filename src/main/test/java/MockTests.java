import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

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



    private static final String city = "Tallinn";

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
    public void testGetTemperatureWeatherMain() throws IOException, InterruptedException {
        when(weatherApi.weatherMainAPI(anyString())).thenReturn(weatherMain);
        WeatherMain weatherGetTemperature = weatherApi.weatherMainAPI(city);
        assertThat(weatherMain.getCurrentWeatherReport().getTemperature(), equalTo(weatherGetTemperature.getCurrentWeatherReport().getTemperature()));
    }

    @Test
    public void testGetPressureWeatherMain() throws IOException, InterruptedException {
        when(weatherApi.weatherMainAPI(anyString())).thenReturn(weatherMain);
        WeatherMain weatherGetPressure = weatherApi.weatherMainAPI(city);
        assertThat(weatherMain.getCurrentWeatherReport().getPressure(), equalTo(weatherGetPressure.getCurrentWeatherReport().getPressure()));
    }

    @Test
    public void testGetHumidityWeatherMain() throws IOException, InterruptedException {
        when(weatherApi.weatherMainAPI(anyString())).thenReturn(weatherMain);
        WeatherMain weatherGetHumidity = weatherApi.weatherMainAPI(city);
        assertThat(weatherMain.getCurrentWeatherReport().getHumidity(), equalTo(weatherGetHumidity.getCurrentWeatherReport().getHumidity()));
    }
}