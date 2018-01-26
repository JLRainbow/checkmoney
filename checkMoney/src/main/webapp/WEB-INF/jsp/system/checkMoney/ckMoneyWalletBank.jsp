<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/common/bootstrap/bootstrap-select.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>钱包，银行资金对账处理</title>
</head>
<body>
<div class="page-wrapper">
    <div class="container-fluid">
        <h4 class="module-title">钱包-银行资金对账处理</h4>
        <a class="module-close" href="${pageContext.request.contextPath}/index.do">X</a>
        <div class="row checking">
        	<div class="import wallet">
                    <div class="row">
                    	<div class="col-md-6">
	                        <div class="col-md-3">
	                            <p class="form-control-static">选择导入文件：</p>
	                        </div>
	                        <div class="col-md-6">
	                            <select class="form-control" id="fileType" name="fileType">
	                            	<option value="">-----------请选择----------</option>
	                                <c:forEach items="${walletBankList}" var="list">
									<option value="${list.channel_name}">${list.channel_name}</option>
									</c:forEach>
	                            </select>
	                        </div>
	                        <div class="col-md-3">
	                            <p class="form-control-static" id="payDataType"></p>
	                        </div>  
                        </div>
	                    <div class="col-md-6 file-team">
	                        <div class="col-md-3">
	                            <a href="javascript:;"  class="simulation-file btn btn-default">
	                                <input type="file" id="importFile" name="file">浏览
	                            </a>
	                        </div>
	                        <div class="col-md-6">
	                            <input type="text" id="fileName" readonly name="fileName" class="form-control">
	                        </div>
	                        <div class="col-md-3">
	                            <button type="button" id="impButton" onclick="impFile();" class="btn btn-info">
	                         	导入
	                        </button>
	                        </div>  
	                    </div>
	            	</div>
                </div>
            <div class="col-md-6 checkPay">
                <dl>
                    <dd><span class="pull-left" >钱包提现未对账：</span><span id="walletChkNum">0</span>件（截止<span id="closingDate1">XXXX年XX月XX日</span>）</dd>
                    <dd><span class="pull-left" >银行明细未对账：</span><span id="bankDetailChkNum1">0</span>件（截止<span id="closingDate2">XXXX年XX月XX日</span>）</dd>
                </dl>
              </div>
              <div class="col-md-6 checkCollection">
                <dl>
                    <dd><span class="pull-left" >银行汇总（315）未对账：</span><span id="bankDetailChkNum2">0</span>件（截止<span id="closingDate3">XXXX年XX月XX日</span>）</dd>
                    <dd><span class="pull-left" >银行汇总（140）未对账：</span><span id="bankCollectChkNum">0</span>件（截止<span id="closingDate4">XXXX年XX月XX日</span>）</dd>
                </dl>
            </div>
        </div>
        <div class="latitude">
            <div class="latitude-list row">
                <div class="col-md-1">
                    <h5>对账纬度</h5>
                </div>
                <div class="col-md-offset-2 col-md-5">
                    <div class="form-group">
                        <div class="col-sm-8">
                            <select class="form-control" id="chkType" name="chkType">
                                <option value="">-----------请选择-----------</option>
                                <option value="1">钱包银行资金结算对账（虚实对账）</option>
                                <option value="2">银行汇总资金结算对账（实实对账）</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 text-center">
                    <button type="button" class="btn btn-danger" id="chk_money" > 对账处理</button>
                </div>
            </div>
        </div>
	</div>
</div>	
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/checkMoney/ckMoneyWalletBank.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery/jquery.form.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery/ajaxfileupload.js" ></script>
</html>