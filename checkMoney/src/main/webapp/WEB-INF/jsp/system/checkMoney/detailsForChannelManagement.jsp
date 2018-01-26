<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body class="bounced form-horizontal ">
	<div class="container-fluid ">
			<div class="form-group">
		    	<label class="col-sm-3 control-label">渠道名称</label>
		    	<div class="col-sm-9">
		     		<input type="hidden" id="id" value="${channelManagementFormMap.id}" name="channelManagementFormMap.id">
					<input type="text"  class="form-control" id="channel_name" value="${channelManagementFormMap.channel_name}" 
						disabled="disabled" name="channelManagementFormMap.channel_name">
		    	</div>
		   	</div>
		   	<div class="form-group">
		    	<label class="col-sm-3 control-label">渠道类型</label>
		    	<div class="col-sm-9">
		     		<select id="channel_type" disabled="disabled" name="channelManagementFormMap.channel_type"  class="form-control">
						<option value="1" ${channelManagementFormMap.channel_type=="1"?'selected':''}>支付渠道</option>
						<option value="2" ${channelManagementFormMap.channel_type=="2"?'selected':''}>收款渠道</option>
					</select>
		    	</div>
		   	</div>
		    <div class="form-group">
			    <label class="col-sm-3 control-label">数据格式</label>
			    <div class="col-sm-9">
			     	<select id="data_type" disabled="disabled" name="channelManagementFormMap.data_type"  class="form-control">
						<option value="1" ${channelManagementFormMap.data_type=="1"?'selected':''}>CSV文件</option>
						<option value="2" ${channelManagementFormMap.data_type=="2"?'selected':''}>DB</option>
						<option value="3" ${channelManagementFormMap.data_type=="3"?'selected':''}>Excel</option>
					</select>
			    </div>
		   	</div>
		   	
		   	<div class="form-group">
		    	<label class="col-sm-3 control-label">是否可用</label>
		    	<div class="col-sm-9">
		     		<select id="status" name="channelManagementFormMap.status"  value="${channelManagementFormMap.status}" 
		     			class="form-control" disabled="disabled">
						<option value="0" ${channelManagementFormMap.status=="0"?'selected':''}>是</option>
						<option value="1" ${channelManagementFormMap.status=="1"?'selected':''}>否</option>
				</select>
			    </div>
		   </div>
		   <div class="form-group">
			    <label class="col-sm-3 control-label">配置信息</label>
			    <div class="col-sm-9">
			     	<textarea id="config_inf" disabled="disabled"  class="form-control" rows="10"
			     		name="channelManagementFormMap.config_inf">${channelManagementFormMap.config_inf}</textarea>
			    </div>
		   </div>
	   </div>
		
</body>
</html>