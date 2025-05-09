package com.ecommerce.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

public class DateUtil {

    /** The Constant MM_DD_YY. */
    public static final String MM_DD_YY = "MM/dd/yy HH:mm aa";

    /** The Constant LARGE_FORMAT. */
    public static final String LARGE_FORMAT = "EEEE, MMMM dd, yyyy HH:mm:ss aa";

    /** The Constant HOUR_FORMAT. */
    public static final String HOUR_FORMAT = "HH:mm:ss aa";


    /**
     * Format.
     *
     * @param calendar the calendar
     * @param pattern the pattern
     * @return the string
     */
    public static String format(Calendar calendar, String pattern) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.print(new DateTime(calendar));
    }


    /**
     * Format to zone.
     *
     * @param milliseconds the milliseconds
     * @param zone the zone
     * @param pattern the pattern
     * @return the string
     */
    public static String formatToZone(Long milliseconds, DateTimeZone zone, String pattern) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        fmt = fmt.withZone(zone);
        String strDate = fmt.print(new DateTime(milliseconds));
        return strDate;
    }

}
