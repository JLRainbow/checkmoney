<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<link href="${ctx}/css/system/resources/edit.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/js/system/resources/edit.js"></script>
</head>
<body>
	<div class="l_err"></div>
	<form id="form" name="form" class="form-horizontal bounced" method="post"
		action="${pageContext.request.contextPath}/resources/editEntity.do">
		<input type="hidden" value="${resources.id}" name="resFormMap.id"
			id="id">
		<div class="container-fluid">
			<div class="form-group">
				<label class="col-sm-3 control-label">菜单名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入菜单名称" name="resFormMap.name" id="name"
						value="${resources.name}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label">菜单标识</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入菜单标识" name="resFormMap.reskey" id="reskey"
						value="${resources.reskey}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label">菜单url</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入菜单url" name="resFormMap.resurl" id="resurl"
						value="${resources.resurl}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label">上级菜单</label>
				<div class="col-sm-9">
					<select id="parentid" name="resFormMap.parentid"
						class="form-control m-b">
					</select>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label">菜单类型</label>
				<div class="col-sm-9">
						<select id="type" name="resFormMap.type" class="form-control m-b"
							tabindex="-1" onchange="but(this)">
							<option value="0">------ 目录 ------</option>
							<option value="1">------ 菜单 ------</option>
							<option value="2">------ 按扭 ------</option>
						</select>
				</div>
			</div>
			<div class="form-group" id="divbut" style="display: none;">
				<label class="col-sm-3 control-label">选择</label>
				<div class="col-sm-9">
					<div id="but" class="doc-buttons"></div>
					<font color="red">可自定义填入html代码</font>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">图标</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入icon" name="resFormMap.icon" id="icon"
						value="${resources.icon}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否隐藏</label>
				<div class="col-sm-9">
					<input id="gritter-light" type="checkbox"
						<c:if test="${resources.ishide eq 1}"> checked="checked"</c:if>
						name="resFormMap.ishide" id="ishide"
						class="ace ace-switch ace-switch-5" value="1">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">菜单描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入菜单描述" name="resFormMap.description"
						id="description" value="${resources.description}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">优先级别</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入优先级别" name="resFormMap.level"
						id="level" value="${resources.level}">
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> </section>
	</form>
	<script type="text/javascript">
		$("#type").val("${resources.type}");
		if ("${resources.type}" == "2") {
			showBut();
		}
		byRes("${resources.parentid}");
	</script>
</body>
</html>