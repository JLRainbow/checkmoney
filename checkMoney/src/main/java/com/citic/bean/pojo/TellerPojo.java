package com.citic.bean.pojo;

import java.util.Date;

public class TellerPojo {
	
	//创建时间
	private String createTime;
	//批次号
	private String batchCode;
	//公司名称
	private String companyName;
	//收支类型名称
	private String budgettypeName;
	//总人数
	private int count;
	//收支月份
	private int trandate;
	//发放账户名称
	private String accountName;
	//原始文件名称
	private String originalfilename;
	
	//状态：-1废弃，0正常
	private Byte status;
	
	//上传时的描述
	private String description;
	private int sendCount;
	private int validationCount;
	
	private int openedAccountCount;
	private int notOpenedAccountCount;
	private int remittingCount;
	private int completedCount;
	private int failureCount;
	
	
	//最后修改时间
	private String modifytime;

	//修改次数
	private int version;
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBudgettypeName() {
		return budgettypeName;
	}
	public void setBudgettypeName(String budgettypeName) {
		this.budgettypeName = budgettypeName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTrandate() {
		return trandate;
	}
	public void setTrandate(int trandate) {
		this.trandate = trandate;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getOriginalfilename() {
		return originalfilename;
	}
	public void setOriginalfilename(String originalfilename) {
		this.originalfilename = originalfilename;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSendCount() {
		return sendCount;
	}
	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}
	public int getValidationCount() {
		return validationCount;
	}
	public void setValidationCount(int validationCount) {
		this.validationCount = validationCount;
	}

	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public String getModifytime() {
		return modifytime;
	}
	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}
	
	
	public int getRemittingCount() {
		return remittingCount;
	}
	public void setRemittingCount(int remittingCount) {
		this.remittingCount = remittingCount;
	}
	public int getCompletedCount() {
		return completedCount;
	}
	public void setCompletedCount(int completedCount) {
		this.completedCount = completedCount;
	}
	public int getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}
	public int getOpenedAccountCount() {
		return openedAccountCount;
	}
	public void setOpenedAccountCount(int openedAccountCount) {
		this.openedAccountCount = openedAccountCount;
	}
	public int getNotOpenedAccountCount() {
		return notOpenedAccountCount;
	}
	public void setNotOpenedAccountCount(int notOpenedAccountCount) {
		this.notOpenedAccountCount = notOpenedAccountCount;
	}
	
	
}
