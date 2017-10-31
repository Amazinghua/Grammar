package com.BusinessLayer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.WebLayer.HandleLimite;
import com.WebLayer.MainActivity;

import android.R.bool;
import android.R.string;
import android.content.Context;
import android.content.Intent;

import com.CommenLayer.GetTime;
import com.CommenLayer.JSONHelper;
import com.CommenLayer.PostData;
import com.DataLayer.CheckImgName;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.BusinessLayer.MyLog;
import com.BusinessLayer.DeleteBeforeUD;
public class CheckLoadImg {
	private static String nowImgName = "";

	// 存放检测今天是否已经更新了图片
	public static String path = android.os.Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/phoneshow/check";
	static String filenameTemp = path + "/renewtime" + ".txt";
	String filenameTempName = path + "/reName" + ".txt";

	// 是否限制了不停的播放
	public String pathlimite = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/phoneshow/checklimite";
	String filenameTemplimite = pathlimite + "/renewlimite" + ".txt";
	Context contents;

	// 存放日志
	public String myLogPath = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/phoneshow/myLog";
	public String filenameTempLog = myLogPath + "/" + GetTime.getTime1()
			+ ".txt";

	public String logText(String text) {
		String logtextStr = GetTime.getTime3() +"/"+ text;
		return logtextStr;
	}

	public CheckLoadImg(Context content) {
		// TODO Auto-generated constructor stub
		contents = content;
	}

	public boolean chechrefreshimg() {
		boolean result = true;
		MyLog mLog = new MyLog();
		CreateText(path, filenameTemp);
		CreateText(path, filenameTempName);
		CreateText(myLogPath, filenameTempLog);
		
		String fileTime = readSDFile(filenameTemp).trim();
		String fileName = readSDFile(filenameTempName).trim();
		
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH");
		String datetime = tempDate.format(new java.util.Date()).toString();
//		if(DeleteBeforeUD.checkVersion){
//			result = false;
//			DeleteBeforeUD.checkVersion=false;
//		}
		if (checkUpdate(fileTime,fileName,datetime)) {
			result = false;
		} 
		else {
			File dir = new File(path);
			for (File file : dir.listFiles()) {
				if (file.isFile())
					file.delete(); // 删除所有文件
			}
			CreateText(path, filenameTemp);
			print(filenameTemp, datetime);
			
			CreateText(path, filenameTempName);
			print(filenameTempName, nowImgName);
			
			CreateText(myLogPath, filenameTempLog);
			mLog.print(filenameTempLog, logText("start"));
			
			Loadimg imgdo = new Loadimg();
			imgdo.loaddownimg();
			result = true;
		}
		return result;
	}
	//检查更新的方法
	public static boolean checkUpdate(String fileTime,String fileName,String datetime) {		
		boolean isconnect = MainActivity.isConnect;
		MyLog mLog = new MyLog();
//		mLog.print(mLog.filenameTempLog, mLog.logText("网络："+conString));
		if (isconnect == false) {
			return true;
		}
		else{
			boolean resultTime = checkUpdateTime(fileTime,datetime);
			File dir = new File(filenameTemp);
			dir.delete();
			CreateText(path, filenameTemp);
			print(filenameTemp, datetime);
//			mLog.print(mLog.filenameTempLog, mLog.logText("是否到点："+timeString));
			if(resultTime){
				boolean resultName = checkImgName(fileName);
				String nameString  = Boolean.toString(resultName);
				mLog.print(mLog.filenameTempLog, mLog.logText("comparison:"+nameString));
				
				if(resultName){
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return true;
			}
		}
		
	}
		
	// 检测图片名字
	public static boolean checkImgName(String oldName) {
//		CheckImgName check_name = new CheckImgName();
//		check_name.ftpdata();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "appimgname");
		String postdata = JSONHelper.toJson(map);
		// 图名集合的字符串
		String imgname = PostData.Connect_BackStage(postdata);
		nowImgName = imgname;
		if (oldName.equals(imgname)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 在9时，12时，15时检查更新
	 */
	public static boolean checkUpdateTime(String oldTime,String nowTime) {
		String now = nowTime.toString().substring(11);
		if(oldTime==null || oldTime==""){
			return true;
		}
		else{
			String old = oldTime.toString().substring(11);
			if(old.equals(now)){
				return false;
			}
			else if(now.equals("09") || now.equals("12") || now.equals("15")){
				return true;
			}
			else{
				return false;
			}
		}
	}
	/** 获取播放模式 */
	public boolean checkalwayrun() {
		boolean result = true;
		CreateText(pathlimite, filenameTemplimite);
		String filecontent = readSDFile(filenameTemplimite).trim();
		// 1是不停播放0是不播放
		if (filecontent.equals("0")) {
			result = false;
		} else if (filecontent.equals("")) {
			CreateText(pathlimite, filenameTemplimite);
			print(filenameTemplimite, "1");
			result = true;
		} else if (filecontent.equals("1")) {
			result = true;
		} else {
			File dir = new File(pathlimite);
			for (File file : dir.listFiles()) {
				if (file.isFile())
					file.delete(); // 删除所有文件
			}
			CreateText(pathlimite, filenameTemplimite);
			print(filenameTemplimite, "1");
			result = true;
		}
		return result;
	}

	/**
	 * 改变播放模式 1是不停播放0是不播放
	 */
	public String changewayrun() {
		String result = "1";
		CreateText(pathlimite, filenameTemplimite);
		String filecontent = readSDFile(filenameTemplimite).trim();
		// 1是不停播放0是不播放
		if (filecontent.equals("")) {
			CreateText(pathlimite, filenameTemplimite);
			print(filenameTemplimite, "1");
			result = "1";
		} else if (filecontent.equals("1")) {
			File dir = new File(pathlimite);
			for (File file : dir.listFiles()) {
				if (file.isFile())
					file.delete(); // 删除所有文件
			}
			CreateText(pathlimite, filenameTemplimite);
			print(filenameTemplimite, "0");
			result = "0";
		} else {
			File dir = new File(pathlimite);
			for (File file : dir.listFiles()) {
				if (file.isFile())
					file.delete(); // 删除所有文件
			}
			CreateText(pathlimite, filenameTemplimite);
			print(filenameTemplimite, "1");
			result = "1";
		}
		return result;
	}

	// 创建文件夹及文件
	public static void CreateText(String bootpath, String bootpath_filename) {
		File file = new File(bootpath);
		if (!file.exists()) {
			try {
				// 按照指定的路径创建文件夹
				file.mkdirs();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		File dir = new File(bootpath_filename);
		if (!dir.exists()) {
			try {
				// 在指定的文件夹中创建文件
				dir.createNewFile();
			} catch (Exception e) {
			}
		}

	}

	// 向已创建的文件中写入数据
	public static void print(String bootpath_filename, String content) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(bootpath_filename, true);//
			// 创建FileWriter对象，用来写入字符流
			bw = new BufferedWriter(fw); // 将缓冲对文件的输出
			String myreadline = content;
			bw.write(myreadline + "\n"); // 写入文件
			bw.newLine();
			bw.flush(); // 刷新该流的缓冲
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				bw.close();
				fw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			}
		}
	}

	/**
	 * 读取SD卡中文本文件
	 * 
	 * @param fileName
	 * @return
	 */
	public String readSDFile(String bootpath_filename) {
		StringBuffer sb = new StringBuffer();
		File file = new File(bootpath_filename);
		try {
			FileInputStream fis = new FileInputStream(file);
			int c;
			while ((c = fis.read()) != -1) {
				sb.append((char) c);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
