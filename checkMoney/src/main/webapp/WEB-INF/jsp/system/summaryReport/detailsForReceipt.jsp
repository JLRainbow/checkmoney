<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>收款详情</title>
</head>
<body>
<form class="form-horizontal bounced">
	<div class="container-fluid ">
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">销售日期</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" id="date" value="${accountReceiptChkFormMap.receipt_date}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">流水号</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountReceiptChkFormMap.relation_id}" disabled="disabled">
		    </div>
		</div>
		<div class="form-group">			
		    <label class="col-sm-3 control-label">收款渠道</label>
		    <div class="col-sm-9">
		    	<select disabled="disabled"  class="form-control">
		    		<option value="1" ${accountReceiptChkFormMap.source=="1"?'selected':''} >平台销售数据</option>
		    		<option value="2" ${accountReceiptChkFormMap.source=="2"?'selected':''} >微超销售数据</option>
				</select>
			</div>
		</div>
		<div class="form-group">
		    <label class="col-sm-3 con平台销售数据trol-label">收款金额</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountReceiptChkFormMap.pay_amount}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">支付金额</label>
		    <div class="col-sm-9">
		     	<input type="text"  class="form-control" value="${accountReceiptChkFormMap.recipt_amount}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">对账状态</label>
		    <div class="col-sm-9">
		    	<select disabled="disabled"  class="form-control">
		    		<option value="0"  ${accountReceiptChkFormMap.check_result=="0"?'selected':''} >未对账</option>
		    		<option value="1"  ${accountReceiptChkFormMap.check_result=="1"?'selected':''} >对账符合</option>
		    		<option value="2"  ${accountReceiptChkFormMap.check_result=="2"?'selected':''} >稽查</option>
		    		<option value="3"  ${accountReceiptChkFormMap.check_result=="3"?'selected':''} >合并未对账</option>
		    		<option value="4"  ${accountReceiptChkFormMap.check_result=="4"?'selected':''} >合并对账符合</option>
		    		<option value="5"  ${accountReceiptChkFormMap.check_result=="5"?'selected':''} >合并待稽查</option>
				</select>
		    </div>
   	   	</div>
   	   	<div class="form-group">
		    <label class="col-sm-3 control-label">收款/退款</label>
		    <div class="col-sm-9">
		    	<select disabled="disabled"  class="form-control">
		    		<option value="1"  ${accountReceiptChkFormMap.fund_type=="1"?'selected':''} >收款</option>
		    		<option value="2"  ${accountReceiptChkFormMap.fund_type=="2"?'selected':''} >退款</option>
				</select>
		    </div>
   	   	</div>
   	   	<div class="form-group">
		    <label class="col-sm-3 control-label">门店名称</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountReceiptChkFormMap.store_name}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">E店名称</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountReceiptChkFormMap.eshop_name}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">E店ID</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountReceiptChkFormMap.eshop_id}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">供应商</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountReceiptChkFormMap.provider_name}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">是否自营</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountReceiptChkFormMap.self}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">支付平台</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountReceiptChkFormMap.pay_platform}" disabled="disabled">
		    </div>
	   	</div>
	   	<div class="form-group">
		    <label class="col-sm-3 control-label">备注</label>
		    <div class="col-sm-9">
		    	<input type="text"  class="form-control" value="${accountReceiptChkFormMap.comment}" disabled="disabled">
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