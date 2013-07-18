package com.siwe.dutschedule.activity;
/** 
 * @author Zhanglinwei
 * @version 2013/3/13
 * �ֶ���ȡ����Ŀγ�����
 */ 

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.siwe.dutschedule.R;
import com.siwe.dutschedule.setting_edit.SetBackgroundImage;
import com.umeng.analytics.MobclickAgent;

//���ܣ���ȡ����Ŀγ����ݲ�����

//�̳�PreferenceActivity����ʵ��OnPreferenceChangeListener��OnPreferenceClickListener�����ӿ�
public class ClassEdit extends PreferenceActivity implements OnPreferenceClickListener,
OnPreferenceChangeListener{
	//������ر���
	MySQLiteOpenHelper myOpenHelper;
	SQLiteDatabase  mysql ; // ʵ�����ݿ�
    GestureDetector detector;
	String updateFrequencyKey;
	String strclassno;
	String strclassdetail;
	String strclassname;
	String strclassaddress;
	String strclassweeks;
	int day=1,no=1;
	ListPreference updateFrequencyListPref;
	ListPreference classno;
	PreferenceCategory classdetail;
	EditTextPreference classname;
	EditTextPreference classaddress;
	EditTextPreference classweeks;


	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
        detector = new GestureDetector(this, new backGestureListener(this));
		myOpenHelper = new MySQLiteOpenHelper(this);
		mysql = myOpenHelper.getWritableDatabase(); 
		setContentView(R.layout.activity_edit);
		SetBackgroundImage.setTheme(this, null, null);
		addPreferencesFromResource(R.xml.preferences); 
		//��ȡ����Preference
		updateFrequencyKey = getResources().getString(R.string.auto_update_frequency_key);
		strclassno =  getResources().getString(R.string.class_no_key);
		strclassdetail = getResources().getString(R.string.classdetail_key);
		strclassname=getResources().getString(R.string.class_name);
		strclassaddress=getResources().getString(R.string.class_address);
		strclassweeks=getResources().getString(R.string.class_weeks);

		updateFrequencyListPref = (ListPreference)findPreference(updateFrequencyKey);
		classno =(ListPreference)findPreference(strclassno);
		classdetail=(PreferenceCategory)findPreference(strclassdetail);
		classname=(EditTextPreference)findPreference(strclassname);
		classaddress=(EditTextPreference)findPreference(strclassaddress);
		classweeks=(EditTextPreference)findPreference(strclassweeks);

		//Ϊ����Preferenceע������ӿ�    
		updateFrequencyListPref.setOnPreferenceClickListener(this); 
		updateFrequencyListPref.setOnPreferenceChangeListener(this);
		classno.setOnPreferenceChangeListener(this);
		classno.setOnPreferenceClickListener(this);
		classname.setOnPreferenceChangeListener(this);
		classaddress.setOnPreferenceClickListener(this);
		classaddress.setOnPreferenceChangeListener(this);
		classweeks.setOnPreferenceClickListener(this);
		classweeks.setOnPreferenceChangeListener(this); 

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		return detector.onTouchEvent(me);
	}

	// �ַ����ƣ���listview�ȿؼ���ȡ����������˳���Ӻ���ִ��onTouchEvent()
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		this.onTouchEvent(event);
		return super.dispatchTouchEvent(event);
	}
	
	@Override
	public boolean onPreferenceClick(Preference preference) {
		// TODO Auto-generated method stub
		Log.v("Key_SystemSetting", preference.getKey());
		//�ж����ĸ�Preference�������
		if(preference.getKey().equals(updateFrequencyKey))
		{
			Log.v("SystemSetting", "list preference is clicked");
		}
		else
		{
			return false;
		}
		return true;
	}
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		Log.v("Key_SystemSetting", preference.getKey());
		//�ж����ĸ�Preference�ı���
		if(preference.getKey().equals(updateFrequencyKey))
		{

			if(((String) newValue).trim().equals("����һ"))day=1;
			else if(((String) newValue).trim().equals("���ڶ�"))day=2;
			else if(((String) newValue).trim().equals("������"))day=3;
			else if(((String) newValue).trim().equals("������"))day=4;
			else if(((String) newValue).trim().equals("������"))day=5;
			else if(((String) newValue).trim().equals("������"))day=6;
			else if(((String) newValue).trim().equals("������"))day=7;


			preference.setSummary(((String) newValue).trim());
			if(classno.isEnabled()){                 //���ν���ѡ��
				String[] temp = myOpenHelper.selectWithDayAndNo(mysql, day, no);
				classname.setSummary(temp[0]);
				classname.setText(temp[0]);
				classaddress.setSummary(temp[3]);
				classaddress.setText(temp[3]);
				classweeks.setSummary(temp[2]);
				classweeks.setText(temp[2]);
			}			
			classno.setEnabled(true);      //����ν�ѡ��
		}
		else  if(preference.getKey().equals(strclassno))
		{

			if(((String) newValue).trim().equals("��һ���"))no=1;
			else if(((String) newValue).trim().equals("�ڶ����"))no=3;
			else if(((String) newValue).trim().equals("�������"))no=5;
			else if(((String) newValue).trim().equals("���Ĵ��"))no=7;
			else if(((String) newValue).trim().equals("������"))no=9;


			preference.setSummary(((String) newValue).trim());  

			String[] temp = myOpenHelper.selectWithDayAndNo(mysql, day, no);
			classname.setSummary(temp[0]);
			classname.setText(temp[0]);
			classaddress.setSummary(temp[3]);
			classaddress.setText(temp[3]);
			classweeks.setSummary(temp[2]);
			classweeks.setText(temp[2]);
			classdetail.setEnabled(true);

		}
		else  if(preference.getKey().equals(strclassname))
		{			
			preference.setSummary(newValue.toString()); 

			mysql.execSQL("UPDATE classes SET name='"+newValue.toString()+"' WHERE day="+day+" AND no="+no+";");
			Toast.makeText(ClassEdit.this, "�γ����޸ĳɹ�", Toast.LENGTH_SHORT).show();

		}
		else  if(preference.getKey().equals(strclassaddress))
		{

			preference.setSummary(newValue.toString());  

			mysql.execSQL("UPDATE classes SET address='"+newValue.toString()+"' WHERE day="+day+" AND no="+no+";");
			Toast.makeText(ClassEdit.this, "�ص��޸ĳɹ�", Toast.LENGTH_SHORT).show();

		}
		else  if(preference.getKey().equals(strclassweeks))
		{

			preference.setSummary(newValue.toString());  

			mysql.execSQL("UPDATE classes SET weeks='"+newValue.toString()+"' WHERE day="+day+" AND no="+no+";");
			Toast.makeText(ClassEdit.this, "�Ͽ��ܴ��޸ĳɹ�", Toast.LENGTH_SHORT).show();

		}
		else
		{
			//�������false��ʾ�������ı�
			return false;
		}
		//����true��ʾ����ı�		 
		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(mysql.isOpen())
			mysql.close();
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	
	public void bt_back(View v){
		finish();
		
	}

}
