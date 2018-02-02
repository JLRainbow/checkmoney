var grid = null;
$(function() {
	var chanelType = $("#chanelType").val();
	getGrid(chanelType);
	$("#chanelType").change(function (){
		var chanelType = $("#chanelType").val();
		getGrid(chanelType);
	});
});
 $("#search").click("click", function() {// 绑定查询按扭
 
    var check_result = $("#check_result").val();
    var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	var fund_type=$("#fund_type").val();
	var channel_name=$("#channel_name").val();
	var pay_platform=$("#pay_platform").val();
	var check_order=$("#check_order").val();
    grid.setOptions({//设置参数，具体参数与表格参数一致
        data : {"check_result":check_result,
	        	"startTime":startTime,
	        	"fund_type":fund_type,
	        	"endTime":endTime,
	        	"channel_name":channel_name,
	        	"pay_platform":pay_platform,
	        	"check_order":check_order}//查询条件数据，必须是json格式
    });
});
 function queryDetails(id){
	 var chanelType = $("#chanelType").val(); 
	 layer.open({
		    type: 2,
		    title: '查看详情',
		    maxmin: true,
		    area: ['400px', '500px'],
		    content:  rootPath +"/summary_report/queryDetailsById.do?id="+id+"&chanelType="+chanelType,
		   
		  });
	 
 }
 //查询导出
 function queryReport(){
	 $("#form").submit();
	 //ajax不能下载。需要通过提交from来发送请求下载
 }
 $("#check_order").blur(function(){
	 var check_order=$("#check_order").val();
	 var check_result = document.getElementById("check_result");//获取dom
	 var channel_name = document.getElementById("channel_name");
	 var pay_platform = document.getElementById("pay_platform");
	 var startTime = document.getElementById("startTime");
	 var endTime = document.getElementById("endTime");
	 if(""==check_order){
		 
		 check_result.removeAttribute('disabled');
		 channel_name.removeAttribute('disabled');
		 pay_platform.removeAttribute('disabled');
		 startTime.removeAttribute('disabled');
		 endTime.removeAttribute('disabled');
	 }else{
		 $("#check_result").val("");
		 check_result.setAttribute('disabled','disabled');//添加属性
		 $("#channel_name").val("");
		 channel_name.setAttribute('disabled','disabled');//添加属性
		 $("#pay_platform").val("");
		 pay_platform.setAttribute('disabled','disabled');//添加属性
		 $("#startTime").val("");
		 startTime.setAttribute('disabled','disabled');//添加属性
		 $("#endTime").val("");
		 endTime.setAttribute('disabled','disabled');//添加属性
	 }
 })
