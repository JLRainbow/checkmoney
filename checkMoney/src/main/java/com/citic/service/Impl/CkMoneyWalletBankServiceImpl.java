package com.citic.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.citic.entity.BankCollectConfig;
import com.citic.entity.BankCollectFromMap;
import com.citic.entity.BankDetailConfig;
import com.citic.entity.BankDetailFromMap;
import com.citic.entity.ChannelManagementFormMap;
import com.citic.entity.WalletAccountConfig;
import com.citic.entity.WalletAccountFromMap;
import com.citic.mapper.BankCollectMapper;
import com.citic.mapper.BankDetailMapper;
import com.citic.mapper.ChannelManagementMapper;
import com.citic.mapper.WalletAccountMapper;
import com.citic.service.CkMoneyWalletBankService;
@Service
public class CkMoneyWalletBankServiceImpl implements CkMoneyWalletBankService {

	@Autowired
	private ChannelManagementMapper channelManagementMapper;
	
	@Autowired
	private BankDetailMapper bankDetailMapper;
	
	@Autowired
	private BankCollectMapper bankCollectMapper;
	
	@Autowired
	private WalletAccountMapper walletAccountMapper;
	
	public HashMap<String, Object> importWalletFile(ArrayList<ArrayList<Object>> result, String fileType) {
		ChannelManagementFormMap channelManagementFormMap = new ChannelManagementFormMap();
		HashMap<String,Object> map = new HashMap<String, Object>();
		//通过支付方式找到对应配置信息，将json信息转换成对象
		channelManagementFormMap.put("where", "where channel_name = '"+fileType+"'");
		List<ChannelManagementFormMap> channelManagementList = channelManagementMapper.findByWhere(channelManagementFormMap);
		ChannelManagementFormMap channelManagement = channelManagementList.get(0);
		String config_inf=(String) channelManagement.get("config_inf");
		WalletAccountConfig configInf = JSON.parseObject(config_inf,WalletAccountConfig.class);
		System.out.println(configInf.toString());
		
		int n = 0 , m = 0;
		
		WalletAccountFromMap walletAccountFromMap = null;
	    for(int i = 0 ;i < result.size() ;i++){  
		      for(int j = 0;j<result.get(i).size(); j++){
		    	  if(i>=configInf.getStart_line()-1){
		    		  if(j==configInf.getTransaction_type_position()-1){
		    			  String a1 = result.get(i).get(configInf.getTransaction_type_position()-1).toString();
		    			  if("8".equals(a1)){
		    				  String a2 = result.get(i).get(configInf.getWallet_order_position()-1).toString();
		    				  String a3 = result.get(i).get(configInf.getPlatform_order_position()-1).toString();
		    				  String a4 = result.get(i).get(configInf.getBank_order_position()-1).toString();
		    				  String a5 = result.get(i).get(configInf.getAmount_position()-1).toString();
		    				  String a6 = result.get(i).get(configInf.getUser_phone_position()-1).toString();
		    				  String a7 = result.get(i).get(configInf.getUser_name_position()-1).toString();
		    				  String a8 = result.get(i).get(configInf.getOrder_finish_date_position()-1).toString();
		    				  System.out.println(a1+" "+a2+" "+a3+" "+a4+" "+a5+" "+a6+" "+a7+" "+a8); 
		    				  walletAccountFromMap = new WalletAccountFromMap();
		    				  walletAccountFromMap.put("wallet_order", a2);
		    				  walletAccountFromMap.put("platform_order", a3);
		    				  walletAccountFromMap.put("bank_order", a4);
		    				  walletAccountFromMap.put("amount", this.fromFenToYuan(a5));
		    				  walletAccountFromMap.put("check_result", 0);
		    				  walletAccountFromMap.put("transaction_type", a1);
		    				  walletAccountFromMap.put("user_phone", a6);
		    				  walletAccountFromMap.put("user_name", a7);
		    				  walletAccountFromMap.put("order_finish_date", this.formatDate(a8));
		    				  walletAccountFromMap.put("where", "where wallet_order = '"+a2+"'");
		    				  
			    				//导入数据时先查看数据库是否有这条数据
									List<WalletAccountFromMap> list = walletAccountMapper.findByWhere(walletAccountFromMap);
									if(list.size() == 0){
										try {
											m++;//计数
											walletAccountMapper.addEntity(walletAccountFromMap);
											map.put("success", "导入成功");
										} catch (Exception e) {
											e.printStackTrace();
											map.put("error", "导入失败");
										}
									}else{
										n++;//导入重复数据的个数
									}
		    			  }
		    		  }
		    	  }
		      }  
	    } 
	    map.put("walletChkNumMap", this.getWalletChkNum());
	    map.put("impDataNum", m);
		map.put("sameDataNum", n);
		return map;
	} 

