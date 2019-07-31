package com.nongjinsuo.mimijinfu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sahara on 2016/5/10.
 */
public class UtilDate {

    public static UtilDate utilDate;

    public static UtilDate getInstance(){
        if (utilDate==null){
            utilDate = new UtilDate();
        }
        return utilDate;
    }

    /**
     * 将日期格式转换成秒
     * @param formatStr
     * @return
     */
    public static long getSec(String formatStr){
        if (formatStr==null){
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d ;
        long time = 0;
        try {
            d = format.parse(formatStr);
            if (d!=null){
                time = d.getTime();//单位毫秒
            }else{
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time/1000;
    }

    /**
     * 将日期格式转换成秒
     * @param formatStr
     * @return
     */
    public static long getSec2(String formatStr){
        if (formatStr==null){
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d ;
        long time = 0;
        try {
            d = format.parse(formatStr);
            if (d!=null){
                time = d.getTime();//单位毫秒
            }else{
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time/1000;
    }

    public static long getSecDate(String formatStr){
        if (formatStr==null){
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d ;
        long time = 0;
        try {
            d = format.parse(formatStr);
            if (d!=null){
                time = d.getTime();//单位毫秒
            }else{
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time/1000;
    }

    public static long getSecTime(String formatStr){
        if (formatStr==null){
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date d ;
        long time = 0;
        try {
            d = format.parse(formatStr);
            if (d!=null){
                time = d.getTime();//单位毫秒
            }else{
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time/1000;
    }

    /**
     * 根据秒计算出日期
     * @param mss
     * @return
     */
    public static String formatDuring2(long sec) {
        long days = sec / (60 * 60 * 24);
        long hours = (sec % (60 * 60 * 24)) / (60 * 60);
        long minutes = (sec % (60 * 60)) / (60);
        long seconds = (sec % (60));
        if (days!=0){//天数不为0就直接显示天数
            return days+"天前";
        }
        if (hours!=0){
            return hours+"小时前";
        }
        if (minutes!=0){
            return minutes+"分钟前";
        }
        if (seconds!=0){
            return seconds+"秒前";
        }
        return "刚刚";
    }

    /**
     * 根据秒计算出日期
     * @param mss
     * @return
     */
    public static String[] formatDuring(long sec) {
        String[] s = new String[4];
        long days = sec / (60 * 60 * 24);
        long hours = (sec % (60 * 60 * 24)) / (60 * 60);
        long minutes = (sec % (60 * 60)) / (60);
        long seconds = (sec % (60));
        if (days<10){
            s[0] = "0"+days;
        }else{
            if (days>99){
                s[0] = 99+"";
            }else{
                s[0] = days+"";
            }
        }
        if (hours<10){
            s[1] = "0"+hours;
        }else{
            s[1] = hours+"";
        }
        if (minutes<10){
            s[2] = "0"+minutes;
        }else{
            s[2] = minutes+"";
        }
        if (seconds<10){
            s[3] = "0"+seconds;
        }else{
            s[3] = seconds+"";
        }
        return s;
//        return seconds+"秒/"+minutes + "分钟/"+ hours + "小时/"+days + "天";
    }

    /**
     * 获取系统当前时间
     */
    public static String getDate(){
        SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd HH:mm:ss");
        Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        return str;
    }

}
