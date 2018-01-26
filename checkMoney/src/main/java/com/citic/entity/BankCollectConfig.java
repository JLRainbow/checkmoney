package com.citic.entity;

public class BankCollectConfig {

	//支付名称
	private String channel_name;
	//第几行开始读取
	private Integer start_line;
	//交易时间位置
	private Integer trade_date_position;
	//账号位置
	private Integer account_position;
	//借位置
	private Integer borrow_amount_position;
	//贷位置
	private Integer loan_amount_position;
	//账户余额位置
	private Integer surplus_amount_position;
	//对方账户位置
	private Integer opposite_account_position;
	//对方账户名称位置
	private Integer opposite_account_name_position;
	//交易流水号位置
	private Integer check_order_position;
	//摘要位置
	private Integer abstract_position;
	//用途位置
	private Integer purpose_position;
	
	
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
	public Integer getTrade_date_position() {
		return trade_date_position;
	}
	public void setTrade_date_position(Integer trade_date_position) {
		this.trade_date_position = trade_date_position;
	}
	public Integer getAccount_position() {
		return account_position;
	}
	public void setAccount_position(Integer account_position) {
		this.account_position = account_position;
	}
	public Integer getBorrow_amount_position() {
		return borrow_amount_position;
	}
	public void setBorrow_amount_position(Integer borrow_amount_position) {
		this.borrow_amount_position = borrow_amount_position;
	}
	public Integer getLoan_amount_position() {
		return loan_amount_position;
	}
	public void setLoan_amount_position(Integer loan_amount_position) {
		this.loan_amount_position = loan_amount_position;
	}
	public Integer getOpposite_account_position() {
		return opposite_account_position;
	}
	public void setOpposite_account_position(Integer opposite_account_position) {
		this.opposite_account_position = opposite_account_position;
	}
	
	public Integer getOpposite_account_name_position() {
		return opposite_account_name_position;
	}
	public void setOpposite_account_name_position(Integer opposite_account_name_position) {
		this.opposite_account_name_position = opposite_account_name_position;
	}
	public Integer getCheck_order_position() {
		return check_order_position;
	}
	public void setCheck_order_position(Integer check_order_position) {
		this.check_order_position = check_order_position;
	}
	public Integer getAbstract_position() {
		return abstract_position;
	}
	public void setAbstract_position(Integer abstract_position) {
		this.abstract_position = abstract_position;
	}
	public Integer getPurpose_position() {
		return purpose_position;
	}
	public void setPurpose_position(Integer purpose_position) {
		this.purpose_position = purpose_position;
	}
	public Integer getSurplus_amount_position() {
		return surplus_amount_position;
	}
	public void setSurplus_amount_position(Integer surplus_amount_position) {
		this.surplus_amount_position = surplus_amount_position;
	}
	@Override
	public String toString() {
		return "BankDetailConfig [channel_name=" + channel_name + ", start_line=" + start_line
				+ ", trade_date_position=" + trade_date_position + ", account_position=" + account_position
				+ ", borrow_amount_position=" + borrow_amount_position + ", loan_amount_position="
				+ loan_amount_position + ", surplus_amount_position=" + surplus_amount_position
				+ ", opposite_account_position=" + opposite_account_position + ", paccount_name_position="
				+ opposite_account_name_position + ", check_order_position=" + check_order_position + ", abstract_position="
				+ abstract_position + ", purpose_position=" + purpose_position + "]";
	}
	
	
	
	
		
	
}
