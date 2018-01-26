<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<link rel="stylesheet" href="${ctx}/citicwages/css/system/wages/tellerList.css" type="text/css">
<!-- 面包屑开始 -->
	<ol class="breadcrumb">
		<li><a href="${ctx}/citicwages/index.do">首页</a></li>
		<li class="nolink"><a href="#">收支管理</a></li>
		<li class="active">我的出纳清单</li>
	</ol>
<!-- 面包屑结束 -->
<div class="m-b-md over">
	<div class="main">
		<div class="order_container">
			<c:forEach items="${tellerList}" var="tellerPojo">
				<div class="trade_order">
					<table class="bought_table_mod">
						<thead class="bought_mod_head">
							<tr>
								<td class="txt_left">
     								<span class="bought_create_time">
       									${tellerPojo.createTime}
       								</span>
								</td>
								<td>
									<span class="txt_left">
	       								<span>批次号：</span>
										<span class="batch">${tellerPojo.batchCode}</span>
									</span>
								</td>
								<td colspan="2" class="txt_right paddRight"></td>
							</tr>
						</thead>
						<tbody class="bought_mod_body">
							<tr class="bold">
								<td class="txt_left" >
									基本信息
								</td>
								<td >详情</td>
								<td >
									人数
								</td>
								<td>
									操作
								</td>
							</tr>
							<tr>
								<td rowspan="5" class="border-right">
									<p><label>出&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp纳：</label><span>${tellerPojo.accountName}</span></p>
								 	<p><label>公&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp司：</label><span>${tellerPojo.companyName}</span></p>
								 	<p><label>收支类型：</label><span>${tellerPojo.budgettypeName}</span></p>
								 	<p><label>总&nbsp&nbsp人&nbsp&nbsp数：</label><span>${tellerPojo.count}</span></p>
								 	<p><label>收支月份：</label><span>${tellerPojo.trandate}</span></p>
								 </td>	
								<td>已开户</td>
								<td >${tellerPojo.openedAccountCount}</td>
								<td>
									<button type="button" id="send" class="btn btn-primary btn-xs">放款</button>
								</td>
							</tr>
							<tr>
								<td>未开户</td>
								<td >${tellerPojo.notOpenedAccountCount}</td>
								<td>
									<button type="button" id="reValidate" class="btn btn-primary btn-xs">校验</button>
								</td>
							</tr>
							<tr>
								<td>放款中</td>
								<td >${tellerPojo.remittingCount}</td>
								<td></td>
							</tr>
							<tr>
								<td>完成</td>
								<td >${tellerPojo.completedCount}</td>
								<td></td>
							</tr>
							<tr>
								<td>失败</td>
								<td >${tellerPojo.failureCount}</td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>	
			</c:forEach>
		</div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/tellerList.js"></script>