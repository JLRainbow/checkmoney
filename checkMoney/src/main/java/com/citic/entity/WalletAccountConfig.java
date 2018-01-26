package com.citic.entity;

public class WalletAccountConfig {

	//支付名称
	private String channel_name;
	//第几行开始读取
	private Integer start_line;
	//钱包订单号位置
	private Integer wallet_order_position;
	//钱包前置流水号（平台对账用）位置
	private Integer platform_order_position;
	//银行传送流水号（对账用）位置
	private Integer bank_order_position;
	//金额位置
	private Integer amount_position;
	//对账金额位置
	private Integer check_amount_position;
	//对账结果位置
	private Integer check_result_position;
	//交易类型位置
	private Integer transaction_type_position;
	//用户电话号位置
	private Integer user_phone_position;
	//姓名位置
	private Integer user_name_position;
	//订单完成时间位置
	private Integer order_finish_date_position;
	//交易名称位置
	private Integer transactionNamePosition;
	//银行卡支付流水号（平台对账用）位置
	private Integer platformUoionPayOrderPosition;
	
	public String getChannel_name() {
		return channel_name;
	}
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	public Integer getStart_line() {
		return start_line;
	}
	public void setStart_line(Integer start_line) {
		this.start_line = start_line;
	}
	public Integer getWallet_order_position() {
		return wallet_order_position;
	}
	public void setWallet_order_position(Integer wallet_order_position) {
		this.wallet_order_position = wallet_order_position;
	}
	public Integer getPlatform_order_position() {
		return platform_order_position;
	}
	public void setPlatform_order_position(Integer platform_order_position) {
		this.platform_order_position = platform_order_position;
	}
	public Integer getBank_order_position() {
		return bank_order_position;
	}
	public void setBank_order_position(Integer bank_order_position) {
		this.bank_order_position = bank_order_position;
	}
	public Integer getAmount_position() {
		return amount_position;
	}
	public void setAmount_position(Integer amount_position) {
		this.amount_position = amount_position;
	}
	public Integer getCheck_amount_position() {
		return check_amount_position;
	}
	public void setCheck_amount_position(Integer check_amount_position) {
		this.check_amount_position = check_amount_position;
	}
	public Integer getCheck_result_position() {
		return check_result_position;
	}
	public void setCheck_result_position(Integer check_result_position) {
		this.check_result_position = check_result_position;
	}
	public Integer getTransaction_type_position() {
		return transaction_type_position;
	}
	public void setTransaction_type_position(Integer transaction_type_position) {
		this.transaction_type_position = transaction_type_position;
	}
	public Integer getUser_phone_position() {
		return user_phone_position;
	}
	public void setUser_phone_position(Integer user_phone_position) {
		this.user_phone_position = user_phone_position;
	}
	public Integer getUser_name_position() {
		return user_name_position;
	}
	public void setUser_name_position(Integer user_name_position) {
		this.user_name_position = user_name_position;
	}
	public Integer getOrder_finish_date_position() {
		return order_finish_date_position;
	}
	public void setOrder_finish_date_position(Integer order_finish_date_position) {
		this.order_finish_date_position = order_finish_date_position;
	}
	public Integer getTransactionNamePosition() {
		return transactionNamePosition;
	}
	public void setTransactionNamePosition(Integer transactionNamePosition) {
		this.transactionNamePosition = transactionNamePosition;
	}
	public Integer getPlatformUoionPayOrderPosition() {
		return platformUoionPayOrderPosition;
	}
	public void setPlatformUoionPayOrderPosition(Integer platformUoionPayOrderPosition) {
		this.platformUoionPayOrderPosition = platformUoionPayOrderPosition;
	}
	@Override
	public String toString() {
		return "WalletAccountConfig [channel_name=" + channel_name + ", start_line=" + start_line
				+ ", wallet_order_position=" + wallet_order_position + ", platform_order_position="
				+ platform_order_position + ", bank_order_position=" + bank_order_position + ", amount_position="
				+ amount_position + ", check_amount_position=" + check_amount_position + ", check_result_position="
				+ check_result_position + ", transaction_type_position=" + transaction_type_position
				+ ", user_phone_position=" + user_phone_position + ", user_name_position=" + user_name_position
				+ ", order_finish_date_position=" + order_finish_date_position + ", transactionNamePosition="
				+ transactionNamePosition + ", platformUoionPayOrderPosition=" + platformUoionPayOrderPosition + "]";
	}
	
	
	
}
