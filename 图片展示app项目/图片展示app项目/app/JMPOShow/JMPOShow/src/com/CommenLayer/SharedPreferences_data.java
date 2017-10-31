package com.CommenLayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

public class SharedPreferences_data {
	private static final String PREFERENCES_NAME = "config";
	//getSharedPreferences(String name, int mode)
	//mode为操作模式，默认的模式为0或MODE_PRIVATE,
	//mode指定为MODE_PRIVATE，则该配置文件只能被自己的应用程序访问。
	//name为本组件的配置文件名( 自己定义)，当这个文件不存在时，直接创建，如果已经存在，则直接使用
	/**
	 * 存数据进入SharedPreferences
	 * key是名称，value是值
	 * */
	public static void setValue(Context context, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getstreet_or_roadid(String type) {
		//getValue
		return "";
	}
	
	
	
	/**
	 * 这个是获取存在SharedPreferences里面的值的方法
	 * 还有获取这个APP版本的方法
	 * */
	public static String getValue(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFERENCES_NAME, Context.MODE_PRIVATE);
		// 使用getString方法获得value，注意第2个参数是value的默认值 
		String result = sharedPreferences.getString(key, "");
		if (key.equals("version")) {
			if (TextUtils.isEmpty(result)) {
				PackageManager pm = context.getPackageManager();
				PackageInfo info;
				try {

					info = pm.getPackageInfo(context.getPackageName(), 0);
					result = info.versionName;
					setValue(context, "version", info.versionName);
				} catch (NameNotFoundException e) {

					e.printStackTrace();
				}
			}
			result = "Android " + result;
		}
		return result;
	}
	/**
	 * 这个是获取SharedPreferences的值
	 * key这个名称的值
	 * defaultvalue默认值
	 * */
	public static String getValue(Context context, String key,
			String defaultvalue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFERENCES_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defaultvalue);

	}
}
