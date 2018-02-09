<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/common/bootstrap/bootstrap-select.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/system/wages/bootstrap-datetimepicker.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>财务对账处理(虚虚对账)</title>
</head>
<body>
<div class="page-wrapper">
    <div class="container-fluid">
        <h4 class="module-title">平台销售业务对账处理（虚虚对账）</h4>
        <a class="module-close" href="${pageContext.request.contextPath}/index.do">X</a>
        <div class="row checking">
            <div class="col-md-6 checkPay">
                <div class="import">
                    <div class="row">
                        <div class="col-md-3">
                            <p class="form-control-static">支付渠道：</p>
                        </div>
                        <div class="col-md-6">
                            <select class="form-control" id="payWay" name="payWay">
                            	<option value="">-----------请选择----------</option>
                                <c:forEach items="${payList}" var="list">
								<option value="${list.channel_id}">${list.channel_name}</option>
								</c:forEach>
								<option value="支付文件下载自动导入">支付文件下载自动导入</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <p class="form-control-static" id="payDataType"></p>
                        </div>  
                    </div>
                    <div class="row file-team" id="payManualImportDiv">
                        <div class="col-md-3">
                            <a href="javascript:;"  class="simulation-file btn btn-default">
                                <input type="file" id="importFile" name="file">浏览
                            </a>
                        </div>
                        <div class="col-md-6">
                            <input type="text" readonly id="fileName" readonly name="fileName" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <button type="button" id="payImpButton" onclick="impPayData();" class="btn btn-info">
                         	导入
                        </button>
                        </div>  
                    </div>
                    
                    <div id='payFileAutoImportDiv' >

					     <div class="row file-team ">
					     	<div class="col-md-3">
					            <p class="form-control-static">支付平台：</p>
					       	</div>
					        <div class="col-md-6">
								<select class="form-control" id="payWayAuto">
	                            	<option value="">-----------请选择----------</option>
	                                <c:forEach items="${payList}" var="list">
									<option value="${list.channel_id}">${list.channel_name}</option>
									</c:forEach>
	                            </select>
							  </div>
					        <div class="col-md-3">
	                            <button type="button" id="payFileAutoImportBtn" class="btn btn-info" >导入</button>
	                        </div>
					     </div>
                      	<div class="row file-team ">
		                   <div class="col-md-3">
					             <p class="form-control-static">日　　期：</p>
					        </div>
					        <div class="col-md-4">
					            <div class="input-append date form_datetime datetime_c" id="datetimepicker1" data-date="" data-date-format="yyyy-mm-dd">
					                <input  id="payFileAutoStartTime" name="startTime" type="text" value="" readonly="" class="form-control" style="float:left;">
					                <span class="add-on "><i class="icon-th glyphicon glyphicon-th-large"></i></span>
					            </div>
					      	</div>
					        <div class="col-md-4">
					            <div class="input-append date form_datetime datetime_c" id="datetimepicker2" data-date="" data-date-format="yyyy-mm-dd">
					                <input id="payFileAutoEndTime" name="endTime" type="text" value="" readonly="" class="form-control" style="float:left;">
					                <span class="add-on "><i class="icon-th glyphicon glyphicon-th-large"></i></span>
					            </div>
					        </div>
					    </div>
					     
                    </div>
                    
                </div>
                <dl>
                    <dt style="overflow:hidden;"><!-- 截止<span id="payClosingDate">XXXX年XX月XX日</span> -->
                    	 <!-- <p class="form-control-static" style="float:left;">数据：</p> -->
                    	 <p class="form-control-static" style="float:left;">日期选择：</p>
                    	<div class="input-append date form_datetime datetime_a" id="datetimepicker2" data-date="" style="width:150px;margin:auto;;">
			                <input  id="monthPay" name="monthPay" type="text" value="" readonly="" class="form-control" style="float:left;">
			                <span class="add-on "><i class="icon-th glyphicon glyphicon-th-large"></i></span>
			            </div>
			            <span class="pull-right" style="margin-top:-25px;display:none;" id="confirm1">确认中...</span>
                    </dt>
                    <dd><span class="pull-left" >支付渠道未对账处理数据：</span><span id="payNoChkNum">0</span>件</dd>
                    <dd><span class="pull-left" >支付宝：</span><span id="payAlipayNum">0</span>件</dd>
                    <dd><span class="pull-left" >微   信：</span><span id="payWCNum">0</span>件</dd>
                    <dd><span class="pull-left" >国安付：</span><span id="payGapayNum">0</span>件</dd>
                </dl>
              </div>
              <div class="col-md-6 checkCollection">
                <div class="import">
                    <div class="row">
                        <div class="col-md-3">
                            <p class="form-control-static">收款渠道：</p>
                        </div>
                        <div class="col-md-6">
                            <select class="form-control"  id="receiptWay" name="receiptWay">
                                <option value="">-----------请选择-----------</option>
								<c:forEach items="${receiptList}" var="list">
									<option value="${list.channel_name}">${list.channel_name}</option>
								</c:forEach>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <p class="form-control-static" id="receiptDataType"></p>
                        </div>  
                    </div>
                    <div class="row file-team sweep" id="receiptDataDiv">
                    	<div class="col-md-12">
                            <p class="form-control-static">数据：</p>
                        </div>
                        <div class="col-md-3">
                            <a href="javascript:;" class="simulation-file btn btn-default">
                                <input type="file" id="receiptData" name="receiptData">浏览
                            </a>
                        </div>
                        <div class="col-md-6">
                            <input type="text" readonly name="fileName" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <button type="button" readonly id="impReceiptButton" class="btn btn-info" onclick="impReceiptData();" > 导入</button>
                        </div>  
                    </div>
                    <div class="row file-team sweep" id='relationDataDiv'>
                        <div class="col-md-12">
                            <p class="form-control-static">关系数据：</p>
                        </div>
                        <div class="col-md-3">
                            <a href="javascript:;" class="simulation-file btn btn-default">
                                <input type="file" id="receiptRelation" name="receiptRelation">浏览
                            </a>
                        </div>
                        <div class="col-md-6">
                            <input type="text" readonly readonly name="fileName" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <button type="button" id="impReceiptRelationButton" class="btn btn-info" onclick="impReceiptRelation();">导入</button>
                        </div>  
                    </div>
                    <div id='DBDataDiv' >

					     <div class="row file-team ">
					     	<div class="col-md-3">
					            <p class="form-control-static">支付平台：</p>
					       	</div>
					        <div class="col-md-9">
						      	<label for=""></label>
								<select id="DBpayPlatform" class="selectpicker bla bla bli" multiple data-live-search="true">
								    <option value="wx">微信</option>
								    <option value="wx_pub_qr">微信扫码</option>
								    <option value="wx_pub">微信公众号</option>
								    <option value="alipay">支付宝</option>
								    <option value="alipay_qr">支付宝扫码</option>
									<option value="gapay,union_pay">国安付/银行卡支付</option>
									<option value="pos">POS</option>
									<option value="cash">现金</option>
								  </select>
							  </div>
					     </div>
					    <div class="row file-team ">
	                        <div class="col-md-3">
					            <p class="form-control-static">收款/退款：</p>
					       	</div>
	                        <div class="col-md-6">
	                            <select class="form-control" id="DBpayOrReceipt">
									<option value="1">收款</option>
									<option value="2">退款</option>
	                            </select>
	                        </div>
					        <div class="col-md-3">
	                            <button type="button" id="DBimp" class="btn btn-info" >导入</button>
	                        </div>
                      	</div>
                      	<div class="row file-team ">
		                    <div class="col-md-3">
					             <p class="form-control-static">日　　期：</p>
					        </div>
					        <div class="col-md-4">
					            <div class="input-append date form_datetime datetime_c" id="datetimepicker1" data-date="" data-date-format="yyyy-mm-dd">
					                <input  id="startTime" name="startTime" type="text" value="" readonly="" class="form-control">
					                <span class="add-on "><i class="icon-th glyphicon glyphicon-th-large"></i></span>
					            </div>
					      	</div>
					        <div class="col-md-4">
					            <div class="input-append date form_datetime datetime_c" id="datetimepicker2" data-date="" data-date-format="yyyy-mm-dd">
					                <input id="endTime" name="endTime" type="text" value="" readonly="" class="form-control">
					                <span class="add-on "><i class="icon-th glyphicon glyphicon-th-large"></i></span>
					            </div>
					        </div>
					     </div>
                    </div>
                </div>
                <dl>
                    <dt><!-- 截止<span id="receiptClosingDate">XXXX年XX月XX日</span> -->
                    <p class="form-control-static" style="float:left;">日期选择：</p>
                    	<div class="input-append date form_datetime datetime_a" id="datetimepicker2" data-date=""  style="width:150px;margin:auto;;">
			                <input  id="monthReceipt" name="monthReceipt" type="text" value="" readonly="" class="form-control" style="float:left;">
			                <span class="add-on "><i class="icon-th glyphicon glyphicon-th-large"></i></span>
			            </div>
			            <span class="pull-right" style="margin-top:-25px;display:none;" id="confirm2">确认中...</span>
                    </dt>
                    <dd><span class="pull-left" >收款渠道未对账处理数据：</span><span id="receiptNoChkNum">0</span>件</dd>
                    <dd><span class="pull-left" >支付宝：</span><span id="receiptAlipayNum">0</span>件</dd>
                    <dd><span class="pull-left" >微   信：</span><span id="receiptWCNum">0</span>件</dd>
                    <dd><span class="pull-left" >国安付：</span><span id="receiptGapayNum">0</span>件</dd>
                </dl>
            </div>
        </div>
        <div class="latitude">
            <div class="latitude-list row">
                <div class="col-md-1">
                    <h5>对账纬度</h5>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                    	<div class="col-md-3">
				            <p class="form-control-static">支付方式：</p>
				       	</div>
                        <div class="col-sm-8">
                            <select class="form-control" id="chkPayWay" name="chkPayWay">
                                <option value="">-----------请选择-----------</option>
                                <option value="支付宝">支付宝</option>
                                <option value="微信">微信</option>
                                <option value="国安付/银行卡支付">国安付/银行卡支付</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <div class="col-md-3">
				            <p class="form-control-static">收款/退款：</p>
				       	</div>
                        <div class="col-sm-8">
                            <select class="form-control" id="chkReceiptWay" name="chkReceiptWay">
								<option value="1">收款</option>
								<option value="2">退款</option>
                            </select>
                        </div>
                    </div>
                </div>
                  </div>
                <div class="latitude-list row">
                <div class="col-md-1">
                    
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                    	<div class="col-md-3">
				            <p class="form-control-static">开始日期：</p>
				       	</div>
                        <div class="col-sm-8">
                            <div class="input-append date form_datetime datetime_b" id="datetimepicker1" data-date="" data-date-format="yyyy-mm-dd" style="width:250px;margin:auto;;">
			                <input  id="startTimeChkMoney" name="startTime" type="text" value="" readonly="" class="form-control" style="float:left;">
			                <span class="add-on "><i class="icon-th glyphicon glyphicon-th-large"></i></span>
			            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <div class="col-md-3">
				            <p class="form-control-static">结束日期：</p>
				       	</div>
                        <div class="col-sm-8">
                            <div class="input-append date form_datetime datetime_b" id="datetimepicker1" data-date="" data-date-format="yyyy-mm-dd" style="width:250px;margin:auto;;">
			                <input  id="endTimeChkMoney" name="endTime" type="text" value="" readonly="" class="form-control" style="float:left;">
			                <span class="add-on "><i class="icon-th glyphicon glyphicon-th-large"></i></span>
			            </div>
                        </div>
                    </div>
                </div>
           </div>
                
                <div class="col-md-12 text-center">
                    <button type="button" class="btn btn-danger" data-loading-text="对账中..." id="chk_money" > 对账处理</button>
                </div>
            </div>
        </div>
	</div>
