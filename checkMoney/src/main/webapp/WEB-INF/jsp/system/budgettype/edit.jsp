<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/budgettype/edit.js"></script>
</head>
<body>
	<div class="l_err"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
		action="${ctx}/budgettype/editEntity.do">
		<input type="hidden" class="form-control checkacc" value="${budgettype.id}"
			name="budgettypeFormMap.id" id="id">
		<div class="container-fluid bounced mt-20">
				<div class="form-group">
					<label class="col-sm-3 control-label">名称</label>
					<div class="col-sm-9">
						<input type="text" class="form-control checkRole"
							placeholder="请输入唯一的收支类型名称" name="budgettypeFormMap.name" id="name" value="${budgettype.name}" readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">Key</label>
					<div class="col-sm-9">
						<input type="text" class="form-control checkacc"
							placeholder="请输入唯一的budgettypekey" name="budgettypeFormMap.budgettypekey" id="budgettypekey" value=${budgettype.budgettypekey} readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">描述</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" placeholder="请输入收支类型描述"
							name="budgettypeFormMap.description" id="description" value="${budgettype.description }">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">启用状态</label>
					<div class="col-sm-9">
						<div class="btn-group m-r">
							<button data-toggle="dropdown"
								class="btn btn-sm btn-default dropdown-toggle">
								<span class="dropdown-label"><c:if test="${budgettype.status eq 1}">启用</c:if><c:if test="${budgettype.status eq 0}">停用</c:if></span> <span class="caret"></span>
							</button>
							<ul class="dropdown-menu dropdown-select">
								<li class=""><a href="#"><input type="radio"
										name="budgettypeFormMap.status" value="1"<c:if test="${budgettype.status eq 1}"> checked="checked"</c:if>>启用</a></li>
								<li class="active"><a href="#"><input type="radio"
										name="budgettypeFormMap.status" value="0" <c:if test="${budgettype.status eq 0}"> checked="checked"</c:if>>停用</a></li>
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