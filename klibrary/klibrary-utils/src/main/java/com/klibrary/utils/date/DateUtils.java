package com.klibrary.utils.date;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final long MINUTE = 1000 * 60;
    public static final long HOUR = 1000 * 60 * 60;
    public static final long DAY = 1000 * 60 * 60 * 24;
    public static final long QUARTER_HOUR = 1000 * 60 * 15;

    public static Date getDayOfDate(Date date) {
        return new Date(date.getTime() - date.getTime() % DAY);
    }

    public static Date getHourOfDate(Date date) {
        return new Date(date.getTime() - date.getTime() % HOUR);
    }

    public static Date getQuarterHourOfDate(Date date) {
        return new Date(date.getTime() - date.getTime() % QUARTER_HOUR);
    }

    public static Date addDay(Date date, int num) {
        return new Date(date.getTime() + DAY * num);
    }

    public static Date addHour(Date date, int num) {
        return new Date(date.getTime() + (HOUR * num));
    }

    public static Date addQuarterHour(Date date, int num) {
        return new Date(date.getTime() + (QUARTER_HOUR * num));
    }

    public static Date addMonth(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, num);
        return c.getTime();
    }

    public static Date addYear(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, num);
        return c.getTime();
    }

    public static Date getMonthOfDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        truncateToMonth(c);

        return c.getTime();
    }

    public static Date getQuarterOfDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        truncateToMonth(c);

        int month = c.get(Calendar.MONTH);
        c.add(Calendar.MONTH, -(month % 3));

        return c.getTime();
    }

    public static Date getYearOfDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        truncateToMonth(c);
        c.set(Calendar.MONTH, 0);

        return c.getTime();
    }

    private static void truncateToMonth(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    public static int difMinutes(Date lastDate, Date firstDate ) {
        return getTodayMinutesAsOfNow(lastDate)- (getTodayMinutesAsOfNow(firstDate));
    }

    public static int getTodayMinutesAsOfNow(Date date) {
        long result =date.getTime()/60000;
        return (int)  result;
    }

    private static Calendar truncateToMonth(Date date) {
        Calendar c = truncateToDay(date);
        c.set(Calendar.DAY_OF_MONTH, 1);

        return c;
    }

    private static Calendar truncateToDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c;
    }

    public static Date minusMinutes(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -amount);
        return calendar.getTime();
    }

    public static Date minusHours(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -amount);
        return calendar.getTime();
    }


    public static Date zeroMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();

    }

    public static Date getMinuteOfDate(Date date) {
        return new Date(date.getTime() - date.getTime() % MINUTE);
    }

}
