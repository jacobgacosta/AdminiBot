package io.dojogeek.adminibot.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {

    public static String getCurrentData() {
        DateTimeZone dateTime = DateTimeZone.getDefault();

        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyy/MM/dd HH:mm:ss");

        return dateTime.toString();

    }

}
