package com.CommenLayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.BusinessLayer.MyLog;

import android.hardware.Camera.Size;
import android.util.Log;

public class PostData {
	// private static int downSize = 0;
	static String POST_URL = ServerURL.getURL();
	static String POST_URLimg = ServerURL.getURLimg();

	/**
	 * 这个是post传值
	 */
	public static String Connect_BackStage(String data) {
		String line = "";

		try {
			URL postUrl = new URL(POST_URL);
			HttpURLConnection connection = (HttpURLConnection) postUrl
					.openConnection();

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setConnectTimeout(120000);// 120秒
			connection.setInstanceFollowRedirects(true);

			String content = data;// URLEncoder.encode(data, "utf-8");

			// 设置请求属性
			// 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
			byte[] requestStringBytes = content.getBytes("utf-8");
			connection.setRequestProperty("Content-length", ""
					+ requestStringBytes.length);
			connection.setRequestProperty("Content-Type",
					"application/octet-stream");
			connection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			connection.setRequestProperty("Charset", "UTF-8");
			connection.connect();
			// 建立输出流，并写入数据
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(requestStringBytes);
			outputStream.close();
			// 获得响应状态
			int responseCode = connection.getResponseCode();
			if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功

				// 当正确响应时处理数据
				BufferedReader responseReader;
				// 处理响应流，必须与服务器响应流输出的编码一致
				responseReader = new BufferedReader(new InputStreamReader(
						connection.getInputStream(), "utf-8"));
				String line2 = "";
				while ((line2 = responseReader.readLine()) != null) {
					line += new String(line2.getBytes(), "utf-8");
					// break;
				}
				responseReader.close();
				// line = "OK";
			}
		} catch (Exception ex) {
			line = ex.getMessage();
		}
		return line;
	}

	public static String getInput2(String urlStr) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.connect();
			InputStream stream = conn.getInputStream();
			byte[] data = new byte[102400];
			int length = stream.read(data);
			String str = new String(data, 0, length);
			conn.disconnect();
			stream.close();
			return str;
		} catch (Exception ee) {
			return "error:" + ee.getMessage();
		}
	}

	/**
	 * 下载指定的文件并保存
	 * 
	 * */

	public static void loaddownfile(String filename, String savepath) {
		if (filename == "") {
			return;
		}
		String finalypath = savepath + "/" + GetTime.getTime2() + ".png";
		File file = new File(finalypath);
		try {
			// 构造URL
			URL url = new URL(POST_URLimg + filename);
			// 打开连接
			URLConnection con = url.openConnection();
			con.setConnectTimeout(10000);
			// 维持长连接
			con.setRequestProperty("Connection", "Keep-Alive");
			// 获得文件的长度
			int contentLength = con.getContentLength();
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(finalypath);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// downSize++;
			// 完毕，关闭所有链接
			os.close();
			is.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void loaddownimg(String filename, String savepath) {
		String finalypath = savepath + "/" + GetTime.getTime2() + ".png";
		File file = new File(finalypath);
		String urlString = POST_URLimg + filename;
		try {
			long ff = downloadUpdateFile(urlString, file);
			if (ff > 0) {

				Log.i("s", "成功");
			} else {
				Log.i("f", "失败");
			}
		} catch (Exception e) {
			Log.i("e", e.toString());
		}

	}

	public static synchronized long downloadUpdateFile(String downloadUrl,
			File saveFile) throws Exception {

		int currentSize = 0;
		long totalSize = 0;
		HttpURLConnection httpConnection = null;
		InputStream is = null;
		FileOutputStream fos = null;
		try {

			URL url = new URL(downloadUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection
					.setRequestProperty("User-Agent", "PacificHttpClient");
			if (currentSize > 0) {
				httpConnection.setRequestProperty("RANGE", "bytes="
						+ currentSize + "-");
			}
			httpConnection.setRequestProperty("Content-Type",
					"application/octet-stream");
			httpConnection.setRequestProperty("Connection", "Keep-Alive");
			httpConnection.setConnectTimeout(10000);
			httpConnection.setReadTimeout(20000);
			httpConnection.setInstanceFollowRedirects(false);
			httpConnection.setUseCaches(false);
			httpConnection.connect();

			if (httpConnection.getResponseCode() == 404) {
				throw new Exception("文件不存在，请与管理员联系");
			}
			is = httpConnection.getInputStream();
			fos = new FileOutputStream(saveFile, false);

			byte buffer[] = new byte[1024];
			int readsize = 0;
			while ((readsize = is.read(buffer)) > 0) {
				fos.write(buffer, 0, readsize);
				totalSize += readsize;
			}
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		if(totalSize>0){
			MyLog logName = new MyLog();
			logName.print(logName.filenameTempLog,logName.logText(downloadUrl));
		}
		return totalSize;
	}
}
