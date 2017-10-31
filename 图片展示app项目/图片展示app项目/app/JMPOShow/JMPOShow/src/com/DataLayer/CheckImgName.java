package com.DataLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import android.R.string;
import android.os.Environment;
import android.util.Log;

import com.BusinessLayer.MyLog;
import com.CommenLayer.GetTime;
import com.CommenLayer.JSONHelper;
import com.CommenLayer.PostData;
import com.CommenLayer.ServerURL;

public class CheckImgName {
	private FTPClient ftpClient;
	private String fileName, strencoding;
	private String ip = "221.179.63.114"; // ������IP��ַftp://221.179.63.114:2121/
	private String userName = "csyftp"; // �û���
	private String userPwd = "Olpy@$2016"; // ����
	private int port = 2121; // �˿ں�
	private String path = ""; // ��ȡ�ļ��Ĵ��Ŀ¼
	public  String imgList="";
	
	public CheckImgName() {
		this.reSet();
	}
	public void reSet() {
		this.connectServer(ip, port, userName, userPwd);
	}
	public void connectServer(String ip, int port, String userName, String userPwd) {
		ftpClient = new FTPClient();
		try {
			// ����
			ftpClient.connect(ip, port);
			// ��¼
			ftpClient.login(userName, userPwd);
		} catch (Exception e) {
			e.toString();
			String uhu = "";
		}
	}
	public void closeServer() {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	public void ftpdata() {
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("GBK");
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		conf.setServerLanguageCode("zh");
		ftp.configure(conf);

		try {
			ftp.connect(ip, port);
			if (ftp.login(userName, userPwd)) {
				int reply = ftp.getReplyCode();
				if (!FTPReply.isPositiveCompletion(reply)) {
					ftp.disconnect();
					System.out.println("disconnect");
				} else {
					Log.i("�ļ���","��ʼ");
					ftp.enterLocalPassiveMode();
					ftp.setFileType(FTP.BINARY_FILE_TYPE);
					FTPFile[] files = ftp.listFiles();
					imgList=getImgName(files);
					Log.i("�ļ���","����");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static String getImgName(FTPFile[] files) {
		String imgNameStr = "";
		for(FTPFile file : files){
			imgNameStr+=file.getName()+"/";
		}
		return imgNameStr;
	}
}

