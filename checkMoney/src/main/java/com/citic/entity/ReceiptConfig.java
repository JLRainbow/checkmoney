package com.citic.entity;
/**
 * 收款配置信息实体类
 * @author Administrator
 *
 */
public class ReceiptConfig{
		
		//名称
		private String channel_name;
		//第几行开始读取
		private Integer start_line;
		//支付平台位置
		private Integer pay_platform_position;
		//流水号位置
		private Integer check_order_position;
		//金额位置
		private Integer pay_amount_position;
		//门店名称位置
		private Integer store_name_position;
		//E店名称位置
		private Integer eshop_name_position;
		//E店id位置
		private Integer eshop_id_position;
		//是否协议位置
		private Integer self_position;
		//支付状态位置
		private Integer status_position;
		//支付时间位置
		private Integer receipt_date_position;
		//关系开始读取行
		private Integer relation_start_line;
		//关系id位置
		private Integer relation_id_position;
		//关系流水号位置
		private Integer relation_chkId_position;
		//关系支付平台位置
		private Integer relation_pay_platform_position;
		//退款第几行开始读取
		private Integer refund_start_line;
		//退款支付平台位置
		private Integer refund_pay_platform_position;
		//退款流水号位置
		private Integer refund_check_order_position;
		//退款金额位置
		private Integer refund_amount_position;
		//退款门店名称位置
		private Integer refund_store_name_position;
		//退款E店名称位置
		private Integer refund_eshop_name_position;
		//退款E店名称位置
		private Integer refund_eshop_id_position;
		//退款是否协议位置
		private Integer refund_self_position;
		//退款状态位置
		private Integer refund_status_position;
		//退款时间位置
		private Integer refund_date_position;
		//退款备注位置
		private Integer refund_comment_position;
		
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
		public Integer getPay_platform_position() {
			return pay_platform_position;
		}
		public void setPay_platform_position(Integer pay_platform_position) {
			this.pay_platform_position = pay_platform_position;
		}
		public Integer getCheck_order_position() {
			return check_order_position;
		}
		public void setCheck_order_position(Integer check_order_position) {
			this.check_order_position = check_order_position;
		}
		public Integer getPay_amount_position() {
			return pay_amount_position;
		}
		public void setPay_amount_position(Integer pay_amount_position) {
			this.pay_amount_position = pay_amount_position;
		}
		public Integer getStore_name_position() {
			return store_name_position;
		}
		public void setStore_name_position(Integer store_name_position) {
			this.store_name_position = store_name_position;
		}
		public Integer getEshop_name_position() {
			return eshop_name_position;
		}
		
		public Integer getEshop_id_position() {
			return eshop_id_position;
		}
		public void setEshop_id_position(Integer eshop_id_position) {
			this.eshop_id_position = eshop_id_position;
		}
		public void setEshop_name_position(Integer eshop_name_position) {
			this.eshop_name_position = eshop_name_position;
		}
		public Integer getSelf_position() {
			return self_position;
		}
		public void setSelf_position(Integer self_position) {
			this.self_position = self_position;
		}
		public Integer getStatus_position() {
			return status_position;
		}
		public void setStatus_position(Integer status_position) {
			this.status_position = status_position;
		}
		public Integer getReceipt_date_position() {
			return receipt_date_position;
		}
		public void setReceipt_date_position(Integer receipt_date_position) {
			this.receipt_date_position = receipt_date_position;
		}
		public Integer getRelation_start_line() {
			return relation_start_line;
		}
		public void setRelation_start_line(Integer relation_start_line) {
			this.relation_start_line = relation_start_line;
		}
		public Integer getRelation_id_position() {
			return relation_id_position;
		}
		public void setRelation_id_position(Integer relation_id_position) {
			this.relation_id_position = relation_id_position;
		}
		public Integer getRelation_chkId_position() {
			return relation_chkId_position;
		}
		public void setRelation_chkId_position(Integer relation_chkId_position) {
			this.relation_chkId_position = relation_chkId_position;
		}
		
