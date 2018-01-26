<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf" %>
<script type="text/javascript" src="${ctx}/js/system/role/add.js">
</script>
</head>
<body>
	<div class="l_err"></div>
	<form id="form" name="form" class="form-horizontal bounced" method="post" action="${ctx}/role/addEntity.do">
		<div class="container-fluid ">
			<div class="form-group">
				<label class="col-sm-3 control-label">角色名</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入角色名" name="roleFormMap.name" id="name">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">roleKey</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入roleKey" name="roleFormMap.rolekey" id="roleKey">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入账号描述"
						name="roleFormMap.description" id="description">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">角色状态</label>
				<div class="col-sm-9">
					<!-- <select class="form-control" id="channel_type" name="channelManagementFormMap.channel_type">
		                <option value="0">支付渠道</option>
		                <option value="1">收款渠道</option>
		            </select> -->
					<div class="btn-group m-r">
						<button data-toggle="dropdown"
							class="btn btn-sm btn-default dropdown-toggle">
							<span class="dropdown-label">是</span> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-select">
							<li class=""><a href="#"><input type="radio"
									name="roleFormMap.status" value="0" checked="checked">是</a></li>
							<li class="active"><a href="#"><input type="radio"
									name="roleFormMap.status" value="1">否</a></li>
						</ul>
					</div>
				</div>
			</div>
			 
		</div>
		<footer class="panel-footer text-right bg-light lter">
			<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> 
		</section>
	</form>
</body>
</html>