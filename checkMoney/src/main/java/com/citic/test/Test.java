package com.citic.test;


public class Test {

	public static void main(String[] args) {
		
            String str = "20887019486626410156_20171230_账务明细.csv";
            
            System.out.println(encodeFileName(str));
	      
	}
	
	private static String encodeFileName(String fileName) {
    	String prefix = fileName.substring(0,fileName.lastIndexOf("_")+1);
    	String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
    	fileName = fileName.substring(fileName.lastIndexOf("_")+1,fileName.lastIndexOf("."));
    	switch (fileName) {
		case "业务明细(汇总)":  fileName = prefix+"1"+suffix; break; 
		case "业务明细": fileName = prefix+"2"+suffix;  break;
		case "账务明细(汇总)": fileName = prefix+"3"+suffix; break;
		case "账务明细": fileName = prefix+"alipayBill"+suffix; break; 

		}
		return fileName;
    }
	
}
