<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- content main container -->
	<!-- row -->
	<!-- 复测的图表 -->
	<div class="panel-group" id="accordion">
		<div class="panel panel-default">
			<div class="panel-heading panel-backcolor">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-toggle="collapse"
						data-parent="#accordion" href="#collapseOne"> <strong>R/R</strong>
						Rate 
					</a>
					<a href="#" id="lineBackID">返回</a>
				</h4>
			</div>
			<div id="collapseOne" class="panel-collapse collapse in">
				<div class="tile-body nopadding panel-body bg-transparent-white-1"
					id="rrData" style="width: 950px; height: 400px;"></div>
			</div>
		</div>
	</div>
<!-- ／复测的图表 -->
<!-- 良率的图表 -->
	<div class="panel-group" id="accordion1">
		<div class="panel panel-default">
			<div class="panel-heading panel-backcolor">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-toggle="collapse"
						data-parent="#accordion1" href="#collapseOne1"> <strong>Y/R</strong>
						Rate
					</a>
				</h4>
			</div>
			<div id="collapseOne1" class="panel-collapse collapse in">
				<div class="tile-body nopadding panel-body bg-transparent-white-1"
					id="yrData" style="width: 950px; height: 400px;"></div>
			</div>
		</div>
	</div>
<!-- ／良率的图表 -->
<!-- 复测良率的表格 -->
	<div class="panel-group" id="accordion2">
		<div class="panel panel-default">
			<div class="panel-heading panel-backcolor">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-toggle="collapse"
						data-parent="#accordion1" href="#collapseOne2"><strong>Data</strong>
						TableShow </a>
				</h4>
			</div>
			<div id="collapseOne2" class="panel-collapse collapse in">
				<div
					class="tile-body nopadding bg-transparent-white-1 Tables panel-body" style="padding: 10px;"></div>
			</div>
		</div>
	</div>
	<!-- ／复测良率的表格 -->
	<!-- /row -->
	<script src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/js/vendor/nicescroll/jquery.nicescroll.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/vendor/datepicker/bootstrap-datetimepicker.min.js"></script>
	<!--myself -->
	<script src="${pageContext.request.contextPath}/assets/js/jquery-editable-select.js"></script>
	<script type="text/javascript"
		src="http://172.28.136.18/static/echarts-2.2.7/build/dist/echarts-all.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/homeLines.js"></script>
		<script
	src="${pageContext.request.contextPath}/assets/js/tableCreate.js"></script>