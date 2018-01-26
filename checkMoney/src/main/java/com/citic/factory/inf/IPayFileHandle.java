package com.citic.factory.inf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.citic.entity.ConfigInf;
import com.citic.entity.WalletAccountConfig;
import com.citic.util.CsvUtil;
/**
 * 支付文件处理
 * @author jiel
 *
 */
public interface IPayFileHandle {

	/**
	 * 
	 *方法：支付csv文件处理方法
	 *创建时间：2018年1月22日
	 *创建者：jial
	 */
	public List<Object> getPayFileHandle(ConfigInf configInf,CsvUtil csvUtil)throws IOException;
	
	/**
	 * 
	 *方法：支付Excel文件处理方法
	 *创建时间：2018年1月22日
	 *创建者：jial
	 */
	public List<Object> getPayFileHandle(WalletAccountConfig configInf,ArrayList<ArrayList<Object>> result)throws IOException;
}
