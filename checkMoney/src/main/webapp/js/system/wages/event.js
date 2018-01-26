/*默认加载事件*/
var $check=$("#check");
var $tableAll=$(".table-all");
var $tableImg=document.createElement("img");
$.ajax({
	type:'post',
	url:rootPath + '/wages/checkStatus.do',
	dataType:'json',
	success:function(data){
		console.log(data);
		if(data=='notimport'||data=='imported'){
			$("#confirm").hide();
		 } else if(data=='signed'){//签章状态
			 $("#sign").hide();
			 $("#validate").hide();
			 $("#form").hide();
			 $("#confirm").show();
			 $tableImg.src=rootPath+'/images/signConfirm.png';
			 $tableAll.append($tableImg);
		 } else if(data=='sended'||data=='completed' || data=='failure'){//签章以后的状态
			 $("#sign").hide();
			 $("#validate").hide();
			 $("#confirm").hide();
			 $("#form").hide();
			 
			 switch (data) {
			 case "sended":
				
				 $tableImg.src=rootPath+'/images/sended.png';
				 $tableAll.append($tableImg);
				 break;
			 case "completed":
				 $tableImg.src=rootPath+'/images/completed.png';
				 $tableAll.append($tableImg);
				 break;
			 case "failure":
				 $tableImg.src=rootPath+'/images/failure.png';
				 $tableAll.append($tableImg);
				 break;
			 }
		 }				
	}
});
/*签章事件*/
$("#sign").click(function() {
	$.ajax({
		type : 'post',
		url : rootPath + '/wages/signConfirm.do',
		dataType : "json",
		success : function(data) {
			/*正确即将执行的函数*/
			if (data == 'failure') {
				layer.alert("签章失败!");
			} else if (data == 'success') {
				$tableImg.src = rootPath + "/images/signConfirm.png"
				$tableAll.append($tableImg);
				$("#sign").hide();
				$("#confirm").show();
			}
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			/*错误即将执行的函数*/
			alert(XmlHttpRequest);
			alert(textStatus);
			alert(errorThrown);
		}
	});
})
/*确认事件*/
$("#confirm").click(function() {
	$.ajax({
		type : 'post',
		url : rootPath + '/wages/send.do',
		dataType : 'json',
		success : function(data) {
			if (data == 'failure') {
				layer.alert("确认失败!");
			} else if (data == 'success') {
				$("#confirm").hide();
				$tableImg.src = rootPath + "/images/sended.png"
				$tableAll.append($tableImg);
			}
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert(XmlHttpRequest);
			alert(textStatus);
			alert(errorThrown);
		}
	});
})
/*校验事件*/
$("#validate").click(function() {
	$.ajax({
		type : 'post',
		url : rootPath + '/wages/reValidate.do',
		success : function(data) {
			/*正确即将执行的函数*/
			if (data == 'failure') {
				layer.alert("校验失败!");
			} else if (data == 'success') {
				layer.alert("校验成功!");
			}
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			/*错误即将执行的函数*/
			alert(XmlHttpRequest);
			alert(textStatus);
			alert(errorThrown);
		}
	});
});