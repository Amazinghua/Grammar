package com.CommenLayer;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.content.ContextWrapper;
import com.BusinessLayer.DeleteBeforeUD;
import com.WebLayer.MainActivity;

public class CheckNewVersion {
	private Activity contexts;

	public void CheckNewVersion_thread(Activity context) {
		this.contexts = context;
		new MyTask().execute();
	}

	String currentVersion = "";
	String serverVerion = "";
	int tcint;
	int tsint;

	// ���Ǽ���°汾���첽�߳�
	public class MyTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {

			PackageManager manager = contexts.getPackageManager();
			PackageInfo info;
			try {
				info = manager.getPackageInfo(contexts.getPackageName(), 0);
				currentVersion = String.valueOf(info.versionCode);// ��ȡ��ǰapp�İ汾��
				String data = "";
				String url = ServerURL.newversion();
				serverVerion = PostData.getInput2(url);// ��ȡ�������İ汾��

			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return serverVerion;
		}

		@Override
		protected void onPostExecute(String result) {
			if (serverVerion.startsWith("error"))
				return;
			serverVerion = result.replace("\r", "").replace("\n", "");
			tcint = 0;
			tsint = 0;
			result = result.replace("\r", "").replace("\n", "");
			try {
				tsint = Integer.parseInt(serverVerion.replaceAll("\\D+", "")
						.replaceAll("\r", "").replaceAll("\n", "").trim(), 10);
				tcint = Integer.parseInt(currentVersion.trim());

			} catch (Exception e) {
				String ds = e.toString();
				return;
			}
			if (tcint < tsint) {
				Builder builder = new Builder(contexts);
				builder.setMessage("���µİ汾������Ҫ������")
						.setCancelable(false)
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {										
										Intent intent = new Intent(contexts,
												UpdateService.class);
										contexts.startService(intent);										
//										DeleteBeforeUD.beforeDelete();
									}
								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										return;
									}
								}).setTitle("ѯ��");
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		
	}
}
