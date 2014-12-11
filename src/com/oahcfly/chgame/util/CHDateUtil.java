
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
    public static String todayDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
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

}
