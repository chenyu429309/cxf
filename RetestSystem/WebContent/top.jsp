<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>复测率良率系统</title>
<link rel="icon" type="image/ico"
	href="http://tattek.com/minimal//assets/images/favicon.ico" />
<style type="text/css">
.titlespan {
	font-size: 18px;
}

.panel-backcolor {
	background-color: gray;
	background: gray;
}
</style>
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/assets/css/vendor/bootstrap/bootstrap.min.css"
	rel="stylesheet">
<link
	href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/vendor/animate/animate.css">
<link type="text/css" rel="stylesheet" media="all"
	href="${pageContext.request.contextPath}/assets/js/vendor/mmenu/css/jquery.mmenu.all.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/js/vendor/videobackground/css/jquery.videobackground.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/vendor/bootstrap-checkbox.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/js/vendor/summernote/css/summernote.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/js/vendor/summernote/css/summernote-bs3.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/js/vendor/chosen/css/chosen.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/js/vendor/chosen/css/chosen-bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/js/vendor/datepicker/css/bootstrap-datetimepicker.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/js/vendor/colorpicker/css/bootstrap-colorpicker.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/js/vendor/colorpalette/bootstrap-colorpalette.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jquery-editable-select.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/js/vendor/datatables/css/dataTables.bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/js/vendor/datatables/css/ColVis.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/js/vendor/datatables/css/TableTools.css">
<link href="${pageContext.request.contextPath}/assets/css/minimal.css"
	rel="stylesheet">
<style type="text/css">
.astyleClass {
	text-align: center;
	vertical-align: middle;
	height: 37px;
}
.ulClass {
	height: 45px;
}
</style>
</head>
<body style="background-color: #D8D8D8;">
	<div id="wrap">
		<div class="row">
			<!-- Fixed navbar -->
			<div
				class="navbar navbar-default navbar-fixed-top navbar-transparent-black mm-fixed-top"
				role="navigation" id="navbar">
				<div class="navbar-header col-md-2">
					<a class="navbar-brand" href="#"> <font
						style="font-size: 33px; font-family: '微软雅黑'; color: #808080;">复测率/良率</font>
					</a>
					<div class="sidebar-collapse">
						<a href="#"> <i class="fa fa-bars"></i>
						</a>
					</div>
				</div>
				<!-- Branding end -->
				<!-- .nav-collapse -->
				<div class="navbar-collapse">
					<!-- Page refresh -->
					<ul class=" navbar-nav refresh ulClass breadcrumb">
						<li><a href="#" class='astyleClass'><span
								style="color: #808080;">Home</span></a><i></i>
					</ul>
					<ul class="nav navbar-nav quick-actions">
						<li class="dropdown divided user" id="current-user">
							<div class="profile-photo">
								<img
									src="${pageContext.request.contextPath}/assets/images/profile-photo.jpg"
									alt="" />
							</div> <a class="dropdown-toggle options" data-toggle="dropdown"
							href="#">Visitor <i class="fa fa-caret-down"></i>
						</a>
							<ul class="dropdown-menu arrow settings">
								<li class="divider"></li>
								<li><a href="#" id="loginout"><i
										class="fa fa-power-off"></i> Logout</a></li>
								<li class="divider"></li>
							</ul>
						</li>
						<li><a href="#mmenu"><i class="fa fa-comments"></i></a></li>
					</ul>
				</div>
				<div>
					<ul class="nav navbar-nav side-nav" id="sidebar">
						<li class="divider"></li>
						<li class="divider"></li>
						<li class="navigation" id="navigation"><a href="#"
							class="sidebar-toggle" data-toggle="#navigation"><font
								style="font-size: 30px;">导航</font><i class="fa fa-angle-up"></i></a>
							<ul class="menu">
								<li class="dropdown open active"><a href="#"
									class="dropdown-toggle" data-toggle="dropdown"> <i
										class="fa fa-th-large"></i><font
										style="font-size: 18px; color: #808080;"> &nbsp; &nbsp;
											查询</font> <b class="fa fa-plus dropdown-plus"></b>
								</a>
									<ul class="dropdown-menu">
										<li class="active"><a
											href="manager/goDay_tables_jsp.spring" id="searchByDay">
												<i class="fa fa-caret-right"></i>&nbsp; &nbsp;&nbsp; <font
												style="font-size: 14px; color: #808080;">&nbsp;
													&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;BY DAY</font>
										</a></li>
										<li><a href="#" id="searchByShift"> <i
												class="fa fa-caret-right"></i> &nbsp; &nbsp;&nbsp; <font
												style="font-size: 14px; color: #808080;">&nbsp;
													&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;BY SHIFT</font>
										</a></li>
										<li><a href="#" id="searchByHour"> <i
												class="fa fa-caret-right"></i>&nbsp; &nbsp;&nbsp; <font
												style="font-size: 14px; color: #808080;">&nbsp;
													&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;BY HOURS</font>
										</a></li>
									</ul></li>
								<li class="dropdown"><a href="#" class="dropdown-toggle"
									data-toggle="dropdown"> <i class="fa fa-pencil"></i> <font
										style="font-size: 18px; color: #808080;">&nbsp; &nbsp;
											后台管理 </font><b class="fa fa-plus dropdown-plus"></b>
								</a>
									<ul class="dropdown-menu">
										<li><a href="#" id="usermanager"> <i
												class="fa fa-caret-right"></i> <font
												style="font-size: 14px; color: #808080;">&nbsp;
													&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; 用户管理</font>
										</a></li>
										<li><a href="#" id="rolemanager"> <i
												class="fa fa-caret-right"></i> <font
												style="font-size: 14px; color: #808080;"> &nbsp;
													&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; 角色管理</font>
										</a></li>
										<li><a href="#" id="permissionmanager"> <i
												class="fa fa-caret-right"></i> <font
												style="font-size: 14px; color: #808080;">&nbsp;
													&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; 权限管理</font>
										</a></li>
									</ul></li>

								<li class="dropdown"><a href="#" class="dropdown-toggle"
									data-toggle="dropdown"> <i class="fa fa-pencil"></i><font
										style="font-size: 18px; color: #808080;"> &nbsp; &nbsp;
											其他</font> <b class="fa fa-plus dropdown-plus"></b>
								</a>
									<ul class="dropdown-menu">
										<li><a href="#" id="download"> <i
												class="fa fa-caret-right"></i> <font
												style="font-size: 14px; color: #808080;">&nbsp;
													&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;数据维护</font>
										</a></li>
									</ul></li>
							</ul></li>
					</ul>
					<!-- Sidebar end -->
				</div>
				<!--/.nav-collapse -->
			</div>
			<!-- Fixed navbar end -->
			<div id="content" class="col-md-12">
				<!-- content main container -->
				<div class="main">