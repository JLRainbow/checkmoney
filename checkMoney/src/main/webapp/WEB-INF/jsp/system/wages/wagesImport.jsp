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
 .layui-layer-dialog .layui-layer-content {
    position: relative;
    padding: 20px;
    line-height: 24px;
    word-break: break-all;
    font-size: 14px;
    overflow: auto;
    text-align: center;
} 

</style>
<!-- 面包屑开始 -->
<ol class="breadcrumb">
	<li><a href="${ctx}/citicwages/index.do">首页</a></li>
	<li class="nolink"><a href="#">收支管理</a></li>
	<li class="active">清单导入</li>
</ol>
<!-- 面包屑结束 -->
<div class="over dn_wanges">
	<div id="dn_taggle">
		<ul class="dn_wanges_info over">
			<li><i class="fa fa-angle-right"></i>&nbsp;第一步：请选择收支明细Excel表</li>
			<li><i class="fa fa-angle-right"></i>&nbsp;第二步：初始化收支明细表</li>
		</ul>
		<form id="form" class="import" enctype="multipart/form-data" method="post"   >
				<div class="form-group ">
	              <label>所属公司：</label>
	              <select class="form-control" name="companykey">
	              	<c:forEach items="${companies}" var="key">
					 	<option value="${key.companykey}">${key.name}</option>
					</c:forEach>
	              </select>
	            </div>
	            <div class="form-group ">
	              <label>收支类型：</label>
	              <select class="form-control" name="budgettypekey">
	               <c:forEach items="${budgettypes}" var="key">
					 	<option value="${key.budgettypekey}">${key.name}</option>
					</c:forEach>
	              </select>
	            </div>
	            
				<div class="form-group ">
	              <label>收支月份：</label>
	              <input type="text"  id="trandate" name="trandate" value="" class="form-control form_datetime1"  placeholder="请输入收支月份" readonly style="width:120px;">
	              <span class="add-on1 line-hei34">清空时间</span>
	            </div>
			
			<div class="form-group">
				<label for="uploadWagesExcelFile">收支明细项目表：</label>
				<input type="file" name="uploadWagesExcelFile" class="inline" id="uploadWagesExcelFile">
			</div>
			<div class="form-group">
				<label>备注：</label>
				<input type="text" class="form-control" name="description" placeholder="备注">
			</div>
			<div class="form-group" >
				<c:forEach items="${res}" var="key">
					${key.description}
				</c:forEach>
			</div>
					
		</form>
			
	</div>
	<dl class="dn_box_import">
		<dd>1.工资明细Excel表的表头格式，字段顺序，字段名称不可随意更改！</dd>
		<dd>2.员工唯一标识默认为中国公民身份证，必填。员工姓名，部门，实发工资为必填项!共四项必填！</dd>
		<dd>3.工资明细Excel表的Sheet名请勿随意更改，默认名称为citicwages。</dd>
	</dl>

</div>
<div class="table-responsive">
	<div id="paging" class="pagclass"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/wagesImport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/event.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/date.js"></script>