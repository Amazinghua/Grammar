package com.BusinessLayer;

import java.io.File;

import com.WebLayer.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
			Intent startService = new Intent(context, ScreenService.class);// 解锁和亮屏服务
			startService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(startService);
//			Intent ootStartIntent = new Intent(context, MainActivity.class);
//			ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(ootStartIntent);
	}
	
	
}
