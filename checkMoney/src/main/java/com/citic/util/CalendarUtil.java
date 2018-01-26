package com.citic.util;

import java.util.Calendar;

public class CalendarUtil {
	
	
	private static final CalendarUtil instance = new CalendarUtil();
	
	public static CalendarUtil getInstance() {
		return instance;
	}
	
	private CalendarUtil(){};
	
	public int getMonthofYear() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		String monthStr = year + (month < 10?"0":"") + month;
		int monthNum = Integer.parseInt(monthStr);
		return monthNum;
	}

}
