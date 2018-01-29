package com.citic.bill.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 * @author jial
 *
 */
public class DateUtil {

	private DateUtil(){
	}
	/**
	 * 将时间段的分隔成每一天
	 * @param dBegin
	 * @param dEnd
	 * @return
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}

	
	/**
	 * 将时间段的分隔成每一天
	 * @param dBegin
	 * @param dEnd
	 * @return
	 * @throws ParseException 
	 */
	public static List<String> findDates(String dBegin, String dEnd,String pattern) throws ParseException {
		List<String> lDate = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(sdf.parse(dBegin));
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(sdf.parse(dEnd));
		// 测试此日期是否在指定日期之后
		while (sdf.parse(dEnd).after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(sdf.format(calBegin.getTime()));
		}
		return lDate;
	}
}
