package com.citic.entity;
/**
 * 渠道配置信息实体类
 * @author Administrator
 *
 */
public class ConfigInf {
	//支付名称
	private String channel_name;
	//关键字个数
	private Integer keywords_num;
	//关键字位置
	private String keywords_position;
	//支付渠道金额位置
	private Integer pay_amount_position;
	//支付渠道金额位置
	private Integer refund_amount_position;
	//支付交易日位置
	private Integer pay_date_position;
	//款单种类位置
	private Integer fund_type_position;
	//备注位置
	private Integer comment_position;
	//第几行开始读取
	private Integer start_line;
	//倒数第几行结束读取
	private Integer end_bottom_line;

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public Integer getKeywords_num() {
		return keywords_num;
	}

	public void setKeywords_num(Integer keywords_num) {
		this.keywords_num = keywords_num;
	}

	public String getKeywords_position() {
		return keywords_position;
	}

	public void setKeywords_position(String keywords_position) {
		this.keywords_position = keywords_position;
	}

	public Integer getPay_amount_position() {
		return pay_amount_position;
	}

	public void setPay_amount_position(Integer pay_amount_position) {
		this.pay_amount_position = pay_amount_position;
	}

	public Integer getPay_date_position() {
		return pay_date_position;
	}

	public void setPay_date_position(Integer pay_date_position) {
		this.pay_date_position = pay_date_position;
	}

	public Integer getFund_type_position() {
		return fund_type_position;
	}

	public void setFund_type_position(Integer fund_type_position) {
		this.fund_type_position = fund_type_position;
	}

	public Integer getStart_line() {
		return start_line;
	}

	public void setStart_line(Integer start_line) {
		this.start_line = start_line;
	}

	public Integer getEnd_bottom_line() {
		return end_bottom_line;
	}

	public void setEnd_bottom_line(Integer end_bottom_line) {
		this.end_bottom_line = end_bottom_line;
	}

	public Integer getRefund_amount_position() {
		return refund_amount_position;
	}

	public void setRefund_amount_position(Integer refund_amount_position) {
		this.refund_amount_position = refund_amount_position;
	}

	public Integer getComment_position() {
		return comment_position;
	}

	public void setComment_position(Integer comment_position) {
		this.comment_position = comment_position;
	}

	@Override
	public String toString() {
		return "ConfigInf [channel_name=" + channel_name + ", keywords_num=" + keywords_num + ", keywords_position="
				+ keywords_position + ", pay_amount_position=" + pay_amount_position + ", refund_amount_position="
				+ refund_amount_position + ", pay_date_position=" + pay_date_position + ", fund_type_position="
				+ fund_type_position + ", comment_position=" + comment_position + ", start_line=" + start_line
				+ ", end_bottom_line=" + end_bottom_line + "]";
	}


}
