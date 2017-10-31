package com.DataLayer;

import java.io.File;
import java.util.ArrayList;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.TextView;

@SuppressLint("SdCardPath")
public class SQLite_DBHelper {
	public static final String DATABASE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/xinhui";
	private final String DATABASE_FILENAME = "xinhuigs2.db";
	public SQLiteDatabase database;
	public Cursor cursor;
	
	public String conditionExpress = "";
	public String sqlCmd = "";
	private ArrayList<DbKeyItem> alFieldItems = new ArrayList<DbKeyItem>();
	private ArrayList<DbKeyItem> alConditionParameters = new ArrayList<DbKeyItem>();
	
	/**
	 * SQLite的增删改都很简单，关键是查询，当你想要执行一些复杂的查询时,这是对查询
	 * 在查询返回的是一个Cursor类型的对象，它是一个指针，且永远都不会为空，
	 * 
	 * */
	
	
	/**
	 * 一个查询语句
	 * 	DBHelper database = new DBHelper();
	*	Cursor cursor = null;
	*	String sql = "";
	*	sql = "select * from t_handindesc where entityno=?";
	*	cursor = database.getCursor(sql, new String[] { entityno });
	*   if (cursor.getCount() > 0) {
	*	cursor.moveToFirst();
	*	do {
	*   string fd= cursor.getString(cursor.getColumnIndex("filecaption"))
	*   	} while (cursor.moveToNext());
	*	} else {
	*		TextView tv1 = new TextView(HandescActivity.this);
	*		tv1.setText("查无记录");
	*		tv1.setTextAppearance(HandescActivity.this, R.style.nomaltext);
	*		tv1.setPadding(0, 5, 0, 5);
	*		lay2.addView(tv1);
	*	}
	*	cursor.close();
	*	database.close();
	* */
	public Cursor getCursor(String sql) {
		if (database == null)
			openDatabase();
		if (database != null)
			cursor = database.rawQuery(sql, new String[] {});

		return cursor;
	}

	public Cursor getCursor(String sql, String[] pars) {
		if (database == null)
			openDatabase();
		if (database != null)
			cursor = database.rawQuery(sql, pars);
		return cursor;
	}
	/**
	 * 
	 * 执行一条语句的方法，比如delete
	 * */
	public void execSQL(String sql) {
		if (database == null)
			openDatabase();
		if (database != null)
			database.execSQL(sql);
	}
	public class DbKeyItem {
		public DbKeyItem(String _fieldName, Object _fieldValue) {
			this.fieldName = _fieldName;
			this.fieldValue = _fieldValue.toString();
		}

		public String fieldName;
		public String fieldValue;
	}
	/**
	 * 这是执行带参数的方法，insert之类的方法其实也是最终调用的是它
	 * 
	 * 
	 * */
	public void execSQL(String sql, String[] pars) {
		if (database == null)
			openDatabase();
		if (database != null)
			database.execSQL(sql, pars);
	}

	public static String getString(Cursor cursor, String colname) {
		String v = "";
		int c = cursor.getColumnIndex(colname);
		if (c >= 0) {
			v = cursor.getString(c);
		}
		if (v == null)
			v = "";

		return v;
	}

	public String DATABASEPATH() {
		return DATABASE_PATH;
	}

	public String DATABASEFILENAME() {
		return DATABASE_FILENAME;
	}
	
	
	/**
	 * 初始化打开sqlite数据库的类
	 * 
	 * 
	 * */
	public void reset() {
		this.alFieldItems.clear();
		this.alConditionParameters.clear();
		this.conditionExpress = "";
		this.sqlCmd = "";

	}
	/**
	 * 插入数据到表中
	 *      DBHelper db = new DBHelper(context);
	 *		db.reset();
	 *		db.addFieldItem("id", Guid.newGuid());
	 *      db.insert("location");
	 *      db.close();
	 * 
	 * */
	public void insert(String tableName) {
		ArrayList<String> pars = new ArrayList<String>();
		String sql1 = "insert into " + tableName + "(";
		String sql2 = "values(";
		for (DbKeyItem item : this.alFieldItems) {
			sql1 += item.fieldName + ",";
			sql2 += "?,";
			pars.add(item.fieldValue);
		}
		String sql = sql1.substring(0, sql1.length() - 1) + ")"
				+ sql2.substring(0, sql2.length() - 1) + ")";
		execSQL(sql, pars.toArray(new String[pars.size()]));

	}
	public void addFieldItem(String _fieldName, Object _fieldValue) {

		for (int i = 0; i < this.alFieldItems.size(); i++) {
			if (((DbKeyItem) this.alFieldItems.get(i)).fieldName
					.equals(_fieldName)) {
				try {
					throw new Exception(_fieldName + "涓嶈兘閲嶅璧嬪??!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.alFieldItems.add(new DbKeyItem(_fieldName, _fieldValue));
	}
	
	/**
	 * 对指定的数据库打开
	 * 
	 * */
	public void openDatabase() {
		try {

			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			File dirFile = new File(DATABASE_PATH);
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
			database = SQLiteDatabase.openDatabase(databaseFilename, null,
					SQLiteDatabase.OPEN_READWRITE);

		} catch (SQLiteException e) {

		} catch (IllegalStateException e) {

		}

	}
	/**
	 * 关闭打开的sqlite数据库
	 * 
	 * */
	public void close() {

		try {
			if (cursor != null)
				cursor.close();
		} catch (SQLiteException e) {

		} catch (IllegalStateException e) {

		} catch (Exception e) {

		}

		try {
			if (database != null)
				database.close();
		} catch (SQLiteException e) {

		} catch (IllegalStateException e) {

		} catch (Exception e) {

		}
	}
}
