<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/user/list.js"></script>
<div class="page-wrapper">
	<div class="container-fluid">
		<h4 class="module-title">用户管理</h4>
		<a class="module-close" href="${pageContext.request.contextPath}/index.do">X</a>
		<form class="form-search" role="form" id="searchForm" name="searchForm" onsubmit="return false;">
            <div class="form-group">
				<label class="control-label">账号:</label> 
				<input class="input-medium ui-autocomplete-input form-control" id="accountname" name="userFormMap.accountname">
			</div>
            <button type="button" class="btn btn-info" id="search">
                <span class="glyphicon glyphicon-search"></span> 查询
            </button>
            <a href="javascript:grid.exportData('/user/export.do')" class="btn btn-primary" id="search">导出用户列表</a>
        </form>
        <div class="table-btn">
        	<c:forEach items="${res}" var="key">
				${key.description}
			</c:forEach>
		</div>
		<div class="table-responsive">
			<div id="paging" class="pagclass"></div>
		</div>
		<div class="table-responsive">
			<div id="paging2" class="pagclass"></div>
		</div>	
	</div>
</div>