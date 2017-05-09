<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link
	href="${pageContext.request.contextPath}/assets/css/vendor/bootstrap/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/normalize.css"
	rel="stylesheet">
<%-- 	<link href="${pageContext.request.contextPath}/assets/css/vendor/default.css" --%>
<!-- 	rel="stylesheet"> -->
<link
	href="${pageContext.request.contextPath}/assets/css/vendor/styles.css"
	rel="stylesheet">
</head>
<body>
	<div class="container"
		style="text-align: center; vertical-align: middle; margin: 5px; width: 1200px;">
		<div id="medetail" class="row"
			style="height: 250px; width: 1150px; margin-left: 130px;margin-top: 50px;">
			<div class='post-module col-md-4' style="height: 230px; width: 350px">
				<div>
					<img src='/RetestSystem/assets/images/123.jpg'
						style="height: 230px; width: 350px">
				</div>
				<div class='post-content'>
					<p class='description'><span>功能用途：Coater機腔體內，保證氣缸氣密性。單臺用量2pcs（110.16RMB/pcs），每6個月更換一次。</span></p>
				</div>
			</div>
			<div class='post-module col-md-4' style="height: 230px; width: 350px">
				<div>
					<img src='/RetestSystem/assets/images/demo.jpg'
						style="height: 230px; width: 350px">
				</div>
				<div class='post-content'>
					<p class='description'><span>这人很懒，什么也没留下</span></p>
				</div>
			</div>
			<div class='post-module col-md-4' style="height: 230px; width: 350px">
				<div>
					<img src='/RetestSystem/assets/images/123.jpg'
						style="height: 230px; width: 350px">
				</div>
				<div class='post-content'>
					<p class='description'>功能用途：Coater機腔體內，保證氣缸氣密性。單臺用量2pcs（110.16RMB/pcs），每6個月更換一次。</p>
				</div>
			</div>
		</div>
	</div>
	<div id="charts" style="height: 300px;margin-left: 150px;margin-right: 150px;"></div>
	
</body>
<script src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript"
	src="http://172.28.136.18/static/echarts-2.2.7/build/dist/echarts-all.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/testCharts.js"></script>
</html>