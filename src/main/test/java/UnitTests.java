import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

public class UnitTests {
    String wrongFile;
    String wrongFileType;
    String fileWithWrongCity;
    String stringToLookFor;
    LogsAppender logsAppender;
    String invalidCityName;
    String filePath;

    private static Logger LOGGER = LoggerFactory.getLogger(WeatherApi.class);
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    WeatherApi weatherApiObject;

    @Before
    public void setUp() {
        invalidCityName = "Tallinnaaan";
        weatherApiObject = new WeatherApi();
        logsAppender = new LogsAppender();
        wrongFile = "file.txt";
        wrongFileType = "file.csv";
        fileWithWrongCity = "src/test/java/cities.txt";
        stringToLookFor = "Lisbon";
        filePath = "StaticForecast.json";
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Rule
    public SystemOutRule systemOutRule1 = new SystemOutRule().enableLog();

    @Test
    public void testWrongFileFormat() {
        WeatherApi.openFile(wrongFileType);
        Assert.assertEquals("Invalid file format!", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void testWrongFileName() {
        WeatherApi.openFile(wrongFile);
        Assert.assertEquals("File not found!", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void testWrongCityName() throws IOException {
        String errorMessage = invalidCityName + " is invalid city name, file not created!";
        BufferedReader r = new BufferedReader(new StringReader(invalidCityName));
        weatherApiObject.readFile(r);
        Assert.assertTrue(LogsAppender.returnLogList().size() > 0);
        Assert.assertEquals(errorMessage, LogsAppender.returnLogList().get(LogsAppender.returnLogList().size() - 1));
    }
}