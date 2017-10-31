package com.CommenLayer;

public class ServerURL {
	public static String getURL()
	{		
//		return "http://221.179.63.114:82/getdata.aspx";
//		return "http://120.237.31.12:877/getdata.aspx";
		return "http://phoneshow.jmydhb.net:8017/getdata.aspx";
	}
	public static String getURLimg()
	{
//		return "http://221.179.63.114:82/images/";
//		return "http://120.237.31.12:877/images/";
		return "http://phoneshow.jmydhb.net:8017/images/";
	}
	public static String newversion()
	{
//		return "http://221.179.63.114:82/AppVersion.html";
//		return "http://120.237.31.12:877/AppVersion.html";
		return "http://phoneshow.jmydhb.net:8017/AppVersion.html";
	}
	/**
	 * 去服务器拿apk的地址
	 * */
	public static String getapk() {
//		return "http://221.179.63.114:82/APK/JMPOShow.apk";
//		return "http://120.237.31.12:877/APK/JMPOShow.apk";
		return "http://phoneshow.jmydhb.net:8017/APK/JMPOShow.apk";
	}
}
