<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/user/edit.js"></script>
</head>
<body>
	<div class="l_err"></div>
	<form id="form" name="form"  class="form-horizontal bounced" method="post" action="${ctx}/user/editEntity.do">
		<input type="hidden" class="form-control checkacc" value="${user.id}" name="userFormMap.id" id="id">
		<div class="container-fluid">
			<div class="form-group">
				<label class="col-sm-3 control-label">用户名</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入用户名" value="${user.username}"
						name="userFormMap.username" id="username" readonly="readonly">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">账号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入账号" value="${user.accountname}"
						name="userFormMap.accountname" id="accountname" readonly="readonly">
				</div>
			</div>
			
			<div id="selGroup"
				data-url="/role/selRole.do?roleFormMap.userid=${user.id}"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">是否禁用</label>
				<div class="col-sm-9">
					<div class="btn-group m-r">
						<button data-toggle="dropdown"
							class="btn btn-sm btn-default dropdown-toggle">
							<span class="dropdown-label"><c:if
									test="${user.locked eq 1}">是</c:if>
								<c:if test="${user.locked eq 0}">否</c:if></span> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-select">
							<li class="active"><a href="#"><input type="radio"
									name="userFormMap.locked" value="1"
									<c:if test="${user.locked eq 1}"> checked="checked"</c:if>>是</a></li>
							<li class=""><a href="#"><input type="radio"
									name="userFormMap.locked" value="0"
									<c:if test="${user.locked eq 0}"> checked="checked"</c:if>>否</a></li>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入账号描述"
						value="${user.description}" name="userFormMap.description" id="description">
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">保存</button>
		</footer> </section>
	</form>
	<script type="text/javascript">
	onloadurl();
</script>
</body>
</html>