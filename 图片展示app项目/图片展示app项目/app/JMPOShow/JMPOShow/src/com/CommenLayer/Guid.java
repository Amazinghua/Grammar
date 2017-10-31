package com.CommenLayer;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Guid {

	public static String newGuid() {

		Random rd = new Random();
		String ff = String.valueOf(rd.nextDouble())
				+ String.valueOf(rd.nextDouble())
				+ String.valueOf(rd.nextDouble())
				+ String.valueOf(rd.nextDouble())
				+ String.valueOf(rd.nextDouble())
				+ String.valueOf(rd.nextDouble());
		SimpleDateFormat sdf = new SimpleDateFormat("",
				Locale.SIMPLIFIED_CHINESE);
		sdf.applyPattern("yyyyMMddHHmmss");
		String timeStr = sdf.format(new Date());

		String s = timeStr + ff;

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			String tmep = new String(s.getBytes(), "GBK");
			byte[] strTemp = tmep.getBytes("UTF8");
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 鍒嗘垚楂樹綆4浣嶅鐞?
				str[k++] = hexDigits[byte0 & 0xf];
			}
			String Id = new String(str);
			Id = Id.substring(0, 8) + "-" + Id.substring(8, 12) + "-"
					+ Id.substring(12, 16) + "-" + Id.substring(16, 20) + "-"
					+ Id.substring(20, 32);

			return Id;
		} catch (Exception e) {
			return "39ebf36b-0105-1000-e000-017c0a0e4019";
		}
	}

	public static String md5(String s) {
		String Id = "";
		try {
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
					'9', 'a', 'b', 'c', 'd', 'e', 'f' };
			String tmep = new String(s.getBytes(), "GBK");
			byte[] strTemp = tmep.getBytes("UTF8");
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 鍒嗘垚楂樹綆4浣嶅鐞?
				str[k++] = hexDigits[byte0 & 0xf];
			}
			Id = new String(str);

		} catch (Exception e) {

		}
		return Id;
	}


}