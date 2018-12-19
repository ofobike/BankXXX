package alibaba.utils;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作的工具类
 */
public class MyDataUtils {
    public static final String YYYY_YEAR_MM_MONTH_DD_DATE = "yyyy年MM月dd日";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_BIAS_MM_BIAS_DD = "yyyy/MM/dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY = "yyyy";
    public static final String MM = "MM";
    public static final String DD = "dd";
    public static final String HH = "HH";
    public static final String MI = "mm";
    public static final String SS = "ss";
    public static final String SIMPLE_YYYY_MM_DD = "yyyyMMdd";

    /**
     * 构造方法
     */
    public MyDataUtils() {

    }

    /**
     * 把日期字符串格式为date
     *
     * @param result
     * @return
     */
    public static Date getDateFromString(String result) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
            Date date = sdf.parse(result);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把传入的日期转为指定的日期字符串
     *
     * @param date
     * @param str
     * @return
     */
    public static String getDateStr(Date date, String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        return sdf.format(date);
    }

    /**
     * 根据日期获取制定格式的字符串
     *
     * @param date
     * @return
     */
    public static String getyyyy_MM_dd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return sdf.format(date);
    }

    /**
     * 把Date转为Calendar
     *
     * @param date
     * @return
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance;
    }

    /**
     * 对当前date类型的时间做天的加减操作
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDayToDate(Date date, int day) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 对当前date类型的时间做月的加减操作
     *
     * @param date
     * @return
     */
    public static Date addMonthToDate(Date date, int month) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 对当前date类型的时间做年的加减操作
     *
     * @param date
     * @return
     */
    public static Date addYearToDate(Date date, int year) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 对当前date类型的时间做周的加减操作
     *
     * @param date
     * @return
     */
    public static Date addWeekToDate(Date date, int week) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.WEEK_OF_MONTH, week);
        return calendar.getTime();
    }

    /**
     * 获取两个时间之间差
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDiffDays(Date startDate, Date endDate) {
        long days = 0;
        if (startDate.after(endDate)) {
            Date temp = startDate;
            startDate = endDate;
            endDate = temp;
        }
        days = (endDate.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24;
        return days;
    }


    /**
     * 获取每个月有多少天
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能：判断日期是否和当前date对象在同一天。
     *
     * @return boolean 如果在返回true，否则返回false。
     */
    public static boolean isSameDay(String day) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        int year1 = pre.get(Calendar.YEAR);
        int month1 = pre.get(Calendar.MONTH) + 1;
        int day1 = pre.get(Calendar.DAY_OF_MONTH);


        Calendar cal = Calendar.getInstance();
        Date date = MyDataUtils.getDateFromString(day);
        cal.setTime(date);
        int year2 = cal.get(Calendar.YEAR);
        int month2 = cal.get(Calendar.MONTH) + 1;
        int day2 = cal.get(Calendar.DAY_OF_MONTH);


        if (year1 == year2 && month1 == month2 && day1 == day2) {
            return true;
        }

        return false;
    }
    /**
     * 日期类的比较大小
     */
    public static void compareDate(String str,String str1){
        Date date = MyDataUtils.getDateFromString(str);
        Date date1 = MyDataUtils.getDateFromString(str1);
        if (date.before(date1)){
            System.out.println("最大日期是str1:"+str1);
        }else if (date.after(date1)){
            System.out.println("最大日期是str:"+str);
        }else if (date.equals(date1)){
            System.out.println("两个日期相等");
        }
    }

}
