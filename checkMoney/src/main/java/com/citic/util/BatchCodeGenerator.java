package com.citic.util;

import java.util.Calendar;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import sun.util.calendar.CalendarUtils;

public class BatchCodeGenerator {
	
	private BatchCodeGenerator(){};
	
	private static final BatchCodeGenerator instance = new BatchCodeGenerator();
	
	public static BatchCodeGenerator getInstance() {
		return instance;
	}
	
	public String generate() {
		int monthofYear = CalendarUtil.getInstance().getMonthofYear();
		
		long timeInMillis = Calendar.getInstance().getTimeInMillis();
		
		return monthofYear + "-" + timeInMillis;
	}
}
