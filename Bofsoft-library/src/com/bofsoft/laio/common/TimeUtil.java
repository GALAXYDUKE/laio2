package com.bofsoft.laio.common;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {

    public final static String FormatYMD = "yyyy-MM-dd";
    public final static String FormatYMD_HMS = "yyyy-MM-dd hh:mm:ss";
    public final static String FormatYMD_HM = "yyyy-MM-dd hh:mm";

    // yyyy-MM-dd hh:mm:ss

    /**
     * @param date1
     * @param date2
     * @return data1<date2 返回 负数
     */
    public static int compareTo(Date date1, Date date2) {
        int flag = 0;
        if (date1 == null || date2 == null) {
            return flag;
        }

        return date1.compareTo(date2);
    }

    public static String FormatDate(String formatStr, String date) {
        String dateString = "";
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            dateString = format.format(date);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static String FormatDate(String formatStr, Date date) {
        String dateString = "";
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            dateString = format.format(date);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static String FormatDate(String formatStr, Calendar calendar) {
        String dateString = "";
        try {
            dateString = DateFormat.format(formatStr, calendar).toString();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     * 字符串转Date
     *
     * @param date
     * @param format
     * @return
     */
    public static Date String2Date(String format, String dateStr) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);// 小写的mm表示的是分钟
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        return date;
    }

    /**
     * 字符串转Calendar
     *
     * @param date
     * @param format
     * @return
     */
    public static Calendar String2Calendar(String format, String dateStr) {
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);// 小写的mm表示的是分钟
        try {
            date = sdf.parse(dateStr);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            calendar = null;
        }
        return calendar;
    }

    /**
     * 取得当前日期是多少周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 得到某一年周的总数
     *
     * @param year
     * @return
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekOfYear(c.getTime());
    }

    /**
     * 得到某年某周的第一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Calendar getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);
        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);
        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 得到某年某周的最后一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Calendar getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);
        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);
        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 取得指定日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Calendar getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c;
    }

    /**
     * 取得指定日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Calendar getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c;
    }

    /**
     * 取得当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Calendar getFirstDayOfWeek() {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c;
    }

    /**
     * 取得当前日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Calendar getLastDayOfWeek() {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c;
    }

}
