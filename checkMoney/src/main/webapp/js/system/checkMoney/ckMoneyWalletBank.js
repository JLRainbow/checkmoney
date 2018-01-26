//对账处理
$("#chk_money").click(function (){
	var chkType = $("#chkType").val();
	if(chkType==""){
		alert("请选择对账纬度")
	}else{
		var $btn = $("#chk_money").button('loading');
		if("1"==chkType){
			$.post(rootPath +'/ckMoneyWalletBank/chkMoneyEmptyToEntity.do',
					function(result){
						alert(result.success);
						$btn.button('reset');
						$("#walletChkNum").text(result.walletChkNumMap.walletChkNum);
				    	$("#bankDetailChkNum1").text(result.bankDetailChkNumMap.bankDetailCashChkNum);
						
						$("#closingDate1").text(result.walletChkNumMap.closingDate);
				    	$("#closingDate2").text(result.bankDetailChkNumMap.cashClosingDate);
				    	
			},"json")
		}
		
		if("2"==chkType){
			$.post(rootPath +'/ckMoneyWalletBank/chkMoneyEntityToEntity.do',
					function(result){
						alert(result.success);
						$btn.button('reset');
				    	$("#bankDetailChkNum2").text(result.bankDetailChkNumMap.bankDetailCollectChkNum);
						$("#bankCollectChkNum").text(result.bankCollectChkNumMap.bankCollectChkNum);
						
				    	$("#closingDate3").text(result.bankDetailChkNumMap.collectClosingDate);
						$("#closingDate4").text(result.bankCollectChkNumMap.closingDate);
			},"json")
		}
	}
	
});
//获取支付数据格式
$("#fileType").change(function (){
	$('.file-team').find('[name=fileName]').val('');
	$('.file-team').find('[type=file]').val('');
	var fileType = $('#fileType').val();
	if(fileType!=""){
		$.post(rootPath +'/check_money/getDataTypeByChannelName.do',
				{'ChannelManagementFormMap.channel_name':fileType},
				function(result){
					$("#payDataType").text(result);
				},"json")
	}
	$("#payDataType").text('');
});

//选择支付文件后判断文件格式是否正确
$('.file-team').delegate('#importFile','change',function(e){
	var $input = $(this).parents('.file-team').find('[name=fileName]');
	$input.val('');
	var payDataType = $("#payDataType").text();
	if(payDataType==""){
		alert("请先选择支付渠道，再导入文件!");
		resetFile('importFile');
	}
	var filepath = $("#importFile").val().split("\\");
	var filepath = filepath[filepath.length-1];
	var extStart = filepath.lastIndexOf(".");
	var suffix = filepath.substring(extStart, filepath.length).toLowerCase();// 获取后缀名
	if(payDataType == "CSV文件"){
		if(suffix != ".csv"){
			alert("导入文件格式不正确,请重新导入!")
			resetFile('importFile');
		}else{
			// 支付渠道：支付宝上传时文件名加入input
			$input.val(filepath).end().attr('title',filepath);
		}
	}
	if(payDataType == "Excel"){
		if(suffix != ".xlsx"&&suffix != ".xls"){
			alert("导入文件格式不正确,请重新导入!")
			resetFile('importFile');
		}else{
			// 支付渠道：微信上传时文件名加入input
			$input.val(filepath).end().attr('title',filepath);
		}
	}
	
})
		

