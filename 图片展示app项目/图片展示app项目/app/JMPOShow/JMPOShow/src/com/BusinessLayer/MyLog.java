package com.BusinessLayer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.CommenLayer.GetTime;

public class MyLog {
	// �����־��·��
	public String myLogPath = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/phoneshow/myLog";
	public String filenameTempLog = myLogPath +"/"+GetTime.getTime1() + ".txt";

	public String logText(String text){
		String logtextStr = GetTime.getTime3()+"/"+text;
		return logtextStr;
	}
	
	
	
	// �����ļ��м��ļ�
	public void CreateText(String bootpath, String bootpath_filename) {
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
	public void print(String bootpath_filename, String content) {
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
}
