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
			name : "收支类型名称",
			isSort:true,
		}, {
			colkey : "budgettypekey",
			name : "budgettypeKey",
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
		jsonUrl : rootPath + '/budgettype/findByPage.do',
		checkbox : true,
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});
	$("#addBudgettype").click("click", function() {
		addBudgettype();
	});
	$("#editBudgettype").click("click", function() {
		editBudgettype();
	});
	$("#delBudgettype").click("click", function() {
		delBudgettype();
	});

});
function editBudgettype() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/budgettype/editUI.do?id=' + cbox
	});
}
function addBudgettype() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/budgettype/addUI.do'
	});
}
function delBudgettype() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/budgettype/deleteEntity.do';
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
