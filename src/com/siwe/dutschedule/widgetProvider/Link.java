package com.siwe.dutschedule.widgetProvider;
/** 
 * @author Zhanglinwei
 * @version 2013/3/09
 * С��������������
 */ 


import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.siwe.dutschedule.activity.MySQLiteOpenHelper;
import com.umeng.analytics.MobclickAgent;

public class Link extends Activity {
	private MySQLiteOpenHelper myOpenHelper;
	private SQLiteDatabase mysql ;


	/***
	 * ��ȡС�����ϵ���Ŀγ�
	 * @param 
	 * @return infoArry[0]�γ���
	 *     	   infoArry[1]�γ̺� 
	 *         infoArry[2]�Ͽ��ܴ� 
	 *         infoArry[3]�Ͽεص� 
	 */
	public String[] doSelect() {


		try {
			System.out.println("��ʼʵ�������ݿ�2-----1");
			myOpenHelper = new MySQLiteOpenHelper(this);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("1ʵ����shibai");
		}


		try {
			//#########################    BUG
			//###    �˴����ڳ���Ľ���֮�б���ʹ�þ���·�������޷������ݿ�
			
			//���п�ָ���쳣�������ݿ��ܹ������򿪣���������
			mysql = SQLiteDatabase.openOrCreateDatabase("data/data/com.siwe.dutschedule/databases/schedule.db", null);
			System.out.println("��ʼʵ�������ݿ�2----2");
			//		mysql = this.openOrCreateDatabase("schedule.db",MODE_PRIVATE, null);
			mysql = myOpenHelper.getReadableDatabase();
			//		myOpenHelper.onCreate(mysql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("2ʵ����shibai");
		}   


		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		dayOfWeek = dayOfWeek < 1 || dayOfWeek > 6 ? 7 : dayOfWeek;
		System.out.println(dayOfWeek);

		String[] infoArry = myOpenHelper.selectWithDay(mysql, dayOfWeek);
		System.out.println("get successful");
		return infoArry;
	}

	public void doClose() {
		System.out.println("doClose");
		if (mysql.isOpen())
			mysql.close();
	}
	
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
}
