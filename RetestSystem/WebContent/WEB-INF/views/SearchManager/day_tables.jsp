<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row pageHead">
	<div class="col-md-12">
		<section class="tile color bg-transparent-white-1">
			<!-- tile header -->
			<div class="tile-header">
				<h4>
					<strong></strong>
				</h4>
				<div class="controls">
					<a href="#" class="refresh" ><i class="fa fa-refresh"></i></a> <a
						href="#" class="remove"><i class="fa fa-times"></i></a>
				</div>
			</div>
			<!-- /tile header -->
			<!-- tile body -->
			<div class="tile-body">
				<div class="form-horizontal">
					<div class="row">
						<div class="form-group col-md-4 bg-transparent-white">
							<label class="col-sm-4 control-label" for="project">Project</label>
							<div class="col-sm-8">
								<select class="form-control editableSelect" id="project">
									<c:forEach items="${pList}" var="p">
										<option value="${p}">${p}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label" for="route">Route</label>
							<div class="col-sm-8">
								<select class="form-control editableSelect" id="route">
									<c:forEach items="${rList}" var="r">
										<option value="${r}">${r}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label" for="line">Line</label>
							<div class="col-sm-8">
								<select class="form-control editableSelect" id="line">
									<c:forEach items="${lList}" var="l">
										<option value="${l}">${l}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label" for="station">Station</label>
							<div class="col-sm-8">
								<select class="editableSelect form-control" id="station">
									<c:forEach items="${sList}" var="s">
										<option value="${s}">${s}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label" for="startTime">StartTime</label>
							<div class="col-sm-8">
								<input type="text"
									class="form-control datepicker input-append date"
									id="startTime" placeholder="startTime"> <span
									class="add-on"> <i data-time-icon='icon-time'
									data-date-icon='icon-calendar'></i>
								</span>
							</div>
						</div>
						<div class="form-group col-md-4">
							<label class="col-sm-4 control-label" for="endTime">EndTime</label>
							<div class="col-sm-8">
								<input type="text"
									class="form-control datepicker input-append date" id="endTime"
									placeholder="endTime"> <span class="add-on"> <i
									data-time-icon='icon-time' data-date-icon='icon-calendar'></i>
								</span>
							</div>
						</div>

						<div class="form-group col-md-4" style="margin-left: 800px;">
							<button type="button" class="btn btn-blue searchByDay">查询</button>
							<button type="button" class="btn btn-greensea downLoadByDay">下载</button>
						</div>
					</div>
				</div>
			</div>
			<!-- /tile body -->
		</section>
	</div>
	<!-- /tile body -->
	<!-- /tile -->
</div>

<!-- col 6 -->
<div id="containerBody">

<!-- 复测的图表 -->
	<div class="panel-group" id="accordion">
		<div class="panel panel-default">
			<div class="panel-heading panel-backcolor">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-toggle="collapse"
						data-parent="#accordion" href="#collapseOne"> <strong>R/R</strong>
						Rate
					</a>
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
					<a data-toggle="collapse"
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
</div>
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
<script src="${pageContext.request.contextPath}/assets/js/home.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/tableCreate.js"></script>
</body>
</html>