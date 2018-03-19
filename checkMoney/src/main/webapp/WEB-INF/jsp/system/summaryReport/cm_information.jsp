<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>对账信息查询</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/system/wages/bootstrap-datetimepicker.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mloading/jquery.mloading.css">

</head>
<body>
<div class="page-wrapper">
    <div class="container-fluid">
        <h4 class="module-title">对账信息查询</h4>
        <a class="module-close" href="${pageContext.request.contextPath}/index.do">X</a>
        <form class="form-search" id="form" name="form"  method="post" action="${pageContext.request.contextPath}/summary_report/queryReport.do">
	        <div class="form-block">
	            <label>渠道</label>
	           	<select class="form-control" id="chanelType" name="chanelType">
	                <option value="支付渠道">支付渠道</option>
	                <option value="收款渠道">收款渠道</option>
	                <option value="weBank">微众银行</option>
	            </select>
				<label class="control-label">流水号</label> 
				<input class="input-medium ui-autocomplete-input form-control" id="check_order" name="check_order">
	            <label>对账状态</label>
	           	<select class="form-control" id="check_result" name="check_result">
	                <option value="">全选</option>
	                <option value="0">未对账</option>
	                <option value="1">对账符合</option>
	                <option value="2">稽查</option>
	           	</select>
	           	<label>收款/退款</label>
	           	<select class="form-control" id="fund_type" name="fund_type">
	                <option value="1,2">全选</option>
	                <option value="1">收款</option>
	                <option value="2">退款</option>
	           	</select>
	           	
	        </div>
           	<div class="form-block">
           		<div id="channel_nameDIV">
	           		<label>支付渠道</label>
		           	<select class="form-control" id="channel_name" name="channel_name">
		                <option value="">全选</option>
		                <option value="支付宝">支付宝</option>
		                <option value="微信">微信</option>
		                <option value="国安付/银行卡支付">国安付/银行卡支付</option>
		           	</select>
	           	</div>
	           	<div id="pay_platformDIV"  style="display: none;">
		           	<label>支付平台</label>
		           	<select class="form-control" id="pay_platform" name="pay_platform">
		                <option value="">全选</option>
		                <option value="微信">微信</option>
		                <option value="微信扫码">微信扫码</option>
		                <option value="微信公众号">微信公众号</option>
		                <option value="支付宝">支付宝</option>
		                <option value="支付宝扫码">支付宝扫码</option>
		                <option value="国安付/银行卡支付">国安付/银行卡支付</option>
		                <option value="礼品卡">礼品卡</option>
		           	</select>
	           	</div>
	           	<div id="weBankDIV"  style="display: none;">
		           	<label>支付平台</label>
		           	<select class="form-control" id="weBankType">
		                <option value="微信支付">微信支付</option>
		                <option value="平台收款">平台收款</option>
		           	</select>
	           	</div>
	            <label>开始日期</label>
	            <div class="input-append date form_datetime" id="datetimepicker1" data-date="" data-date-format="yyyy-mm-dd">
	                <input id="startTime" name="startTime" type="text" value="" readonly="" class="form-control">
	                <span class="add-on "><i class="icon-th glyphicon glyphicon-th-large"></i></span>
	            </div>
	       
	            <label>结束日期</label>
	            <div class="input-append date form_datetime" id="datetimepicker2" data-date="" data-date-format="yyyy-mm-dd">
	                <input id="endTime" name="endTime" type="text" value="" readonly="" class="form-control">
	                <span class="add-on "><i class="icon-th glyphicon glyphicon-th-large"></i></span>
	            </div>
	            <button type="button" class="btn  btn-info" id="search">
	                <span class="glyphicon glyphicon-search"></span>  查询
	            </button>
	            <button type="button" class="btn  btn-info"  onclick="queryReport()">
	                <span class="glyphicon glyphicon-list-alt"></span>  导出
	            </button>
	         </div>
        </form>
        <div class="table-responsive">
			<div id="paging" class="pagclass"></div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/locales/bootstrap-datetimepicker.fr.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/summaryReport/cm_information.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/mloading/jquery.mloading.js"></script>

</body>
</html>