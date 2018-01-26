package com.citic.bean.po;

import java.util.Date;

public class Operationstatus {
	
	private int id;
	private String batchcode;
	private String companykey;
	private String budgettypekey;
	private int trandate;
	private String accountname;
	private String originalfilename;
	private int sendcount;
	private int validationcount;
	private Byte status;
	private String description;
	private Date modifytime;
	private Date createtime;
	private Byte version;
	
	public String getCompanykey() {
		return companykey;
	}
	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}
	public String getBudgettypekey() {
		return budgettypekey;
	}
	public void setBudgettypekey(String budgettypekey) {
		this.budgettypekey = budgettypekey;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOriginalfilename() {
		return originalfilename;
	}
	public void setOriginalfilename(String originalfilename) {
		this.originalfilename = originalfilename;
	}
	
	public String getBatchcode() {
		return batchcode;
	}
	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public Date getModifytime() {
		return modifytime;
	}
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Byte getVersion() {
		return version;
	}
	public void setVersion(Byte version) {
		this.version = version;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTrandate() {
		return trandate;
	}
	public void setTrandate(int trandate) {
		this.trandate = trandate;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public int getSendcount() {
		return sendcount;
	}
	public void setSendcount(int sendcount) {
		this.sendcount = sendcount;
	}
	public int getValidationcount() {
		return validationcount;
	}
	public void setValidationcount(int validationcount) {
		this.validationcount = validationcount;
	}

}
