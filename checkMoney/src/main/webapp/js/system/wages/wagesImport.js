var dialog;
var grid;
var pageii = null;
$(function() {
	
	$("#searchForm").click("click", function() {//绑定查询按扭
		var searchParams = $("#fenye").serializeJson();
		grid.setOptions({
			data : searchParams
		});
	});
	$(function(){  
        //异步提交表单  
        $("#initial").on("click",function(){  
            $("#form").ajaxSubmit({  
                type:'post', 
                async:true,
                url:rootPath + '/wages/initialWagesExcel.do',  
                success:function(data){  
                
                	layer.alert(data);
                },  
                error:function(XmlHttpRequest,textStatus,errorThrown){  
                	alert(XmlHttpRequest);  
                	alert(textStatus);  
                	alert(errorThrown);  
                }  
            });  
        });  
    });  
});