package com.siwe.dutschedule.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/***
 * �Զ���view
 * 
 * @author zhangjia
 * 
 */
public class MyViewGroup extends ViewGroup {
	private Scroller scroller;// ����
	private int distance;// ��������

	private View menu_view, content_view;
	private int duration = 500;

	private CloseAnimation closeAnimation;

	public static boolean isMenuOpned = false;// �˵��Ƿ��

	public MyViewGroup(Context context) {
		super(context, null);
	}

	public void setCloseAnimation(CloseAnimation closeAnimation) {
		this.closeAnimation = closeAnimation;
	}

	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		scroller = new Scroller(context);
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	/*	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			menu_view = getChildAt(0);// ��ȡ�����˵���view
			content_view = getChildAt(1);// �����ҳview

			// �൱��fill_parent
			content_view.measure(0, 0);
			content_view.layout(0, 0, getWidth(), getHeight());
			//	content_view.layout(0, 0,240, 320);
		}
	}
	 */

	//����ֻ��Ҫ��ViewGroup�е�onMeasure���������һ������Ԫ�صı�����������
	//onLayout�����һ�����ֱ�����ʵ���˼򵥵Ĳ����ˡ�
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// TODO Auto-generated method stub 
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		/*	int childCount = getChildCount();
		for(int i = 0; i < childCount; i ++){*/

		View v = getChildAt(1);

		v.measure(widthMeasureSpec, heightMeasureSpec);

		//	}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		System.out.println("����myViewGroup ��onLayout����");
		menu_view = getChildAt(0);
		content_view = getChildAt(1);
		int childCount = getChildCount();

		for(int i = 0; i < childCount; i ++){

			View v = getChildAt(i);
			v.layout(l, t, r, b);
		}

		//.layout(int l, int t, int r, int b)

		menu_view.measure(0, distance);
		menu_view.layout(-distance, 0, distance, getHeight());
		content_view.layout(0, 0, getWidth(), getHeight());

		/*if(changed){
			System.out.println("changed");
			menu_view.measure(0, distance);
			menu_view.layout(-distance, 0, 0, getHeight());
			content_view.layout(0, 0, getWidth(), getHeight());
		}*/
	//	invalidate();

	}



	@Override
	public void computeScroll() {
		//		System.out.println("����myViewGroup ��computeScroll()����");
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();// ˢ��
			if (closeAnimation != null)
				closeAnimation.closeMenuAnimation();
		}
	}

	void showMenu() {	
		isMenuOpned = true;
		scroller.startScroll(0, 0, -distance, 0, duration);

		invalidate();// ˢ��
	}

	// �رղ˵���ִ���Զ��嶯����
	void closeMenu(){		
		isMenuOpned = false;
		scroller.startScroll(getScrollX(), 0, distance, 0, duration);
		invalidate();// ˢ��
	}

	// �رղ˵���ִ���Զ��嶯����
	void closeMenu_1() {
		isMenuOpned = false;
		scroller.startScroll(getScrollX(), 0, distance - getWidth(), 0,
				duration);
		invalidate();// ˢ��
	}

	// �رղ˵���ִ���Զ��嶯����
	void closeMenu_2() {
		isMenuOpned = false;
		scroller.startScroll(getScrollX(), 0, getWidth(), 0, duration);
		invalidate();// ˢ��
	}

	/***
	 * Menu startScroll(startX, startY, dx, dy)
	 * 
	 * dx=e1�ļ�ȥe2��x,��������Ϊ�������ƶ�Ϊ�� dxΪ�ƶ��ľ��룬���Ϊ�������ʶ�����ƶ�|dx|�����Ϊ�������ʶ�����ƶ�|dx|
	 */
	void slidingMenu() {

		// û�г�������
		if (getScrollX() > -getWidth() / 2) {
			scroller.startScroll(getScrollX(), 0, -getScrollX(), 0, duration);
			isMenuOpned = false;
		}
		// ��������
		else if (getScrollX() <= -getWidth() / 2) {
			scroller.startScroll(getScrollX(), 0, -(distance + getScrollX()),
					0, duration);
			isMenuOpned = true;
		}

		invalidate();// ˢ��
		Log.v("jj", "getScrollX()=" + getScrollX());
	}

}

abstract class CloseAnimation {
	// ���list item �ر�menu����
	public void closeMenuAnimation() {

	};
}
