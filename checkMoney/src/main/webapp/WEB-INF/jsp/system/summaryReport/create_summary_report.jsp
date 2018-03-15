<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>汇总报表做成(虚虚对账)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/system/wages/bootstrap-datetimepicker.css" type="text/css">
</head>
<body>
<div class="page-wrapper">
    <div class="container-fluid">
        <h4 class="module-title">平台销售汇总报表做成（虚虚对账）</h4>
        <a class="module-close" href="${pageContext.request.contextPath}/index.do">X</a>
        <form class="form-search" id="form" name="form"  method="post" action="${pageContext.request.contextPath}/summary_report/exportExcel.do">
        	 <div class="form-block">
	            <label>报表种类</label>
	            <div class="form-group">
		            <select class="form-control" id="reportType" name="reportType">
		                <option value="平台销售核对汇总表">平台销售核对汇总表</option>
<!-- 		                <option value="微超销售核对汇总表">微超销售核对汇总表</option> -->
		                <option value="支付宝核对汇总表">支付宝核对汇总表</option>
		                <option value="微信核对汇总表">微信核对汇总表</option>
		                <option value="国安付核对汇总表">国安付核对汇总表</option>
<!-- 		                <option value="微信扫码核对汇总表">微信扫码核对汇总表</option> -->
			        </select>
		        </div>
	            <label>支付方式</label>
	            <div class="form-group">
	            	<select class="form-control" id="payWay" name="payWay">
		               	<option value="">全部</option>
		               	 <option value="支付宝">支付宝</option>
		                <option value="微信">微信</option>
		                <option value="国安付/银行卡支付">国安付/银行卡支付</option>
		                <option value="礼品卡">礼品卡</option>
		        	</select>
		        </div>
		        <label>收款/退款</label>
	            <div class="form-group">
	            	<select class="form-control" id="fund_type" name="fund_type">
		               	<option value="1,2">全部</option>
		                <option value="1">收款</option>
		                <option value="2">退款</option>
		        	</select>
		        </div>
		     </div>
	         <div class="form-block">
	            <label>开始日期</label>
	            <div class="input-append date form_datetime" id="datetimepicker1" data-date="" data-date-format="yyyy-mm-dd">
	                <input  id="startTime" name="startTime" type="text" value="" readonly="" class="form-control">
	                <span class="add-on "><i class="icon-th glyphicon glyphicon-th-large"></i></span>
	            </div>
	      
	            <label>终了日期</label>
	            <div class="input-append date form_datetime" id="datetimepicker2" data-date="" data-date-format="yyyy-mm-dd">
	                <input id="endTime" name="endTime" type="text" value="" readonly="" class="form-control">
	                <span class="add-on "><i class="icon-th glyphicon glyphicon-th-large"></i></span>
	            </div>
	            <button type="button" class="btn  btn-info"  onclick="createReport()">
	                <span class="glyphicon glyphicon-list-alt"></span>  报表做成
	            </button>
	        </div>
        </form>
    </div>
</div>  
</body>
<script type="text/javascript">
//获取支付方式
// $().ready(function(){
// 	$.post('${pageContext.request.contextPath}/summary_report/getPayWay.do',
// 			function(result){
// 				for (var i = 0, length = result.length; i< length; i++) {
// 					var newOption  =  document.createElement("option"); //动态添加option
// 			            newOption.value = result[i].channel_name; 
// 			            newOption.text =result[i].channel_name;
// 			            document.getElementById("payWay").options.add(newOption); 
// 				}
// 				var newOption1  =  document.createElement("option"); //动态添加option
// 	            newOption1.value = "POS"; 
// 	            newOption1.text = "POS";
// 	            document.getElementById("payWay").options.add(newOption1); 
// 	            var newOption2  =  document.createElement("option"); //动态添加option
// 	            newOption2.value = "现金"; 
// 	            newOption2.text = "现金";
// 	            document.getElementById("payWay").options.add(newOption2); 
// 	},"json")
// });
function createReport(){
	var h1 = document.getElementById("payWay");//获取dom
	h1.removeAttribute('disabled');
	$("#form").submit();
	var reportType = $("#reportType").val();
	if(reportType != "平台销售核对汇总表"){
		 h1.setAttribute('disabled','disabled');//添加属性
	}
}

$("#reportType").change(function (){
	var h1 = document.getElementById("payWay");//获取dom
	h1.removeAttribute('disabled');
	var reportType = $("#reportType").val();
	if(reportType == "支付宝核对汇总表"){
		 $("#payWay").val("支付宝");
		 h1.setAttribute('disabled','disabled');//添加属性
	}
	if(reportType == "微信核对汇总表"){
		 $("#payWay").val("微信");
		 h1.setAttribute('disabled','disabled');//添加属性
	}
	if(reportType == "国安付核对汇总表"){
		 $("#payWay").val("国安付/银行卡支付");
		 h1.setAttribute('disabled','disabled');//添加属性
	}
// 	if(reportType == "微信扫码核对汇总表"){
// 		 $("#payWay").val("微信扫码");
// 		 h1.setAttribute('disabled','disabled');//添加属性
// 	}
	
});

</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/locales/bootstrap-datetimepicker.fr.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/date.js"></script>
</html>