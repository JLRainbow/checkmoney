<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/userlogin/list.js"></script>
<div class="page-wrapper">
	<div class="container-fluid">
		<h4 class="module-title">用户登录记录</h4>
		<a class="module-close" href="${pageContext.request.contextPath}/index.do">X</a>
		<form class="form-search" role="form" id="searchForm" name="searchForm"  onsubmit="return false;">
            <div class="form-group">
				<label class="control-label">账号:</label> 
				<input class="input-medium ui-autocomplete-input form-control" id="accountname" name="userLoginFormMap.accountname">
			</div>
            <button type="button" class="btn btn-info" id="search">
                <span class="glyphicon glyphicon-search"></span> 查询
            </button>
        </form>
		<div class="table-responsive">
			<div id="paging" class="pagclass"></div>
		</div>
	</div>
</div>