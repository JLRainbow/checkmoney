<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>支付详情</title>
</head>
<body>
<form class="form-horizontal bounced">
	<div class="container-fluid ">
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">销售日期</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" id="date" value="${accountPaymentChkFormMap.pay_date}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">流水号</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountPaymentChkFormMap.check_order}" disabled="disabled">
		    </div>
		</div>
		<div class="form-group">
		    <label class="col-sm-3 control-label">支付渠道</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountPaymentChkFormMap.channel_name}" disabled="disabled">
		    </div>
	   	</div>
		<div class="form-group">
		    <label class="col-sm-3 con平台销售数据trol-label">支付金额</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountPaymentChkFormMap.pay_amount}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">收款金额</label>
		    <div class="col-sm-9">
		     	<input type="text"  class="form-control" value="${accountPaymentChkFormMap.recipt_amount}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">对账状态</label>
		    <div class="col-sm-9">
		    	<select disabled="disabled"  class="form-control">
		    		<option value="0"  ${accountPaymentChkFormMap.check_result=="0"?'selected':''} >未对账</option>
		    		<option value="1"  ${accountPaymentChkFormMap.check_result=="1"?'selected':''} >对账符合</option>
		    		<option value="2"  ${accountPaymentChkFormMap.check_result=="2"?'selected':''} >稽查</option>
				</select>
		    </div>
   	   	</div>
   	   	<div class="form-group">
		    <label class="col-sm-3 control-label">收款/退款</label>
		    <div class="col-sm-9">
		    	<select disabled="disabled"  class="form-control">
		    		<option value="1"  ${accountPaymentChkFormMap.fund_type=="1"?'selected':''} >收款</option>
		    		<option value="2"  ${accountPaymentChkFormMap.fund_type=="2"?'selected':''} >退款</option>
				</select>
		    </div>
   	   	</div>
   	   	<div class="form-group">
		    <label class="col-sm-3 control-label">门店名称</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountPaymentChkFormMap.store_name}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">E店名称</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountPaymentChkFormMap.eshop_name}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">供应商</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountPaymentChkFormMap.provider_name}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">是否自营</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountPaymentChkFormMap.self}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">支付平台</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountPaymentChkFormMap.pay_platform}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">备注</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountPaymentChkFormMap.comment}" disabled="disabled">
		    </div>
	   	</div>
	</div>
</form>
</body>
<script type="text/javascript">
$().ready(function(){
	var date =$("#date").val();
	$("#date").val(date.substring(0,10));
});
</script>
</html>