	public HashMap<String, Object> importBankDetailFile(ArrayList<ArrayList<Object>> result, String fileType) {
		ChannelManagementFormMap channelManagementFormMap = new ChannelManagementFormMap();
		HashMap<String,Object> map = new HashMap<String, Object>();
		//通过支付方式找到对应配置信息，将json信息转换成对象
		channelManagementFormMap.put("where", "where channel_name = '"+fileType+"'");
		List<ChannelManagementFormMap> channelManagementList = channelManagementMapper.findByWhere(channelManagementFormMap);
		ChannelManagementFormMap channelManagement = channelManagementList.get(0);
		String config_inf=(String) channelManagement.get("config_inf");
		BankDetailConfig configInf = JSON.parseObject(config_inf,BankDetailConfig.class);
		System.out.println(configInf.toString());
		
		int n = 0 , m = 0;
		
		BankDetailFromMap bankDetailFromMap = null;
	    for(int i = 0 ;i < result.size() ;i++){  
		      for(int j = 0;j<result.get(i).size(); j++){
		    	  if(i>=configInf.getStart_line()-1){
		    		  if(j==configInf.getAbstract_position()-1){
		    			  if(!result.get(i).get(configInf.getAccount_position()-1).toString().contains("315")){
		    				  map.put("error", "请确认导入文件账户是否为315");
		    			  }else{
		    			  String a1 = result.get(i).get(configInf.getAbstract_position()-1).toString();
		    			  String a10 = result.get(i).get(configInf.getPurpose_position()-1).toString();
		    			  //只筛选资金结算和用途数据为数字的数据
		    			  if("资金结算".equals(a1)&&this.isNumeric(a10)){
		    				  String a2 = result.get(i).get(configInf.getTrade_date_position()-1).toString();
		    				  String a3 = result.get(i).get(configInf.getAccount_position()-1).toString();
		    				  String a4 = result.get(i).get(configInf.getBorrow_amount_position()-1).toString();
		    				  String a5 = result.get(i).get(configInf.getLoan_amount_position()-1).toString();
		    				  String a6 = result.get(i).get(configInf.getSurplus_amount_position()-1).toString();
		    				  String a7 = result.get(i).get(configInf.getOpposite_account_position()-1).toString();
		    				  String a8 = result.get(i).get(configInf.getOpposite_account_name_position()-1).toString();
		    				  String a9 = result.get(i).get(configInf.getCheck_order_position()-1).toString();
		    				  System.out.println(a1+" "+a2+" "+a3+" "+a4+" "+a5+" "+a6+" "+a7+" "+a8+" "+a9+" "+a10); 
		    				  bankDetailFromMap = new BankDetailFromMap();
		    				  bankDetailFromMap.put("trade_date", a2);
		    				  bankDetailFromMap.put("account", a3);
		    				  bankDetailFromMap.put("borrow_amount", a4);
		    				  bankDetailFromMap.put("loan_amount", a5);
		    				  bankDetailFromMap.put("surplus_amount", a6);
		    				  bankDetailFromMap.put("check_result", 0);
		    				  bankDetailFromMap.put("opposite_account", a7);
		    				  bankDetailFromMap.put("opposite_account_name", a8);
		    				  bankDetailFromMap.put("check_order", a9);
		    				  bankDetailFromMap.put("abstract", a1);
		    				  bankDetailFromMap.put("purpose", a10);
		    				  bankDetailFromMap.put("where", "where check_order = '"+a9+"'");
		    				  
		    				//导入数据时先查看数据库是否有这条数据
								List<BankDetailFromMap> list = bankDetailMapper.findByWhere(bankDetailFromMap);
								if(list.size() == 0){
									try {
										m++;//计数
										bankDetailMapper.addEntity(bankDetailFromMap);
										map.put("success", "导入成功");
									} catch (Exception e) {
										e.printStackTrace();
										map.put("error", "导入失败");
									}
								}else{
									n++;//导入重复数据的个数
								}
		    			  }
		    			  
		    		  }
		    	    }	  
		    	  }
		      }  
	    } 
	    map.put("bankDetailChkNumMap", this.getBankDetailChkNum());
	    map.put("impDataNum", m);
		map.put("sameDataNum", n);
		return map;
	}

