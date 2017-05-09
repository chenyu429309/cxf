<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<!-- content main container -->
<!-- row -->
<div class="row">
	<div class="col-md-12">
		<!-- tile 复测率数据 -->
		<section class="tile color" style="background-color: #C0C0C0">
			<!-- tile header -->
			<div class="tile-header">
				<h1>
					<strong>上传资料</strong>
				</h1>
				<div class="controls">
					<a href="#" class="refresh"><i class="fa fa-refresh"></i></a> <a
						href="#" class="remove"><i class="fa fa-times"></i></a>
				</div>
			</div>
		</section>
	</div>
	<div class="col-md-12" style="border: 1px solid #C0C0C0;height: 200px;widows: 500px;padding-top: 100px;">
		<!-- tile 复测率数据 -->
		<section class="tile color transparent-black">
			<div class="mainbody col-md-6" style="padding: 10px; margin-left: 300px">
				<div class="col-md-6">
					<div class="control-group">
						<div class="controls">
							<input id="file" class="input-small" type="file" name="file" />
						</div>
					</div>
						<p>维护复测率良率数据</p>
					<div class="control-group">
						<div class="controls">
							<button type="button" class="btn" id="flieupload">
								<span class="glyphicon glyphicon-upload"></span>上傳
							</button>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="control-group">
						<div class="controls">
							<input id="devicefile" class="input-small" type="file"
								name="file" />
						</div>
					</div>
					<p>维护StationID数据</p>
					<div class="control-group">
						<div class="controls">
							<button type="button" class="btn" id="devicefileupload">
								<span class="glyphicon glyphicon-upload"></span>上傳
							</button>
						</div>
					</div>
				</div>
			</div>
		</section>
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
	src="${pageContext.request.contextPath}/assets/js/download_tables.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/ajaxfileupload.js"></script>