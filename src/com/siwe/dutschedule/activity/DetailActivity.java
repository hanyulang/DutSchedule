package com.siwe.dutschedule.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.siwe.dutschedule.R;
import com.siwe.dutschedule.setting_edit.SetBackgroundImage;
import com.umeng.analytics.MobclickAgent;
//�γ̵���ϸ��Ϣ
public class DetailActivity extends Activity {
	int no , dayOfWeek;
	private MySQLiteOpenHelper myHelper;
	private SQLiteDatabase mysql;
	TextView t1,t2,t3,t4,t5,t6,t7,title;
	TextView[] textview ={t1,t2,t3,t4,t5,t6,t7};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail);
		SetBackgroundImage.setTheme(this, null, null);
		title = (TextView)findViewById(R.id.maintitle);
		t1 = (TextView)findViewById(R.id.detail1);
		t2 = (TextView)findViewById(R.id.detail2);
		t3 = (TextView)findViewById(R.id.detail3);
		t4 = (TextView)findViewById(R.id.detail4);
		t5 = (TextView)findViewById(R.id.detail5);
		t6 = (TextView)findViewById(R.id.detail6);
		t7 = (TextView)findViewById(R.id.detail7);
		getDetail();
		
	}

	public void getDetail(){
		myHelper = new MySQLiteOpenHelper(this);
		mysql = myHelper.getReadableDatabase();
		no = getIntent().getIntExtra("no", 1);
		dayOfWeek = getIntent().getIntExtra("day", 1);
		String titText = "";
		titText = (dayOfWeek==1?"��һ":dayOfWeek==2?"�ܶ�":dayOfWeek==3?"����":dayOfWeek==4?"����":dayOfWeek==5?"����":dayOfWeek==6?"����":"����")
			+"  "+(no==1?"��һ���":no==3?"�ڶ����":no==5?"�������":no==7?"���Ĵ��":"������");
		title.setText(titText);
	/*	String[] infoArry = myHelper.selectWithDayAndNo(mysql, dayOfWeek, no);
		for(int i=0;i<textview.length;i++){
			textview[i].setText(infoArry[i]);		
		}*/
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
	
	
	
	

	public void bt_back(View v){
		this.finish();	
	}

}
