package com.citic.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 读取Excel文件工具类
 * 
 * @author xutao
 * @date 2017年3月15日
 */
public class POIUtils {

	/**
	 * @author xutao date：2017-03-15
	 * @param exportData
	 *            列表头
	 * @param lis
	 *            数据集
	 * @param fileName
	 *            文件名
	 */
	public static void exportToExcel(HttpServletResponse response, List<Map<String, Object>> exportData, List<?> lis,
			String fileName) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ExcelUtil.createWorkBook(exportData, lis).write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".xls").getBytes("GBK"), "ISO-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
			}

		}
	}


	/**
	 * 读取Excel数据内容,兼容xls和xlsx。返回字符类型键值对的map数据。
	 * 
	 * @param filePath文件路径。
	 * @return Map 包含单元格数据内容的Map对象
	 * @throws Exception
	 */
	public  static List<Map<String, String>> readExcelContent(String filePath) throws Exception {
		Workbook wb = ExcelUtil.getWorkbook(filePath);
		
		Sheet sheet = wb.getSheetAt(0);

		List<String> titles = new ArrayList<String>();// 放置所有的标题
		
		List<Map<String, String>> rowList = new ArrayList<Map<String,String>>();
             
            int rowSize = sheet.getLastRowNum() + 1;
            for (int j = 0; j < rowSize; j++) {//遍历行
                Row row = sheet.getRow(j);
                if (row == null) {//略过空行
                    continue;
                }
                int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
                if (j == 0) {//第一行是标题行
                    for (int k = 0; k < cellSize; k++) {
                        Cell cell = row.getCell(k);
                        titles.add(cell.toString());
                    }
                } else {//其他行是数据行
                    Map<String, String> rowMap = new HashMap<String, String>();//对应一个数据行
                    for (int k = 0; k < titles.size(); k++) {
                        Cell cell = row.getCell(k);
                        String key = titles.get(k);
                        String value = null;
                        if (cell != null) {
                        	int cellType = cell.getCellType();
                        	switch (cellType) {
							case Cell.CELL_TYPE_STRING:
								 value = String.valueOf(cell.getStringCellValue());
								break;
							case Cell.CELL_TYPE_NUMERIC:
								 value = String.valueOf(cell.getNumericCellValue());
								 if (value.endsWith(".0")) {
									 value = value.substring(0, value.indexOf("."));
								 }
								break;
							case Cell.CELL_TYPE_BLANK:
								 value = String.valueOf(cell.getStringCellValue());
								break;
							default:
								break;
							}
                           
                        }
                        rowMap.put(key, value);
                    }
                    rowList.add(rowMap);
                }
            }

		return rowList;
	}

	
	
	
	public static void main(String[] args) throws Exception {
		try {

			String filePath = "E:\\temp\\citicwages.xlsx";
			POIUtils excelReader = new POIUtils();

			// 对读取Excel表格内容测试
			List<Map<String, String>> map = excelReader.readExcelContent(filePath);
			System.out.println("获得Excel表格的内容:");
			for (int i = 0; i <map.size(); i++) {
				System.out.println(map.get(i));
			}

		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		}
	}

}