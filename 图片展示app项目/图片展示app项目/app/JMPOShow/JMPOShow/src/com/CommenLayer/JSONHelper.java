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
	 * ��ȡjson��ʽ�����ݣ������������ַ�����
	 * ���ӣ�String code = JSONHelper.getValue(result, "code");
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
	 * ��table���б���������
	 * */
	public static void record_table() {
		
		
        String streetdata="";//���ػ���������
	    String data=JSONHelper.getValue(streetdata,"data_road");//data_road����table��
	    JSONArray array;
        try {
            array = new JSONArray(data);
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String dv=obj.getString("street").toString();//street����������Ȼ��Ϳ��Բ��ϵ��ó�������
           }
        }
        catch (Exception ex) {
            String uih=ex.toString();
            String hu="";
        }
	}
	
	
	
     /**��ȡjson��ʽ�����ݣ�����������json����*/
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
	 * �����ת��Ϊjson��ʽ�ķ��������������
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
	 * Cursor 转Json
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