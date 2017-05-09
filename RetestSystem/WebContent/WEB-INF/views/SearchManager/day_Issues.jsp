<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- row -->
<!-- 复测的集中站位的饼图 -->
<div class="panel-group" id="accordion">
	<div class="panel panel-default">
		<div class="panel-heading panel-backcolor">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-toggle="collapse"
					data-parent="#accordion" href="#collapseOne"> <strong>集中站位
						(Fail)</strong>
				</a>
			</h4>
		</div>
		<div id="collapseOne" class="panel-collapse collapse in">
			<div class="tile-body nopadding panel-body bg-transparent-white-1"
				id="ConcentrateByFail"
				style="width: 980px; height: 500px; left: 100px;"></div>
		</div>
	</div>
</div>
<!-- ／复测的集中站位的饼图 -->
<!-- 失败的集中站位的饼图 -->
<div class="panel-group" id="accordion1">
	<div class="panel panel-default">
		<div class="panel-heading panel-backcolor" >
			<h4 class="panel-title">
				<a data-toggle="collapse" data-toggle="collapse"
					data-parent="#accordion1" href="#collapseOne1"> <strong>集中站位
						(Retest)</strong>
				</a>
			</h4>
		</div>
		<div id="collapseOne1" class="panel-collapse collapse in">
			<div class="tile-body nopadding panel-body bg-transparent-white-1"
				id="ConcentrateByRetest"
				style="width: 980px; height: 500px; left: 100px;"></div>
		</div>
	</div>
</div>
<!-- ／失败的集中站位的饼图 -->


<!-- 复测的TOPISSURE的饼图 -->
<div class="panel-group" id="accordion2">
	<div class="panel panel-default">
		<div class="panel-heading panel-backcolor">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-toggle="collapse"
					data-parent="#accordion2" href="#collapseOne2"> <strong>Top5
						issues（Fail）</strong>
				</a>
			</h4>
		</div>
		<div id="collapseOne2" class="panel-collapse collapse in">
			<div class="tile-body nopadding panel-body bg-transparent-white-1"
				id="TopIssueByFail" style="width: 980px; height: 500px; left: 100px;"></div>
		</div>
	</div>
</div>
<!-- ／复测的TOPISSURE的饼图 -->
<!-- 失败的TOPISSURE的饼图 -->
<div class="panel-group" id="accordion3">
	<div class="panel panel-default ">
		<div class="panel-heading panel-backcolor">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-toggle="collapse"
					data-parent="#accordion3" href="#collapseOne3"> <strong>Top5
						issues (Retest)</strong>
				</a>
			</h4>
		</div>
		<div id="collapseOne3" cla
		ss="panel-collapse collapse in">
			<div class="tile-body nopadding panel-body bg-transparent-white-1"
				id="TopIssueByRetest"
				style="width: 980px; height: 500px; left: 100px;"></div>
		</div>
	</div>
</div>
<!-- ／失败的TOPISSURE的饼图 -->
<!-- /row -->
<script src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/vendor/nicescroll/jquery.nicescroll.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/vendor/datepicker/bootstrap-datetimepicker.min.js"></script>
<!--myself -->
<script
	src="${pageContext.request.contextPath}/assets/js/jquery-editable-select.js"></script>
<script type="text/javascript"
	src="http://172.28.136.18/static/echarts-2.2.7/build/dist/echarts-all.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/homeIssues.js"></script>
