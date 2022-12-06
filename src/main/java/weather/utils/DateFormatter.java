package weather.utils;

import logs.LogsAppender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static String formatDate(String date) {
        String newObjectDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Date d = sdf.parse(date);
            sdf.applyPattern("dd-mm-yyyy");
            newObjectDate = sdf.format(d);
        } catch (ParseException e) {
            LogsAppender.exceptionLog(e);
        }
        return newObjectDate;
    }
    public static String returnCurrentDate(){
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();
        return sdf2.format(currentDate);
    }
}
