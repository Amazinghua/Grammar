package com.CommenLayer;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class GetTime {
    
	
	public static String getTime(String frm) {
		SimpleDateFormat sdf = new SimpleDateFormat("",
				Locale.SIMPLIFIED_CHINESE);
		sdf.applyPattern(frm);
		String timeStr = sdf.format(new Date());
		return timeStr;
	}
	
	
    /**
	 * 获取当地时间，时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 **/
	public static String getTime() {
		return getTime("yyyy-MM-dd HH:mm:ss");
	}
   /**
	* 获取当地时间，时间格式yyyy-MM-dd
	* 
	* */
	public static String getTime1() {
		return getTime("yyyy-MM-dd");
	}
	/**
	 * 获取当地时间，时间格式yyyyMMddHHmmss
	 * */
	public static String getTime2() {
		return getTime("yyyyMMddHHmmss");
	}
	
	public static String getTime3() {
		return getTime("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getTime(String str, String frm) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("",
					Locale.SIMPLIFIED_CHINESE);
			sdf.applyPattern(frm);
			Date date = sdf.parse(str);
			str = sdf.format(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getTime(String dt, int days) {
		if (dt.equals("")) {
			dt = GetTime.getTime();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dt));
		} catch (Exception e) {
		}
		c.add(Calendar.DATE, days); // number of days to add
		dt = sdf.format(c.getTime()); // dt is now the new date
		return dt;
	}

	public static Date getDate(String str) {
		Date date = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("",
					Locale.SIMPLIFIED_CHINESE);
			sdf.applyPattern("yyyy-MM-dd");
			date = sdf.parse(str);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getTime2(Date date, String frm) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("",
				Locale.SIMPLIFIED_CHINESE);
		sdf.applyPattern(frm);
		str = sdf.format(date);
		return str;
	}
}
