package com.BusinessLayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

@SuppressLint("HandlerLeak")
public class ReadPictrue  {

	private ArrayList<String> imagePathList = null;

	private ArrayList<String> bitMapList = null;

	private HashMap<String, String> imageMap = new HashMap<String, String>();


	private void initView() {

	
		new Thread(new GetImageFilePathThread()).start();
	
	}

	Handler handler = new Handler() {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void handleMessage(Message msg) {
			HashMap<String, String> map = (HashMap<String, String>) msg.obj;
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String filePath = (String) entry.getKey();
				String bitmapPath = (String) entry.getValue();
				//把图片添加到适配器里面，以便调整图片的属�?
//				adapter.addData(filePath, bitmapPath);
//				adapter.notifyDataSetChanged();
			}
			map.clear();
			super.handleMessage(msg);
		}
	};

	private class GetImageFilePathThread implements Runnable {
		@Override
		public void run() {

			File file = new File(Environment.getExternalStorageDirectory() + "");

			getFileList(file);
		}
	}

	private void getFileList(File file) {
		File[] files = file.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isFile()) {
					if (".png".equals(getFileEx(f))) {
						if (!imageMap.containsKey(file.getAbsolutePath())) {
							imageMap.put(file.getAbsolutePath(),
									f.getAbsolutePath());
							HashMap<String, String> temp = new HashMap<String, String>();
							temp.put(file.getAbsolutePath(),f.getAbsolutePath());
							Message msg = handler.obtainMessage();
							msg.obj = temp;
							handler.sendMessage(msg);
						}
					}

					if (".jpg".equals(getFileEx(f))) {
						if (!imageMap.containsKey(file.getAbsolutePath())) {

							imageMap.put(file.getAbsolutePath(),
									f.getAbsolutePath());
							HashMap<String, String> temp = new HashMap<String, String>();
							temp.put(file.getAbsolutePath(),
									f.getAbsolutePath());
							Message msg = handler.obtainMessage();
							msg.obj = temp;
							handler.sendMessage(msg);
						}
					}
				} else {
					getFileList(f);
				}
			}
		}
	}

	public String getFileEx(File file) {
		String fileName = file.getName();
		int index = fileName.indexOf('.');
		if (index != -1) {
			int length = fileName.length();
			String str = fileName.substring(index, length);
			return str;
		}
		return "";
	}


}