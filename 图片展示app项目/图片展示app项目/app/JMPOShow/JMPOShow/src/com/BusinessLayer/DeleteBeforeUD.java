package com.BusinessLayer;

import java.io.File;

public class DeleteBeforeUD {
	public static boolean checkVersion = false;
	public static String path = android.os.Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/phoneshow";
	
	public static void beforeDelete(){
		File dir = new File(path);
		delete(dir);
		checkVersion=true;
	}
	
	public static void setupDelete(){
		File dir = new File(path);
		delete(dir);		
	}
	

	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	} 
	
}
