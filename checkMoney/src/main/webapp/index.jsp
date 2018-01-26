<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html lang="en" class="app">
<head> 
<!-- 引入公用页面 -->
<%@include file="/common/common.jspf"%>
<!-- 自定义样式  开始 -->

<link rel="stylesheet" data-href="default" href="" type="text/css" class="skincsslittle">
<script type="text/javascript" src="${ctx}/js/system/user/updatePassword.js"></script>
<script type="text/javascript">
	$(function() {
    var winwidth = $("body").width();
    if(winwidth<770){
      $("#nav ul.lt li").click(function(){
        $("#nav").removeClass("nav-off-screen");
     });
    }
		var tb = $("#loadhtml");
		tb.html(CommnUtil.loadingImg());
		tb.load(rootPath+"/welcome.jsp");
		$("[nav-n]").each(function () {
		 		$(this).unbind('click');
				$(this).bind("click",function(){
						var nav = $(this).attr("nav-n");
						var sn = nav.split(",");
						var html = '<li><i class="fa fa-home"></i>';
						html+='<a href="index.do">Home</a></li>';
						for(var i=0;i<2;i++){
							html+='<li><a href="javascript:void(0)">'+sn[i]+'</a></li>';
						}
						$("#topli").html(html);
						var tb = $("#loadhtml");
						tb.html(CommnUtil.loadingImg());
						tb.load(rootPath+sn[2]);
						
						$(this).parents('.nav-primary').find('li').removeClass('active').end().find('a').removeClass('active');
						$(this).parent().parent().hide();//二级ul隐藏	
						
				});
			});
		});
		//点击其他地方  二级菜单消失
		$(document).bind('click',function(e){
               var e = e || window.event; //浏览器兼容性
               var elem = e.target || e.srcElement;
               while (elem) { //循环判断至跟节点，防止点击的是div子元素
                   if (elem.id && elem.id=='menu_nav') {
                       return;
                   }
                   elem = elem.parentNode;
               }

               $('#menu_nav ul').css('display','none'); //点击的不是div或其子元素
               $('#menu_nav').find('li').removeClass('active').end().find('a').removeClass('active');
           });
</script>
</head>
<body class="" >
	<section class="vbox">
		<header class="bg-dark dk header navbar navbar-fixed-top-xs" id="theme_head">
			<div class="navbar-header aside-md"  id="logo">
				<a href="${ctx}/index.do" class="navbar-brand">
					<img src="${ctx}/images/logo.png" class="m-r-sm">
					财务对账系统
				</a>
			</div>
			
			<ul class="nav navbar-nav navbar-right m-n hidden-xs nav-user head-userinfo">
				<li class="dropdown">
					<a href="index.html#" class="dropdown-toggle" data-toggle="dropdown"> 
						<span class="glyphicon glyphicon-user"></span>
						<span class="username">${userFormMap.accountname}</span> 
					</a>
				</li>
				<li><a href="#" onclick="javascript:updatePasswordLayer();"><span class="glyphicon glyphicon-edit"></span>密码修改</a></li>
				<li>
					<a class="theme_check"  href="#">主题  <span class="glyphicon glyphicon-chevron-down"></span></a>
					<ul class="theme">
						<li class="default" title="default">默认主题</li>
						<li class="theme_a" title="theme_a">主题一</li>
						<li class="theme_b active" title="theme_b">主题二</li>
						<li class="theme_c " title="theme_c">主题三</li>
						<li class="theme_d " title="theme_d">主题四</li>
					</ul>
				</li>
				<li><a href="logout.do"><span class="glyphicon glyphicon-off"></span>退出</a></li>
				
			</ul>
		</header>
		<section>
			<section class="hbox stretch">
				<aside class="bg-dark lter aside-md hidden-print hidden-xs" id="nav">
					<section class="vbox menuover">
						<section class="w-f scrollable">
							<div class="slim-scroll" data-height="auto"
								data-disable-fade-out="true" data-distance="0" data-size="5px"
								data-color="#333333">
								<!-- nav -->
								<nav class="nav-primary hidden-xs">
									<ul class="nav" id="menu_nav">
										<c:forEach var="key" items="${list}" varStatus="s">
											<!-- <li class="active"> 某一项展开-->
											<li <c:if test="${s.index==3}"></c:if>><a
												href="javascript:void(0)"
												<c:if test="${s.index==0}"></c:if>> <c:if
														test="${s.index==0}">
														<i class="icon-system"> 
														</i>
													</c:if> <c:if test="${s.index==1}">
														<i class="icon-user">
														</i>
													</c:if> <c:if test="${s.index==2}">
														<i class="icon-log"> 
														</i>
													</c:if> <c:if test="${s.index==3}">
														<i class="icon-finance"> 
														</i>
													</c:if> <c:if test="${s.index==4}">
														<i class="icon-user">
														</i>
													</c:if> <c:if test="${s.index==5}">
														<i class="icon-report"> 
														</i>
													</c:if> <c:if test="${s.index==6}">
														<i class="fa fa-th-list icon">
														</i>
													</c:if><span class="pull-right"> <i
														class="fa fa-angle-down text"></i> <i
														class="fa fa-angle-up text-active"></i>
												</span> <span>${key.name}</span>
											</a>
											<ul class="nav lt">
												<c:forEach var="kc" items="${key.children}">
													<li>
													<a href="javascript:void(0)" nav-n="${key.name},${kc.name},${kc.resurl}?id=${kc.id}"> 
														<i class="fa fa-angle-right"></i> 
														<span>${kc.name}</span>
													</a
													></li>
												</c:forEach>
											</ul>
											</li>
										</c:forEach>
									</ul>
								</nav>
								<!-- / nav -->
							</div>
						</section>
						
					</section>
				</aside>
				<!-- /.aside -->
				<section id="content">
					<section id="id_vbox" class="vbox">
						<!--<ul class="breadcrumb no-border no-radius b-b b-light" id="topli"></ul> -->
						<section class="scrollable contentover" >
						<div id="loadhtml"></div>
						</section>
					</section>
				</section>
				<aside class="bg-light lter b-l aside-md hide" id="notes">
					<div class="wrapper">Notification</div>
				</aside>
			</section>
		</section>
	</section>
	<!-- Bootstrap -->
	<div id="flotTip" style="display: none; position: absolute;"></div>
	
<script type="text/javascript" src="${ctx}/js/common/theme.js"></script>
</body>
</html>