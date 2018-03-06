package com.citic.platform.entity;

import java.util.Date;

public class WxGiftCardBuyRecord {

	private String id;
	private String wxOrderId;
	private String payFinishTime;
	private String totalPrice;
	private String price;
	private String cardId;
	private String cardCode;
	private Date createTime;
	private Date checkTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWxOrderId() {
		return wxOrderId;
	}
	public void setWxOrderId(String wxOrderId) {
		this.wxOrderId = wxOrderId;
	}
	public String getPayFinishTime() {
		return payFinishTime;
	}
	public void setPayFinishTime(String payFinishTime) {
		this.payFinishTime = payFinishTime;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrive(String prive) {
		this.price = prive;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	
}
