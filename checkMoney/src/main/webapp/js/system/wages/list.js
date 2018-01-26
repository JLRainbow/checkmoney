var dialog;
var grid;
$(function() {

	grid = lyGrid({
		id : 'paging',
		l_column : [ {
			colkey : "id",
			name : "id",
			hide : true
		}, {
			colkey : "batchcode",
			name : "批次号",
			isSort : true
		}, {
			colkey : "employeename",
			name : "姓名",
			isSort : true
		}, {
			colkey : "dept",
			name : "部门",
			isSort : true
		}, {
			colkey : "thirdcustid",
			name : "证件类型",
			isSort : true
		}, {
			colkey : "idno",
			name : "证件号码",
			isSort : true
		}, {
			colkey : "tranamount",
			name : "实发合计",
			isSort : true
		},{
			colkey : "trandate",
			name : "发放月份",
			isSort : true
		}, {
			colkey : "status",
			name : "状态",
			isSort : true
		}, {
			colkey : "remark",
			name : "描述",
			isSort : true
		}, {
			colkey : "createtime",
			name : "创建时间",
			isSort : true,
			renderData : function(rowindex, data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		}],
		//localData:localData,
		jsonUrl : rootPath + '/wages/findHistoriesByPage.do',
		//local:true,//支持本地数据前端分页
		checkbox : false,
		serNumber : true
	});

	$("#searchForm").click("click", function() { //绑定查询按扭
		var searchParams = $("#search").serializeJson();
		grid.setOptions({
			data : searchParams
		});
	});
});
