//对账处理
$("#chk_money").click(function (){
	var chkPayWay = $("#chkPayWay").val();
	var chkReceiptWay = $("#chkReceiptWay").val();
	var startTimeChkMoney = $("#startTimeChkMoney").val();
	var endTimeChkMoney = $("#endTimeChkMoney").val();
	if(chkPayWay==""){
		alert("请选择对账支付方式")
	}else{
		if(startTimeChkMoney==""||endTimeChkMoney==""){
			alert("日期不能为空!")
		}else{
			var $btn = $("#chk_money").attr("data-loading-text","对账中...").button('loading');
			$.post(rootPath +'/check_money/chkMoney.do',
					{'chkPayWay':chkPayWay,'chkReceiptWay':chkReceiptWay,
					'startTimeChkMoney':startTimeChkMoney,'endTimeChkMoney':endTimeChkMoney},
					function(result){
						$btn.button('reset');
						if(result.success){
							alert("对账结束");
						}else{
							alert(result.errMsg);
						}
			},"json")
		}
	}
	
});
//获取支付数据格式
$("#payWay").change(function (){
	$('.checkPay').find('[name=fileName]').val('');
	$('.checkPay').find('[type=file]').val('');
	var payWay = $('#payWay').val();
	if(payWay!=""){
		$.post(rootPath +'/check_money/getDataTypeByChannelName.do',
				{'ChannelManagementFormMap.channel_id':payWay},
				function(result){
					$("#payDataType").text(result);
				},"json")
	}
	$("#payDataType").text('');
});
//获取收款数据格式
$("#receiptWay").change(function (){
	$('.checkCollection').find('[name=fileName]').val('');
	$('.checkCollection').find('[type=file]').val('');
	var receiptWay = $('#receiptWay').val();
	if(receiptWay=="微超"){//微超导入时不导入关系数据
		$("#relationDataDiv").hide(); 
	}else{
		$("#relationDataDiv").show(); 
	}
	if(receiptWay!=""){
		$.post(rootPath +'/check_money/getDataTypeByChannelName.do',
				{'ChannelManagementFormMap.channel_name':receiptWay},
				function(result){
					$("#receiptDataType").text(result);
				},"json")
	}
	$("#receiptDataType").text('');
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
			alert("导入文件格式不正确,请重新选择文件导入!")
			resetFile('importFile');
		}else{
			// 支付渠道：支付宝上传时文件名加入input
			$input.val(filepath).end().attr('title',filepath);
		}
	}
	if(payDataType == "Excel"){
		if(suffix != ".xlsx"&&suffix != ".xls"){
			alert("导入文件格式不正确,请重新选择文件导入!")
			resetFile('importFile');
		}else{
			// 支付渠道：微信上传时文件名加入input
			$input.val(filepath).end().attr('title',filepath);
		}
	}
	
})
		
