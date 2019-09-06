package com.kayisoft.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ruopeng.cheng
 * @date 2018-6-26
 */
@SuppressWarnings("all")
public class DateUtil {


    /**
     * 日期格式
     */
    public static final String DATE_TIME_PATTERN_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_PATTERN_YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_PATTERN_YYYYMMDD = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN_YYYYMMDDHHMMSS_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String DATE_TIME_PATTERN_YYYYMMDD_CHINESE = "yyyy年MM月dd日";
    public static final String DATE_TIME_PATTERN_MMDDHHMM = "MM月dd日 HH:mm";


    /**
     * 日期格式化
     *
     * @param strTime
     * @param pattern
     * @return
     */
    public static Date convertStrToDate(String strTime, String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(strTime);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 日期格式化
     *
     * @param strTime
     * @param srcPattern
     * @param descPattern
     * @return String
     */
    public static String formatDate(String strTime, String srcPattern, String descPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(descPattern);

        try {
            return sdf.format(convertStrToDate(strTime, srcPattern));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 日期格式化
     *
     * @param date
     * @return (yyyy - MM - dd)
     */
    public static String formatDate(String date) {
        try {
            return formatDate(convertStrToDate(date, DATE_TIME_PATTERN_YYYYMMDD), DATE_TIME_PATTERN_YYYYMMDD);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 与当前日期大小的比较 ture:早于当前时间； false：不早于当前时间
     */
    public static boolean isPreNowDate(String time) {

        Date nowdate = new Date();
        Date inDate = convertStrToDate(time, DATE_TIME_PATTERN_YYYYMMDDHHMMSS);

        if (inDate.before(nowdate)) {
            return true;
        }

        return false;
    }

    /**
     * 与当前日期大小的比较 ture:早于当前时间； false：不早于当前时间
     */
    public static boolean isPreDate(String time) {

        Date inDate = convertStrToDate(time, DATE_TIME_PATTERN_YYYYMMDD);

        if (inDate.before(convertStrToDate(getCurrentDate(), DATE_TIME_PATTERN_YYYYMMDD))) {

            return true;
        }

        return false;
    }


    /**
     * 比较两个时间
     * 0 两者时间相等
     * 1 前者晚于后者
     * -1 前者早于后者
     */
    public static int compareDate(String time, String time2) {

        Date date1 = convertStrToDate(time, DATE_TIME_PATTERN_YYYYMMDD);
        Date date2 = convertStrToDate(time2, DATE_TIME_PATTERN_YYYYMMDD);
        if (date1.equals(date2)) {
            return 0;
        }

        if (date1.before(date2)) {
            return -1;
        } else {
            return 1;
        }

    }

    /**
     * 计算两个时间 差 天数
     */
    public static int dayDiff(String startTime, String endTime) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(startTime);
            two = df.parse(endTime);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            diff = time2 - time1;
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) days;

    }

    /**
     * timestamp取得
     */
    public static Timestamp getTimestamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }



    /**
     * 取得当前系统时间(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 取得当前系统时间
     *
     * @param pattern 时间格式
     * @return
     */
    public static String getCurrentDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
     * 取得当前系统时间去年(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static String getLastYearCurrentDate() {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -1);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);

    }

    /**
     * 取得当前系统时间上一个月(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static String getLastMonthCurrentDate() {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);

    }

    /**
     * 取得当前系统时间N个月后(yyyy-MM-dd)
     *
     * @param n 返回的月数
     * @return
     */
    public static String getFewMonthCurrentDate(int n) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, n);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);

    }

    /**
     * 取得当前系统时间N个月后(yyyy-MM-dd)
     *
     * @param n 返回的月数
     * @return
     */
    public static String getFewMonthCurrentDate(String dateTime, int n) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(convertStrToDate(dateTime, "yyyy-MM-dd"));
        calendar.add(Calendar.MONTH, n);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);

    }

    /**
     * 取得当前系统时间N天后(yyyy-MM-dd)
     *
     * @param n 返回的天数
     * @return
     */
    public static String getFewDaysCurrentDate(int n) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, n);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);

    }

    /**
     * 传入日期与当前日期N天前比较
     *
     * @param strDate
     * @param n
     */

    public static boolean isPreFewDaysCurrentDate(String strDate, int n) {

        DateFormat fmt = new SimpleDateFormat(DATE_TIME_PATTERN_YYYYMMDD);
        Date formDate = null;
        try {
            formDate = fmt.parse(getCurrentDate(DATE_TIME_PATTERN_YYYYMMDD));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar calendar = new GregorianCalendar();
        calendar.setTime(formDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1 * n);
        Date date = calendar.getTime();

        Date inDate = convertStrToDate(strDate, DATE_TIME_PATTERN_YYYYMMDD);

        if (inDate.before(date)) {
            return true;
        }

        return false;
    }


    /**
     * 取得传入时间的 n天前或后(yyyy-MM-dd HH:mm)
     *
     * @param strDate
     * @param n       正数为n天后 负数为n天前
     * @return
     */
    public static String getFewDaysCurrentDate(String strDate, int n) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date formDate = null;
        try {
            formDate = fmt.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(formDate);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);

    }

    /**
     * 取得传入时间 n 分钟前或后(yyyy-MM-dd HH:mm)
     *
     * @param strDate
     * @param n       正数 为 n分钟后 负数为n分钟前
     * @return String
     */
    public static String getFewMinutesCurrentDate(String strDate, int n) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date formDate = null;
        try {
            formDate = fmt.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(formDate);
        calendar.add(Calendar.MINUTE, n);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }


    /**
     * 取得传入时间的 n天前或后(yyyy-MM-dd)
     *
     * @param strDate
     * @param n       正数为n天后 负数为n天前
     * @return
     */
    public static String getFewDaysDate(String strDate, int n) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date formDate = null;
        try {
            formDate = fmt.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(formDate);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);

    }


    /**
     * 取得当前系统时间上一周(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */

    public static String getLastWeekCurrentDate() {

        return getFewDaysCurrentDate(-7);

    }

    /**
     * 取得当前系统时间上一天(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */

    public static String getYesterdayCurrentDate() {

        return getFewDaysCurrentDate(-1);

    }

    /**
     * 取得某个时间上一天(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */

    public static String getYesterdayDate(int n) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据 月份 获取今年对应月份的天数
     */
    public static int getDaysByMonth(int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据日期 找到对应日期的 星期
     */
    public static String getDayOfWeekByDate(String date) {
        String dayOfweek = "-1";
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = myFormatter.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String str = formatter.format(myDate);
            dayOfweek = str;

        } catch (Exception e) {
            System.out.println("错误!");
        }
        return dayOfweek;
    }

    /**
     * 获取年份
     *
     * @param appYear
     * @return Integer 传入0 为当前年份 -1 为去年 +1 为明年
     */
    public static Integer getCurrentYear(int appYear) {
        Calendar a = Calendar.getInstance();
        // 得到年
        return a.get(Calendar.YEAR) + appYear;
    }

    /**
     * 获取两个时间的月份差
     *
     * @param date1
     * @param date2
     * @throws ParseException void
     */
    public static int monthDiff(String date1, String date2) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(date1));
            c2.setTime(sdf.parse(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 12 * (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) + c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
//        System.out.println(Math.abs(result)+1+"="+getFewMonthCurrentDate("2016-10-02 00:12:15",1));
    }

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     *
     * @param beginDate
     * @param endDate
     * @return List
     */
    public static List<String> getDatesBetweenTwoDate(String beginDate, String endDate) {
        List<String> lDate = new ArrayList<String>();
        Date start = convertStrToDate(beginDate, "yy-MM-dd");
        Date end = convertStrToDate(endDate, "yy-MM-dd");
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(start);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            if (end.after(cal.getTime())) {
                lDate.add(cal.get(Calendar.MONTH) + 1 + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日");
            } else {
                break;
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后

        }
        // 把结束时间加入集合
        lDate.add(cal.get(Calendar.MONTH) + 1 + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日");
        return lDate;
    }

}