</div>	
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/checkMoney/check_money.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery/jquery.form.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery/ajaxfileupload.js" ></script>
<!-- 日期控件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/locales/bootstrap-datetimepicker.fr.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/system/wages/date.js"></script> --%>

 <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/bootstrap/bootstrap-select.js"></script>
  <script type="text/javascript">
	  $("#endTime").unbind('change').change(function(){
		// 新的绑定事件
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
		var sta_date = new Date(startTime);  
		var end_date = new Date(endTime);// 将字符串转化为时间
		var num = (end_date-sta_date)/(1000*3600*24);// 求出两个时间的时间差
		var days = parseInt(Math.ceil(num)+1);//转化为整天（小于零的话剧不用转了）
		var DBpayOrReceipt = $("#DBpayOrReceipt").val();
		if(days<0){
			alert('结束日期不能小于开始日期!');
			$('#endTime').val("");
		}else if(days>7){
			
			if(DBpayOrReceipt==1){
				alert('最多只能选择7天');
				$('#endTime').val("");
			}
			if(DBpayOrReceipt==2){
				if(days>31){
					alert('最多只能选择31天');
					$('#endTime').val("");
				}
			}
			
			
		}
	});
  $("#startTime").change(function(){
	  $('#endTime').val("");
	});
  
  $("#payFileAutoEndTime").unbind('change').change(function(){
		// 新的绑定事件
		var payFileAutoStartTime = $('#payFileAutoStartTime').val();
		var payFileAutoEndTime = $('#payFileAutoEndTime').val();
		var sta_date = new Date(payFileAutoStartTime);  
		var end_date = new Date(payFileAutoEndTime);// 将字符串转化为时间
		var num = (end_date-sta_date)/(1000*3600*24);// 求出两个时间的时间差
		var days = parseInt(Math.ceil(num)+1);//转化为整天（小于零的话剧不用转了）
		if(days<0){
			alert('结束日期不能小于开始日期!');
			$('#payFileAutoEndTime').val("");
		}else if(days>7){
			alert('最多只能选择7天');
			$('#payFileAutoEndTime').val("");
		}
	});
