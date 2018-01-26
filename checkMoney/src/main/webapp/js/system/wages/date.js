//设置当前日期
var date = new Date();//获取当前时间  
var vYear = date.getFullYear()
var vMon = date.getMonth() + 1
var vDay = date.getDate()
date= vYear + '-' + (vMon<10 ? "0" + vMon : vMon) + '-' + (vDay<10 ? "0"+ vDay : vDay);
$(".form_datetime").find('input').val(date);

//日历控件
$(".form_datetime").datetimepicker({
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
/*.on('changeDate', function(ev){
	//$('#datetimepicker2').datetimepicker('setStartDate', new Date($('#startTime').val()));
});*/
$('#endTime').change(function(){
	compareDate();
})
function compareDate(){
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	if(startTime > endTime){
		alert('结束日期不能小于开始日期!');
		$('#endTime').val("");
	}
}