// 选择收款文件后判断文件格式是否正确
$('.file-team').delegate('#receiptData','change',function(e){
	var $input = $(this).parents('.file-team').find('[name=fileName]');
	$input.val('');
	var receiptDataType = $("#receiptDataType").text();
	if(receiptDataType==""){
		alert("请先选择收款渠道，再导入文件!");
		resetFile('receiptData');
	}
	var filepath = $("#receiptData").val().split("\\");
	var filepath = filepath[filepath.length-1];
	var extStart = filepath.lastIndexOf(".");
	var suffix = filepath.substring(extStart, filepath.length).toLowerCase();// 获取后缀名
	if(receiptDataType == "CSV文件"){
		if(suffix != ".csv"){
			alert("导入文件格式不正确,请重新选择文件导入!")
			resetFile('receiptData');
		}else{
			// 支付渠道：支付宝上传时文件名加入input
			$input.val(filepath).end().attr('title',filepath);
		}
	}
	if(receiptDataType == "Excel"){
		if(suffix != ".xlsx"&&suffix != ".xls"){
			alert("导入文件格式不正确,请重新选择文件导入!")
			resetFile('receiptData');
		}else{
			// 支付渠道：微信上传时文件名加入input
			$input.val(filepath).end().attr('title',filepath);
		}
	}
	
})
// 导入支付数据
function impPayData(){
	if($("#importFile").val() == ""){
		alert("请选择要导入的文件!");
	}else{
		$('#monthPay').val('');
		$("#payNoChkNum").text('0');
		$("#payAlipayNum").text('0');
		$("#payWCNum").text('0');
		$("#payGapayNum").text('0');
		var payWay = $.trim($('#payWay').val());
		var $btn = $("#payImpButton").button('loading');
		if("alipay"==payWay||"wx_302"==payWay||"wx_401"==payWay){
			$.ajaxFileUpload({
				url:rootPath +'/check_money/importFileLoadData.do?payWay='+payWay,
				secureuri:false,
				fileElementId:'importFile',
				dataType:'json',
				success: function (data, status)
				{
					$btn.button('reset');
					if(data.success){
						alert("成功导入"+data.impDataNum+"条数据");
					}else{
						alert(data.errMsg);
					}
					
				},
				error: function (data, status, e)
				{
					alert("【服务器异常，请连续管理员！】"+e);
				}
				
			});
		}
			
		if("gapay"==payWay){
			$.ajaxFileUpload({
				url:rootPath +'/check_money/importWalletFile.do?payWay='+payWay,
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
						alert(data.success+data.impDataNum+"条数据");
					}
					if(data.importError!=undefined){
						alert(data.importError);
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
// 导入收款数据
function impReceiptData(){
	if($("#receiptData").val() == ""){
		alert("请选择要导入的文件!");
	}else{
		$("#monthReceipt").val('');
		$("#receiptNoChkNum").text('0');
		$("#receiptAlipayNum").text('0');
		$("#receiptWCNum").text('0');
		$("#receiptGapayNum").text('0');
		var receiptWay = $.trim($('#receiptWay').val());
		var $btn = $("#impReceiptButton").button('loading');
		$.ajaxFileUpload({
			url:rootPath +'/check_money/impReceiptData.do?receiptWay='+receiptWay,
			secureuri:false,
			fileElementId:'receiptData',
			dataType:'json',
			success: function (data, status)
			{
				$btn.button('reset');
				if(data.isEmpty!=undefined){
					alert(data.isEmpty);
				}
				if(data.success!=undefined){
					alert(data.success+data.impDataNum+"条数据");
				}
				if(data.importError!=undefined){
					alert(data.importError);
				}
			},
			error: function (data, status, e)
			{
				alert("【服务器异常，请连续管理员！】"+e);
			}

		});
	}
}
// 导入关系数据
function impReceiptRelation(){
	if($("#receiptWay").val() == ""){
		alert("请先选择收款渠道!");
	}else{
		var receiptWay = $.trim($('#receiptWay').val());
		var $btn = $("#impReceiptRelationButton").button('loading');
		$.ajaxFileUpload({
			url:rootPath +'/check_money/impReceiptRelation.do?receiptWay='+receiptWay,
			secureuri:false,
			fileElementId:'receiptRelation',
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
				if(data.sameDataNum!=undefined){
					alert("导入中存在"+data.sameDataNum+"条重复数据");
				}
				if(data.importError!=undefined){
					alert(data.importError);
				}
			},
			error: function (data, status, e)
			{
				alert("【服务器异常，请连续管理员！】"+e);
			}

		});
	}
}
// 重置导入
function resetFile(id){
	$('#'+id).val('');
}

// 根据收款渠道不同的改变样式
$('#receiptWay').change(function(e){
	$("#receiptDataDiv").css('display','block');
	$("#relationDataDiv").css('display','block');
	$("#DBDataDiv").css('display','none');
	$("#platformWeBankDiv").css('display','none');
	var receiptWay = $.trim($('#receiptWay').val());
	if(receiptWay=="微超"){
		$("#relationDataDiv").css('display','none');
	}
	if(receiptWay=="平台DB"){
		$("#receiptDataDiv").css('display','none');
		$("#relationDataDiv").css('display','none');
		$("#platformWeBankDiv").css('display','none');
		$("#DBDataDiv").css('display','block');
		$('.selectpicker').selectpicker({
            'selectedText': 'cat'
        });
	}
	if(receiptWay=="平台微众"){
		$("#receiptDataDiv").css('display','none');
		$("#relationDataDiv").css('display','none');
		$("#platformWeBankDiv").css('display','block');
		$('.selectpicker').selectpicker({
            'selectedText': 'cat'
        });
	}
	
})
// 支付方式为自动导入改变样式
$('#payWay').change(function(e){
	$("#payManualImportDiv").css('display','block');
	$("#payFileAutoImportDiv").css('display','none');
	var payWay = $.trim($('#payWay').val());
	if(payWay=="支付文件下载自动导入"){
		$("#payManualImportDiv").css('display','none');
		$("#payFileAutoImportDiv").css('display','block');
		$('.selectpicker').selectpicker({
            'selectedText': 'cat'
        });
	}
	
})
//页面加载的时候隐藏DBdiv
$(document).ready(function(){ 
　　$("#DBDataDiv").css('display','none');
   $("#payFileAutoImportDiv").css('display','none');
   $("#platformWeBankDiv").css('display','none');
});
// 关系数据：上传时文件名加入input
$('.file-team').delegate('#receiptRelation','change',function(e){
	var $input = $(this).parents('.file-team').find('[name=fileName]');
	var filepath = $("#receiptRelation").val().split("\\");
	var filepath = filepath[filepath.length-1];  
	$input.val(filepath).end().attr('title',filepath);
})
		

//db数据导入
$("#DBimp").click(function(e){
	
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var DBpayOrReceipt = $("#DBpayOrReceipt").val();
	
	if($("#DBpayPlatform").val()==null){
		alert("请选择支付平台!")
	}else{
		if(startTime==""||endTime==""){
			alert("日期不能为空!")
		}else{
			var $btn = $("#DBimp").button('loading');
			var DBpayPlatform = $("#DBpayPlatform").val().toString();
			if(DBpayOrReceipt==1){
				$.post(rootPath +'/platform/getPlatformPayData.do',
						{'startTime':startTime,
						 'endTime':endTime,
						 'DBpayPlatform':DBpayPlatform},
						function(data){
							$btn.button('reset');
							if(data.success){
								alert("成功导入"+data.impDataNum+"条数据");
							}else{
								alert(data.errMsg)
							}
							 
						},"json")
				
			}
			if(DBpayOrReceipt==2){
				$.post(rootPath +'/platform/getPlatformReceiptData.do',
						{'startTime':startTime,
						 'endTime':endTime,
						 'DBpayPlatform':DBpayPlatform},
						function(data){
							$btn.button('reset');
							if(data.success){
								alert("成功导入"+data.impDataNum+"条数据");
							}else{
								alert(data.errMsg)
							}
							
						},"json")
			}
	
		}
	}
})

//平台微众银行数据导入
$("#platformWeBankImpBtn").click(function(){
	
	var startTime = $("#platformWeBankStartTime").val();
	var endTime = $("#platformWeBankEndTime").val();
	var DBpayOrReceipt = $("#DBpayOrReceipt").val();
	
	
		if(startTime==""||endTime==""){
			alert("日期不能为空!")
		}else{
			var $btn = $("#DBimp").button('loading');
			
			
				$.post(rootPath +'/platform/getWxGiftCardBuyRecordList.do',
						{'startTime':startTime,
						 'endTime':endTime},
						function(data){
							$btn.button('reset');
							if(data.success){
								alert("成功导入"+data.impDataNum+"条数据");
							}else{
								alert(data.errMsg)
							}
							 
						},"json")
				
			
			
	
		}
	
})
//支付文件下载自动导入
$("#payFileAutoImportBtn").click(function (){
	var startTime = $("#payFileAutoStartTime").val();
	var endTime = $("#payFileAutoEndTime").val();
	var payWay = $("#payWayAuto").val();
	
	if($("#payWayAuto").val()==""){
		alert("请选择支付平台!")
	}else{
		if(startTime==""||endTime==""){
			alert("日期不能为空!")
		}else{
			var $btn = $("#payFileAutoImportBtn").button('loading');
			$.post(rootPath +'/check_money/payFileAutoImport.do',
					{'startTime':startTime,
					 'endTime':endTime,
					 'payWay':payWay},
					function(data){
						$btn.button('reset');
						if(data.success){
							alert("成功导入"+data.impDataNum+"条数据");
						}else{
							alert(data.errMsg);
						}
						 
					},"json")
		}
	}
})

$('#monthPay').change(function(){
	$("#confirm1").css('display','block');
	var monthPay = $("#monthPay").val();
	$.post(rootPath +'/check_money/getPayNoChkNum.do',
			{'monthPay':monthPay},
			function(data){
				 
		    	$("#payNoChkNum").text(data.payNoChkNum);
				$("#payAlipayNum").text(data.payAlipayNum);
				$("#payWCNum").text(data.payWCNum);
				$("#payGapayNum").text(data.payGapayNum);
				$("#confirm1").css('display','none');
			},"json")
})
$('#monthReceipt').change(function(){
	$("#confirm2").css('display','block');
	var monthReceipt = $("#monthReceipt").val();
	$.post(rootPath +'/check_money/getReceiptNoChkNum.do',
			{'monthReceipt':monthReceipt},
			function(data){
				 
				$("#receiptNoChkNum").text(data.receiptNoChkNum);
				$("#receiptAlipayNum").text(data.receiptAlipayNum);
				$("#receiptWCNum").text(data.receiptWCNum);
				$("#receiptGapayNum").text(data.receiptGapayNum);
				$("#confirm2").css('display','none');
			},"json")
})
