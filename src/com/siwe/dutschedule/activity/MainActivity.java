package com.siwe.dutschedule.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.android.pushservice.richmedia.MediaListActivity;
import com.siwe.dutschedule.R;
import com.siwe.dutschedule.setting_edit.ClearAll;
import com.siwe.dutschedule.video.Videolist;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends Activity implements OnClickListener {

	private ImageButton ib1, ib2, ib3, ib4, ib5, ib6;
	private Button bt_menu;
	private MySQLiteOpenHelper myOpenHelper;
	private SQLiteDatabase mysql;
	MyViewGroup myViewGroup;
	private LayoutInflater layoutInflater;
	private View menu_view, content_view;
	private ListView lv_menu;
	private ViewTreeObserver viewTreeObserver;
	boolean hasMeasured;
	private static int distance; // ��������
	private String[] menu_item = { "���µ�½��ȡ", "�������", "�ύ����", "����", "�˳�" };
	private GestureDetector detector;
	FeedbackAgent agent;  


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.groupmain);
		detector = new GestureDetector(this, new myGestureListener());
		InitMenu();
		InitContentView();
		setListViewHeightBaseOnChildren(lv_menu);
		getMAX_WIDTH();
		
		// �����Զ�������
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		com.umeng.common.Log.LOG = true;
		UmengUpdateAgent.update(this);
		// �����ظ�����
		agent = new FeedbackAgent(MainActivity.this);
		agent.sync();
	}

	private void InitContentView() {
		ib1 = (ImageButton) findViewById(R.id.imageButton1);
		ib2 = (ImageButton) findViewById(R.id.imageButton2);
		ib3 = (ImageButton) findViewById(R.id.imageButton3);
		ib4 = (ImageButton) findViewById(R.id.imageButton4);
		ib5 = (ImageButton) findViewById(R.id.imageButton5);
		ib6 = (ImageButton) findViewById(R.id.imageButton6);
		ib1.setOnClickListener(this);
		ib2.setOnClickListener(this);
		ib3.setOnClickListener(this);
		ib4.setOnClickListener(this);
		ib5.setOnClickListener(this);
		ib6.setOnClickListener(this);
		bt_menu = (Button) findViewById(R.id.bt_menu);
		bt_menu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (MyViewGroup.isMenuOpned)
					myViewGroup.closeMenu();
				else
					myViewGroup.showMenu();
			}

		});
	}

	void InitMenu() {
			myViewGroup = (MyViewGroup) this.findViewById(R.id.vg_main);
			layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			menu_view = layoutInflater.inflate(R.layout.slide_menu, null);
			content_view = layoutInflater.inflate(R.layout.viewbox, null);
			myViewGroup.addView(menu_view);
			myViewGroup.addView(content_view);
			lv_menu = (ListView) menu_view.findViewById(R.id.lv_menu);
			lv_menu.setCacheColorHint(0);
			lv_menu.setAdapter(new ArrayAdapter<String>(this, R.layout.menuitem,
					R.id.tv_item, menu_item));
		lv_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				myViewGroup.closeMenu();
				switch (position) {
				case 0: // ���µ�¼��ȡ
					startActivity(new Intent(MainActivity.this,
							LoginActivity.class));
					break;
				case 1: // �������
					new AlertDialog.Builder(MainActivity.this)
							.setCancelable(false)
							.setIcon(R.drawable.ic_launcher)
							.setTitle("�������")
							.setMessage("��ȷ��Ҫ������пα�ͳɼ���Ϣ��?")
							.setPositiveButton("ȷ��",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											myOpenHelper = new MySQLiteOpenHelper(
													MainActivity.this);
											mysql = myOpenHelper
													.getReadableDatabase();
											ClearAll.clear(mysql);

											new AlertDialog.Builder(
													MainActivity.this)
													.setCancelable(false)
													.setIcon(
															R.drawable.ic_launcher)
													.setTitle("����ɹ�")
													.setMessage("������Ϣ��ɾ����")
													.setPositiveButton(
															"ȷ��",
															new DialogInterface.OnClickListener() {
																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	dialog.cancel();
																}
															}).show();
											mysql.close();

										}
									})
							.setNegativeButton("ȡ��",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									}).show();
					break;
				case 2: // �ύ����
					
				    agent.startFeedbackActivity();
					break;
				case 3: // ����
					startActivity(new Intent(MainActivity.this,
							AboutActivity.class));
					break;
				/*case 4:
					ExchangeDataService service = new ExchangeDataService();
			        new ExchangeViewManager(MainActivity.this,service)
			                    .addView(ExchangeConstants.type_list_curtain, null);  
					break;*/
				case 4:
					MainActivity.this.finish();
					break;
				default:
					break;
				}

			}
		});
	}

	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		Intent in;
		switch (v.getId()) {
		case R.id.imageButton1:
			in = new Intent(MainActivity.this, ClassActivity.class);
			startActivity(in);
			break;
		case R.id.imageButton2:
			in = new Intent(MainActivity.this, JidianActivity.class);
			startActivity(in);
			break;
		case R.id.imageButton3:
			in = new Intent(MainActivity.this, KaoActivity.class);
			startActivity(in);
			break;
		case R.id.imageButton4:
			in = new Intent(MainActivity.this, NoticeActivity.class);
			startActivity(in);
			break;
		case R.id.imageButton5:
			Intent sendIntent = new Intent();
			sendIntent.setClass(this, MediaListActivity.class);
			sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.startActivity(sendIntent);
			break;
		case R.id.imageButton6:
			in = new Intent(MainActivity.this, Videolist.class);
			startActivity(in);
			break;
		default:
			break;
		}
	}

	boolean isExit;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (MyViewGroup.isMenuOpned) {
				myViewGroup.closeMenu();
			} else
				exit();
			return true;
		}

		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (MyViewGroup.isMenuOpned)
				myViewGroup.closeMenu();
			else
				myViewGroup.showMenu();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(this, "�ٰ�һ�η��ؼ��˳�����", Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			System.exit(0);
		}
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			isExit = false;
		}

	};

	/***
	 * ����iv_button�Ŀ��
	 * 
	 * @param
	 */
	public void getMAX_WIDTH() {
		System.out.println("����getMAX_WIDTH()����");
		viewTreeObserver = menu_view.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				if (!hasMeasured) {
					distance = getWindowManager().getDefaultDisplay()
							.getWidth() / 2;
					System.out.println(distance);
					myViewGroup.setLeft(false); // ����Ϊ�Ҳ�˵�
					myViewGroup.setDistance(distance);

					// ����������Ҫ����lv_menu�Ŀ�ȣ�����Ϊviewpager��listview
					// �ᷢ�ͳ�ͻ��Ч����scrollviewһ������������˽������һ��.��
					ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) lv_menu
							.getLayoutParams();
					layoutParams.width = distance; // �޸��˲˵��Ұ벿�ݿհ׵�BUG
					lv_menu.setLayoutParams(layoutParams);
					hasMeasured = true;
				}
				return true;
			}
		});

	}

	/***
	 * ��̬����listview�ĸ߶� ע����listview��scrollview��ͻ��ʱ�����ǿ������������������̬����listview�ĸ߶ȣ�
	 * ���������ﲻ�У�ԭ����ȷ�����ǻ���һ���취���ǰ�listview���ú���Ļһ���ĸ߶ȣ�
	 * ����������viewgroup��scrillview�϶�������.
	 * 
	 * @param listView
	 */

	public void setListViewHeightBaseOnChildren(ListView listView) {
		ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
		layoutParams.height = getWindowManager().getDefaultDisplay()
				.getHeight();
		listView.setLayoutParams(layoutParams);
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

		System.out.println("dispatchTouchEvent");
		if (MyViewGroup.isMenuOpned && (event.getX() <= distance)) {// �Ҳ�˵��򿪲��Ҵ�����벿��ʱ�رղ˵�
			myViewGroup.closeMenu();// �������ƶ�������֮�����С����
			return false; //����Ϊtrueʱ�����²�onTouchEvent�����������·ַ�������ֹͣ�ַ�
		}
		detector.onTouchEvent(event);
		return super.dispatchTouchEvent(event);
	}
	
/*	@Override
	public boolean onTouchEvent(MotionEvent event){
		
		super.onTouchEvent(event);
		return detector.onTouchEvent(event);
	}*/

	
	class myGestureListener extends SimpleOnGestureListener {

		final int FLIP_DISTANCE = 120;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (!MyViewGroup.isMenuOpned&&e1.getX() - e2.getX() > FLIP_DISTANCE) {
				myViewGroup.showMenu();
				return true;
			}
			return false;
		}

	
	}
}
