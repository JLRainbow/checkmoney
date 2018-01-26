<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en"	class="app js no-touch no-android chrome no-firefox no-iemobile no-ie no-ie10 no-ie11 no-ios no-ios7 ipad">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Google Chrome Frame也可以让IE用上Chrome的引擎: -->
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
<link href="/favicon.ico" type="image/x-icon" rel=icon>
<link href="/favicon.ico" type="image/x-icon" rel="shortcut icon">
<meta name="renderer" content="webkit">
<title>财务对账系统</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.servletContext.contextPath}/css/common/bootstrap/bootstrap.min.css" rel="stylesheet">
<!-- 针对登录界面 -->
<link rel="stylesheet"	type="text/css" href="${pageContext.servletContext.contextPath}/css/system/login.css">
<link rel="stylesheet" data-href="default" href="" type="text/css" class="skincsslittle">
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/common/jquery/jquery-1.8.3.js"></script>
</head>
<body onload="javascript:to_top();">
	<div id="loginbox">
		<form id="loginform" name="loginform" class="form-vertical"
			action="${pageContext.servletContext.contextPath }/login.do"
			method="post">
			<div class="logotitle">
				
				<h1><img src="${pageContext.servletContext.contextPath }/images/logo_left.png" alt="Logo">财务对账系统</h1>
			</div>
			<div class="main_input_box">
				<span class="add-on">
					<img src="${pageContext.servletContext.contextPath }/images/account_1.png" alt="请输入账号..">
					<%-- <img src="${pageContext.servletContext.contextPath }/images/login/tim1g.jpg" alt="请输入账号.."> --%>
				</span>
				<input type="text" placeholder="用户名" name="username" value=""/>
			</div>
			<div class="main_input_box">
				<span class="add-on">
					<img src="${pageContext.servletContext.contextPath }/images/lock_1.png" alt="请输入密码..">
				</span>
				<input type="password" placeholder="密码" name="password" value="" id="password"/>
			</div>
			<div class="form-actions">
				<!-- <a href="#" class="btn btn-info" id="to-recover">忘记密码？</a> -->
				<a type="button"  class="btn btn-success " onclick="checkUserForm()">登  　录</a>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		if ("${error}" != "") {
			alert("${error}");
		};
		//登录按钮事件
		function checkUserForm() {
			document.loginform.submit();
		}
		function to_top(){
			if(window != top){
		        top.location.href=location.href;
		    }
		}
		//按回车键登录
		document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];         
             if(e && e.keyCode==13){ // enter 键
                checkUserForm();
            }
        };


	</script>
<%-- 	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/common/theme.js"></script> --%>
</body>
</html>