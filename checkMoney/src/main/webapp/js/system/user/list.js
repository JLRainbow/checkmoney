var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "id",
			name : "id",
			hide : true
		}, {
			colkey : "username",
			name : "用户名",
			isSort:true,
		}, {
			colkey : "accountname",
			name : "账号",
			isSort:true,
		}, {
			colkey : "rolename",
			name : "所属角色"
		}, {
			colkey : "locked",
			name : "账号状态",
			width : '90px',
			isSort:true,
			renderData : function(rowindex, data, rowdata, column) {
				if(data=="0"){
					return "可用";
				}else if(data=="1"){
					return "禁用";
				}
			},
		}, {
			colkey : "description",
			name : "描述"
		}, {
			colkey : "createtime",
			name : "时间",
			isSort:true,
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		}
		],
		jsonUrl : rootPath + '/user/findByPage.do',
		checkbox : true,
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});
	$("#addAccount").click("click", function() {
		addAccount();
	});
	$("#editAccount").click("click", function() {
		editAccount();
	});
	$("#delAccount").click("click", function() {
		delAccount();
	});
	$("#permissions").click("click", function() {
		permissions();
	});
});
function editAccount() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "500px", "80%" ],
		content : rootPath + '/user/editUI.do?id=' + cbox
	});
}
function addAccount() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "500px", "80%" ],
		content : rootPath + '/user/addUI.do'
	});
}
function delAccount() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/user/deleteEntity.do';
		var s = CommnUtil.ajax(url, {
			ids : cbox.join(",")
		}, "json");
		if (s == "success") {
			layer.msg('删除成功');
			grid.loadData();
		} else {
			layer.msg('删除失败');
		}
	});
}
function permissions() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/resources/permissions.do?userId='+cbox;
	pageii = layer.open({
		title : "分配权限",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
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