		public Integer getRelation_pay_platform_position() {
			return relation_pay_platform_position;
		}
		public void setRelation_pay_platform_position(Integer relation_pay_platform_position) {
			this.relation_pay_platform_position = relation_pay_platform_position;
		}
		public Integer getRefund_start_line() {
			return refund_start_line;
		}
		public void setRefund_start_line(Integer refund_start_line) {
			this.refund_start_line = refund_start_line;
		}
		public Integer getRefund_pay_platform_position() {
			return refund_pay_platform_position;
		}
		public void setRefund_pay_platform_position(Integer refund_pay_platform_position) {
			this.refund_pay_platform_position = refund_pay_platform_position;
		}
		public Integer getRefund_check_order_position() {
			return refund_check_order_position;
		}
		public void setRefund_check_order_position(Integer refund_check_order_position) {
			this.refund_check_order_position = refund_check_order_position;
		}
		public Integer getRefund_amount_position() {
			return refund_amount_position;
		}
		public void setRefund_amount_position(Integer refund_amount_position) {
			this.refund_amount_position = refund_amount_position;
		}
		public Integer getRefund_store_name_position() {
			return refund_store_name_position;
		}
		public void setRefund_store_name_position(Integer refund_store_name_position) {
			this.refund_store_name_position = refund_store_name_position;
		}
		public Integer getRefund_eshop_name_position() {
			return refund_eshop_name_position;
		}
		public void setRefund_eshop_name_position(Integer refund_eshop_name_position) {
			this.refund_eshop_name_position = refund_eshop_name_position;
		}
		public Integer getRefund_self_position() {
			return refund_self_position;
		}
		public void setRefund_self_position(Integer refund_self_position) {
			this.refund_self_position = refund_self_position;
		}
		public Integer getRefund_status_position() {
			return refund_status_position;
		}
		public void setRefund_status_position(Integer refund_status_position) {
			this.refund_status_position = refund_status_position;
		}
		public Integer getRefund_date_position() {
			return refund_date_position;
		}
		public void setRefund_date_position(Integer refund_date_position) {
			this.refund_date_position = refund_date_position;
		}
		public Integer getRefund_comment_position() {
			return refund_comment_position;
		}
		public void setRefund_comment_position(Integer refund_comment_position) {
			this.refund_comment_position = refund_comment_position;
		}
		
		public Integer getRefund_eshop_id_position() {
			return refund_eshop_id_position;
		}
		public void setRefund_eshop_id_position(Integer refund_eshop_id_position) {
			this.refund_eshop_id_position = refund_eshop_id_position;
		}
		@Override
		public String toString() {
			return "ReceiptConfig [channel_name=" + channel_name + ", start_line=" + start_line
					+ ", pay_platform_position=" + pay_platform_position + ", check_order_position="
					+ check_order_position + ", pay_amount_position=" + pay_amount_position + ", store_name_position="
					+ store_name_position + ", eshop_name_position=" + eshop_name_position + ", self_position="
					+ self_position + ", status_position=" + status_position + ", receipt_date_position="
					+ receipt_date_position + ", relation_start_line=" + relation_start_line + ", relation_id_position="
					+ relation_id_position + ", relation_chkId_position=" + relation_chkId_position
					+ ", relation_pay_platform_position=" + relation_pay_platform_position + ", refund_start_line="
					+ refund_start_line + ", refund_pay_platform_position=" + refund_pay_platform_position
					+ ", refund_check_order_position=" + refund_check_order_position + ", refund_amount_position="
					+ refund_amount_position + ", refund_store_name_position=" + refund_store_name_position
					+ ", refund_eshop_name_position=" + refund_eshop_name_position + ", refund_self_position="
					+ refund_self_position + ", refund_status_position=" + refund_status_position
					+ ", refund_date_position=" + refund_date_position + ", refund_comment_position="
					+ refund_comment_position + "]";
		}
		
		
		
		

}
