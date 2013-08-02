package com.siwe.dutschedule.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.siwe.dutschedule.R;
import com.siwe.dutschedule.infoGeter.KaoGeter;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @author Yimu ������Ϣ��ʾ����
 */
public class KaoActivity extends Activity {

	private static MySQLiteOpenHelper myOpenHelper;
	private static SQLiteDatabase mysql;
	Button refresh;
	ListView list;
	ProgressBar progressbar;
	static String[] test;
	String param;
	RelativeLayout re;
	GestureDetector detector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_kao);
		list = (ListView) findViewById(R.id.lv01);
		list.setCacheColorHint(0);
		progressbar = (ProgressBar) findViewById(R.id.progressBar1);
		progressbar.setVisibility(View.GONE);
		refresh = (Button) findViewById(R.id.refresh);
		refresh.setOnClickListener(new MyListener());
		re = (RelativeLayout) findViewById(R.id.kao);
		detector = new GestureDetector(this, new backGestureListener(this));
		myOpenHelper = new MySQLiteOpenHelper(this);
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

	public class MyListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			GetTestTask task = new GetTestTask();
			task.execute((Void) null);
		}

	}

	public void bt_back(View v) {
		this.finish();
	}

	public void refreshData() {
		mysql = myOpenHelper.getReadableDatabase();
		Cursor cur = mysql.rawQuery("select * from tests", null);
		if (cur.getCount() == 0) {
			list.setBackgroundResource(R.drawable.noinfo);
			return;
		}
		if (cur != null) {
			
			@SuppressWarnings("deprecation")
			SimpleCursorAdapter mSchedule = new SimpleCursorAdapter(
					KaoActivity.this,// ������Դ
					R.layout.kao_item,// ListItem��XMLʵ��
					// ��̬������ListItem��Ӧ������
					cur, new String[] { "_id", "position", "time" },
					// ListItem��XML�ļ����������TextView ID
					new int[] { R.id.name, R.id.address, R.id.time });
			// ��Ӳ�����ʾ
			list.setAdapter(mSchedule);
		}
		mysql.close();

	}

	@Override
	public void onResume() {
		super.onResume();
		refreshData();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public boolean isContinue;
	public class GetTestTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			isContinue = true;
			mysql = myOpenHelper.getReadableDatabase();
			SharedPreferences sharedPrefrences = getSharedPreferences(
					"user", MODE_PRIVATE);
			param = sharedPrefrences.getString("usernamepassword", null);
			System.out.println("param=" + param);
			progressbar.setVisibility(View.VISIBLE);

		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Looper.prepare();
			return new KaoGeter(KaoActivity.this,param).getTwoPageInfo();
			// ������ȡ
		}

		@Override
		public void onCancelled() {
			System.out.println("task onCancelled");
			super.onCancelled();
		}

		@Override
		public void onPostExecute(Boolean result) {

			System.out.println("ִ��onPostExecute");
			if (!result) {
				Toast.makeText(KaoActivity.this, "ˢ��ʧ��", Toast.LENGTH_SHORT)
						.show();
			} else {
				list.setBackgroundResource(R.drawable.beijing22);
				refreshData();
				Toast.makeText(KaoActivity.this, "ˢ�³ɹ�", Toast.LENGTH_SHORT)
						.show();
			}
			progressbar.setVisibility(View.GONE);

		}

	}


}
