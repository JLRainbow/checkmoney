package com.citic.constants;

public class WagesStatusEnum {
	public static final byte NOTOPENEDACCOUNT = -1;
	public static final byte OPENEDACCOUNT = 0;
	public static final byte REMITTING = 1;
	public static final byte COMPLETED = 2;
	public static final byte FAILURE = 3;
	
	public static String getName(byte type) {
		
		String name = "";
		switch (type) {
		
		case NOTOPENEDACCOUNT:
			name = "未开户";
			break;
		case OPENEDACCOUNT:
			name = "已开户";
			break;
		case REMITTING:
			name = "放款中";
			break;
		case COMPLETED:
			name = "完成";
			break;
		case FAILURE:
			name = "失败";
			break;
		default:
			break;
		}
		return name;
	}
}
