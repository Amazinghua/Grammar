package com.BusinessLayer;

import com.WebLayer.MainActivity;

import android.app.KeyguardManager;
import android.app.Service;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

/**
 * ����������񣬷�������ע��㲥���㲥������mainactivity��ִ�������ͽ�������
 * http://www.th7.cn/Program/Android/201404/190738.shtml
 */
public class ZdLockService extends Service {
	KeyguardManager mKeyguardManager;
	KeyguardLock mKeyguardLock;
	Intent zdLockIntent;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		final IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		registerReceiver(testReceiver, filter);
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY;
	}

	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(testReceiver);
		startService(new Intent(ZdLockService.this, ZdLockService.class));// �ڴ���������
	}

	/** ����һ���㲥����Ҫ�жϺ������������� */
	public BroadcastReceiver testReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (Intent.ACTION_SCREEN_ON.equals(action)) {
				try {
					final Intent intentw = new Intent();
					intentw.setAction("ITOP.MOBILE.SIMPLE.SERVICE.SENSORSERVICE");
					stopService(intentw);
				} catch (Exception ex) {
				}
				
			} else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
				CheckLoadImg cli = new CheckLoadImg(ZdLockService.this);
				if (cli.checkalwayrun()) {
//					MyLog mLog = new MyLog();
//					mLog.print(mLog.filenameTempLog, mLog.logText("ZdLockService.this"));
					Intent alarmIntent = new Intent(context, MainActivity.class);
					alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(alarmIntent);
					Intent startService = new Intent(ZdLockService.this,
							ScreenService.class);// ��������������
					startService(startService);
				}

			} else if (Intent.ACTION_USER_PRESENT.equals(action)) {
				// Toast.makeText(MainActivity.this, "screen unlock",
				// Toast.LENGTH_SHORT).show();
			}
		}
	};
}