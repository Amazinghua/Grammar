package com.CommenLayer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;

public class JSONHelper {
	
	
	/**
	 * 获取json格式的数据，传进来的是字符串，
	 * 例子：String code = JSONHelper.getValue(result, "code");
	 * */
	public static String getValue(String data, String key) {
		String r = "";
		try {
			JSONObject jsonObject = new JSONObject(data);
			r = jsonObject.getString(key);
		} catch (Exception e) {

		}
		return r;
	}
	
	/**
	 * 对table进行遍历的例子
	 * */
	public static void record_table() {
		
		
        String streetdata="";//返回回来的数据
	    String data=JSONHelper.getValue(streetdata,"data_road");//data_road就是table名
	    JSONArray array;
        try {
            array = new JSONArray(data);
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String dv=obj.getString("street").toString();//street就是列名，然后就可以不断的拿出数据了
           }
        }
        catch (Exception ex) {
            String uih=ex.toString();
            String hu="";
        }
	}
	
	
	
     /**获取json格式的数据，传进来的是json对象*/
	public static String getValue(JSONObject jsonObject, String key) {
		String r = "";

		try {
			r = jsonObject.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return r;
	}
	/**
	 * 这个是转换为json格式的方法，传入进来是
	 * HashMap<String, String> map = new HashMap<String, String>();
	 * map.put("cmd", "location");
	 * */
	@SuppressWarnings("rawtypes")
	public static String toJson(HashMap<String, String> map) {

		JSONObject jsonObjectTT = new JSONObject();
		try {
			Iterator iter = map.keySet().iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
				Object val = map.get(key);
				jsonObjectTT.put(key.toString(),
						val == null ? "null" : val.toString());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObjectTT.toString();
	}

	/*
	 * Cursor 杞琂son
	 */
	public static String ToJson(Cursor cur, String tablename) {
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("\"");
		jsonBuilder.append(tablename);
		jsonBuilder.append("\":[");
		if (cur != null && cur.moveToFirst()) {
			if (cur.getCount() == 0) {
				jsonBuilder.append(",");
			}
			do {
				StringBuilder jsonBuilder2 = new StringBuilder();
				jsonBuilder2.append("{");
				for (int i = 0; i < cur.getColumnCount(); i++) {
					
					jsonBuilder2.append("\"");
					jsonBuilder2.append(cur.getColumnName(i).toLowerCase(
							Locale.getDefault()));
					jsonBuilder2.append("\":\"");

					String v = null;
					try {

						v = cur.getString(i);
					} catch (Exception ex) {
					}

					if (v == null) {
						v = "";
					}
					v = v.replace("\"", "\\\"");
					jsonBuilder2.append(v);

					jsonBuilder2.append("\",");
				}

				int len = jsonBuilder2.toString().length();
				jsonBuilder.append(jsonBuilder2.toString()
						.substring(0, len - 1));
				jsonBuilder.append("},");
			} while (cur.moveToNext());
		}
		if (jsonBuilder.toString().endsWith("[")) {
			jsonBuilder.append(",");
		}
		String str = jsonBuilder.toString();
		str = str.substring(0, str.length() - 1) + "]";
		return str;
	}	
}