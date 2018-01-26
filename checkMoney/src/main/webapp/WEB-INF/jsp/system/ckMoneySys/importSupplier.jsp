<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/common/bootstrap/bootstrap-select.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>E店供应商关联设置</title>
</head>
<body>
<div class="page-wrapper">
    <div class="container-fluid">
        <h4 class="module-title">E店供应商关联设置</h4>
        <a class="module-close" href="${pageContext.request.contextPath}/index.do">X</a>
        <div class="row checking" style="border-bottom: #fff">
            <div class="col-md-6 checkPay">
                <div class="import">
                    <div class="row">
                        <div class="col-md-3">
                            <p class="form-control-static" style="width: 200px">选择E店供应商关联表：</p>
                        </div>
                    </div>
                    <div class="row file-team">
                        <div class="col-md-3">
                            <a href="javascript:;"  class="simulation-file btn btn-default">
                                <input type="file" id="importFile" name="file">浏览
                            </a>
                        </div>
                        <div class="col-md-6">
                            <input type="text" readonly id="fileName" readonly name="fileName" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <button type="button" id="ImpButton" onclick="impSupplier();" class="btn btn-info">
                         	导入
                        </button>
                        </div>  
                    </div>
                </div>
              </div>
           
        </div>
        
	</div>
</div>	
</body>
<script type="text/javascript">
//选择文件后判断文件格式是否正确
$('.file-team').delegate('#importFile','change',function(e){
	var $input = $(this).parents('.file-team').find('[name=fileName]');
	$input.val('');
	
	var filepath = $("#importFile").val().split("\\");
	var filepath = filepath[filepath.length-1];
	var extStart = filepath.lastIndexOf(".");
	var suffix = filepath.substring(extStart, filepath.length).toLowerCase();// 获取后缀名
	
	if(suffix != ".xlsx"&&suffix != ".xls"){
		alert("导入文件格式不正确,请重新选择Excel文件导入!")
		resetFile('importFile');
	}else{
		
		$input.val(filepath).end().attr('title',filepath);
	}
	
})
//导入
function impSupplier(){
	if($("#importFile").val() == ""){
		alert("请选择要导入的文件!");
	}else{
		var $btn = $("#ImpButton").button('loading');
		$.ajaxFileUpload({
			url:rootPath +'/ckMoneySys/importSupplierFile.do',
			secureuri:false,
			fileElementId:'importFile',
			dataType:'json',
			success: function (data)
			{
				$btn.button('reset');
				if(data.isEmpty!=undefined){
					alert(data.isEmpty);
				}
				if(data.success!=undefined){
					alert(data.success);
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
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery/jquery.form.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery/ajaxfileupload.js" ></script>
</html>