	public HashMap<String, Object> importBankCollectFile(ArrayList<ArrayList<Object>> result, String fileType) {
		ChannelManagementFormMap channelManagementFormMap = new ChannelManagementFormMap();
		HashMap<String,Object> map = new HashMap<String, Object>();
		//通过支付方式找到对应配置信息，将json信息转换成对象
		channelManagementFormMap.put("where", "where channel_name = '"+fileType+"'");
		List<ChannelManagementFormMap> channelManagementList = channelManagementMapper.findByWhere(channelManagementFormMap);
		ChannelManagementFormMap channelManagement = channelManagementList.get(0);
		String config_inf=(String) channelManagement.get("config_inf");
		BankCollectConfig configInf = JSON.parseObject(config_inf,BankCollectConfig.class);
		System.out.println(configInf.toString());
		
		int n = 0 , m = 0;
		
		BankCollectFromMap bankCollectFromMap = null;
	    for(int i = 0 ;i < result.size() ;i++){  
		      for(int j = 0;j<result.get(i).size(); j++){
		    	  if(i>=configInf.getStart_line()-1){
		    		  if(j==configInf.getAbstract_position()-1){
		    			  if(!result.get(i).get(configInf.getAccount_position()-1).toString().contains("140")){
		    				  map.put("error", "请确认导入文件账户是否为140");
		    			  }else{
		    			  String a1 = result.get(i).get(configInf.getAbstract_position()-1).toString();
		    			  String a10 = result.get(i).get(configInf.getPurpose_position()-1).toString();
		    			  //只筛选资金结算和用途数据为数字的数据
		    			  if("资金结算".equals(a1)&&this.isNumeric(a10)){
		    				  String a2 = result.get(i).get(configInf.getTrade_date_position()-1).toString();
		    				  String a3 = result.get(i).get(configInf.getAccount_position()-1).toString();
		    				  String a4 = result.get(i).get(configInf.getBorrow_amount_position()-1).toString();
		    				  String a5 = result.get(i).get(configInf.getLoan_amount_position()-1).toString();
		    				  String a6 = result.get(i).get(configInf.getSurplus_amount_position()-1).toString();
		    				  String a7 = result.get(i).get(configInf.getOpposite_account_position()-1).toString();
		    				  String a8 = result.get(i).get(configInf.getOpposite_account_name_position()-1).toString();
		    				  String a9 = result.get(i).get(configInf.getCheck_order_position()-1).toString();
		    				  System.out.println(a1+" "+a2+" "+a3+" "+a4+" "+a5+" "+a6+" "+a7+" "+a8+" "+a9+" "+a10); 
		    				  bankCollectFromMap = new BankCollectFromMap();
		    				  bankCollectFromMap.put("trade_date", a2);
		    				  bankCollectFromMap.put("account", a3);
		    				  bankCollectFromMap.put("borrow_amount", a4);
		    				  bankCollectFromMap.put("loan_amount", a5);
		    				  bankCollectFromMap.put("surplus_amount", a6);
		    				  bankCollectFromMap.put("check_result", 0);
		    				  bankCollectFromMap.put("opposite_account", a7);
		    				  bankCollectFromMap.put("opposite_account_name", a8);
		    				  bankCollectFromMap.put("check_order", a9);
		    				  bankCollectFromMap.put("abstract", a1);
		    				  bankCollectFromMap.put("purpose", a10);
		    				  bankCollectFromMap.put("where", "where check_order = '"+a9+"'");
		    				  
		    				//导入数据时先查看数据库是否有这条数据
								List<BankCollectFromMap> list = bankCollectMapper.findByWhere(bankCollectFromMap);
								if(list.size() == 0){
									try {
										m++;//计数
										bankCollectMapper.addEntity(bankCollectFromMap);
										map.put("success", "导入成功");
									} catch (Exception e) {
										e.printStackTrace();
										map.put("error", "导入失败");
									}
								}else{
									n++;//导入重复数据的个数
								}
		    			  }
		    			  
		    		  }
		    		}	  
		    	  }
		      }  
	    } 
	    map.put("bankCollectChkNumMap", this.getBankCollectChkNum());
	    map.put("impDataNum", m);
		map.put("sameDataNum", n);
		return map;
	}
	
	public void chkMoneyEmptyToEntity() {
		walletAccountMapper.updateMatchData();
		bankDetailMapper.updateMatchData();
		walletAccountMapper.updateChkResult();
		bankDetailMapper.updateEmptyToEntityChkResult();
	}
	
