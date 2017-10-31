package com.BusinessLayer;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class ScreenService extends Service {

	// �������̹�����
	KeyguardManager mKeyguardManager = null;
	// ����������
	private KeyguardLock mKeyguardLock = null;
	// ������Դ������
	private PowerManager pm;
	private PowerManager.WakeLock wakeLock;
	public static final String ACTION = "com.example.codetest_1.action.startPlayService";

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		// ��ȡ��Դ�ķ���
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		// ��ȡϵͳ����
		mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// ��������
		wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.FULL_WAKE_LOCK, "My Tag");
		wakeLock.acquire();
		Log.i("Log : ", "------>mKeyguardLock");
		// ��ʼ��������������������⿪������
		mKeyguardLock = mKeyguardManager.newKeyguardLock("");
		// ������ʾ��������
		mKeyguardLock.disableKeyguard();
	}

	@Override
	public void onDestroy() {
		wakeLock.release();
		super.onDestroy();
	}
}