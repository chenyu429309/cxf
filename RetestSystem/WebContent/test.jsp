<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <link href="${pageContext.request.contextPath}/assets/css/vendor/bootstrap/bootstrap.min.css" rel="stylesheet">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/js/vendor/jquery-file-upload/css/jquery.fileupload-noscript.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/js/vendor/jquery-file-upload/css/jquery.fileupload-ui-noscript.css">
</head>
<body>
	<div id="center" align="center">
		<span class="btn btn-success fileinput-button"> <span>选择文件</span>
			<input id="fileld" type="file" name="file" multiple />
		</span>
		<div id="files" class="btn_blue" style="width: 400px; height: 200px;"></div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/bootstrap/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/jquery-file-upload/vendor/jquery.ui.widget.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/jquery-file-upload/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/jquery-file-upload/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/jquery-file-upload/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/jquery-file-upload/jquery.fileupload-image.js"></script>
<!-- The File Upload audio preview plugin -->
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/jquery-file-upload/jquery.fileupload-audio.js"></script>
<!-- The File Upload video preview plugin -->
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/jquery-file-upload/jquery.fileupload-video.js"></script>
<!-- The File Upload validation plugin -->
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/jquery-file-upload/jquery.fileupload-validate.js"></script>
<!-- The File Upload user interface plugin -->
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/jquery-file-upload/jquery.fileupload-ui.js"></script>
<!-- The main application script -->
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/jquery-file-upload/fileupload-main.js"></script>

<script type="text/javascript">
	$(function() {
		$("#fileld").fileupload({
			url : "${pageContext.request.contextPath}/UpOrDownload.spring",
			done : function(e, data) {
				alert("122");
				$('#files').append(data.result.name + '<br>');
			},
			fail : function() {
				alert('2333');
			}
		});
	});
</script>
</html>