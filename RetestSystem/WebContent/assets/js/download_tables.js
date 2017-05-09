$(function(){
	$(".breadHome").nextAll().remove();
	$('.breadcrumb').append("<li class='breadDay'><a href='#' class='astyleClass'><span	style='color: #808080;'>MAINTENANCE DATA SYSTEM</span></a><i></i>");
	$("#flieupload").bind('click',function(){
		var checkFile = $("#file").val();// 获取属性为file的值
		if (checkFile == "" || checkFile == null) {
			alert("可能沒有選擇文件！！！");
			return false;
		}
		$.ajaxFileUpload({
			url : 'upOrDownload/uploadRateData.spring',// 请求链接
			type : 'POST',// 请求的方式
			secureuri : false,
			fileElementId : 'file',
			dataType : "application/json",// 请求的参数类型
			success : function(data, status) {// 回现函数
				if (data.indexOf("success") > 0) {
					alert("上傳成功！！");
				} else {
					alert("文件數據有誤，請仔細檢查！！！");
				}
			}
		});
	});
	$("#devicefileupload").bind('click',function(){
		var checkFile = $("#devicefile").val();// 获取属性为file的值
		if (checkFile == "" || checkFile == null) {
			alert("可能沒有選擇文件！！！");
			return false;
		}
		$.ajaxFileUpload({
			url : 'upOrDownload/devicefileuploadRateData.spring',// 请求链接
			type : 'POST',// 请求的方式
			secureuri : false,
			fileElementId : 'devicefile',
			dataType : "application/json",// 请求的参数类型
			success : function(data, status) {// 回现函数
				if (data.indexOf("success") > 0) {
					alert("上傳成功！！");
				} else {
					alert("文件數據有誤，請仔細檢查！！！");
				}
			}
		});
	});
});
