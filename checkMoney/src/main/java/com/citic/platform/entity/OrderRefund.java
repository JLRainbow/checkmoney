package com.citic.platform.entity;

public class OrderRefund {
	
	private String receiptsChargeId;
	
	private String refundChargeId;
	
	private String id;
	
	private String receiptId;

	private String price;
	
	private String refundPlatform;//退款平台
	
	private String refundOrderStatus;
	
	private String eshopId;
	
	private String remark;
	
	private String refundTime;
	
	private Store store;
	
	private Eshop eshop;
	
	private String paySource;

	private String payPlatform;//支付平台
	
	private String orderSn;//订单编号
	
	public String getReceiptsChargeId() {
		return receiptsChargeId;
	}

	public void setReceiptsChargeId(String receiptsChargeId) {
		this.receiptsChargeId = receiptsChargeId;
	}

	
	
	public String getRefundChargeId() {
		return refundChargeId;
	}

	public void setRefundChargeId(String refundChargeId) {
		this.refundChargeId = refundChargeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRefundPlatform() {
		return refundPlatform;
	}

	public void setRefundPlatform(String refundPlatform) {
		this.refundPlatform = refundPlatform;
	}

	public String getRefundOrderStatus() {
		return refundOrderStatus;
	}

	public void setRefundOrderStatus(String refundOrderStatus) {
		this.refundOrderStatus = refundOrderStatus;
	}

	public String getEshopId() {
		return eshopId;
	}

	public void setEshopId(String eshopId) {
		this.eshopId = eshopId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
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

	public String getPaySource() {
		return paySource;
	}

	public void setPaySource(String paySource) {
		this.paySource = paySource;
	}

	public String getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	
	
}
