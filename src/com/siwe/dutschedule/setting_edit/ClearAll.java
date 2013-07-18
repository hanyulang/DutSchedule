package com.siwe.dutschedule.setting_edit;

import android.database.sqlite.SQLiteDatabase;


public class ClearAll {
	
	public final static String TABLE_NAME = "classes";// ��1��
	public final static String Day = "day";//���ڼ�
	public final static String No = "no";//�ڼ��ڿ�
	public final static String Name = "name";//����
	public final static String ClassId = "classid";//�γ���
	public final static String Weeks = "weeks";//�Ͽ�����
	public final static String Address = "address";//�Ͽεص�

	
	public void clear(SQLiteDatabase mysql){
		clearClasses(mysql);
		clearScoreAndNoticeAndTest(mysql);
	}
	
	public void clearClasses(SQLiteDatabase mysql){
		for (int i = 1; i < 8; i++)
			for (int j = 1; j < 10; j += 2) {
				// db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES("+i+","+j+","+i+","+j+");");
				mysql.execSQL("update " + TABLE_NAME +" set " + ClassId + "='"
						+ "��" + "', " + Name + "='" +"��"
						+ "'," + Weeks + "='" +"��"+ "'," + Address
						+ "='" +"��"+ "' where " + Day
						+ "='" + i + "' and " + No + "='"
						+ j + "';");;

			}
	}
	
	public void clearScoreAndNoticeAndTest(SQLiteDatabase mysql){
		mysql.execSQL("delete from scores");
		mysql.delete("notices", null, null);
		mysql.delete("tests", null, null);
		//mysql.delete("scores",null,null);
	}
	

}
