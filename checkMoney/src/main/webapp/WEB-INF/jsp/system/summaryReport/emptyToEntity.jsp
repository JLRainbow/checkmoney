<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>汇总报表做成(虚实对账)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/system/wages/bootstrap-datetimepicker.css" type="text/css">
</head>
<body>
<div class="page-wrapper">
    <div class="container-fluid">
        <h4 class="module-title">钱包充值提现报表做成（虚实对账）</h4>
        <a class="module-close" href="${pageContext.request.contextPath}/index.do">X</a>
        <form class="form-search" id="form" name="form"  method="post" action="${pageContext.request.contextPath}/sumReportyEmptyToEntity/exportExcel.do">
        	 <div class="form-block">
	            <label>报表种类</label>
	            <div class="form-group">
		            <select class="form-control" id="reportType" name="reportType">
		                <option value="钱包明细账户核对汇总表">钱包明细账户核对汇总表</option>
		                <option value="银行明细账户核对汇总表">银行明细账户核对汇总表</option>
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
function createReport(){
	$("#form").submit();
}



</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/locales/bootstrap-datetimepicker.fr.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/date.js"></script>
</html>