// 导入支付数据
function impFile(){
	if($("#importFile").val() == ""){
		alert("导入文件不能为空!");
	}else{
		var fileType = $.trim($('#fileType').val());
		var $btn = $("#impButton").button('loading');
		if(fileType=="钱包提现明细"){
			$.ajaxFileUpload({
				url:rootPath +'/ckMoneyWalletBank/importWalletFile.do?fileType='+fileType,
				secureuri:false,
				fileElementId:'importFile',
				dataType:'json',
				success: function (data, status)
				{
					$btn.button('reset');
					
					if(data.isEmpty!=undefined){
						alert(data.isEmpty);
					}
					if(data.success!=undefined){
						alert(data.success);
					}
					if(data.sameDataNum==0){
						alert("成功导入"+data.impDataNum+"条数据");
					}else if(data.sameDataNum!=undefined){
						alert("成功导入"+data.impDataNum+"条数据，存在"+data.sameDataNum+"条重复数据未导入");
					}
					$("#closingDate1").text(data.walletChkNumMap.closingDate);
					$("#walletChkNum").text(data.walletChkNumMap.walletChkNum);
				},
				error: function (data, status, e)
				{
					alert("【服务器异常，请连续管理员！】"+e);
				}

			});
		}
		
		if(fileType=="银行结算明细（315账户）"){
			$.ajaxFileUpload({
				url:rootPath +'/ckMoneyWalletBank/importBankDetailFile.do?fileType='+fileType,
				secureuri:false,
				fileElementId:'importFile',
				dataType:'json',
				success: function (data, status)
				{
					$btn.button('reset');
					if(data.error!=undefined){
						alert(data.error);

					}else{
						
						if(data.isEmpty!=undefined){
							alert(data.isEmpty);
						}
						if(data.success!=undefined){
							alert(data.success);
						}
						if(data.sameDataNum==0){
							alert("成功导入"+data.impDataNum+"条数据");
						}else if(data.sameDataNum!=undefined){
							alert("成功导入"+data.impDataNum+"条数据，存在"+data.sameDataNum+"条重复数据未导入");
						}
						$("#closingDate2").text(data.bankDetailChkNumMap.cashClosingDate);
						$("#closingDate3").text(data.bankDetailChkNumMap.collectClosingDate);
						$("#bankDetailChkNum1").text(data.bankDetailChkNumMap.bankDetailCashChkNum);
						$("#bankDetailChkNum2").text(data.bankDetailChkNumMap.bankDetailCollectChkNum);
					}
					
				},
				error: function (data, status, e)
				{
					alert("【服务器异常，请连续管理员！】"+e);
				}

			});
		}
		
		if(fileType=="银行结算汇总（140账户）"){
			$.ajaxFileUpload({
				url:rootPath +'/ckMoneyWalletBank/importBankCollectFile.do?fileType='+fileType,
				secureuri:false,
				fileElementId:'importFile',
				dataType:'json',
				success: function (data, status)
				{
					$btn.button('reset');
					if(data.error!=undefined){
						alert(data.error);

					}else{
						if(data.isEmpty!=undefined){
							alert(data.isEmpty);
						}
						if(data.success!=undefined){
							alert(data.success);
						}
						if(data.sameDataNum==0){
							alert("成功导入"+data.impDataNum+"条数据");
						}else if(data.sameDataNum!=undefined){
							alert("成功导入"+data.impDataNum+"条数据，存在"+data.sameDataNum+"条重复数据未导入");
						}
						$("#closingDate4").text(data.bankCollectChkNumMap.closingDate);
						$("#bankCollectChkNum").text(data.bankCollectChkNumMap.bankCollectChkNum);
					}
				},
				error: function (data, status, e)
				{
					alert("【服务器异常，请连续管理员！】"+e);
				}

			});
		}
	}
}

// 重置导入
function resetFile(id){
	$('#'+id).val('');
}
		
// 页面加载时候去查未对账数目
$(document).ready(function(){ 
　　$.post(rootPath +'/ckMoneyWalletBank/getNoChkNum.do',
		function(result){
		$("#walletChkNum").text(result.walletChkNumMap.walletChkNum);
    	$("#bankDetailChkNum1").text(result.bankDetailChkNumMap.bankDetailCashChkNum);
    	$("#bankDetailChkNum2").text(result.bankDetailChkNumMap.bankDetailCollectChkNum);
		$("#bankCollectChkNum").text(result.bankCollectChkNumMap.bankCollectChkNum);
		
		$("#closingDate1").text(result.walletChkNumMap.closingDate);
    	$("#closingDate2").text(result.bankDetailChkNumMap.cashClosingDate);
    	$("#closingDate3").text(result.bankDetailChkNumMap.collectClosingDate);
		$("#closingDate4").text(result.bankCollectChkNumMap.closingDate);
	},"json")
});