function getGrid(chanelType){
	var check_result = $("#check_result").val();
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	var fund_type=$("#fund_type").val();
	var channel_name=$("#channel_name").val();
	var pay_platform=$("#pay_platform").val();
	var check_order=$("#check_order").val();
	if(chanelType=="收款渠道"){
		//选择渠道改变时查询条件改变
		$("#pay_platformDIV").css('display','block');
		$("#channel_nameDIV").css('display','none');
		grid = lyGrid({
			id : 'paging',
			l_column : [ {
				colkey : "id",
				name : "id",
				width : "50px",
				hide : true
			}, {
				colkey : "receipt_date",
				name : "销售日期",
				width : "70px",
				isSort:true,
				renderData : function(rowindex,data, rowdata, column) {
					return new Date(data).format("yyyy-MM-dd");
				}
			},{
				colkey : "relation_id",
				name : "流水号",
				width : "160px",
				isSort:true,
			}, {
				colkey : "source",
				name : "收款渠道",
				width : "100px",
				renderData : function(rowindex, data, rowdata, column) {
					if(data=="1"){
						return "平台销售数据";
					}else if(data=="2"){
						return "微商销售数据";
					}
				},
			}, {
				colkey : "pay_amount",
				name : "收款金额",
				width : "50px",
			},  {
				colkey : "recipt_amount",
				name : "支付金额",
				width : "50px",
			}, {
				colkey : "check_result",
				name : "对账状态",
				width : "80px",
				renderData : function(rowindex, data, rowdata, column) {
					if(data=="0"){
						return "未对账";
					}else if(data=="1"){
						return "对账符合";
					}else if(data=="2"){
						return "稽查";
					}else if(data=="3"){
						return "合并未对账";
					}else if(data=="4"){
						return "合并对账符合";
					}else if(data=="5"){
						return "合并稽查";
					}
				},
			}, {
				colkey : null,
				name : "操作",
				width : "50px",
				renderData : function(rowindex, data, rowdata, column) {
					var id=rowdata.id;
					return "<a class='details' title='查看详情' onclick='queryDetails("+id+")'>"
                    		+"<span class='glyphicon glyphicon-file'></span></a>";
				},
			} ],
			jsonUrl :  rootPath +'/summary_report/chkMoneyInforFindByPage.do',
			checkbox : false,
			serNumber : true,// 是否显示序号
			data : {"chanelType":chanelType,
					"check_result":check_result,
					"fund_type":fund_type,
		        	"startTime":startTime,
		        	"endTime":endTime,
		        	"channel_name":channel_name,
		        	"pay_platform":pay_platform,
		        	"check_order":check_order}
		});
	}
	if(chanelType=="支付渠道"){
		$("#pay_platformDIV").css('display','none');
		$("#channel_nameDIV").css('display','block');
//		$.post(rootPath +'/summary_report/getPayWay.do',
//				function(result){
//			 		$("#channel_name").empty();
//					var newOption1  =  document.createElement("option"); //动态添加option
//		            newOption1.value = ""; 
//		            newOption1.text = "全部";
//		            document.getElementById("channel_name").options.add(newOption1); 
//					for (var i = 0, length = result.length; i< length; i++) {
//							var newOption  =  document.createElement("option"); //动态添加option
//							newOption.value = result[i].channel_name; 
//							newOption.text =result[i].channel_name;
//							document.getElementById("channel_name").options.add(newOption); 
//							
//					}
//					
//				},"json")
		grid = lyGrid({
			id : 'paging',
			l_column : [ {
				colkey : "id",
				name : "id",
				width : "50px",
				hide : true
			}, {
				colkey : "pay_date",
				name : "销售日期",
				width : "70px",
				isSort:true,
				renderData : function(rowindex,data, rowdata, column) {
					return new Date(data).format("yyyy-MM-dd");
				}
			},{
				colkey : "check_order",
				name : "流水号",
				width : "160px",
				isSort:true,
			}, {
				colkey : "channel_name",
				name : "支付渠道",
				width : "100px",
			}, {
				colkey : "pay_amount",
				name : "支付金额",
				width : "50px",
			},  {
				colkey : "recipt_amount",
				name : "收款金额",
				width : "50px",
			}, {
				colkey : "check_result",
				name : "对账状态",
				width : "80px",
				renderData : function(rowindex, data, rowdata, column) {
					if(data=="0"){
						return "未对账";
					}else if(data=="1"){
						return "对账符合";
					}else if(data=="2"){
						return "稽查";
					}
				},
			}, {
				colkey : null,
				name : "操作",
				width : "50px",
				renderData : function(rowindex, data, rowdata, column) {
					var id=rowdata.id;
					return "<a class='details' title='查看详情' onclick='queryDetails("+id+")'>"
                   		  +"<span class='glyphicon glyphicon-file'></span></a>";
				},
			} ],
			jsonUrl :  rootPath +'/summary_report/chkMoneyInforFindByPage.do',
			checkbox : false,
			serNumber : true,// 是否显示序号
			data : {"chanelType":chanelType,
					"check_result":check_result,
					"fund_type":fund_type,
		        	"startTime":startTime,
		        	"endTime":endTime,
		        	"channel_name":channel_name,
		        	"pay_platform":pay_platform,
		        	"check_order":check_order}
		});
	}
};
