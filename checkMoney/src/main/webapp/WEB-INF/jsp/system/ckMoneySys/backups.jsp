<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/common/bootstrap/bootstrap-select.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>对账表单备份</title>
</head>
<body>
<div class="page-wrapper">
    <div class="container-fluid">
        <h4 class="module-title">对账表单备份</h4>
        <a class="module-close" href="${pageContext.request.contextPath}/index.do">X</a>
			<div class="row checking">
				<div class="import wallet">
					<div class="row">
						<div class="col-md-6">
							<div class="col-md-3">
								<p class="form-control-static">选择表名称</p>
							</div>
							<div class="col-md-6">
								<select class="form-control" id="tableType">
									<option value="">-----------请选择----------</option>
									<option value="平台对账表">平台对账表</option>
									<option value="支付渠道对账表">支付渠道对账表</option>
								</select>
							</div>
						</div>

					</div>
				</div>
				<div class="col-md-6 checkPay">
					<dl style="width: 600px; height: 60px;">
						<dd>
							<span class="pull-left">平台对账表当前数据期间为：（XXXX年XX月XX日~XXXX年XX月XX日）</span>
						</dd>
					</dl>
				</div>

			</div>
			<form class="form-search" id="form" method="post" >
				<div class="latitude">
					<div class="form-block">
						<div class="row">
							<div class="col-md-3">
								<p class="form-control-static" style="width: 200px">请选择要备份区间：</p>
							</div>
						</div>
						<div class="form-block">
							<label>开始日期</label>
							<div class="input-append date form_datetime" id="datetimepicker1"
								data-date="" data-date-format="yyyy-mm-dd">
								<input id="startTime" name="startTime" type="text" value=""
									readonly="" class="form-control"> <span class="add-on "><i
									class="icon-th glyphicon glyphicon-th-large"></i></span>
							</div>

							<label>终了日期</label>
							<div class="input-append date form_datetime" id="datetimepicker2"
								data-date="" data-date-format="yyyy-mm-dd">
								<input id="endTime" name="endTime" type="text" value=""
									readonly="" class="form-control"> <span class="add-on "><i
									class="icon-th glyphicon glyphicon-th-large"></i></span>
							</div>
							<button type="button" class="btn btn-danger" id="backupsAndDel">备份并删除</button>
						</div>

					</div>
				</div>
			</form>
		</div>
</div>	
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/ckMoneySys/backups.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery/jquery.form.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery/ajaxfileupload.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/locales/bootstrap-datetimepicker.fr.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/date.js"></script>
</html>