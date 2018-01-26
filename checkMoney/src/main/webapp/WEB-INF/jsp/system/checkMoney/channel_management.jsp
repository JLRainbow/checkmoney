<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>渠道设置管理</title>
</head>
<body>

<div class="page-wrapper">
	<div class="container-fluid">
		<h4 class="module-title">渠道设置管理</h4>
		<a class="module-close" href="${pageContext.request.contextPath}/index.do">X</a>
		<form class="form-search" role="form" id="searchForm" name="searchForm" onsubmit="return false;">
            <label>渠道类型</label>
            <select class="form-control" id="channel_type" name="channelManagementFormMap.channel_type">
                <option value="">全部</option>
                <option value="1">支付渠道</option>
                <option value="2">收款渠道</option>
                <option value="3">钱包银行</option>
            </select>
            <label>渠道名称</label>
            <input class="form-control" id="channel_name"  name="channelManagementFormMap.channel_name">
            <button type="button" class="btn btn-info" id="search">
                <span class="glyphicon glyphicon-search"></span> 查询
            </button>
        </form>
        <div class="table-btn">
        	<c:forEach items="${res}" var="key">
					${key.description}
				</c:forEach>
        </div>

		<div class="table-responsive">
				<div id="paging" class="pagclass"></div>
		</div>
	</div>
</div>
 <div class="table-responsive">
		<div id="paging" class="pagclass"></div>
</div>
<script src="${pageContext.request.contextPath}/js/system/checkMoney/channel_management.js" ></script>
</body>
</html>