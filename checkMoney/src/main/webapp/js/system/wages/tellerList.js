//截取字符串
var oSpan = $('.border-right').find('span');
var str,length,start,last
oSpan.each(function(){
	str = $(this).text();
	length = str.length;
	start = str.substring(0,4) 
	last = str.substring(length-4); 
	if(length > 10){
		$(this).text(start + '...' + last) ;
		$(this).attr('title',str);
	}
})

/*放款事件*/
$("#send").click(function() {
	var  oBatch = $(this).parents('table').find('thead').find('.batch').text();
	$.ajax({
		type : 'get',
		url : rootPath + '/wages/send.do?batchCode=' + oBatch,
		success : function(data) {
			/*正确即将执行的函数*/
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			/*错误即将执行的函数*/
			alert(XmlHttpRequest);
			alert(textStatus);
			alert(errorThrown);
		}
	});
});
/*校验事件*/
$("#reValidate").click(function() {
	var  oBatch = $(this).parents('table').find('thead').find('.batch').text();
	$.ajax({
		type : 'get',
		url : rootPath + '/wages/reValidate.do?batchCode=' + oBatch,
		success : function(data) {
			/*正确即将执行的函数*/
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			/*错误即将执行的函数*/
			alert(XmlHttpRequest);
			alert(textStatus);
			alert(errorThrown);
		}
	});
});