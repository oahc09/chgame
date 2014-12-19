
package com.oahcfly.chgame.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CHDateUtil {

    /** 格式化字符串格式 */
    public static final String DATEFORMATSHORT = "yyyy-MM-dd";

    public static final String DATEFORMATINT = "yyyyMMdd";

    public static final String FORMAT_HMS = "HH:mm:ss";

    public static final String FORMAT_TIME = "HH:mm";

    public static final String DATEFORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    public static final String DATEFORMAT_LONG2 = "yyyy/MM/dd HH:mm:ss";

    public static final String DATEFORMAT_MIN = "MM-dd HH:mm";

    public static final String TIME_FORMART = "00:00:00";

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMATSHORT);
        return sdf.format(date);
    }

    /**
     * 秒转换成hh:mm:ss格式
     * 
     * @param times
     * @return
     */
    public static String millisToTimeFormat(int times) {
        // GregorianCalendar gc = new GregorianCalendar();
        // gc.setTimeInMillis(times * 1000);
        long ms = times * 1000L;
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_HMS);
        return format.format(ms);
    }

    /**
     * 将毫秒值装换为 "kk:mm:ss" 的格式
     * 
     * @param timeMillis
     * @return
     */
    public static String changeMillisToTime(long timeMillis) {
        String timeString = "";
        timeMillis = timeMillis / 1000;
        int hours = (int)(timeMillis / 60 / 60);
        if (hours < 10) {
            timeString += "0" + hours + ":";
        } else {
            timeString += hours + ":";
        }

        int minutes = (int)((timeMillis - hours * 60 * 60) / 60);
        if (minutes < 10) {
            timeString += "0" + minutes + ":";
        } else {
            timeString += minutes + ":";
        }

        int seconds = (int)(timeMillis % 60);
        if (seconds < 10) {

            timeString += "0" + seconds;
        } else {
            timeString += seconds;
        }

        return timeString;
    }

    public static String changeMillisToDayTime(long timeMillis) {
        String timeString = "";
        timeMillis = timeMillis / 1000;
        int days = (int)(timeMillis / 60 / 60 / 24);
        if (days > 0) {
            if (days < 10) {
                timeString += "0" + days + "-";
            } else {
                timeString += days + "-";
            }
        }
        int dayTime = days * 24 * 60 * 60;
        int hours = (int)((timeMillis - dayTime) / 60 / 60);
        if (hours < 10) {
            timeString += "0" + hours + ":";
        } else {
            timeString += hours + ":";
        }

        int minutes = (int)((timeMillis - dayTime - hours * 60 * 60) / 60);
        if (minutes < 10) {
            timeString += "0" + minutes + ":";
        } else {
            timeString += minutes + ":";
        }

        int seconds = (int)(timeMillis % 60);
        if (seconds < 10) {

            timeString += "0" + seconds;
        } else {
            timeString += seconds;
        }

        return timeString;
    }

    /**
     * 样式 nDay hh:mm:ss
     * @param timeMillis
     * @return
     */
    public static String changeMillisToDayTime2(long timeMillis) {
        String timeString = "";
        timeMillis = timeMillis / 1000;
        int days = (int)(timeMillis / 60 / 60 / 24);
        if (days > 0) {
            timeString += days + " Day ";
        } else {
            timeString += 0 + " Day ";
        }
        int dayTime = days * 24 * 60 * 60;
        int hours = (int)((timeMillis - dayTime) / 60 / 60);
        if (hours < 10) {
            timeString += "0" + hours + ":";
        } else {
            timeString += hours + ":";
        }

        int minutes = (int)((timeMillis - dayTime - hours * 60 * 60) / 60);
        if (minutes < 10) {
            timeString += "0" + minutes + ":";
        } else {
            timeString += minutes + ":";
        }

        int seconds = (int)(timeMillis % 60);
        if (seconds < 10) {

            timeString += "0" + seconds;
        } else {
            timeString += seconds;
        }

        return timeString;
    }

    /**
     * 取得当天事件yyyy-MM-dd
     * 
     * @return
     */
    public static String getTodayDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 取得当天事件yyyyMMdd
     * 
     * @return
     */
    public static int getTodayDayInt() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATEFORMATINT);
        // 获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        return Integer.valueOf(formatter.format(curDate));
    }

    /**
     * 取得两个时间段的间隔天数
     * 
     * @author color
     * @param t1
     *            时间1
     * @param t2
     *            时间2
     * @return t2 与t1的间隔天数
     * @throws ParseException
     *             如果输入的日期格式不是0000-00-00 格式抛出异常
     */
    public static int getBetweenDays(String t1, String t2) {
        int betweenDays = 0;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = format.parse(t1);
            Date d2 = format.parse(t2);
            betweenDays = (int)((d2.getTime() - d1.getTime()) / 86400000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return betweenDays;
    }

    /**
     * 取得当前时间戳
     * 
     * @return
     */
    public static long getTimestamp() {
        long time = Calendar.getInstance().getTimeInMillis();
        return time;
    }

    /**
     * 将Unix时间戳转换成指定格式日期
     * 
     * @param timestampString
     * @param formats
     * @return
     */
    public static String TimeStampDate(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));
        return date;
    }

    /**
     * 将Unix时间戳转换成指定格式日期
     * 
     * @param timestampString
     * @param formats
     * @return
     */
    public static String stampTime(String timestampString, String formats) {
        String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(Long.valueOf(timestampString)));
        return date;
    }

    public static int getIntTime(String time) {
        Double longTime = Double.valueOf(time);
        Double.parseDouble(time.toString());
        DecimalFormat df = new DecimalFormat("#");
        int updradeTime = Integer.valueOf(df.format(longTime));
        return updradeTime;

    }

    /**
     * 获得1天离线时，需要提示玩家的剩余时间
     * @return
     */
    public static int getAFKOneDayTimeMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 22 && hour <= 24) {
            // [22, 24] 区间
            return 24 - (hour - 22);
        } else {
            // [0, 22] 区间
            return (22 - hour) + 24;
        }
    }

    // 将字符串转为时间戳 
    public static long getTimeMillis(String user_time) {
        long re_time = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_LONG);
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            String time = str.substring(0, 10);
            re_time = Long.parseLong(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    // 将字符串转为时间戳 
    public static long getTimeMillis(String user_time, String format) {
        long re_time = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            String time = str.substring(0, 10);
            re_time = Long.parseLong(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    /**
     * 毫秒转日期字符串
     * 
     * @param str
     * @return
     */
    public static String getDateTimeByMillisecond(long str) {
        Date date = new Date(str);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        return time;
    }

    public static String getDateTimeByMillisecond(long str, String timeformat) {
        Date date = new Date(str);
        SimpleDateFormat format = new SimpleDateFormat(timeformat);
        String time = format.format(date);
        return time;
    }

    public final static String YYYY = "yyyy";

    public final static String MM = "MM";

    public final static String DD = "dd";

    public final static String YYYY_MM_DD = "yyyy-MM-dd";

    public final static String YYYY_MM = "yyyy-MM";

    public final static String HH_MM_SS = "HH:mm:ss";

    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String formatStr_yyyyMMddHHmmssS = "yyyy-MM-dd HH:mm:ss.S";

    public static String formatStr_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    public static String formatStr_yyyyMMddHHmm = "yyyy-MM-dd HH:mm";

    public static String formatStr_yyyyMMddHH = "yyyy-MM-dd HH";

    public static String formatStr_yyyyMMdd = "yyyy-MM-dd";

    public static String[] formatStr = {
            formatStr_yyyyMMddHHmmss, formatStr_yyyyMMddHHmm, formatStr_yyyyMMddHH, formatStr_yyyyMMdd
    };

    /**
     * 日期格式化－将<code>Date</code>类型的日期格式化为<code>String</code>型
     * 
     * @param date 待格式化的日期
     * @param pattern 时间样式
     * @return 一个被格式化了的<code>String</code>日期
     */
    public static String format(Date date, String pattern) {
        if (date == null)
            return "";
        else
            return getFormatter(pattern).format(date);
    }

    /**
     * 默认把日期格式化成yyyy-mm-dd格式
     * 
     * @param date
     * @return
     */
    public static String format(Date date) {
        if (date == null)
            return "";
        else
            return getFormatter(YYYY_MM_DD).format(date);
    }

    /**
     * 把字符串日期默认转换为yyyy-mm-dd格式的Data对象
     * 
     * @param strDate
     * @return
     */
    public static Date format(String strDate) {
        Date d = null;
        if ("".equals(strDate))
            return null;
        else
            try {
                d = getFormatter(YYYY_MM_DD).parse(strDate);
            } catch (ParseException pex) {
                return null;
            }
        return d;
    }

    /**
     * 把字符串日期转换为f指定格式的Data对象
     * 
     * @param strDate ,f
     * @return
     */
    public static Date format(String strDate, String f) {
        Date d = null;
        if ("".equals(strDate))
            return null;
        else
            try {
                d = getFormatter(f).parse(strDate);
            } catch (ParseException pex) {
                return null;
            }
        return d;
    }

    /**
     * 日期解析－将<code>String</code>类型的日期解析为<code>Date</code>型
     * 
     * @param date 待格式化的日期
     * @param pattern 日期样式
     * @exception ParseException 如果所给的字符串不能被解析成一个日期
     * @return 一个被格式化了的<code>Date</code>日期
     */
    public static Date parse(String strDate, String pattern) throws ParseException {
        try {
            return getFormatter(pattern).parse(strDate);
        } catch (ParseException pe) {
            throw new ParseException("Method parse in Class DateUtils err: parse strDate fail.", pe.getErrorOffset());
        }
    }

    /**
     * 获取当前日期
     * 
     * @return 一个包含年月日的<code>Date</code>型日期
     */
    public static Date getCurrDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 获取当前日期
     * @param millis 毫秒数
     * @return 一个包含年月日的<code>Date</code>型日期
     */
    public static Date getDate(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 获取当前日期
     * 
     * @return 一个包含年月日的<code>String</code>型日期，但不包含时分秒。yyyy-mm-dd
     */
    public static String getCurrDateStr() {
        return format(getCurrDate(), YYYY_MM_DD);
    }

    /**
     * 获取当前时间
     * 
     * @return 一个包含年月日时分秒的<code>String</code>型日期。hh:mm:ss
     */
    public static String getCurrTimeStr() {
        return format(getCurrDate(), HH_MM_SS);
    }

    /**
     * 获取当前完整时间,样式: yyyy－MM－dd hh:mm:ss
     * 
     * @return 一个包含年月日时分秒的<code>String</code>型日期。yyyy-MM-dd hh:mm:ss
     */
    public static String getCurrDateTimeStr() {
        return format(getCurrDate(), YYYY_MM_DD_HH_MM_SS);
    }

    public static String getCurrDateTimeStr(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        Date date = calendar.getTime();
        return format(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前年分 样式：yyyy
     * 
     * @return 当前年分
     */
    public static String getYear() {
        return format(getCurrDate(), YYYY);
    }

    /**
     * 获取当前月分 样式：MM
     * 
     * @return 当前月分
     */
    public static String getMonth() {
        return format(getCurrDate(), MM);
    }

    /**
     * 获取当前日期号 样式：dd
     * 
     * @return 当前日期号
     */
    public static String getDay() {
        return format(getCurrDate(), DD);
    }

    /**
     * 获取当前星期几
     * @return 
     */
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return 1;
            case Calendar.TUESDAY:
                return 2;
            case Calendar.WEDNESDAY:
                return 3;
            case Calendar.THURSDAY:
                return 4;
            case Calendar.FRIDAY:
                return 5;
            case Calendar.SATURDAY:
                return 6;
            case Calendar.SUNDAY:
                return 7;
            default:
                return 0;
        }
    }

    /**
     * 按给定日期样式判断给定字符串是否为合法日期数据
     * 
     * @param strDate 要判断的日期
     * @param pattern 日期样式
     * @return true 如果是，否则返回false
     */
    public static boolean isDate(String strDate, String pattern) {
        try {
            parse(strDate, pattern);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式日期（包括：年月日yyyy-MM-dd）数据
     * 
     * @param strDate 要判断的日期
     * @return true 如果是，否则返回false
     */
    // public static boolean isDate(String strDate) {
    // try {
    // parse(strDate, YYYY_MM_DD);
    // return true;
    // }
    // catch (ParseException pe) {
    // return false;
    // }
    // }

    /**
     * 判断给定字符串是否为特定格式年份（格式：yyyy）数据
     * 
     * @param strDate 要判断的日期
     * @return true 如果是，否则返回false
     */
    public static boolean isYYYY(String strDate) {
        try {
            parse(strDate, YYYY);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    public static boolean isYYYY_MM(String strDate) {
        try {
            parse(strDate, YYYY_MM);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式的年月日（格式：yyyy-MM-dd）数据
     * 
     * @param strDate 要判断的日期
     * @return true 如果是，否则返回false
     */
    public static boolean isYYYY_MM_DD(String strDate) {
        try {
            parse(strDate, YYYY_MM_DD);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式年月日时分秒（格式：yyyy-MM-dd HH:mm:ss）数据
     * 
     * @param strDate 要判断的日期
     * @return true 如果是，否则返回false
     */
    public static boolean isYYYY_MM_DD_HH_MM_SS(String strDate) {
        try {
            parse(strDate, YYYY_MM_DD_HH_MM_SS);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式时分秒（格式：HH:mm:ss）数据
     * 
     * @param strDate 要判断的日期
     * @return true 如果是，否则返回false
     */
    public static boolean isHH_MM_SS(String strDate) {
        try {
            parse(strDate, HH_MM_SS);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式时间（包括：时分秒hh:mm:ss）数据
     * 
     * @param strTime 要判断的时间
     * @return true 如果是，否则返回false
     */
    // public static boolean isTime(String strTime) {
    // try {
    // parse(strTime, HH_MM_SS);
    // return true;
    // }
    // catch (ParseException pe) {
    // return false;
    // }
    // }

    /**
     * 判断给定字符串是否为特定格式日期时间（包括：年月日时分秒 yyyy-MM-dd hh:mm:ss）数据
     * 
     * @param strDateTime 要判断的日期时间
     * @return true 如果是，否则返回false
     */
    // public static boolean isDateTime(String strDateTime) {
    // try {
    // parse(strDateTime, YYYY_MM_DD_HH_MM_SS);
    // return true;
    // }
    // catch (ParseException pe) {
    // return false;
    // }
    // }

    /**
     * 获取一个简单的日期格式化对象
     * 
     * @return 一个简单的日期格式化对象
     */
    private static SimpleDateFormat getFormatter(String parttern) {
        return new SimpleDateFormat(parttern);
    }

    /**
     * 获取给定日前的后intevalDay天的日期
     * 
     * @param refenceDate 给定日期（格式为：yyyy-MM-dd）
     * @param intevalDays 间隔天数
     * @return 计算后的日期
     */
    public static String getNextDate(String refenceDate, int intevalDays) {
        try {
            return getNextDate(parse(refenceDate, YYYY_MM_DD), intevalDays);
        } catch (Exception ee) {
            return "";
        }
    }

    /**
     * 获取给定日前的后intevalDay天的日期
     * 
     * @param refenceDate Date 给定日期
     * @param intevalDays int 间隔天数
     * @return String 计算后的日期
     */
    public static String getNextDate(Date refenceDate, int intevalDays) {
        try {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(refenceDate);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + intevalDays);
            return format(calendar.getTime(), YYYY_MM_DD);
        } catch (Exception ee) {
            return "";
        }
    }

    /**
     * 得到几天前的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public static long getIntevalDays(String startDate, String endDate) {
        try {
            return getIntevalDays(parse(startDate, YYYY_MM_DD), parse(endDate, YYYY_MM_DD));
        } catch (Exception ee) {
            return 0l;
        }
    }

    public static long getIntevalDays(Date startDate, Date endDate) {
        try {
            java.util.Calendar startCalendar = java.util.Calendar.getInstance();
            java.util.Calendar endCalendar = java.util.Calendar.getInstance();

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);
            long diff = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();

            return (diff / (1000 * 60 * 60 * 24));
        } catch (Exception ee) {
            return 0l;
        }
    }

    /**
     * 求当前日期和指定字符串日期的相差天数
     * 
     * @param startDate
     * @return
     */
    public static long getTodayIntevalDays(String startDate) {
        try {
            // 当前时间
            Date currentDate = new Date();

            // 指定日期
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date theDate = myFormatter.parse(startDate);

            // 两个时间之间的天数
            long days = (currentDate.getTime() - theDate.getTime()) / (24 * 60 * 60 * 1000);

            return days;
        } catch (Exception ee) {
            return 0l;
        }
    }

    public static Date parseToDate(String dateTimeStr) {
        if (dateTimeStr == null)
            return null;
        Date d = null;
        int formatStrLength = formatStr.length;
        for (int i = 0; i < formatStrLength; i++) {
            d = parseToDate2(dateTimeStr, formatStr[i]);
            if (d != null) {
                break;
            }
        }
        return d;
    }

    private static Date parseToDate2(String dateTimeStr, String formatString) {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        try {
            d = sdf.parse(dateTimeStr);
        } catch (ParseException pe) {

        }
        return d;
    }

    public static String dateTimeToString(Date datetime) {
        // dateTime=dateTime.substring(0,4)+dateTime.substring(5,7)+dateTime.substring(8,10)+dateTime.substring(11,13)+dateTime.substring(14,16)+dateTime.substring(17,19);
        // return dateTime;

        java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
        calendar.setTime(datetime);
        String dateTime = calendar.get(Calendar.YEAR) + "" + (calendar.get(Calendar.MONTH) + 1 > 9 ? "" : "0")
                + (calendar.get(Calendar.MONTH) + 1) + "" + (calendar.get(Calendar.DATE) > 9 ? "" : "0")
                + calendar.get(Calendar.DATE) + "" + (calendar.get(Calendar.HOUR_OF_DAY) > 9 ? "" : "0")
                + calendar.get(Calendar.HOUR_OF_DAY) + "" + (calendar.get(Calendar.MINUTE) > 9 ? "" : "0")
                + calendar.get(Calendar.MINUTE) + "" + (calendar.get(Calendar.SECOND) > 9 ? "" : "0")
                + calendar.get(Calendar.SECOND);
        return dateTime;
    }

    /**
     * 由年、月份，获得当前月的最后一天
     * 
     * @param year month 月份 01 02 11 12
     * @return
     * @throws ParseException
     */
    public static String getLastDayOfMonth(String year, String month) throws ParseException {
        String LastDay = "";
        Calendar cal = Calendar.getInstance();
        Date date_;
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-14");
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        date_ = cal.getTime();
        LastDay = new SimpleDateFormat("yyyy-MM-dd").format(date_);
        return LastDay;
    }

    /**
     *比较    date1,date2先后
     * @param date1 (yyyy-mm-dd)
     * @param date2  (yyyy-mm-dd)
     * @return 1: date1(后:例2014-01-24)>date2(先:例2014-01-23)  
     *          -1:date1(先:例2014-01-23)<date2(后:例2014-01-24) 
     *          0:date1=date2
     */
    public static int compareDate(String date1, String date2) {
        int result = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                result = 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                result = -1;
            } else {
                result = 0;
            }
        } catch (Exception exception) {
            result = 0;
        }
        return result;
    }

    /**
     * @param data 格式："2012-09-4 18:00:00"
     * @return 返回秒数：1346752800
     */
    public static long StringToSeconds(String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(data);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
        return date.getTime() / 1000;
    }

    /**
     * 
     * <pre>
     * 当前时间距离下次更新CD 【适用于每日刷新多次】
     * 
     * 例：每隔6小时刷新1次，当前时间4点，则返回2*3600s
     * 【刷新时间点：0点，6点，12点，18点】
     * date: 2014-7-9
     * </pre>
     * @author caohao
     * @param intervalHour 间隔时间
     * @return
     */
    public static int getNextUpdateCD(int intervalHour) {
        Calendar nowCalendar = Calendar.getInstance();
        Calendar targetCalendar = Calendar.getInstance();
        int result = nowCalendar.get(Calendar.HOUR_OF_DAY) / intervalHour * intervalHour;
        if (nowCalendar.get(Calendar.HOUR_OF_DAY) < result) {
            targetCalendar.set(Calendar.HOUR_OF_DAY, result);
        } else {
            targetCalendar.set(Calendar.HOUR_OF_DAY, result + intervalHour);
        }

        targetCalendar.set(Calendar.MINUTE, 0);
        targetCalendar.set(Calendar.SECOND, 0);
        targetCalendar.set(Calendar.MILLISECOND, 0);

        return (int)((targetCalendar.getTimeInMillis() - nowCalendar.getTimeInMillis()) / 1000);
    }

    /**
     * 
     * <pre>
     * 当前时间距离下次更新CD【适用于每日只刷新1次】
     * 
     * 例：每天12点固定刷新，当前时间11点，则返回3600s
     * 
     * date: 2014-7-9
     * </pre>
     * @author caohao
     * @param updateHour 固定更新的时间点
     * @return
     */
    public static int getNextUpdateCD2(int updateHour) {
        Calendar nowCalendar = Calendar.getInstance();
        Calendar targetCalendar = Calendar.getInstance();
        if (targetCalendar.get(Calendar.HOUR_OF_DAY) >= updateHour) {
            targetCalendar.add(Calendar.DATE, 1);
        }
        targetCalendar.set(Calendar.HOUR_OF_DAY, updateHour);
        targetCalendar.set(Calendar.MINUTE, 0);
        targetCalendar.set(Calendar.SECOND, 0);
        targetCalendar.set(Calendar.MILLISECOND, 0);
        return (int)(targetCalendar.getTimeInMillis() / 1000 - nowCalendar.getTimeInMillis() / 1000);
    }

}
