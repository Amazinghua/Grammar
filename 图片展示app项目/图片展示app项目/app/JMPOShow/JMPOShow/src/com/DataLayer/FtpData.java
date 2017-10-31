package com.DataLayer;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.BusinessLayer.Loadimg;
import com.CommenLayer.GetTime;
import com.CommenLayer.JSONHelper;
import com.CommenLayer.PostData;

import android.R.array;
import android.R.integer;
import android.net.ParseException;
import android.nfc.Tag;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @describe 读取FTP上的文件
 * @auto li.wang
 * @date 2013-11-18 下午4:07:34
 */
public class FtpData {

	private FTPClient ftpClient;
	private String fileName, strencoding;
	private String ip = "221.179.63.114"; // 服务器IP地址ftp://221.179.63.114:2121/
	private String userName = "csyftp"; // 用户名
	private String userPwd = "Olpy@$2016"; // 密码
	private int port = 2121; // 端口号
	private String path = ""; // 读取文件的存放目录
	private static int loaddowncount = 0;// 记录下载次数
//	private static String[] imgLoadName;//记录下载图片的名字
	

	/**
	 * init ftp servere
	 */
	public FtpData() {
		this.reSet();
	}

	public static final String DATABASE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/pictrue_temporary";

	public void reSet() {
		this.connectServer(ip, port, userName, userPwd);
	}

	/**
	 * 连接到服务器
	 */
	public void connectServer(String ip, int port, String userName,
			String userPwd) {
		ftpClient = new FTPClient();
		try {
			// 连接
			ftpClient.connect(ip, port);
			// 登录
			ftpClient.login(userName, userPwd);
		} catch (Exception e) {
			e.toString();
			String uhu = "";
		}
	}

	/**
	 * @throws IOException
	 *             function:关闭连接
	 */
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

	/**
	 * @param path
	 * @return function:读取指定目录下的文件名
	 * @throws IOException
	 */
	public List<String> getFileList(String path) {
		List<String> fileLists = new ArrayList<String>();
		// 获得指定目录下所有文件名
		FTPFile[] ftpFiles = null;
		try {
			ftpFiles = ftpClient.listFiles(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
			FTPFile file = ftpFiles[i];
			if (file.isFile()) {
				fileLists.add(file.getName());
			}
		}
		return fileLists;
	}

	public static void loaddownimg() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "appimgname");
		String postdata = JSONHelper.toJson(map);
		String imgname = PostData.Connect_BackStage(postdata);

		String[] areey = imgname.split("[|]");
		int imgSize = areey.length;
		List<String> imgArray=new ArrayList<String>(); 
		for(int i = 0;i<areey.length;i++){
			 imgArray.add(areey[i]);
		}
		
		hasSdcard();
		
		String savepath = DATABASE_PATH;
		deleteAllFiles(savepath);
		Log.i("b","begin");
		if (null != imgArray && imgArray.size() > 0) {
		    Iterator<String> it = imgArray.iterator();  
		    while(it.hasNext()){
				String imgName = (String)it.next();
				it.remove();
				PostData.loaddownimg(imgName, savepath);
				Log.i(imgName,imgName);
		    }
		}
		Log.i("e","end");
		checkImgSize(imgSize);

	}

	/*
	 * 检测
	 */
	public static void checkImgSize(int imgSize) {
		File temFile = new File(DATABASE_PATH);
		File[] tempList = temFile.listFiles();
		if (loaddowncount < 1) {
			if (imgSize == tempList.length) {
				return;
			} else {
				loaddowncount++;
				loaddownimg();
			}
		}
		loaddowncount = 0;
	}

	public void copys() {
		String imagePath = Environment.getExternalStorageDirectory().toString()
				+ "/phoneshow";
		copyFolder(DATABASE_PATH, imagePath);
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File dir = new File(newPath);
			for (File file : dir.listFiles()) {
				if (file.isFile())
					file.delete(); // 删除所有文件
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
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * @param FilePath
	 *            保存到本地的路径
	 * @return function:从服务器上读取指定的文件
	 * @throws ParseException
	 * @throws IOException
	 */
	public String readFile(String FilePath) throws ParseException {
		String savepath = DATABASE_PATH;
		try {

			ftpClient.setKeepAlive(true);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory("/");
			ftpClient.setControlEncoding("GBK");
			FTPFile[] files = ftpClient.listFiles();
			hasSdcard();
			deleteAllFiles(savepath);
			for (FTPFile file : files) {
				String finalypath = savepath + "/" + GetTime.getTime2()
						+ ".png";
				File localFile = new File(finalypath); // 根据绝对路径初始化文件
				OutputStream out = new FileOutputStream(localFile, true);
				InputStream input = ftpClient
						.retrieveFileStream(file.getName());
				byte[] bt = new byte[1024];
				int length = 0;
				while ((length = input.read(bt)) > 0) {
					out.write(bt, 0, length);
				}
				out.flush();
				out.close();
				input.close();

			}
			// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
			ftpClient.getReply();
		} catch (Exception e) {
			// TODO: handle exception
			String ugh = e.toString();
			String y8i = ugh;
		}
		return "";
	}

	public void ftpdata() {
		hasSdcard();
		deleteAllFiles(DATABASE_PATH);
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("GBK");
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		conf.setServerLanguageCode("zh");
		ftp.configure(conf);

		try {
			// 连接
			ftp.connect(ip, port);
			if (ftp.login(userName, userPwd)) {
				int reply = ftp.getReplyCode();
				if (!FTPReply.isPositiveCompletion(reply)) {
					ftp.disconnect();
					System.out.println("disconnect");
				} else {
					ftp.enterLocalPassiveMode();
					ftp.setFileType(FTP.BINARY_FILE_TYPE);

					File dir = new File(DATABASE_PATH);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					String[] names = ftp.listNames();
					for (String name : names) {
						File file = new File(dir.getPath() + File.separator
								+ name);
						if (!file.exists()) {
							file.createNewFile();
						}
						long pos = file.length();
						RandomAccessFile raf = new RandomAccessFile(file, "rw");
						raf.seek(pos);
						ftp.setRestartOffset(pos);
						InputStream is = ftp.retrieveFileStream(name);
						if (is != null) {
							int b;
							while ((b = is.read()) != -1) {
								raf.write(b);
							}
							is.close();
						}
						raf.close();
					}
				}
				ftp.logout();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除指定路径下文件夹里面的所有文件
	 * 
	 * */
	public static void deleteAllFiles(String SDPATH) {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件

		}
		// dir.delete();// 删除目录本身
	}

	public static boolean hasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			File path1 = new File(DATABASE_PATH);
			if (!path1.exists()) {
				path1.mkdirs();
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param fileName
	 *            function:删除文件
	 */
	public void deleteFile(String fileName) {
		try {
			ftpClient.deleteFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 应用上面封装方法的方法
	 */
	public static void main(String[] args) throws ParseException {
		FtpData ftp = new FtpData();
		String str = ftp.readFile("");
		System.out.println(str);
	}
}