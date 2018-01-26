var grid = null;
$(function() {
	grid = lyGrid({
		id : 'paging',
		l_column : [ {
			colkey : "id",
			name : "id",
			width : "50px",
			hide : true
		}, {
			colkey : "channel_name",
			name : "渠道名称"
		},{
			colkey : "channel_type",
			name : "渠道类型",
			renderData : function(rowindex, data, rowdata, column) {
				if(data=="1"){
					return "支付渠道";
				}else if(data=="2"){
					return "收款渠道";
				}else if(data=="3"){
					return "钱包银行";
				}
			},
		}, {
			colkey : "status",
			name : "是否可用",
			width : "100px",
			renderData : function(rowindex, data, rowdata, column) {
				if(data=="0"){
					return "可用";
				}else if(data=="1"){
					return "禁用";
				}
			},
		}, {
			colkey : "data_type",
			name : "数据格式",
			renderData : function(rowindex, data, rowdata, column) {
				if(data=="1"){
					return "CSV文件";
				}else if(data=="2"){
					return "DB";
				}else if(data=="3"){
					return "Excel";
				}
			},
		}, {
			colkey : null,
			name : "操作",
			renderData : function(rowindex, data, rowdata, column) {
				var id=rowdata.id;
				
				return	 "<a class='details' title='查看详情' onclick='queryDetails("+id+")'>"
                 +"<span class='glyphicon glyphicon-file'></span> </a>"
                 +"<a class='editUser' title='编辑' onclick='update("+id+")'>"
                 +"<span class='glyphicon glyphicon-edit'></span></a>"
			},
		} ],
//		jsonUrl : '${pageContext.request.contextPath}/check_money/channelManagementFindByPage.do',
		jsonUrl : rootPath +'/check_money/channelManagementFindByPage.do',
		checkbox : true
	});
});

$("#search").click("click", function() {// 绑定查询按扭
	var channel_name = $("#channel_name").val();
	var channel_type = $("#channel_type").val();// 初始化传参数
	grid.setOptions({
		data : {"channelManagementFormMap.channel_name":channel_name,
				"channelManagementFormMap.channel_type":channel_type}
	});
});

$('#addChannel').on('click', function(){
	layer.open({
	    type: 2,
	    title: '新增渠道',
	    maxmin: true,
	    area: ['400px', '580'],
	    content: rootPath +'/check_money/toAddPayWay.do',
	    end: function(){
			grid.loadData();
	  	}
	  });
});
$("#delChannel").click("click", function() {
	delChannel();
});
function delChannel() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		$.post(rootPath +'/check_money/delChannel.do',
					{'ids':cbox.join(",")},
					function(result){
						if (result == "success") {
							layer.msg('删除成功');
							grid.loadData();
						} else {
							layer.msg('删除失败');
						}
			},"json")
	});
}
function queryDetails(id){
	 layer.open({
		    type: 2,
		    title: '查看详情',
		    maxmin: true,
		    area: ['400px', '580px'],
		    content: rootPath +'/check_money/queryDetailsById.do?id='+id,
		    
		  });
	 
}
function update(id){
	 layer.open({
		    type: 2,
		    title: '编辑',
		    maxmin: true,
		    area: ['400px', '580px'],
		    content: rootPath +'/check_money/toUpdateChannelManagement.do?id='+id,
		  	end: function(){
				grid.loadData();
		  	}
	 });
	 
}
//按回车键
document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];         
     if(e && e.keyCode==13){ // enter 键
        var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
    }
}; 