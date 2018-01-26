package com.citic.bean.vo;

import java.util.List;

import com.citic.bean.pojo.WagesPojo;


public class WalletAbutmentVo {
	private int count;
	private int trandate;
	private String batchcode;
	private List<WagesPojo> data;

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

	public List<WagesPojo> getData() {
		return data;
	}

	public void setData(List<WagesPojo> data) {
		this.data = data;
	}
	public String getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}
}
