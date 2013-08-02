package com.siwe.dutschedule.infoGeter;

import com.siwe.dutschedule.activity.MySQLiteOpenHelper;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;

public class SubjectGeter extends InfoGeter {

	public final static String TABLE_NAME = "classes";// ��1��
	public final static String ClassId = "classid";// �γ̺�
	public final static String Name = "name";// ����
	public final static String Point = "point";// ѧ��
	public final static String Type = "type"; // �޶�����
	public final static String Teacher = "teacher"; // ��ʦ����
	public final static String Weeks = "weeks";// �Ͽ�����
	public final static String Day = "day";// ���ڼ�
	public final static String No = "no";// �ڼ��ڿ�
	public final static String Address = "address";// �Ͽεص�
	private MySQLiteOpenHelper myOpenHelper;
	private SQLiteDatabase mysql;
	String param;

	public SubjectGeter(Context cont,String par) {
		super(cont);
		this.param = par;
		// TODO Auto-generated constructor stub
		FirstUrl = "http://202.118.65.21:8081/loginAction.do?" + param;
		SecondUrl = "http://202.118.65.21:8081/xkAction.do?actionType=6&oper";
	}

	@Override
	public String format(String get) {
		get = Regular.eregi_replace("(\\s)*", "", get);
		get = Regular.eregi_replace("(������|�Ǽ�����|����|ѡ��|��ѡ)*", "", get);
		get = Regular.eregi_replace("(<htmllang=)(.)*(����)", "", get);
		get = Regular.eregi_replace("(function)(.)*?(</html>)", "", get);
		get = Regular.eregi_replace("(<)(.)*?(>)", "/", get);
		get = Regular.eregi_replace("(&nbsp;)", "/", get);
		get = Regular.eregi_replace("(\\d{4})��((.)*?)(/)", "&&", get);
		get = Regular.eregi_replace("/{2,}", "/", get);
		return get;
	}

	/**
	 * �������ݵ��ܷ���
	 */
	@Override
	public void dealStr(String get) {
		String[] result = get.split("/&&/");
		String[] result2;
		myOpenHelper = new MySQLiteOpenHelper(super.context);
		mysql = myOpenHelper.getReadableDatabase();

		// ��Ҫ��ʼ����ֹ�γ������ۼӣ���
		for (int i = 1; i < 8; i++)
			for (int j = 1; j < 10; j += 2) {
				mysql.execSQL("update " + TABLE_NAME + " set " + ClassId + "='"
						+ "��" + "', " + Name + "='" + "��" + "'," + Weeks + "='"
						+ "��" + "'," + Address + "='" + "��" + "'," + Teacher
						+ "='" + "��" + "'," + Type + "='" + "��" + "'," + Point
						+ "='" + "��" + "' where " + Day + "='" + i + "' and "
						+ No + "='" + j + "';");
			}
		System.out.println("���ݱ�classes���γ�ʼ��");

		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
			result2 = result[i].split("/");
			for (int j = 0; j < result2.length; j++) {
				if (result2.length == 14) {// һ��һ��
					judgeByNum(mysql, result2, 1);
				}

				if (result2.length == 21) { // һ������
					judgeByNum(mysql, result2, 2);
				}

				if (result2.length == 28) { // һ������
					judgeByNum(mysql, result2, 3);
				}

				if (result2.length == 35) { // һ���Ľ�
					judgeByNum(mysql, result2, 4);
				}
			}
		}
		mysql.close();
		saveParam(); 
	}

	/**
	 * ��ÿ�ܽڴ������ݿ��в�����Ϣ
	 * @param db
	 * @param test
	 * @param bug
	 */
	public void judgeByNum(SQLiteDatabase db, String[] test, int bug) {
		for (int i = 0; i < bug; i++) {
			Integer no = Integer.parseInt(test[9 + i * 7], 10); // ���ַ���ת��Ϊint
																	// �ڴ�13579
			Integer numbers = Integer.parseInt(test[10 + i * 7], 10); // ���� 2 3
			// 4 6 8
			int flag = numbers / 2;

			// �Ľ��������ε����
			for (int j = 0; j < flag; j++) {
				int temp2 = no + j * 2; // �����Ľڴ�

				db.execSQL("update " + TABLE_NAME + " set " + ClassId + "='"
						+ test[0] + "', " + Name + "='" + test[1] + "',"
						+ Point + "='" + test[3] + "'," + Type + "='" + test[6]
						+ "'," + Teacher + "='" + test[4] + "'," + Weeks + "='"
						+ test[7 + i * 7] + "'," + Address + "='"
						+ test[12 + i * 7] + test[13 + i * 7] + "' where "
						+ Day + "='" + test[8 + i * 7] + "' and " + No + "='"
						+ temp2 + "';");
			}
		}

	}

	/**
	 * �����û������������sharedPreference
	 */
	private void saveParam(){
		Editor editor;
		//context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
		editor = context.getSharedPreferences("filename", Context.MODE_PRIVATE).edit();
		editor.putString("usernamepassword", param);
		editor.commit();
	}

}
