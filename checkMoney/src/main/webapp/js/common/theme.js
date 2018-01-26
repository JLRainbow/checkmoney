/*点击切换主题*/
$('.theme li').click(function(){
    var value = $(this).attr('title');

	creatThemeValue(value);
	themeValue();
	
  $(this).parent().hide();
});

$('.theme_check').parent('li').mouseover(function(){
	$('.theme').show();
})
$('.theme_check').parent('li').mouseout(function(){
	$('.theme').hide();
})
//页面加载去数据库查看当前用户的主题
$().ready(function(){
	
	themeValue()
})
//从数据库读取主题
function themeValue(){
	
	$.post('/checkmoney/user/loadingTheme.do',function(result){
		var skincssurl = '/checkmoney/css/common/'+ result + '.css';
		$('.skincsslittle').attr("href",skincssurl);
	},"json")
}
//写入数据库
function creatThemeValue(value){
	$.post(rootPath +'/user/saveTheme.do',
	{'theme':value},
	function(result){
		
	},"json")
}