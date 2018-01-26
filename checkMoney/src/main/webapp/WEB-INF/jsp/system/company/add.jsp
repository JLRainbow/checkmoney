<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf" %>
<script type="text/javascript" src="${ctx}/js/system/company/add.js">
</script>
</head>
<body>
	<div class="l_err"></div>
	<form id="form" name="form" class="form-horizontal " method="post" action="${ctx}/company/addEntity.do">
		<div class="container-fluid bounced mt-20">
			<div class="form-group">
				<label class="col-sm-3 control-label">名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入唯一的公司名称" name="companyFormMap.name" id="name">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">Key</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc"
						placeholder="请输入唯一的companykey" name="companyFormMap.companykey" id="companykey">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入公司描述"
						name="companyFormMap.description" id="description">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">启用状态</label>
				<div class="col-sm-9">
					<div class="btn-group m-r">
						<button data-toggle="dropdown"
							class="btn btn-sm btn-default dropdown-toggle">
							<span class="dropdown-label">启用</span> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-select">
							<li class=""><a href="#"><input type="radio"
									name="companyFormMap.status" value="1" checked="checked">启用</a></li>
							<li class="active"><a href="#"><input type="radio"
									name="companyFormMap.status" value="0">停用</a></li>
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