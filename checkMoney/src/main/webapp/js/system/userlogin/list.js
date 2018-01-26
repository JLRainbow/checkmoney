var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		id : 'paging',
		l_column : [ {
			colkey : "id",
			name : "id",
			hide : true
		}, {
			colkey : "accountname",
			name : "账号"
		},{
			colkey : "logintime",
			name : "登入时间",
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		} , {
			colkey : "loginip",
			name : "登入IP"
		}],
		jsonUrl : rootPath + '/userlogin/findByPage.do',
		checkbox : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();
		grid.setOptions({
			data : searchParams
		});
	});
});
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
