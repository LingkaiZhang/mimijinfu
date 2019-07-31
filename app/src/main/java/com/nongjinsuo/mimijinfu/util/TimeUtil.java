package com.nongjinsuo.mimijinfu.util;

import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @title: TimeUtil.java 
 * @package com.sinoglobal.app.util 
 * @description: 时间工具类
 * @author zrh
 * @date 2014-11-20 下午3:53:41
 */
public class TimeUtil {

	public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	public static final String[] monthStr = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
	
	public static String parseDateToStr(Date date) {
		return df.format(date);
	}
	public static String parseDateToStr(DateFormat df, Date date) {
		return df.format(date);
	}
	public static Date parseDate(DateFormat df, String date) throws ParseException {
		return df.parse(date);
	}
	public static String getTimeStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(System.currentTimeMillis()));

	}
	public static String getDayForNumStr(int num){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		calendar.add(Calendar.DAY_OF_MONTH,num);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(date);
	}
	/**
	 * 
	 * @author bo.wang
	 * @createdate 2012-10-26 下午3:56:55
	 * @Description 将time转换成long型
	 * @param df
	 *            格式化对象
	 * @param time
	 *            需要转换的字符串
	 */
	public static long parseStringToLong(DateFormat df, String time) {
		try {
			return df.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0L;
	}
	/***
	 * 
	* @author zrh
	* @methods parseTimeStampToStr 
	* @description 转换时间戳为 yyyy-MM-dd HH:mm 格式的字符串
	* @date 2014-11-20 下午4:02:11
	* @param timeStamp 时间戳( 距离1970年0:0:0:0 后的秒数)
	* @return yyyy-MM-dd HH:mm 格式的时间
	 */
	public static String parseTimeStampToStr(long timeStamp) {
		Date date=new Date(timeStamp*1000);
		return df.format(date);
	}
	public static String parseTimeStampToStr(String timeStamp)throws NumberFormatException {
		long t=Long.parseLong(timeStamp);
		Date date=new Date(t*1000);
		return df.format(date);
	}
	/**
	* 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	*/
	public static String getStringDate(Long date) 
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 E");
		String dateString = formatter.format(date);
		
		return dateString;
	}
	/**
	 * @author ty
	 * @createdate 2012-9-23 下午3:24:34
	 * @Description: 将Long值格式化
	 * @param df
	 * @param date
	 * @return
	 */
	public static String parseDateToString(DateFormat df, Date date) {
		return df.format(date);
	}

	public static String getDayNum(){
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		return c.get(Calendar.DAY_OF_MONTH)+"";
	}
	public static int getHourNum(){
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static String getMonthEnglish(){
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		int month = c.get(Calendar.MONTH);
		return  monthStr[month];

	}
	public static  void setDjs(long obj, TextView timeView) {
			long day = obj / 60 / 60 / 24;
			long hour = obj / 60 / 60 % 24;
			long minute = obj / 60 % 60;
			long second = obj % 60 % 60;

			String dayStr;
			String hourStr = hour + "";
			String minuteStr = minute + "";
			String secondStr = second + "";
			if (day>0&&day < 10) {
				dayStr = day + "天";
			}else{
				dayStr = "";
			}
			if (hour < 10) {
				hourStr = "0" + hour;
			}
			if (minute < 10) {
				minuteStr = "0" + minute;
			}
			if (second < 10) {
				secondStr = "0" + second;
			}
			timeView.setText(dayStr + hourStr + ":" + minuteStr + ":" + secondStr);
	}
	//毫秒换成00:00:00
	public static String getCountTimeByLong(long finishTime) {
		int totalTime = (int) (finishTime / 1000);//秒
		int  minute = 0, second = 0;

//		if (3600 <= totalTime) {
//			hour = totalTime / 3600;
//			totalTime = totalTime - 3600 * hour;
//		}
		if (60 <= totalTime) {
			minute = totalTime / 60;
			totalTime = totalTime - 60 * minute;
		}
		if (0 <= totalTime) {
			second = totalTime;
		}
		StringBuilder sb = new StringBuilder();

//		if (hour < 10) {
//			sb.append("0").append(hour).append(":");
//		} else {
//			sb.append(hour).append(":");
//		}
		if (minute < 10) {
			sb.append("0").append(minute).append(":");
		} else {
			sb.append(minute).append(":");
		}
		if (second < 10) {
			sb.append("0").append(second);
		} else {
			sb.append(second);
		}
		return sb.toString();

	}
}

