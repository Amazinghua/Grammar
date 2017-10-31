package com.BusinessLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.BusinessLayer.MyLog;
import com.BusinessLayer.DeleteBeforeUD;

public class DeleteBeforeSetup {

	public static void checkDelete(String cachePath) {
		String cacheTemp = cachePath + "/checkDelete" + ".txt";
		MyLog checkD = new MyLog();
		checkD.CreateText(cachePath, cacheTemp);
		String state =readSDFile(cacheTemp).trim();
		if(state == null || state.length() <= 0){
			DeleteBeforeUD.setupDelete();
			checkD.print(cacheTemp,"run");
		}
	}
	
	
	public static String readSDFile(String bootpath_filename) {
		StringBuffer sb = new StringBuffer();
		File file = new File(bootpath_filename);
		try {
			FileInputStream fis = new FileInputStream(file);
			int c;
			while ((c = fis.read()) != -1) {
				sb.append((char) c);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
