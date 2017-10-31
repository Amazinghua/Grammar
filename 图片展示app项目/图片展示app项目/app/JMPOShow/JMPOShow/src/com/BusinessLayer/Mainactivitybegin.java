package com.BusinessLayer;

import com.WebLayer.MainActivity;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class Mainactivitybegin extends Service { 

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
//		startService(new Intent(Mainactivitybegin.this, MainActivity.class));// 在此重新启动
	}
	public void onDestroy() {
		super.onDestroy();
		
	}
	
}
