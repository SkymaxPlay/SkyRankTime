package com.gmail.skymaxplay.skyranktime.utils;

import com.gmail.skymaxplay.skyranktime.data.Config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Skymax on 10.01.2016.
 */
public class TimeUtils {

    public static String generateDate(long time){
        return new SimpleDateFormat( Config.timeFormat ).format(new Date(time));
    }

    public static long parseTime(String time){

        if(time.endsWith("s")) {
            return parseTimeType(TimeType.SECOND, 1, time);
        }
        if(time.endsWith("sek")) {
            return parseTimeType(TimeType.SECOND, 3, time);
        }
        if(time.endsWith("second")) {
            return parseTimeType(TimeType.SECOND, 6, time);
        }

        if(time.endsWith("m")) {
            return parseTimeType(TimeType.MINUTE, 1, time);
        }
        if(time.endsWith("min")) {
            return parseTimeType(TimeType.MINUTE, 3, time);
        }
        if(time.endsWith("minute")) {
            return parseTimeType(TimeType.MINUTE, 6, time);
        }

        if(time.endsWith("h")) {
            return parseTimeType(TimeType.HOUR, 1, time);
        }
        if(time.endsWith("hour")) {
            return parseTimeType(TimeType.HOUR, 4, time);
        }

        if(time.endsWith("d")) {
            return parseTimeType(TimeType.DAY, 1, time);
        }
        if(time.endsWith("day")) {
            return parseTimeType(TimeType.DAY, 3, time);
        }

        if(time.endsWith("w")) {
            return parseTimeType(TimeType.WEEK, 1, time);
        }
        if(time.endsWith("week")) {
            return parseTimeType(TimeType.WEEK, 4, time);
        }

        if(time.endsWith("mo")) {
            return parseTimeType(TimeType.MONTH, 2, time);
        }
        if(time.endsWith("month")) {
            return parseTimeType(TimeType.MONTH, 5, time);
        }

        if(time.endsWith("y")) {
            return parseTimeType(TimeType.YEAR, 1, time);
        }
        if(time.endsWith("r")) {
            return parseTimeType(TimeType.YEAR, 1, time);
        }
        if(time.endsWith("year")) {
            return parseTimeType(TimeType.YEAR, 4, time);
        }

        throw new NumberFormatException("Wrong time format!");
    }

    private static long parseTimeType(TimeType type, int back, String time){
        String parse;
        long l = 0;

            switch (type) {
                case SECOND:
                    parse = time.substring(0, time.length() - back);
                    l = Long.parseLong(parse);
                    return TimeUnit.SECONDS.toMillis(l);

                case MINUTE:
                    parse = time.substring(0, time.length() - back);
                    l = Long.parseLong(parse);
                    return TimeUnit.MINUTES.toMillis(l);

                case HOUR:
                    parse = time.substring(0, time.length() - back);
                    l = Long.parseLong(parse);
                    return TimeUnit.HOURS.toMillis(l);

                case DAY:
                    parse = time.substring(0, time.length() - back);
                    l = Long.parseLong(parse);
                    return TimeUnit.DAYS.toMillis(l);

                case WEEK:
                    parse = time.substring(0, time.length() - back);
                    l = Long.parseLong(parse);
                    return TimeUnit.DAYS.toMillis(l) * 7;

                case MONTH:
                    parse = time.substring(0, time.length() - back);
                    l = Long.parseLong(parse);
                    return TimeUnit.DAYS.toMillis(l) * 30;

                case YEAR:
                    parse = time.substring(0, time.length() - back);
                    l = Long.parseLong(parse);
                    return TimeUnit.DAYS.toMillis(l) * 365;

                default:
                    return l;
            }
    }

    private enum TimeType {
        SECOND,
        MINUTE,
        HOUR,
        DAY,
        WEEK,
        MONTH,
        YEAR,
    }
}
