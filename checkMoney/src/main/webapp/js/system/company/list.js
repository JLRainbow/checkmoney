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
			colkey : "name",
			name : "公司名称",
			isSort:true,
		}, {
			colkey : "companykey",
			name : "companyKey",
			isSort:true,
		}, {
			colkey : "status",
			name : "状态"
		}, {
			colkey : "description",
			name : "描述"
		}, {
			colkey : "createtime",
			name : "创建时间",
			isSort:true,
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		}
		],
		jsonUrl : rootPath + '/company/findByPage.do',
		checkbox : true,
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});
	$("#addCompany").click("click", function() {
		addCompany();
	});
	$("#editCompany").click("click", function() {
		editCompany();
	});
	$("#delCompany").click("click", function() {
		delCompany();
	});

});
function editCompany() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "305px" ],
		content : rootPath + '/company/editUI.do?id=' + cbox
	});
}
function addCompany() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "305px" ],
		content : rootPath + '/company/addUI.do'
	});
}
function delCompany() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/company/deleteEntity.do';
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
