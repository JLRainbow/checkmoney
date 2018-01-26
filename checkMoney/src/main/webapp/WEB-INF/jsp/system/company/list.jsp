<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/company/list.js"></script>
	<!-- 面包屑开始 -->
	<ol class="breadcrumb">
		<li><a href="${ctx}/citicwages/index.do">首页</a></li>
		<li class="nolink"><a href="#">基础管理</a></li>
		<li class="active">公司管理</li>
	</ol>
	<!-- 面包屑结束 -->
	<div class="m-b-md over">
		<form class="form-inline left-search" role="form" id="searchForm" name="searchForm">
			<div class="form-group">
				<label class="control-label">公司名称:</label> 
				<input class="input-medium ui-autocomplete-input form-control" id="name" name="companyFormMap.name">
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
			<a href="javascript:grid.exportData('/company/export.do')" class="btn btn-info" id="search">导出公司列表</a>
		</form>
		<header class="panel-heading  right-btn">
			<div class="doc-buttons">
				<c:forEach items="${res}" var="key">
					${key.description}
				</c:forEach>
			</div>
		</header>
	</div>
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	
	<div class="table-responsive">
		<div id="paging2" class="pagclass"></div>
	</div>
