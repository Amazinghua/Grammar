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

	// ��ż������Ƿ��Ѿ�������ͼƬ
	public static String path = android.os.Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/phoneshow/check";
	static String filenameTemp = path + "/renewtime" + ".txt";
	String filenameTempName = path + "/reName" + ".txt";

	// �Ƿ������˲�ͣ�Ĳ���
	public String pathlimite = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/phoneshow/checklimite";
	String filenameTemplimite = pathlimite + "/renewlimite" + ".txt";
	Context contents;

	// �����־
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
					file.delete(); // ɾ�������ļ�
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
	//�����µķ���
	public static boolean checkUpdate(String fileTime,String fileName,String datetime) {		
		boolean isconnect = MainActivity.isConnect;
		MyLog mLog = new MyLog();
//		mLog.print(mLog.filenameTempLog, mLog.logText("���磺"+conString));
		if (isconnect == false) {
			return true;
		}
		else{
			boolean resultTime = checkUpdateTime(fileTime,datetime);
			File dir = new File(filenameTemp);
			dir.delete();
			CreateText(path, filenameTemp);
			print(filenameTemp, datetime);
//			mLog.print(mLog.filenameTempLog, mLog.logText("�Ƿ񵽵㣺"+timeString));
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
		
	// ���ͼƬ����
	public static boolean checkImgName(String oldName) {
//		CheckImgName check_name = new CheckImgName();
//		check_name.ftpdata();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "appimgname");
		String postdata = JSONHelper.toJson(map);
		// ͼ�����ϵ��ַ���
		String imgname = PostData.Connect_BackStage(postdata);
		nowImgName = imgname;
		if (oldName.equals(imgname)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * ��9ʱ��12ʱ��15ʱ������
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
	/** ��ȡ����ģʽ */
	public boolean checkalwayrun() {
		boolean result = true;
		CreateText(pathlimite, filenameTemplimite);
		String filecontent = readSDFile(filenameTemplimite).trim();
		// 1�ǲ�ͣ����0�ǲ�����
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
					file.delete(); // ɾ�������ļ�
			}
			CreateText(pathlimite, filenameTemplimite);
			print(filenameTemplimite, "1");
			result = true;
		}
		return result;
	}

	/**
	 * �ı䲥��ģʽ 1�ǲ�ͣ����0�ǲ�����
	 */
	public String changewayrun() {
		String result = "1";
		CreateText(pathlimite, filenameTemplimite);
		String filecontent = readSDFile(filenameTemplimite).trim();
		// 1�ǲ�ͣ����0�ǲ�����
		if (filecontent.equals("")) {
			CreateText(pathlimite, filenameTemplimite);
			print(filenameTemplimite, "1");
			result = "1";
		} else if (filecontent.equals("1")) {
			File dir = new File(pathlimite);
			for (File file : dir.listFiles()) {
				if (file.isFile())
					file.delete(); // ɾ�������ļ�
			}
			CreateText(pathlimite, filenameTemplimite);
			print(filenameTemplimite, "0");
			result = "0";
		} else {
			File dir = new File(pathlimite);
			for (File file : dir.listFiles()) {
				if (file.isFile())
					file.delete(); // ɾ�������ļ�
			}
			CreateText(pathlimite, filenameTemplimite);
			print(filenameTemplimite, "1");
			result = "1";
		}
		return result;
	}

	// �����ļ��м��ļ�
	public static void CreateText(String bootpath, String bootpath_filename) {
		File file = new File(bootpath);
		if (!file.exists()) {
			try {
				// ����ָ����·�������ļ���
				file.mkdirs();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		File dir = new File(bootpath_filename);
		if (!dir.exists()) {
			try {
				// ��ָ�����ļ����д����ļ�
				dir.createNewFile();
			} catch (Exception e) {
			}
		}

	}

	// ���Ѵ������ļ���д������
	public static void print(String bootpath_filename, String content) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(bootpath_filename, true);//
			// ����FileWriter��������д���ַ���
			bw = new BufferedWriter(fw); // ��������ļ������
			String myreadline = content;
			bw.write(myreadline + "\n"); // д���ļ�
			bw.newLine();
			bw.flush(); // ˢ�¸����Ļ���
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
	 * ��ȡSD�����ı��ļ�
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
