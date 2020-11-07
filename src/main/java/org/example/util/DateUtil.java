package org.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static String timestampToDateString(Long timestamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Tehran"));
        String str = simpleDateFormat.format(new Date(timestamp));
        return str;
    }

    public static int timestampToMonth(Long timestamp){
        String date = timestampToDateString(timestamp);
        return Integer.valueOf(date.split("-")[1]);
    }
}
