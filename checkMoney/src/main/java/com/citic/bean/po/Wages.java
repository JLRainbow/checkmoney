package com.citic.bean.po;

import java.math.BigDecimal;
import java.util.Date;

public class Wages {
	
	private long id;
	private String batchcode;
	private String employeename;
	private String dept;
	private Byte thirdcustid;
	private String idno;
	private String  mobile;
	private BigDecimal tranamount;
	private int trandate;
	private Byte status;
	private String remark;
	private Date modifytime;
	private Date createtime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Byte getThirdcustid() {
		return thirdcustid;
	}
	public void setThirdcustid(Byte thirdcustid) {
		this.thirdcustid = thirdcustid;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public BigDecimal getTranamount() {
		return tranamount;
	}
	public void setTranamount(BigDecimal tranamount) {
		this.tranamount = tranamount;
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
	
	public String getBatchcode() {
		return batchcode;
	}
	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
