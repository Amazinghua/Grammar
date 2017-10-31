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

import android.R.integer;
import android.R.string;
import android.os.Environment;
import android.util.Log;

import com.CommenLayer.GetTime;
import com.CommenLayer.JSONHelper;
import com.CommenLayer.PostData;
import com.CommenLayer.ServerURL;
import com.BusinessLayer.MyLog;
import com.BusinessLayer.RandomFour;

public class NewFtpData {
//	private FTPClient ftpClient;
	private String fileName, strencoding;
	private String ip = "221.179.63.114"; // ������IP��ַftp://221.179.63.114:2121/
	private String userName = "csyftp"; // �û���
	private String userPwd = "Olpy@$2016"; // ����
	private int port = 2121; // �˿ں�
	private String path = ""; // ��ȡ�ļ��Ĵ��Ŀ¼

	public static final String DATABASE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/pictrue_temporary";

//	public NewFtpData() {
//		this.reSet();
//	}

//	public void reSet() {
//		this.connectServer(ip, port, userName, userPwd);
//	}
//
//	public void connectServer(String ip, int port, String userName,
//			String userPwd) {
//		ftpClient = new FTPClient();
//		try {
//			// ����
//			ftpClient.connect(ip, port);
//			// ��¼
//			ftpClient.login(userName, userPwd);
//		} catch (Exception e) {
//			e.toString();
//			String uhu = "";
//		}
//	}
//
//	public void closeServer() {
//		if (ftpClient.isConnected()) {
//			try {
//				ftpClient.logout();
//				ftpClient.disconnect();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	public static void deleteAllFiles(String SDPATH) {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // ɾ�������ļ�

		}
		// dir.delete();// ɾ��Ŀ¼����
	}

	public void ftpdata() {
		deleteAllFiles(DATABASE_PATH);
		// FTPClient ftp = new FTPClient();
		// ftp.setControlEncoding("GBK");
		// FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		// conf.setServerLanguageCode("zh");
		// ftp.configure(conf);

		try {
			// ftp.connect(ip, port);
			// if (ftp.login(userName, userPwd)) {
			// int reply = ftp.getReplyCode();
			// if (!FTPReply.isPositiveCompletion(reply)) {
			// ftp.disconnect();
			// System.out.println("disconnect");
			// } else {
			// ftp.enterLocalPassiveMode();
			// ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// ɾ��app�е��ļ���
			File dir = new File(DATABASE_PATH);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// ��ȡ��̨��ͼƬ�ļ�����
			// FTPFile[] files = ftp.listFiles();
			MyLog myLog = new MyLog();
			myLog.print(myLog.filenameTempLog, myLog.logText("update"));
			Log.i("b", "begin");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", "appimgname");
			String postdata = JSONHelper.toJson(map);
			// ͼ�����ϵ��ַ���
			String imgname = PostData.Connect_BackStage(postdata);
			// �ָ��ַ���������Ž�һ������
			String[] imgListName = imgname.split("\\|");
			int nameLen = imgListName.length;
			String POST_URL = ServerURL.getURLimg();
			// һ��һ�ŵ�����ͼƬ
			for (int i = 0; i <= nameLen; i++) {

				File ApkFile = new File(DATABASE_PATH + "/"
						+ GetTime.getTime2() + RandomFour.getRandom() + ".png");
				try {
					PostData.downloadUpdateFile(POST_URL + imgListName[i], ApkFile);
					MyLog logName = new MyLog();
					logName.print(logName.filenameTempLog,
							logName.logText(imgname));
					logName.print(
							logName.filenameTempLog,
							logName.logText(GetTime.getTime2()
									+ RandomFour.getRandom()));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}


			Log.i("e", "end");
			myLog.print(myLog.filenameTempLog, myLog.logText("finish"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void copys() {
		String imagePath = Environment.getExternalStorageDirectory().toString()
				+ "/phoneshow";
		copyFolder(DATABASE_PATH, imagePath);
	}

	/**
	 * ���������ļ�������
	 * 
	 * @param oldPath
	 *            String ԭ�ļ�·�� �磺c:/fqf
	 * @param newPath
	 *            String ���ƺ�·�� �磺f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs(); // ����ļ��в����� �������ļ���
			File dir = new File(newPath);
			for (File file : dir.listFiles()) {
				if (file.isFile())
					file.delete(); // ɾ�������ļ�
			}
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// ��������ļ���
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("���������ļ������ݲ�������" + e.toString());
			e.printStackTrace();

		}

	}

}
