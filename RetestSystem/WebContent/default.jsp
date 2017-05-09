<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- content main container -->
	<!-- row -->
	<div class="row">
		<div class="col-md-12">
			<section class="tile color bg-transparent-white-1">
				<!-- tile header -->
				<div class="tile-header">
					<h1>
						<strong>R/R</strong> Rate
					</h1>
					<div class="controls">
						<a href="#" class="refresh"><i class="fa fa-refresh"></i></a> <a
							href="#" class="remove"><i class="fa fa-times"></i></a>
					</div>
				</div>
				<div class="tile-body nopadding" id="rrData"
					style="width: 950px; height: 400px;"></div>
			</section>
			<!-- /tile -->
		</div>
		<div class="col-md-12">
			<section class="tile color bg-transparent-white-1">
				<!-- tile header -->
				<div class="tile-header">
					<h1>
						<strong>Y/R</strong> Rate
					</h1>
					<div class="controls">
						<a href="#" class="refresh"><i class="fa fa-refresh"></i></a> <a
							href="#" class="remove"><i class="fa fa-times"></i></a>
					</div>
				</div>
				<!-- /tile header -->
				<div class="tile-body nopadding" id="yrData"
					style="width: 980px; height: 400px;"></div>
				<!-- /tile body -->
			</section>
			<!-- /tile -->
		</div>
		<!-- col 12 -->
		<div class="col-md-12">
			<!-- tile -->
			<section class="tile color transparent-white">
				<div class="tile-header">
					<h1>
						<strong>Data</strong> TableShow
					</h1>
					<div class="controls">
						<a href="#" class="refresh"><i class="fa fa-refresh"></i></a> <a
							href="#" class="remove"><i class="fa fa-times"></i></a>
					</div>
				</div>
				<!-- tile body -->
				<div class="tile-body nopadding bg-transparent-white-1 Tables">
				</div>
				<!-- /tile footer -->
			</section>
			<!-- /tile -->
		</div>
		<!-- /col 12 -->
	</div>
	<!-- /row -->
	<script src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/js/vendor/nicescroll/jquery.nicescroll.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/vendor/datepicker/bootstrap-datetimepicker.min.js"></script>
	<!--myself -->
	<script src="${pageContext.request.contextPath}/assets/js/jquery-editable-select.js"></script>
	<script type="text/javascript"
		src="http://172.28.136.18/static/echarts-2.2.7/build/dist/echarts-all.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/home.js"></script>
		<script
	src="${pageContext.request.contextPath}/assets/js/tableCreate.js"></script>