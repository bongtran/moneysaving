package com.bongtran.moneysaving.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {
    public static final Calendar FIRST_DAY_OF_TIME;
    public static final Calendar LAST_DAY_OF_TIME;
    public static final int DAYS_OF_TIME;
    public static final int MONTHS_OF_TIME;

    static {
        FIRST_DAY_OF_TIME = Calendar.getInstance();
        FIRST_DAY_OF_TIME.set(Calendar.getInstance().get(Calendar.YEAR) - 100, Calendar.JANUARY, 1);
        LAST_DAY_OF_TIME = Calendar.getInstance();
//        if(new Prefs().getServerTime()!=0)
//        {
//            long temp = new Prefs().getServerTime()+ Util.getTimeOffsetInMilis();
//            LAST_DAY_OF_TIME.setTimeInMillis(temp);
//        }
//        else
//        {
//            long temp = LAST_DAY_OF_TIME.getTimeInMillis()+ Util.getTimeOffsetInMilis();
//            LAST_DAY_OF_TIME.setTimeInMillis(temp);
//        }
        LAST_DAY_OF_TIME.set(Calendar.getInstance().get(Calendar.YEAR) + 100, Calendar.DECEMBER, 31);
//        LAST_DAY_OF_TIME.add(Calendar.DATE,1);
        DAYS_OF_TIME = (int) Math.ceil((LAST_DAY_OF_TIME.getTimeInMillis() - FIRST_DAY_OF_TIME.getTimeInMillis() + Utils.getTimeOffsetInMilis()) / (24 * 60 * 60 * 1000));
        MONTHS_OF_TIME = (LAST_DAY_OF_TIME.get(Calendar.YEAR) - FIRST_DAY_OF_TIME.get(Calendar.YEAR)) * 12 + LAST_DAY_OF_TIME.get(Calendar.MONTH) - FIRST_DAY_OF_TIME.get(Calendar.MONTH) + 1;
    }

    public static int getPositionForMonth(Calendar month) {
        if (month != null) {
            int diffYear = month.get(Calendar.YEAR) - FIRST_DAY_OF_TIME.get(Calendar.YEAR);
            return diffYear * 12 + month.get(Calendar.MONTH) - FIRST_DAY_OF_TIME.get(Calendar.MONTH);
        }
        return 0;
    }

    public static Calendar getMonthForPosition(int position) throws IllegalArgumentException {
        if (position < 0) {
            throw new IllegalArgumentException("position cannot be negative");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(FIRST_DAY_OF_TIME.getTimeInMillis());
        cal.add(Calendar.YEAR, position / 12);
        cal.add(Calendar.MONTH, position % 12);
        return cal;
    }

    public static Calendar getDayForPosition(int position) throws IllegalArgumentException {
        if (position < 0) {
            throw new IllegalArgumentException("position cannot be negative");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(FIRST_DAY_OF_TIME.getTimeInMillis());
        cal.add(Calendar.DAY_OF_YEAR, position);
        return cal;
    }

    public static int getPositionForDay(Calendar day) {
        if (day != null) {
            return (int) ((day.getTimeInMillis() + Utils.getTimeOffsetInMilis() - FIRST_DAY_OF_TIME.getTimeInMillis())
                    / 86400000  //(24 * 60 * 60 * 1000)
            );
        }
        return 0;
    }


    public static int getCurrentMonthPosition() {
        return 0;
    }

    public static String getFormattedDate(long date) {
        final String defaultPattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(defaultPattern, Locale.getDefault());
        return simpleDateFormat.format(new Date(date));
    }

    public static String getFormattedMonth(long date) {
        final String defaultPattern = "yyyy-MM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(defaultPattern, Locale.getDefault());
        return simpleDateFormat.format(new Date(date));
    }

    public static String getFormattedTime(long date, String defaultPattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(defaultPattern, Locale.getDefault());
        return simpleDateFormat.format(new Date(date));
    }

    public static String getFormattedTimeWithoutTimeZone(long date, String defaultPattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(defaultPattern, Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(new Date(date));
    }

    public static String getTimezoneOffsetInMinutes() {
        TimeZone tz = TimeZone.getDefault();
        int offsetMinutes = tz.getRawOffset() / 60000;
        String sign = "";
        if (offsetMinutes < 0) {
            sign = "-";
            offsetMinutes = -offsetMinutes;
        }

        return sign + "" + offsetMinutes;
    }

    public static boolean isCurrentDate(long millisecond) {
        boolean result = false;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(millisecond);

        result = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

        return result;
    }

    public static Calendar getCalendarDay(int dayDistance) {

        Calendar result = Calendar.getInstance();

        result.add(Calendar.DAY_OF_YEAR, -dayDistance);
        result.set(result.get(Calendar.YEAR), result.get(Calendar.MONTH), result.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

//        Log.d(">>>>> getCalendarDay ", result.toString());
        return result;
    }

    public static Calendar getCalendarDayFromDay(long day) {
        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(day);

        result.set(result.get(Calendar.YEAR), result.get(Calendar.MONTH), result.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

//        Log.d(">>>>> getCalendarDay ", result.toString());
        return result;
    }

    public static Calendar getCalendar(int monthDistance) {

        Calendar result = Calendar.getInstance();

//        int year = result.get(Calendar.YEAR);
//        int month = result.get(Calendar.MONTH);
//
//        if (monthDistance > month) {
//            month = 12 + month - monthDistance;
//            year = year - 1;
//        } else {
//            month = month - monthDistance;
//        }
//
//        if(month > 11){
//            month = month % 11;
//            year += month / 11;
//        }
//
//        if(month < 0){
//            month = -month;
//            month = month % 11;
//            year -= month / 11;
//        }
//
//        Log.d(">>>> Calendar: ", String.format("%d %d %d", monthDistance, year, month));

//        result.set(year, month, 1, 0, 0, 0);

        result.add(Calendar.MONTH, -monthDistance);
        result.set(result.get(Calendar.YEAR), result.get(Calendar.MONTH), 1, 0, 0, 0);
        return result;
    }

    public static Calendar getCalendar(int yearDistance, int monthDistance) {
        Calendar result = Calendar.getInstance();
//
//        int year = result.get(Calendar.YEAR);
//        int month = result.get(Calendar.MONTH);
//        Log.d(">>>> Calendar: ", String.format("%d %d %d %d", yearDistance, monthDistance, year, month));
////        if (monthDistance >= month) {
////            month = 12 + month - monthDistance;
////            year = year - 1;
////        } else {
////            month = month - monthDistance;
////        }
////
////        if(month > 11){
////            month = month % 11;
////            year += month / 11;
////        }
////
////        if(month < 0){
////            month = -month;
////            month = month % 11;
////            year -= month / 11;
////        }
////        month = month - monthDistance;
//        year = year - yearDistance;
//
//
//        if (monthDistance > month) {
//            month = 12 + month - monthDistance;
//            year = year - 1;
//        }else {
//            month = month - monthDistance;
//        }
//        Log.d(">>>> Calendar: ", String.format("%d %d %d %d", yearDistance, monthDistance, year, month));

//        result.set(year, month, 1, 0, 0, 0);

        result.add(Calendar.YEAR, -yearDistance);
        result.add(Calendar.MONTH, -monthDistance);
        result.set(result.get(Calendar.YEAR), result.get(Calendar.MONTH), 1, 0, 0, 0);

        return result;
    }

    public static Calendar getLocalCalendar(long time) {
        int monthDistance = 0;
        int yearDistance = 0;

        Calendar now = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        monthDistance = now.get(Calendar.MONTH) - calendar.get(Calendar.MONTH);
        yearDistance = now.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);

//        if(monthDistance != 0){
//            Log.d(">>>>> ", "" + monthDistance);
//        }

        return getCalendar(yearDistance, monthDistance);
    }

    public static long roundSecond(long time) {
        return (time / 1000) * 1000;
    }

    public static int getTimeZoneOffset() {
        return TimeZone.getDefault().getRawOffset() / 1000 / 60;
    }

    public static String displayTimeWithoutOffset(String birthDate) {
        String result = "";
        try {
            String timeString;
            timeString = birthDate.substring(birthDate.indexOf('(') + 1, birthDate.indexOf('+'));
            Date date = new Date(Long.parseLong(timeString));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            result = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static long getFirstTimeOfThisMonth(){
        long result = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);
        result = roundSecond(calendar.getTimeInMillis());
        return result;
    }
}