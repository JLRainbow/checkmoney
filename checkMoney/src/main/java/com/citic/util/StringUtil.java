package com.citic.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：String工具类 
 * 作者：徐涛
 * 时间：2017.3.15 
 * 备注：以下方法都进行了Junit验证，可以放心使用。 工具类会不断扩展，有需要的同事请联系作者。
 */
public class StringUtil {
	private  static  final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	private StringUtil() {
	}

	/**
	 * 判断字符为空或空串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null) || (str.length() == 0);
	}

	/**
	 * 判断字符为空或空串或内容为空格
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return (str == null) || (str.length() == 0) || ("".equals(str.replaceAll(" ", ""))) || ("null".equals(str));
	}

	/**
	 * 优化替换空白字符算法
	 *	
	 * @param str
	 * @return
	 * 
	 * @author xutao
	 * @date Jan 12, 2017 6:05:42 PM
	 */
	public static String replaceAllBlankSpace(String str) {
		if (str != null) {
            int length = str.length();
            if (length > 0) {
                final char[] src = str.toCharArray();
                final char[] dest = new char[length];
                int pos = 0;
                char c = 0;
                for (int i=0; i<length; i++) {
                    c = src[i];
                    if (!Character.isWhitespace(c)) {
                        dest[pos++] = c;
                    }
                }
                str = new String(dest, 0, pos);
            }
        }
        return str;
	}

	/**
	 * 判断字符串是否有效
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isValid(String str) {
		return !isEmpty(str);
	}

	/**
	 * 修正判断数字类型算法
	 *	
	 * @param str
	 * @return
	 * 
	 * @author xutao
	 * @date Jan 12, 2017 6:07:53 PM
	 */
	
	public static boolean isNumber(String str) {
		if (isBlank(str)) {
			return false;
		}
		
		int sz = str.length();
		// 记录'.'的个数
		int count = 0;
		for (int i = 0; i < sz; i++) {
			char c = str.charAt(i);
			// 可能为负数，continue
			if (i == 0 && c == '-') {
				continue;
			}
			//如果当前字符非数字，并且还是非空白字符
			if ((!Character.isDigit(c)) && (!Character.isWhitespace(c)) && (c != '.')) {
				return false;
			}
			// Double类型包含'.'，但是只能有一个
			if (c == '.') {
				count++;
				continue;
			}
			// '.'的个数不能超过1
			if (count > 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将字符串转换成double数值 提供转换服务
	 * 
	 * @param str
	 *            要转换成double数值的字符串
	 * @param d
	 *            如果转换失败的默认值
	 * @return
	 */
	private static Double value(String str, Number defaultNumber) {
		double d = defaultNumber.doubleValue();

		if (isNumber(str)) {
			String reStr = replaceAllBlankSpace(str);
			try {
				d = Double.parseDouble(reStr);
			} catch (NumberFormatException e) {
				//转换异常时将要转的字符串写入日志,目前出现的multiple points异常,但是上面的isNumber方法已经判断了小数点存在多个的情况。
				logger.error("转换异常！！net.xinshi.jemall.commons.StringUtil====当前转换的值:"+reStr);
				e.printStackTrace();
			}
		}

		return d;
	}

	/**
	 * 将字符串转换成double数值
	 * 
	 * @param str
	 *            要转换成double数值的字符串
	 * @param d
	 *            如果转换失败的默认值
	 * @return
	 */
	public static double doubleValue(String str, double defaultDoubleValue) {
		return value(str, defaultDoubleValue);
	}

	/**
	 * 将字符串转换成int数值
	 * 
	 * @param str
	 *            要转换成int数值的字符串
	 * @param defaultIntValue
	 *            如果转换失败的默认值
	 * @return
	 */
	public static int intValue(String str, int defaultIntValue) {
		if (str!= null) {
			int length = str.length();
			try {
				if (length <= 11) {
					int num = Integer.parseInt(str);
					return num;
				}
			} catch (Exception e) {
				logger.error("从字符串直接解析int类型失败，继续执行匹配转换！");
			}
			Double d = value(str, defaultIntValue);

			if (d != null && d.intValue() <= Integer.MAX_VALUE) {
				return d.intValue();
			}
		}
		return defaultIntValue;
	}

	/**
	 * 将字符串转换成long数值
	 * 
	 * @param str
	 *            要转换成long数值的字符串
	 * @param defaultLongValue
	 *            如果转换失败的默认值
	 * @return
	 */
	public static long longValue(String str, long defaultLongValue) {
		Double d = null;
		d = value(str, defaultLongValue);

		if (d != null && d <= Long.MAX_VALUE) {
			return d.longValue();
		}
		return defaultLongValue;
	}

	public static String replaceXssCharacter(String str) {
		if (str == null) {
			return str;
		}
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot");
		str = str.replaceAll("'", "‘");
		return str;
	}
}