$("#payFileAutoStartTime").change(function(){
	  $('#payFileAutoEndTime').val("");
});
	//日历控件_月份
$(".datetime_a").datetimepicker({
	format: 'yyyy-mm',
    weekStart: 1,
    autoclose: true,
    startView: 3,
    minView: 3,
    forceParse: false,
    language: 'zh-CN',
    todayBtn: true,
    pickerPosition: "bottom-left"
     	
})
//日历控件
$(".datetime_b").datetimepicker({
	format: 'yyyy-mm-dd',
    weekStart: 1,
    autoclose: true,
    startView: 2,
    minView: 2,
    forceParse: false,
    language: 'zh-CN',
    todayBtn: true,
    pickerPosition: "top-left"
     	
})

//日历控件
$(".datetime_c").datetimepicker({
	format: 'yyyy-mm-dd',
    weekStart: 1,
    autoclose: true,
    startView: 2,
    minView: 2,
    forceParse: false,
    language: 'zh-CN',
    todayBtn: true,
    pickerPosition: "bottom-left"
     	
})
$('.datetime_c').change(function(){
	var vYear = date.getFullYear()
	var vMon = date.getMonth() + 1 
	var vDay = date.getDate()
	time= vYear + '-' + (vMon<10 ? "0" + vMon : vMon) + '-' + (vDay<10 ? "0"+ vDay : vDay);
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	var payFileAutoStartTime = $('#payFileAutoStartTime').val();
	var payFileAutoEndTime = $('#payFileAutoEndTime').val();
	if(time <= startTime){
		alert('所选日期不能等于或大于当前日期')
		$('#startTime').val('');
	}
	if(time <= endTime){
		alert('所选日期不能等于或大于当前日期')
		$('#endTime').val('');
	}
	if(time <= payFileAutoStartTime){
		alert('所选日期不能等于或大于当前日期')
		$('#payFileAutoStartTime').val('');
	}
	if(time <= payFileAutoEndTime){
		alert('所选日期不能等于或大于当前日期')
		$('#payFileAutoEndTime').val('');
		
	}
})
$('#endTimeChkMoney').change(function(){
	compareDate();
})
function compareDate(){
	var startTimeChkMoney = $('#startTimeChkMoney').val();
	var endTimeChkMoney = $('#endTimeChkMoney').val();
	if(startTimeChkMoney > endTimeChkMoney){
		alert('结束日期不能小于开始日期!');
		$('#endTimeChkMoney').val("");
	}
}
  </script>
</html>