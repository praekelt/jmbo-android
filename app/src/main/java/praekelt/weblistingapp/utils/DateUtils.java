package praekelt.weblistingapp.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by altus on 2015/06/29.
 */
public class DateUtils {

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time*1000);
        String date = DateFormat.format("hh:mm aa", cal).toString();
        return date;
    }

    public static String getDate(String date, String initDateFormat, String outputDateFormat) throws ParseException {

        Date initDate = new SimpleDateFormat(initDateFormat).parse(date.replace('T',' '));
        SimpleDateFormat formatter = new SimpleDateFormat(outputDateFormat);

        return formatter.format(initDate);
    }



}
