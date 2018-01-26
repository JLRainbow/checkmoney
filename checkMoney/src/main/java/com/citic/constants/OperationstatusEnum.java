package com.citic.constants;

public class OperationstatusEnum {
	public static final byte SCRAP = -1;
	public static final byte NORMAL = 0;
	
	public static String getName(byte type) {
		
		String name = "";
		switch (type) {
		
		case SCRAP:
			name = "scrap";
			break;
		case NORMAL:
			name = "normal";
			break;
		default:
			break;
		}
		return name;
	}
}
