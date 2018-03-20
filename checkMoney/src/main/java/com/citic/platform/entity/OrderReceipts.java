package com.citic.platform.entity;


public class OrderReceipts {
	
	private String id;
	
	private String charge_id;
	
	private String price;

	private String pay_status;
	
	private String pay_time;
	
	private String pay_platform;
	
	private String store_id;
	
	private String eshop_id;
	
	private String order_group_id;
	
	private String type;
	
	private Store store;
	
	private Eshop eshop;
	
	private String paySn;//合并支付号
	
	private String paySource;

	private String orderSn;//订单编号
	
	private String cardNo;//礼品卡支付号
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCharge_id() {
		return charge_id;
	}

	public void setCharge_id(String charge_id) {
		this.charge_id = charge_id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getPay_platform() {
		return pay_platform;
	}

	public void setPay_platform(String pay_platform) {
		this.pay_platform = pay_platform;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getEshop_id() {
		return eshop_id;
	}

	public void setEshop_id(String eshop_id) {
		this.eshop_id = eshop_id;
	}

	public String getOrder_group_id() {
		return order_group_id;
	}

	public void setOrder_group_id(String order_group_id) {
		this.order_group_id = order_group_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Eshop getEshop() {
		return eshop;
	}

	public void setEshop(Eshop eshop) {
		this.eshop = eshop;
	}

	public String getPaySn() {
		return paySn;
	}

	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}

	public String getPaySource() {
		return paySource;
	}

	public void setPaySource(String paySource) {
		this.paySource = paySource;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	
	

}
