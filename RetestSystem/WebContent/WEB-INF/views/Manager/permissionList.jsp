<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="icon" type="image/ico"
	href="http://tattek.com/minimal//assets/images/favicon.ico" />
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
<div class="row">
	<div class="col-md-12">
		<!-- tile 复测率数据 -->
		<section class="tile">
			<!-- tile header -->
			<div class="tile-header">
				<h2>
					<strong>权限列表</strong> <font id='permisssionName'></font>
				</h2>
				<div class="controls">
					<a href="#" class="refresh"><i class="fa fa-refresh"></i></a> <a
						href="#" class="remove"><i class="fa fa-times"></i></a>
				</div>
			</div>
			<!-- /tile header -->
			<!-- tile body -->
			<div class="tile-body nopadding">
				<table class="table table-bordered table-sortable">
					<thead>
						<tr>
							<th>序号</th>
							<th>权限名</th>
							<th>描述</th>
							<th>链接</th>
							<th>上级权限</th>
							<th>深度</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageList.recordList}" var="permisssion"
							varStatus="index">
							<tr>
								<td>${index.index}</td>
								<td>${permisssion.name }</td>
								<td>${permisssion.descripation }</td>
								<td>${permisssion.urlPatten }</td>
								<td>${permisssion.parent.name }</td>
								<td>${permisssion.depth }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
			<div class="tile-body nopadding">

				<div class="tile-footer bg-transparent-black-2 rounded-bottom-corners" style="height: 50px;">
				<!-- 显示页面信息 -->
					<div class="col-sm-4 text-left sm-center" style="float: left;">页次：<font> ${pageList.currentPage}</font> /
						<font id="pageCount">${pageList.pageCount}</font>&nbsp;页 &nbsp; 每页显示：&nbsp;<font>${pageList.pageSize}&nbsp;条
						</font>&nbsp; 总记录数:&nbsp;<font>${pageList.recordCount }</font>&nbsp;条
					</div>
					<div style="float:right;">
						<ul
											class="pagination pagination-xs nomargin pagination-custom">
							<li class="disabled"><a href="#" id="firstPage" title="FirstPage" style='cursor: hand;'><i
													class="fa fa-angle-double-left"></i></a></li>
													<c:if test="${pageList.recordCount==0}">
													<li><a href="#" class="PageNum">1</a></li>
													</c:if>
											<c:forEach begin="${pageList.beginPageIndex}" end="${pageList.endPageIndex }" var="num">
												<li><a href="#" class="PageNum">${num}</a></li>
											</c:forEach>
						<li class="disabled"><a href="#" id="endPage" title="EndPage" style='cursor: hand;'><i
													class="fa fa-angle-double-right"></i></a></li>
						</ul>
					</div>
				</div>
			</div>
			<!-- /tile body -->
		</section>
		<!-- /tile -->
	</div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/vendor/mmenu/js/jquery.mmenu.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/vendor/sparkline/jquery.sparkline.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/vendor/nicescroll/jquery.nicescroll.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/vendor/animate-numbers/jquery.animateNumbers.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/vendor/videobackground/jquery.videobackground.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/vendor/blockui/jquery.blockUI.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/summernote/summernote.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/chosen/chosen.jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/momentjs/moment-with-langs.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/datepicker/bootstrap-datetimepicker.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/colorpicker/bootstrap-colorpicker.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/colorpalette/bootstrap-colorpalette.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/minimal.min.js"></script>
<!--myself -->
<script
	src="${pageContext.request.contextPath}/assets/js/jquery-editable-select.js"></script>
<script type="text/javascript"
	src="http://172.28.136.18/static/echarts-2.2.7/build/dist/echarts-all.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/permissionList.js"></script>
