package com.CommenLayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import com.jmposhow.R;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;


/**
 * 这个服务是到后台获取apk然后安装的功�?
 * 
 * */
@SuppressLint({ "HandlerLeak", "SdCardPath" })
public class UpdateService extends Service {

	/**去服务器获取apk的地�?*/
	public String uploadUrl = "/APK/JMPOShow.apk";

	/** 文件存储
	 * */
	private File updateFile = null;
	 /**
	 * 下载下来的apk保存的文件夹位置
	 **/
	public static final String savePath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	 /**
	 * 下载下来的apk保存位置和apk?
	 * */
	public static final String saveFileName = savePath + "JMPOShow.apk";

	/**通知�?*/ 
	private NotificationManager updateNotificationManager = null;
	private Notification updateNotification = null;
	
	
	/**通知栏跳�?
	 * */ 
	private Intent updateIntent = null;
	private PendingIntent updatePendingIntent = null;
    /**apk下载成功后要相应处理的标志主要是提示Handler做相应的处理*/
	private static final int DOWNLOAD_COMPLETE = 1;
	/**apk下载失败后要相应处理的标志主要是提示Handler做相应的处理*/
	private static final int DOWNLOAD_FAIL = 2;
	private static final int DOWNLOAD_UPDATE = 3;
	private static int progress = 0;
	private static String progressString = "";

	@SuppressWarnings("deprecation")
	public int onStartCommand(Intent intent, int flags, int startId) {
		Bundle extras = intent.getExtras();
		if (extras != null && extras.getString("url") != null) {
			uploadUrl = extras.getString("url") + "/apk/jmadsl.apk";
		}
		else {
			uploadUrl=ServerURL.getapk();	
		}
		this.updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		this.updateNotification = new Notification(R.drawable.cm_logo,
				getResources().getText(R.string.app_name) + "正在下载...",
				System.currentTimeMillis());
		updateNotification.contentView = new RemoteViews(getPackageName(),
				R.layout.updata_nitification);
		updateIntent = new Intent(this, UpdateService.class);
		updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent,
				0);
		// 设置通知栏显示内�??
		updateNotification.contentIntent = updatePendingIntent;
		// 发出通知

		updateNotification.contentView.setProgressBar(
				R.id.update_notification_progressbar, 100, 0, false);
		updateNotification.contentView.setTextViewText(
				R.id.update_notification_progresstext, "准备就绪...");

		updateNotificationManager.notify(0, updateNotification);
		new Thread(new updateRunnable()).start();
		return super.onStartCommand(intent, flags, startId);
	}

	/**这个是Thread和Handler的联合使用的线程和页面之间的交互*/
	private class updateRunnable extends Thread {
		Message message = updateHandler.obtainMessage();

		@Override
		public void run() {
			try {
				File file = new File(savePath);

				if (!file.exists()) {
					file.mkdir();
				}

				String apkFile = saveFileName;
				/**
				 * File ApkFile = new File(apkFile);
				 * 这个方法是按指定的路径创建相应的文件
				 * */
				File ApkFile = new File(apkFile);
				long downloadSize = downloadUpdateFile(uploadUrl, ApkFile);
				if (downloadSize > 0) {
					// 下载成功
					message.what = DOWNLOAD_COMPLETE;
					updateHandler.sendMessage(message);
				}
			} catch (Exception ex) {
				// 下载失败
				message.what = DOWNLOAD_FAIL;
				updateHandler.sendMessage(message);
			}
		}
	}

	private Handler updateHandler = new Handler() {
		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD_COMPLETE:
				// 手动安装
				updateFile = new File(saveFileName);
				Uri uri = Uri.fromFile(updateFile);
				Intent installIntent = new Intent(Intent.ACTION_VIEW);
				installIntent.setDataAndType(uri,
						"application/vnd.android.package-archive");
				updatePendingIntent = PendingIntent.getActivity(
						UpdateService.this, 0, installIntent, 0);

				updateNotification.defaults = Notification.DEFAULT_SOUND;// 閾冨０鎻愰啋
				updateNotification.contentIntent = updatePendingIntent;// 瀹夎鐣岄潰
				updateNotification.contentView.setViewVisibility(
						R.id.update_notification_progressblock, View.GONE);
				updateNotification.contentView.setTextViewText(
						R.id.update_notification_progresstext, "下载完成，点击安装！");
				updateNotificationManager.notify(0, updateNotification);
				// 自动安装
				installApk();
				stopSelf();
				break;
			case DOWNLOAD_UPDATE:
				updateNotification.contentView.setProgressBar(
						R.id.update_notification_progressbar, 100, progress,
						false);
				updateNotification.contentView.setTextViewText(
						R.id.update_notification_progresstext, progressString);
				updateNotificationManager.notify(0, updateNotification);
				break;

			case DOWNLOAD_FAIL:

				updateIntent = new Intent(UpdateService.this,
						UpdateService.class);
				updatePendingIntent = PendingIntent.getService(
						UpdateService.this, 0, updateIntent, 0);
				// 设置通知栏显示内�??
				updateNotification.contentIntent = updatePendingIntent;
				// 下载失败
				updateNotification.setLatestEventInfo(UpdateService.this,
						"下载信息", "下载失败,点击重新下载", updatePendingIntent);
				updateNotificationManager.notify(0, updateNotification);
				stopSelf();
				break;
			default:
				stopSelf();
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	/**
	 * �?当synchronized关键字修饰一个方法的时�?�，该方法叫做同步方法�??
	 * downloadUrl下载路径（去服务器拿的路径）
	 * saveFile保存的地�?和apk的名称，把返回的buffer字节放到这个这个文件夹里
	 * */
	public synchronized long downloadUpdateFile(String downloadUrl,
			File saveFile) throws Exception {

		int downloadCount = 0;
		int currentSize = 0;
		long totalSize = 0;
		int updateTotalSize = 0;
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

			httpConnection.setRequestProperty("Connection", "Keep-Alive");
			httpConnection.setConnectTimeout(10000);
			httpConnection.setReadTimeout(20000);
			httpConnection.connect();

			if (httpConnection.getResponseCode() == 404) {
				throw new Exception("fail!");
			}
			is = httpConnection.getInputStream();
			fos = new FileOutputStream(saveFile, false);

			updateTotalSize = httpConnection.getContentLength();

			byte buffer[] = new byte[4096];
			int readsize = 0;
			while ((readsize = is.read(buffer)) > 0) {
				fos.write(buffer, 0, readsize);
				totalSize += readsize;
				/**
				 * 为了防止频繁的�?�知导致应用吃紧，百分比增加10才�?�知�?�?*/ 
				if ((downloadCount == 0)
						|| (int) (totalSize * 100 / updateTotalSize) - 1 > downloadCount) {
					downloadCount += 1;
					progress = (int) (totalSize * 100 / updateTotalSize);
					progressString = (int) (totalSize * 100 / updateTotalSize)
							+ "%";

					Message message = updateHandler.obtainMessage();
					message.what = DOWNLOAD_UPDATE;
					updateHandler.sendMessage(message);
				}
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
		return totalSize;
	}

	/**
	 * 瀹夎apk
	 * 
	 * @param url
	 */
	private void installApk() {
		updateFile = new File(saveFileName);
		Uri uri = Uri.fromFile(updateFile);
		Intent installIntent = new Intent(Intent.ACTION_VIEW);
		installIntent.setDataAndType(uri,
				"application/vnd.android.package-archive");
		installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(installIntent);
	}

}
