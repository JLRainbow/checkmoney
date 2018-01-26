//单独验证某一个input  class="checkpass"
jQuery.validator.addMethod("checkacc", function(value, element) {
	return this.optional(element)
			|| ((value.length <= 30) && (value.length >= 3));
}, "账号由3至30位字符组合构成");
$(function() {
	$("form").validate({
		submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
			ly.ajaxSubmit(form, {// 验证新增是否成功
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data == "success") {
						layer.confirm('添加成功!是否关闭窗口?', function(index) {
							parent.grid.loadData();
							parent.layer.close(parent.pageii);
							return false;
						});
						$("#form")[0].reset();
					} else {
						layer.alert('添加失败！', 3);
					}
				}
			});
		},
		rules : {
			"budgettypeFormMap.budgettypekey" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'isExistBudgettypekey.do',
					data : {
						budgettypekey : function() {
							return $("#budgettypekey").val();
						}
					}
				}
			},"budgettypeFormMap.name" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'isExistBudgettypename.do',
					data : {
						name : function() {
							return $("#name").val();
						}
					}
				}
			}
		},
		messages : {
			"budgettypeFormMap.budgettypekey" : {
				required : "请输入收支类型唯一Key",
				remote : "该收支类型唯一Key已经存在"
			},"budgettypeFormMap.name" : {
				required : "请输入收支类型名称",
				remote : "该收支类型名称已经存在"
			}
		},
		errorPlacement : function(error, element) {// 自定义提示错误位置
			$(".l_err").css('display', 'block');
			// element.css('border','3px solid #FFCCCC');
			$(".l_err").html(error.html());
		},
		success : function(label) {// 验证通过后
			$(".l_err").css('display', 'none');
		}
	});
});
