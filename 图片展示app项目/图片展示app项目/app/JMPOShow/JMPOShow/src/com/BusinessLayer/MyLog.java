package com.BusinessLayer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.CommenLayer.GetTime;

public class MyLog {
	// 存放日志的路径
	public String myLogPath = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/phoneshow/myLog";
	public String filenameTempLog = myLogPath +"/"+GetTime.getTime1() + ".txt";

	public String logText(String text){
		String logtextStr = GetTime.getTime3()+"/"+text;
		return logtextStr;
	}
	
	
	
	// 创建文件夹及文件
	public void CreateText(String bootpath, String bootpath_filename) {
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
	public void print(String bootpath_filename, String content) {
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
}