	public void chkMoneyEntityToEntity() {
		bankDetailMapper.updateEntityToEntityData();
		bankCollectMapper.updateEntityToEntityData();
		bankDetailMapper.updateEntityToEntityChkResult();
		bankCollectMapper.updateChkResult();
	}
	
   /**
    * 判断字符串是否是数字
    */
	private boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
	}

  /**
	* 日期格式转换
	*/
	private String formatDate(String str){
			String[] split = str.split("/");
			StringBuilder sb = new StringBuilder();
			sb.append(split[2]);
		return sb.insert(4, "-"+split[1]).insert(4, "-"+split[0]).toString(); 
	}
	
	/**  
     * 分转换为元
     *   
     */  
	private String fromFenToYuan(final String fen) {  
    	String yuan = "";  
        final int MULTIPLIER = 100;  
        yuan = new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();  
        return yuan; 
    }
	/**  
     *  获取未对账数
     *   
     */  
	public HashMap<String, Object> getNoChkNum() {
		HashMap<String,Object> walletChkNumMap = this.getWalletChkNum();
		HashMap<String,Object> bankDetailChkNumMap = this.getBankDetailChkNum();
		HashMap<String,Object> bankCollectChkNumMap = this.getBankCollectChkNum();
		
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("walletChkNumMap", walletChkNumMap);
		map.put("bankDetailChkNumMap", bankDetailChkNumMap);
		map.put("bankCollectChkNumMap", bankCollectChkNumMap);
		return map;
	}
	/**  
     *  获取钱包未对账数
     *   
     */  
	private HashMap<String,Object> getWalletChkNum(){
		WalletAccountFromMap walletAccountFromMap = new WalletAccountFromMap();
		HashMap<String,Object> map = new HashMap<String, Object>();
		
		walletAccountFromMap.put("where", "where check_result = 0");
		List<WalletAccountFromMap> list = walletAccountMapper.findByWhere(walletAccountFromMap);
		
		List<WalletAccountFromMap> closingDateList = walletAccountMapper.getClosingDate();
		String closingDate = closingDateList.get(0)==null?"XXXX年XX月XX日":(String)closingDateList.get(0).get("order_finish_date");
		map.put("closingDate", closingDate);
		map.put("walletChkNum", list.size());
		return map;
		
	}
	/**  
     *  获取银行明细未对账数
     *   
     */  
	private HashMap<String,Object> getBankDetailChkNum(){
		BankDetailFromMap  bankDetailFromMap = new BankDetailFromMap();
		HashMap<String,Object> map = new HashMap<String, Object>();
		
		bankDetailFromMap.put("where", "WHERE check_result = 0 and borrow_amount is NOT NULL AND wallet_amount IS NULL");
		List<BankDetailFromMap> list = bankDetailMapper.findByWhere(bankDetailFromMap);
		bankDetailFromMap.put("where", "WHERE check_result = 0 and loan_amount is NOT NULL AND collect_amount IS NULL");
		List<BankDetailFromMap> list1 = bankDetailMapper.findByWhere(bankDetailFromMap);
		
		List<BankDetailFromMap> cashClosingDateList = bankDetailMapper.getCashClosingDate();
		String cashClosingDate = cashClosingDateList.get(0)==null?"XXXX年XX月XX日":(String)cashClosingDateList.get(0).get("trade_date");
		List<BankDetailFromMap> collectClosingDateList = bankDetailMapper.getCollectClosingDate();
		String collectClosingDate = collectClosingDateList.get(0)==null?"XXXX年XX月XX日":(String)collectClosingDateList.get(0).get("trade_date");
		
		map.put("cashClosingDate", cashClosingDate);
		map.put("collectClosingDate", collectClosingDate);
		map.put("bankDetailCashChkNum", list.size());
		map.put("bankDetailCollectChkNum", list1.size());
		return map;
		
	}
	/**  
     * 获取银行汇总未对账数
     *   
     */  
	private HashMap<String,Object> getBankCollectChkNum(){
		BankCollectFromMap bankCollectFromMap = new BankCollectFromMap();
		HashMap<String,Object> map = new HashMap<String, Object>();
		
		bankCollectFromMap.put("where", "where check_result = 0");
		List<BankCollectFromMap> list = bankCollectMapper.findByWhere(bankCollectFromMap);
		
		List<BankCollectFromMap> closingDateList = bankCollectMapper.getClosingDate();
		String closingDate = closingDateList.get(0)==null?"XXXX年XX月XX日":(String)closingDateList.get(0).get("trade_date");
		map.put("closingDate", closingDate);
		map.put("bankCollectChkNum", list.size());
		return map;
		
	}
	
}
