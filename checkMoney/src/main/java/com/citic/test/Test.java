package com.citic.test;


public class Test {

	public static void main(String[] args) {
		
            String str = "20887019486626410156_20171230_账务明细.csv";
           System.out.println(str.substring(str.lastIndexOf("_")+1,str.lastIndexOf(".")));
	      
	}
	
	
	
}
