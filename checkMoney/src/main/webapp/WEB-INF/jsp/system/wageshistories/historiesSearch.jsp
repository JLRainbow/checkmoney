<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/system/wages/wagesImport.css"
	type="text/css">
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/system/wages/bootstrap-datetimepicker.css"
	type="text/css">
	<style>
	/*印章样式*/
		.table-all{position:relative;}
		.table-all img{position:absolute; left:40%;top:5%; border:none; z-index:10000; width:200px;height:160px;}
	</style>

      	<!-- 面包屑开始 -->
      	<ol class="breadcrumb">
		  <li><a href="${ctx}/citicwages/index.do">首页</a></li>
		  <li class="nolink"><a href="#">历史明细管理</a></li>
		  <li class="active">历史明细查询</li>
		</ol>
		<!-- 面包屑结束 -->
	<div class="m-b-none over">
		<form class="form-inline left-search" role="form" id="searchForm" name="searchForm">
			<div class="form-group inline mb15">
              <label>所属公司：</label>
              <select class="form-control">
	               <c:forEach items="${companies}" var="key">
					 	<option value="${key.companykey}">${key.name}</option>
					</c:forEach>
	              </select>
            </div>
            <div class="form-group inline mb15">
              <label>收入类型：</label>
             	<select class="form-control">
	               <c:forEach items="${budgettypes}" var="key">
					 	<option value="${key.budgettypekey}">${key.name}</option>
					</c:forEach>
	              </select>
            </div>
			<div class="form-group inline mb15">
              <label>收入月份：</label>
              <input type="text"  value="" class="form-control form_datetime1" readonly >
              <span class="add-on1 line-hei34">清空时间</span>
            </div>
            <a class="morebtn" href="javascript:;">更多搜索</a>
            <a class="streamline" href="javascript:;">精简搜索</a>
            <button type="button" id="search" class="btn btn-default">查询</button>
            <div class="morebody">
	            <div class="form-group inline mb15">
	              <label>批次号：</label>
	              <input type="text"  value="" class="form-control ">
	            </div>
	            <div class="form-group inline mb15">
	              <label>发放状态：</label>
	              <select class="form-control">
	                <option>1(导入基本信息)</option>
	                <option>2(已开户绑卡)</option>
	                <option>-3(收入发放失败)</option>
	              </select>
	            </div>
	            <div class="form-group inline mb15">
	              <label>账户：</label>
	              <input type="text"  value="" class="form-control ">
	            </div>
            </div>
            
		</form>
		
	</div>
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/date.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/list.js"></script>
	<!-- 搜索 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/search/search.js"></script>
