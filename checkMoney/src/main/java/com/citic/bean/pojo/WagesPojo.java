package com.citic.bean.pojo;

import java.math.BigDecimal;

public class WagesPojo {

	private String employeename;
	private Byte thirdcustid;
	private String idno;
	private BigDecimal tranamount;
	private Byte status;
	private String remark;
	
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
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
	public BigDecimal getTranamount() {
		return tranamount;
	}
	public void setTranamount(BigDecimal tranamount) {
		this.tranamount = tranamount;
	}
}
