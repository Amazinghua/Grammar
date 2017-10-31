package com.BusinessLayer;

import android.util.Log;

import com.DataLayer.FtpData;
import com.DataLayer.NewFtpData;

public class Loadimg {
	/**
	 * ¸üÐÂÍ¼Æ¬
	 * */
	public void loaddownimg() {

//		FtpData ftp = new FtpData();
//		ftp.loaddownimg();
//		ftp.copys();
//		ftp.closeServer();
		NewFtpData ftp = new NewFtpData();
		ftp.ftpdata();
		ftp.copys();
//		ftp.closeServer();

	}

}
