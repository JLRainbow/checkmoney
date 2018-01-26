package com.citic.bean.vo;

import java.util.Set;

public class WagesAccountValidationVo {
	private Integer count;
	private String batchcode;
	private Set<IdNoWalletStatus> data;

	
	public String getBatchcode() {
		return batchcode;
	}
	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}
	
	public Set<IdNoWalletStatus> getData() {
		return data;
	}
	public void setData(Set<IdNoWalletStatus> data) {
		this.data = data;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
