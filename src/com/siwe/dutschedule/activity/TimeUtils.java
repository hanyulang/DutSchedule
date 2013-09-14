package com.siwe.dutschedule.activity;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
	
	/***��ȡ����
	 * 
	 * 
	 */
	
	public static int getDayOfWeek(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		dayOfWeek = dayOfWeek < 1 || dayOfWeek > 6 ? 7 : dayOfWeek;
		return dayOfWeek;
	}

	/***
	 * ���㵱ǰ������
	 * 
	 * @param
	 * @return int day
	 */
	public static int getweek() {

		// ��ȡ��ǰ����
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		int currentDayOfYear = c.get(Calendar.DAY_OF_YEAR);
		System.out.println("xianzai" + currentDayOfYear);

		int distanceday = 0;
		if (currentDayOfYear > 56 && currentDayOfYear < 244) {
			distanceday = currentDayOfYear - 56;
		} else if (currentDayOfYear > 244) {
			distanceday = currentDayOfYear - 244;
		} else {
			return 0;
		}
		int week = distanceday / 7 + 1;
		return week;
	}
	

}
