package com.citic.factory.load;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import com.citic.service.Impl.CheckMoneyServiceImpl;
import com.citic.util.CsvUtil;
import com.mysql.jdbc.Connection;

public class DataLoadDB {

	private DataLoadDB(){
	}
	/**
	 * 
	 *方法：将处理好的list数据转化成csv文件load到DB中去
	 *创建时间：2018年1月22日
	 *创建者：jial
	 */
	public static int load(CsvUtil csvUtil,List<Object> dataList,String path,String sql) throws IOException{
		Properties prop2 = new Properties();
		InputStream is2 = CheckMoneyServiceImpl.class.getClassLoader().getResourceAsStream("config.properties");
		// 生成编辑好的csv文件
		prop2.load(is2);
		String tempFilePath = prop2.getProperty("tempFilePath");
		csvUtil.createCsv(dataList, tempFilePath + path);
		// 将编辑好的csv文件中数据load到DB中
		Properties props = new Properties();
		InputStream is = CheckMoneyServiceImpl.class.getClassLoader().getResourceAsStream("jdbc.properties");
		props.load(is);
		String url = props.getProperty("jdbc.url");
		String user = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");
		String driverClass = props.getProperty("jdbc.driverClass");
		Connection conn = null;
		Statement stemt = null;
		int x = 0;
		try {
			Class.forName(driverClass);
			conn = (Connection) DriverManager.getConnection(url, user, password);
			stemt = conn.createStatement();
			sql = sql.replace("xx.csv", tempFilePath + path);
			stemt.execute(sql);
			x = stemt.getUpdateCount();// load数据的个数
			System.out.println("Load执行结果：" + x);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {

			try {
				if (stemt != null)
					stemt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return x;
	} 
}
