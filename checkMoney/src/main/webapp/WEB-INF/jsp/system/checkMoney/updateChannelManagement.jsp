<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<body>
	<form id="form1" action="" method="post" class="form-horizontal bounced">
		<div class="container-fluid ">
			<div class="form-group">
		    	<label class="col-sm-3 control-label">渠道名称</label>
		    	<div class="col-sm-9">
		     		<input type="hidden" id="id" value="${channelManagementFormMap.id}"
					name="channelManagementFormMap.id">
					<input type="text" id="channel_name" value="${channelManagementFormMap.channel_name}" disabled="disabled"  class="form-control"
					name="channelManagementFormMap.channel_name">
					<span id="isExist"></span>
		    	</div>
		   	</div>
		   	<div class="form-group">
		    	<label class="col-sm-3 control-label">渠道类型</label>
		    	<div class="col-sm-9">
		     		<select id="channel_type" disabled="disabled" name="channelManagementFormMap.channel_type" class="form-control">
						<option value="1" ${channelManagementFormMap.channel_type=="1"?'selected':''}>支付渠道</option>
						<option value="2" ${channelManagementFormMap.channel_type=="2"?'selected':''}>收款渠道</option>
						<option value="3" ${channelManagementFormMap.channel_type=="3"?'selected':''}>钱包银行</option>
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
		     		<select id="status" name="channelManagementFormMap.status"  value="${channelManagementFormMap.status}" class="form-control">
						<option value="0" ${channelManagementFormMap.status=="0"?'selected':''}>是</option>
						<option value="1" ${channelManagementFormMap.status=="1"?'selected':''}>否</option>
					</select>
			    </div>
		   </div>
		   <div class="form-group">
			    <label class="col-sm-3 control-label">配置信息</label>
			    <div class="col-sm-9">
			     	<textarea id="config_inf"  class="form-control"  rows="8"
						name="channelManagementFormMap.config_inf">${channelManagementFormMap.config_inf}</textarea>
			    </div>
		   </div>
		   <div class="form-group">
			    <label class="col-sm-3 control-label">备注</label>
			    <div class="col-sm-9">
			     	<input type="text"  class="form-control" id="comment" name="channelManagementFormMap.comment" value="${channelManagementFormMap.comment}">
			    </div>
		   </div>
	   </div>
		<footer class="panel-footer text-right bg-light lter">
			<button type="button" id="updateChannel" class="btn btn-success btn-s-xs">确认
			</button>
		</footer>
	</form>
	<script type="text/javascript">
$(document).ready(function() {
 	//表单提交及验证
     $("#updateChannel").click(function(){
    	 var flag = true;
    	 var id = $.trim($("#id").val());
    	 var channel_name = $.trim($("#channel_name").val());
		 var config_inf = $.trim($("#config_inf").val());
		 var channel_type = $("#channel_type").val();
		 var data_type = $("#data_type").val();
		 var status = $("#status").val();
		 var comment = $.trim($("#comment").val());
		 if(channel_name == ""){
			alert("渠道名称不能为空!");
			flag = false; 
		 }
		if(config_inf == ""){
			alert("配置信息不能为空!");
			flag = false; 
		}
		
		if(flag){
			var isSubmit = true;
	 	 	if (window.confirm("您确认要提交的表单信息吗?")){
	 	 		isSubmit = true;
	 		}else{
	 			isSubmit = false;    
	 		}
	 	 	if(isSubmit){
				$.post('${pageContext.request.contextPath}/check_money/updateChannelManagement.do',
							{'channelManagementFormMap.id':id,
							'channelManagementFormMap.channel_name':channel_name,
							'channelManagementFormMap.config_inf':config_inf,
							'channelManagementFormMap.channel_type':channel_type,
							'channelManagementFormMap.data_type':data_type,
							'channelManagementFormMap.status':status,
							'channelManagementFormMap.comment':comment},
							function(result){
								if (result == "success") {
									parent.layer.msg('修改成功');
								} else {
									parent.layer.msg('修改失败');
								}
								var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		                        parent.layer.close(index);
					},"json")
	 	 	}
		}
     });
    
});

</script>
</body>
</html>