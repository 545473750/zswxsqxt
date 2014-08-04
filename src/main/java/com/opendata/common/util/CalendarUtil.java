package com.opendata.common.util;

import java.util.Calendar;

/**
 * 日期工具类
 * @author 付威
 *
 */
public class CalendarUtil {
	
	// 获得下一天
	public static Calendar getNextDay(Calendar calendar) {
		calendar.add(Calendar.DATE, 1);
		return calendar;
	}

	// 获得下周第一天
	public static Calendar getNextMonday(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		return calendar;
	}
	
	// 获得当前周第一天
	public static Calendar getCurrentMonday(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		return calendar;
	}
	
	// 获得当前周最后一天
	public static Calendar getCurrentSunday(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		calendar.roll(Calendar.DAY_OF_WEEK, -1);
		calendar.add(Calendar.DAY_OF_WEEK, 1);
		return calendar;
	}
	

	// 获得下月第一天
	public static Calendar getNextMonthFirstDay(Calendar calendar) {
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		return calendar;
	}

	// 获得当前月第一天
	public static Calendar getCurrentMonthFirstDay(Calendar calendar) {
		calendar.set(Calendar.DATE, 1);
		return calendar;
	}
	
	// 获得当前月最后一天
	public static Calendar getCurrentMonthEndDay(Calendar calendar) {
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar;
	}
	
}
