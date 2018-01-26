<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/wagesImport.js"></script>

      	<!-- 面包屑开始 -->
      	<ol class="breadcrumb">
		  <li><a href="${ctx}/citicwages/index.do">首页</a></li>
		  <li class="nolink"><a href="#">工资管理</a></li>
		  <li class="active">工资明细导入</li>
		</ol>
		<!-- 面包屑结束 -->
	<div class="m-b-md over">
		<form class="form-inline left-search" role="form" id="searchForm"
			name="searchForm">
			<div class="form-group">
				<label >请导入当前月份工资明细Excel:</label> 
				
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
		</form>
	</div>
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
