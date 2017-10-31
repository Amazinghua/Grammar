package com.BusinessLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.ImageView.ScaleType;

/**
 * �������Ҫʵ�ֵĹ����ǻ�ȡͼƬ��·���Ͷ�ͼƬ��ѹ������
 * 
 * */
public class ImageAdapter {
	private int[] res;
	private Context context;
	private HashMap<String, String> imageMap = new HashMap<String, String>();

	/**
	 * ��ԴͼƬѹ��Ȼ�󷵻�һ��ͼƬ�����Ƕ�ͼƬ��ѹ���������
	 * */
	public View getView(int position) {
		ImageView image = new ImageView(context);
		Bitmap bitmap = readBitMap("");
		image.setImageBitmap(bitmap);
		return image;
	}

	/**
	 * ��ȡ·�������
	 * */
	public void Get_pictrue() {
		new Thread(new GetImageFilePathThread()).start();
	}

	/**
	 * ����ʡ�ڴ�ķ�ʽ��ȡ������Դ��ͼƬ
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(String url){ 
	  Bitmap bitmap = null; 
      BitmapFactory.Options opt = new BitmapFactory.Options();  
      opt.inPreferredConfig = Bitmap.Config.RGB_565;   
      opt.inPurgeable = true;  
      opt.inSampleSize = 20;   //width��hight��Ϊԭ����ʮ��һ
      opt.inInputShareable = true;  
      InputStream is;
      URL pictureUrl;
      //��ȡ��ԴͼƬ  
      try 
      { 
       pictureUrl= new URL(url); 
       is = pictureUrl.openStream();  
       bitmap= BitmapFactory.decodeStream(is,null,opt); 
      } catch (IOException e) 
      { 
       e.printStackTrace(); 
      } 
      return bitmap;
    }
	/**
	 * ����ͼƬ
	 * 
	 * */
	public void savePicture(Bitmap bitmap) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				File sdcardDir = Environment.getExternalStorageDirectory();
				Time t = new Time();
				t.setToNow();
				String filename = sdcardDir.getCanonicalPath()
						+ "/DCIM/camera"
						+ String.format("/ReeCam%04d%02d%02d%02d%02d%02d.jpg",
								t.year, t.month + 1, t.monthDay, t.hour,
								t.minute, t.second);
				File file = new File(filename);
				FileOutputStream out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** ��ȡ��Ƭ���ͼƬ����ѹ������֮��Ĳ��� */
	Handler handler = new Handler() {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void handleMessage(Message msg) {
			HashMap<String, String> map = (HashMap<String, String>) msg.obj;
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String filePath = (String) entry.getKey();// �ļ���·��
				String bitmapPath = (String) entry.getValue();// �ļ���·���ټ���ͼƬ������
				String jhu = "";
				// ��ͼƬ��ӵ����������棬�Ա����ͼƬ������
				// adapter.addData(filePath, bitmapPath);
				// adapter.notifyDataSetChanged();
			}
			map.clear();
			super.handleMessage(msg);
		}
	};

	/** ��ȡ�ļ������ͼƬ�ļ����߳� */
	private class GetImageFilePathThread implements Runnable {
		@Override
		public void run() {
			File file = new File(Environment.getExternalStorageDirectory()
					+ "/phoneshow");
			getFileList(file);
		}
	}

	private void getFileList(File file) {
		File[] files = file.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isFile()) {
					if (".png".equals(getFileEx(f))) {
						String dd = f.getAbsolutePath();
						if (!imageMap.containsKey(f.getAbsolutePath())) {
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

					if (".jpg".equals(getFileEx(f))) {
						if (!imageMap.containsKey(f.getAbsolutePath())) {

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