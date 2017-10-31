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
	//modeΪ����ģʽ��Ĭ�ϵ�ģʽΪ0��MODE_PRIVATE,
	//modeָ��ΪMODE_PRIVATE����������ļ�ֻ�ܱ��Լ���Ӧ�ó�����ʡ�
	//nameΪ������������ļ���( �Լ�����)��������ļ�������ʱ��ֱ�Ӵ���������Ѿ����ڣ���ֱ��ʹ��
	/**
	 * �����ݽ���SharedPreferences
	 * key�����ƣ�value��ֵ
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
	 * ����ǻ�ȡ����SharedPreferences�����ֵ�ķ���
	 * ���л�ȡ���APP�汾�ķ���
	 * */
	public static String getValue(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFERENCES_NAME, Context.MODE_PRIVATE);
		// ʹ��getString�������value��ע���2��������value��Ĭ��ֵ 
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
	 * ����ǻ�ȡSharedPreferences��ֵ
	 * key������Ƶ�ֵ
	 * defaultvalueĬ��ֵ
	 * */
	public static String getValue(Context context, String key,
			String defaultvalue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFERENCES_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defaultvalue);

	}
}
