package com.siwe.dutschedule.activity;

/** 
 * @author Zhanglinwei
 * @version 2013/3/05
 * ���ݿ�����
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	// �γ̱�1����
	public final static int VERSION = 1;// �汾��
	public final static String TABLE_NAME = "classes";// ��1��
	public final static String ClassId = "classid";// �γ̺�
	public final static String Name = "name";// ����
	public final static String Point = "point";//  ѧ��
	public final static String Type = "type"; //�޶�����
	public final static String Teacher = "teacher"; //��ʦ����
	public final static String Weeks = "weeks";// �Ͽ�����
	public final static String Day = "day";// ���ڼ�
	public final static String No = "no";// �ڼ��ڿ�
	public final static String Address = "address";// �Ͽεص�
	public static final String DATABASE_NAME = "schedule.db";// �����ļ���
	// ������2����
	public final static String TABLE_NAME2 = "scores";
	public final static String Name2 = "name2";
	public final static String Xiu = "xiu";
	public final static String Xuefen = "xuefen";
	public final static String Score = "score";
	//������Ϣ��3
	public final static String TABLE_NAME3 = "notices";
	//������Ϣ��3
	public final static String TABLE_NAME4 = "tests";


	// ��д����

	public MySQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	public boolean tableis(SQLiteDatabase db, String tableName) {
		Cursor cur = db.rawQuery(
				"select count(*) from sqlite_master where type ='table' and name = '"
						+ tableName + "'", null);
		if (cur.moveToNext()) {
			int count = cur.getInt(0);
			if (count > 0) {
				return true;
			}
		}
		cur.close();
		return false;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (!this.tableis(db, TABLE_NAME)) {
			String str_sql = "create table " + TABLE_NAME + "(" + Day
					+ " integer," + No + " integer," + Name + " text,"
					+ ClassId + " text," + Weeks + " text," + Address+" text,"+ Point + " text,"+ Type + " text,"
					+ Teacher + " text);";
			db.execSQL(str_sql);
			// �״δ���ʱ��ֵ
			for (int i = 1; i < 8; i++)
				for (int j = 1; j < 10; j += 2) {
					db.execSQL("insert into " + TABLE_NAME + " VALUES(" + i
							+ "," + j + ",'��','��','��','��','��','��','��');");

				}
			System.out.println("���ݱ�classes��ʼ��");
		}

		if (!this.tableis(db, TABLE_NAME2)) {
			String str_sql2 = "create table " + TABLE_NAME2 + "(" + Name2
					+ " text," + Xiu + " text," + Xuefen + " text," + Score
					+ " text);";
			db.execSQL(str_sql2);
			// db.execSQL("insert into "+TABLE_NAME2+" VALUES("+1+",null,null,null);");
			System.out.println("���ݱ�scores���γ�ʼ��");
		}

		if (!this.tableis(db, TABLE_NAME3)) {
			String str_sql3 = "create table " + TABLE_NAME3 + "(url text,_id text,date text);";
			db.execSQL(str_sql3);
			System.out.println("���ݱ�notices���γ�ʼ��");
		}
		if (!this.tableis(db, TABLE_NAME4)) {
			String str_sql4 = "create table " + TABLE_NAME4 + "(week text,_id text,position text,time text);";
			db.execSQL(str_sql4);
			System.out.println("���ݱ�tests���γ�ʼ��");
		}

	}



	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.v("schedule", "onUpgrade");
	}

	// �����ڽڴλ�ȡ���пγ���Ϣ
	/** *infoArry[0]�γ��� infoArry[1] �γ̺� infoArry[2] �Ͽ��ܴ� infoArry[3]�Ͽεص�   infoArry[4]ѧ��
	 * *infoArry[5]�޶�����    infoArry[6]��ʦ����*/
	public String[] selectWithDayAndNo(SQLiteDatabase db, int day, int no) {

		String[] infoArry = new String[7];
		Cursor cur = db.rawQuery("select * from classes where day=" + day
				+ " AND no=" + no + ";", null);
		if (cur != null) {
			while (cur.moveToNext()) {
				infoArry[0] = cur.getString(2); // �γ���
				infoArry[1] = cur.getString(3); // �γ̺�
				infoArry[2] = cur.getString(4); // �Ͽ��ܴ�
				infoArry[3] = cur.getString(5); // �Ͽεص�
				infoArry[4] = cur.getString(6); // ѧ��
				infoArry[5] = cur.getString(7); // �޶�����
				infoArry[6] = cur.getString(8); // ��ʦ����
			}
		}
		System.out.println(infoArry[0] + infoArry[1] + infoArry[2]
				+ infoArry[3]);
		cur.close();
		return infoArry;
	}

	//��ȡ����Ŀγ� 
	/** *infoArry[0]�γ��� infoArry[1] �γ̺� infoArry[2] �Ͽ��ܴ� infoArry[3]�Ͽεص� * */
	public String[] selectWithDay(SQLiteDatabase db, int day) {
		String[] infoArry = { "", "", "", "" };	
		//String judge;
		Cursor cur = db.rawQuery(
				"select * from classes where day=" + day + ";", null);
		if (cur != null) {
			while (cur.moveToNext()) {

			/*	//�ж��Ƿ����Ͽ�������
				judge = cur.getString(4);
				System.out.println(judge);
				if(!judge.equalsIgnoreCase("��")){
					String[] spli =  judge.split("-|��");
					if(spli.length==3){
						int from=Integer.parseInt(spli[0]);
						int to=Integer.parseInt(spli[1]);
						if(week<from||week>to){
							continue;
						}
					}
				}*/
				// ע�⣬�˴���ȡ����ϢΪ�������������
				infoArry[0] += cur.getString(2) + "&"; // �γ���
			//	infoArry[1] += cur.getString(3) + "&"; // �γ̺�
				infoArry[2] += cur.getString(4) + "&"; // �Ͽ��ܴ�
				infoArry[3] += cur.getString(5) + "&"; // �Ͽεص�
			}
		} else {
			System.out.println("��ȡ����");
		}
		cur.close();
		return infoArry;

	}

	//

}