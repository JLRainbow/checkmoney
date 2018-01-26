// 根据选择表不同改变样式
$('#tableType').change(function(e){
	$(".form-search").css('display','none');
	$(".checkPay").css('display','none');
	var tableType = $.trim($('#tableType').val());
	if(tableType=="平台对账表"){
		$(".form-search").css('display','block');
		$(".checkPay").css('display','block');
		$.post(rootPath +'/ckMoneySys/getPlatformDate.do',
			{'tableType':tableType},
			function(result){
			
			$(".pull-left").text('平台对账表当前数据期间为：（'+result.minDate+'~'+result.receiptClosingDate+'）')
		},"json")
	}
	if(tableType=="支付渠道对账表"){
		$(".form-search").css('display','block');
		$(".checkPay").css('display','block');
		$.post(rootPath +'/ckMoneySys/getPayDate.do',
			{'tableType':tableType},
			function(result){
				
				$(".pull-left").text('支付渠道对账表当前数据期间为：（'+result.minDate+'~'+result.payClosingDate+'）')
		},"json")
	}
	
})
//删除并备份
$('#backupsAndDel').click(function (){
	if(confirm("确定要备份并删除数据么？")){
		var tableType = $.trim($('#tableType').val());
		if(tableType=="平台对账表"){
			$("#form").attr("action",rootPath +'/ckMoneySys/backupsAndDelForReceipt.do');
			$("#form").submit();
		}
		if(tableType=="支付渠道对账表"){
			$("#form").attr("action",rootPath +'/ckMoneySys/backupsAndDelForPay.do');
			$("#form").submit();
		}
	}
})
//页面加载的时候隐藏DBdiv
$(document).ready(function(){ 
	$(".form-search").css('display','none');
	$(".checkPay").css('display','none');
});