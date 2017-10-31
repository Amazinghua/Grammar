package com.WebLayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.protocol.ClientContextConfigurer;

import com.BusinessLayer.CheckLoadImg;
import com.BusinessLayer.DeleteBeforeSetup;
import com.BusinessLayer.Mainactivitybegin;
import com.BusinessLayer.ScreenObserver;
import com.BusinessLayer.ScreenObserver.ScreenStateListener;
import com.BusinessLayer.ScreenService;
import com.BusinessLayer.ZdLockService;
import com.CommenLayer.CheckNewVersion;
import com.CommenLayer.UpdateService;
import com.DataLayer.FtpData;
import com.DataLayer.NewFtpData;
import com.jmposhow.*;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings.System;
import android.R.array;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import android.view.View.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.widget.*;

public class MainActivity extends Activity {

	private ImageSwitcher mImageSwitcher;
	private int currentPosition = 0;
	private int threadrun = 0;
	private ScheduledExecutorService scheduledExecutorService;
	private Message msg;
	private List imagePathList;
	boolean refresh = false;
	// private ImageView logo;
	PowerManager pm = null;
	WakeLock wl = null;
	private boolean checkOn = true;
	public static boolean isConnect;
	private boolean isRes = true;
	private int[] onimageList={1,2,3,4,5};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		this.pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
		this.wl = this.pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "TAG");
		KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardLock KeyguardLockkl = km.newKeyguardLock("unLock");
		KeyguardLockkl.disableKeyguard(); // 解锁
		setContentView(R.layout.activity_main);
		setupViewComponent();
		new checkrefreshimg().execute();
		Log.i("更新", "更新");
		Intent startService = new Intent(this, ZdLockService.class);
		startService(startService);
		CheckNewVersion newversion = new CheckNewVersion();
		newversion.CheckNewVersion_thread(MainActivity.this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.wl.acquire();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.wl.release();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		
	}

	public class checkrefreshimg extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			boolean deleteOn = true;
			try {
				if (checkOn) {
					String a = Boolean.toString(checkOn);
					Log.i("锁住", a);
					checkOn = false;
					isConnect = isWifiConnected();
					String b = Boolean.toString(isConnect);
					Log.i("网络", b);
					if(deleteOn){
						deleteOn=false;
						try {
							String deletePath = getCachePath(MainActivity.this);
							DeleteBeforeSetup.checkDelete(deletePath);
							Log.i("检测是否删除文件", "删除");
						} catch (Exception e) {
							// TODO: handle exception
							Log.i("删除异常", e.toString());
						}					
					}
					CheckLoadImg startLoad = new CheckLoadImg(MainActivity.this);
					refresh = startLoad.chechrefreshimg();
					checkOn = true;
					Log.i("checkOn", "checkOn");
				}

			} catch (Exception e) {
				Log.i("异常", e.toString());
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			String a = Boolean.toString(refresh);
			Log.i("refresh", a);
			if (refresh) {
				Log.i("success", "成功");
				try {
					Log.i("success", "成功1");
					scheduledExecutorService.shutdown();
					imagePathList.clear();
					MainActivity.this.finish();					
				} catch (Exception e) {
					Log.i("失败", e.toString());
				}
				Log.i("重启", "重启");
				restartApplication();
				Log.i("执行", "执行");
			}
		}

	}
	//退出应用
	public void exit() {
		android.os.Process.killProcess(android.os.Process.myPid());
    }

	// 检测是否有网络链接
	private boolean isWifiConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null) {
				return true;
			}
		}
		return false;
	}

	// 更新完后重新启动
	private void restartApplication() {
		final Intent intent = getPackageManager().getLaunchIntentForPackage(
				getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	//获取path
	public static String getCachePath(Context context) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			// 外部存储可用
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			// 外部存储不可用
			cachePath = context.getCacheDir().getPath();
		}
		return cachePath;
	}

	/** 页面初始值 */
	public void setupViewComponent() {
		currentPosition = 0;
		Log.i("setupViewComponent", "run");
		mImageSwitcher = (ImageSwitcher) this.findViewById(R.id.imageSwitcher1);
		Log.i("setupViewComponent", "run_setupViewComponent");
		getImagePathFromSD();
		Log.i("getImagePathFromSD", "run_getImagePathFromSD");
		initscheduledExecutorService();// 图片展示的方法
		Log.i("Service", "run_Service");

	}

	/** 页面跳转到限制设计页面 */
	public class handlelimite implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, HandleLimite.class);
			MainActivity.this.startActivity(intent);
		};
	}

	/** 从SD卡中获取资源图片的路径 */

	private void getImagePathFromSD() {
		/* 设定目前所在路径 */
		imagePathList = new ArrayList();
		imagePathList.clear();
		// 根据自己的需求读取SDCard中的资源图片的路径
		String imagePath = Environment.getExternalStorageDirectory().toString()
				+ "/phoneshow";
		File path1 = new File(imagePath);
		if (!path1.exists()) {
			path1.mkdirs();
		}
		File mFile = new File(imagePath);
		File[] files = mFile.listFiles();
		/* 将所有文件存入ArrayList中 */
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (checkIsImageFile(file.getPath())){
				imagePathList.add(file.getPath());
				isRes = false;
			}				
		}
	}
	
	/** 判断是否相应的图片格式 */

	private boolean checkIsImageFile(String fName) {
		boolean isImageFormat;
		/* 取得扩展名 */
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();
		/* 按扩展名的类型决定MimeType */
		if (end.equals("jpg") || end.equals("png") || end.equals("jpeg")) {
			isImageFormat = true;
		} else {
			isImageFormat = false;
		}
		return isImageFormat;
	}

	/**
	 * 定义定时时间间隔执行方法的一个线程
	 * 
	 * */
	private void initscheduledExecutorService() {
		/**
		 * 这是一个线程执行任务
		 * */
		try {
			scheduledExecutorService.shutdown();
		} catch (Exception e) {
			e.toString();
		}

		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		/**
		 * 或排定某个工作5秒后执行，之后每30秒执行一次： 当Activity显示出来后，每八秒钟切换一次图片显示
		 * */
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 0, 8,
				TimeUnit.SECONDS);
	}

	/** 循环 */
	private class ScrollTask implements Runnable {
		public synchronized void run() {
			if(isRes){
				if(currentPosition<onimageList.length-1){
					threadrun = 1;
					int dd = currentPosition;
					int jn = onimageList.length;
					currentPosition++;
					msg = mhandler.obtainMessage(1, currentPosition, 0);
				}
			}
			else {
				if (currentPosition < imagePathList.size() - 1) {
					new checkrefreshimg().execute();
					threadrun = 1;
					int dd = currentPosition;
					int jn = imagePathList.size();
					currentPosition++;
					msg = mhandler.obtainMessage(1, currentPosition, 0);
				}
			}
			mhandler.sendMessage(msg);
		}
	}
	// 通过handler来更新主界面
	// mgallery.setSelection(positon),选中第position的图片，然后调用OnItemSelectedListener监听改变图像
	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				rightIn(msg.arg1);
			}
		}
	};

	private void rightIn(int position) {
		if(isRes){
			if (position == onimageList.length - 1) {
				currentPosition = -1;
			}
			BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
			bitmapFactoryOptions.inSampleSize = 8;
			bitmapFactoryOptions.inPurgeable = true;
			Bitmap bit;
			bit = getRes("image_"+(position+1));
			Drawable drawable = new BitmapDrawable(bit);			
			mImageSwitcher.setBackgroundDrawable(drawable);
			threadrun = 0;
		}
		else{
			try {
				if (position == imagePathList.size() - 1) {
					currentPosition = -1;
				}
				mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
						android.R.anim.fade_in));
				mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
						android.R.anim.fade_out));
				String pathimg = imagePathList.get(position).toString();
				File f = new File(pathimg);
				if(f.exists()){
					BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
					bitmapFactoryOptions.inSampleSize = 8;
					bitmapFactoryOptions.inPurgeable = true;
					Bitmap bit;
					try {
						bit = BitmapFactory.decodeFile(pathimg,
								bitmapFactoryOptions);
					} catch (OutOfMemoryError err) {
						BitmapFactory.Options opts = new BitmapFactory.Options();
						opts.inSampleSize = 10;
						bit = BitmapFactory.decodeFile(pathimg, opts);
					} catch (Throwable e) {
						BitmapFactory.Options opts = new BitmapFactory.Options();
						opts.inSampleSize = 10;
						bit = BitmapFactory.decodeFile(pathimg, opts);
					}
					Bitmap bm = changeimg(pathimg, bit);
					Drawable drawable = new BitmapDrawable(bm);
					mImageSwitcher.setBackgroundDrawable(drawable);
					threadrun = 0;
				} 
				else {
					String a = imagePathList.get(position).toString();
					imagePathList.remove(imagePathList.get(position));
				}
			} catch (Exception ex) {
				String dvd = ex.toString();
			}
		}
	}

	public Bitmap getRes(String name) {
		ApplicationInfo appInfo = getApplicationInfo();
		int resID = getResources().getIdentifier(name, "drawable",
				"com.jmposhow");
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		Bitmap bm =  BitmapFactory.decodeResource(getResources(), resID);
		
		// 获取到这个图片的原始宽度和高度
		int picWidth = opt.outWidth;
		int picHeight = opt.outHeight;

		// 获取屏的宽度和高度
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();

		// isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
		opt.inSampleSize = 1;
		// 根据屏的大小和图片大小计算出缩放比例
		if (picWidth > picHeight) {
			if (picWidth > screenWidth)
				opt.inSampleSize = picWidth / screenWidth;
		} else {
			if (picHeight > screenHeight)

				opt.inSampleSize = picHeight / screenHeight;
		}
		
		// 这次再真正地生成一个有像素的，经过缩放了的bitmap
		opt.inJustDecodeBounds = false;
		bm =  BitmapFactory.decodeResource(getResources(), resID);
		return bm;
	}

	
	public Bitmap changeimg(String absolutePath, Bitmap bm) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		bm = BitmapFactory.decodeFile(absolutePath, opt);

		// 获取到这个图片的原始宽度和高度
		int picWidth = opt.outWidth;
		int picHeight = opt.outHeight;

		// 获取屏的宽度和高度
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();

		// isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
		opt.inSampleSize = 1;
		// 根据屏的大小和图片大小计算出缩放比例
		if (picWidth > picHeight) {
			if (picWidth > screenWidth)
				opt.inSampleSize = picWidth / screenWidth;
		} else {
			if (picHeight > screenHeight)

				opt.inSampleSize = picHeight / screenHeight;
		}

		// 这次再真正地生成一个有像素的，经过缩放了的bitmap
		opt.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(absolutePath, opt);
		return bm;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
