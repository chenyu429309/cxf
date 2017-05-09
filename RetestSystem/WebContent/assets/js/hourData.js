$(function() {
	$(".breadHome").nextAll().remove();
	$('.breadcrumb').append("<li class='breadDay'><a href='#' class='astyleClass'><span	style='color: #808080;'>ByHour</span></a><i></i>");//
	$('.pageHead').show();
	hour.showData();
	// initialize datepicker
	$('.datepicker').datetimepicker({
		// language : 'zh-CN',
		pickDate : true,
		pickTime : true,
		hourStep : 15,
		minuteStep : 15,
		secondStep : 30,
		inputMask : true,
		autoclose : true,
		minView : 'hour',
		icons : {
			time : "fa fa-clock-o",
			date : "fa fa-calendar",
			up : "fa fa-arrow-up",
			down : "fa fa-arrow-down"
		}
	});
	$(".datepicker").on("dp.show", function(e) {
		var newtop = $('.bootstrap-datetimepicker-widget').position().top - 45;
		$('.bootstrap-datetimepicker-widget').css('top', newtop + 'px');
	});
	$('.editableSelect').editableSelect({
		effects : 'slide',
		filter : true
	});
	$('.searchByDay').bind(
			'click',
			function() {
				var url = "search/getRateByDay.spring";
				if ($('#project').val() == "" || $('#project').val() == null) {
					alert("请先选择project");
					return false;
				}
				if ($('#route').val() == "" || $('#route').val() == null) {
					alert("请先选择route");
					return false;
				}
				if ($('#startTime').val() == null
						|| $('#startTime').val() == "") {
					alert("请先选择startTime");
					return false;
				}
				if ($('#endTime').val() == null || $('#endTime').val() == "") {
					alert("请先选择endTime");
					return false;
				}
				// 保存所有的选项
				/**
				 * 对于已有的就保存已有的数据到sessionStorage中，如果不存在的就设置为null，免得起冲突
				 * 有且仅在保存到sessionStorage时需要这样设置，假如是各个选择条件的转换，则不需要，如果涉及到页面的
				 * 返回则需要这样——可考虑js缓存
				 */
				var data = new Object();
				sessionStorage.clear();
				data.project = $('#project').val();// 机种
				data.route = $('#route').val();// 节点
				data.line = $('#line').val();// 线体
				data.station = $('#station').val();// 站位
				data.startTime = $('#startTime').val();// 开始时间
				data.endTime = $('#endTime').val();// 结束时间
				data.page = 1;
				data.SearchMark = 0;// 是否为2为day
				/**
				 * 上面是已有的数据，下面是没有的数据，但是还是需要将其设为null
				 */
				$.post(url, data, function(res) {
					// 重绘参数值
					$('#project').val(data.project);
					$('#route').val(data.route);
					$('#line').val(data.line);
					$('#station').val(data.station);
					$('#startTime').val(data.startTime);
					$('#endTime').val(data.endTime);
					var dayRateList = res.dayRateList;
					if (dayRateList != null) {
						if (dayRateList.horizontalDatas.length >= 1) {
							var inputDatas=[];
							var rrdatasDatas=[];
							var rrdatatargetsDatas=[];
							var horizontals=[];
							var yrdataDatas=[];
							var yrdatatargetDatas=[];
							for(var i in dayRateList.inputs){
								if(dayRateList.inputs[i]!=0){
									inputDatas.push(dayRateList.inputs[i]);
									rrdatasDatas.push(dayRateList.rrdatas[i]);
									rrdatatargetsDatas.push(dayRateList.rrdatatargets[i]);
									horizontals.push(dayRateList.horizontalDatas[i]);
									yrdataDatas.push(dayRateList.yrdatas[i]);
									yrdatatargetDatas.push(dayRateList.yrdatatargets[i]);
								}
							}
							//判断数据是否为空，如果为空则将图表关闭
							//第一个判断复测率的图表
							if(rrdatasDatas.length==0){
								$('#collapseOne').removeAttr("class");
								$('#collapseOne').attr({ class: "panel-collapse collapse"});
							}else{
								$('#collapseOne').removeAttr("class");
								$('#collapseOne').attr({ class: "panel-collapse collapse in"});
							}
							//第一个判断良率的图表
							if(yrdataDatas.length==0){
								$('#collapseOne1').removeAttr("class");
								$('#collapseOne1').attr({ class: "panel-collapse collapse"});
							}else{
								$('#collapseOne1').removeAttr("class");
								$('#collapseOne1').attr({ class: "panel-collapse collapse in"});
							}
							hour.showRrData(inputDatas,
									rrdatasDatas, rrdatatargetsDatas,
									horizontals,
									dayRateList.rrDataContent);
							hour.showYrData(inputDatas,
									yrdataDatas, yrdatatargetDatas,
									horizontals,
									dayRateList.yrDataContent);
							hour.showTable();
						}
					}
					else{
						$('#collapseOne').removeAttr("class");
						$('#collapseOne').attr({ class: "panel-collapse collapse"});
						$('#collapseOne1').removeAttr("class");
						$('#collapseOne1').attr({ class: "panel-collapse collapse"});
					}
				}, 'json');
			});
	$('.downLoadByHour').bind('click', function() {
		if ($('#project').val() == "" || $('#project').val() == null) {
			alert("请先选择project");
			return false;
		}
		if ($('#route').val() == "" || $('#route').val() == null) {
			alert("请先选择route");
			return false;
		}
			var data=new Object();
			data.project=$("#project").val();
			data.route=$("#route").val();
			data.station=$("#station").val();
			data.line=$("#line").val();
			data.startTime=$("#startTime").val();
			data.endTime=$("#endTime").val();
				var url='search/downLoadByHour.spring?station='
					+data.station + '&project='
					+ data.project + '&route='
					+ data.route + '&startTime='
					+ home.dateForMatBySS(new Date(data.startTime)) + '&endTime='
					+ home.dateForMatBySS(new Date(data.endTime))+'&line='
					+data.line;
				window.location.href = url;
			});
})
var hour = {
	dateForMat:function(d){//
		return d.getFullYear()+//
					"-"+((d.getMonth()+1)<10?"0"+(d.getMonth()+1):(d.getMonth()+1))+//
							"-"+(d.getDate()<10?"0"+d.getDate():d.getDate());//
	},
	dateForMatBySS:function(d){//
		return d.getFullYear()+//
					"-"+((d.getMonth()+1)<10?"0"+(d.getMonth()+1):(d.getMonth()+1))+//
					"-"+d.getDate()<10?"0"+d.getDate():d.getDate()+//
					" "+d.getHours()<10?"0"+d.getHours():d.getHours()+//
					":"+d.getMinutes()<10?"0"+d.getMinutes():d.getMinutes()+//
					":"+d.getSeconds()<10?"0"+d.getSeconds():d.getSeconds()//
					;
	},
	showTable : function() {
		sessionStorage.clear();
		var data = new Object();
		data.project = $('#project').val();
		data.route = $('#route').val();
		data.line = $('#line').val();
		data.station = $('#station').val();
		data.startTime = $('#startTime').val();
		data.endTime = $('#endTime').val();
		data.SearchMark = 0;
		sessionStorage.setItem("SearchMark", 0)
		var url = "search/getTableData.spring";
		$.post(url, data, function(res) {
			var heads = res.heads;
			var pageList = res.pageList;
			var html = tableCreate.create(heads, pageList);
			var contentName = ".Tables";
			$('' + contentName).html(html);
			tableCreate.pageUtils("search/getTableData.spring", data);
		}, 'json');
	},
	// 显示信息 当加载该页面时自动加载这个js方法
	showData : function() {
		var url = "search/getRateByDay.spring";
		var data = new Object();
		sessionStorage.clear();
		data.project = $('#project').val();
		data.route = $('#route').val();
		data.line = $('#line').val();
		data.station = $('#station').val();
		data.startTime = new Date(new Date().getTime() - 1000 * 60 * 60 * 24
				* 7);
		data.endTime = new Date();
		data.SearchMark = 0;
		sessionStorage.setItem("SearchMark", 0)
		data.page = 1;
		$.post(url, data, function(res) {
			// 重绘参数值
			$('#project').val(data.project);
			$('#route').val(data.route);
			$('#line').val(data.line);
			$('#station').val(data.station);
			$('#startTime').val(data.startTime);
			$('#endTime').val(data.endTime);
			var dayRateList = res.dayRateList;
			if (dayRateList != null) {
				// 设置表格参数值
				if (dayRateList.horizontalDatas.length >= 1) {
					var inputDatas=[];
					var rrdatasDatas=[];
					var rrdatatargetsDatas=[];
					var horizontals=[];
					var yrdataDatas=[];
					var yrdatatargetDatas=[];
					for(var i in dayRateList.inputs){
						if(dayRateList.inputs[i]!=0){
							inputDatas.push(dayRateList.inputs[i]);
							rrdatasDatas.push(dayRateList.rrdatas[i]);
							rrdatatargetsDatas.push(dayRateList.rrdatatargets[i]);
							horizontals.push(dayRateList.horizontalDatas[i]);
							yrdataDatas.push(dayRateList.yrdatas[i]);
							yrdatatargetDatas.push(dayRateList.yrdatatargets[i]);
						}
					}
					//判断数据是否为空，如果为空则将图表关闭
					//第一个判断复测率的图表
					if(rrdatasDatas.length==0){
						$('#collapseOne').removeAttr("class");
						$('#collapseOne').attr({ class: "panel-collapse collapse"});
					}else{
						$('#collapseOne').removeAttr("class");
						$('#collapseOne').attr({ class: "panel-collapse collapse in"});
					}
					//第一个判断良率的图表
					if(yrdataDatas.length==0){
						$('#collapseOne1').removeAttr("class");
						$('#collapseOne1').attr({ class: "panel-collapse collapse"});
					}else{
						$('#collapseOne1').removeAttr("class");
						$('#collapseOne1').attr({ class: "panel-collapse collapse in"});
					}
					hour.showRrData(inputDatas,
							rrdatasDatas, rrdatatargetsDatas,
							horizontals,
							dayRateList.rrDataContent);
					hour.showYrData(inputDatas,
							yrdataDatas, yrdatatargetDatas,
							horizontals,
							dayRateList.yrDataContent);
					hour.showTable();
				}
			}
			else{
				$('#collapseOne').removeAttr("class");
				$('#collapseOne').attr({ class: "panel-collapse collapse"});
				$('#collapseOne1').removeAttr("class");
				$('#collapseOne1').attr({ class: "panel-collapse collapse"});
			}
		}, 'json');
	},
	showRrData : function(inputs, rrdatas, rrdatatargets, days, contentId) {
		var myChart = echarts.init(document.getElementById(contentId));
		option = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ 'Input', 'R/R data', 'R/R target' ]
			},
			toolbox : {
				show : true,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			dataZoom: {
				 show: true,
			        start : 0,
			        end : 100
		    },
			xAxis : [ {
				type : 'category',
				data : days,
				axisLabel : {
					show : true,
					interval : 'auto',
					rotate : -30,
					magin : 8,
					textStyle : {
						fontFamily : '微软雅黑',
						fontWeight : 'bolder'
					}
				},

			} ],
			yAxis : [ {
				type : 'value',
				scale : true,
				name : 'Input',
				axisLabel : {
					formatter : '{value}'
				},
				boundaryGap : [ 0, 0.2 ]
			}, {
				type : 'value',
				scale : true,
				name : 'R/R data',
				axisLabel : {
					formatter : '{value}%'
				},
				min:0,
				boundaryGap : [ 0, 0.2 ]
			} ],
			calculable : true,
			grid : {
				x : 40,
				x2 : 100,
				y2 : 150
			},
			series : [
					{
						name : 'Input',
						type : 'bar',
						yAxisIndex : 0,
						barMaxWidth:30,
						barMinHeight:10,
						data : inputs,
						itemStyle : {
							normal : {
								color : function(params) {
									if (params.dataIndex > -1) {
										if (Number(rrdatas[params.dataIndex]) > Number(rrdatatargets[params.dataIndex])) {
											return 'rgb(221,37,11)';
										} else {
											return 'rgb(27,137,216)';
										}
									}
								},
								label : {
									show : true,
									position : 'insideTop',
									textStyle : {
										color : 'rgba(0,0,0,0.8)',
										align : 'center',
										fontWeight : 'bolder'
									}
								}
							}
						}
					}, {
						name : 'R/R data',
						type : 'line',
						yAxisIndex : 1,
						data : rrdatas,
						itemStyle : {
							normal : {
								label : {
									show : true,
									position : 'inside',
									formatter : '{c}%',
									textStyle : {
										align : 'center',
									}
								}
							}
						}
					}, {
						name : 'R/R target',
						type : 'line',
						yAxisIndex : 1,
						data : rrdatatargets,
					} ]
		};
		myChart.setOption(option);
		myChart.on('click', function(param) {
			hour.charClickEvent(param);
		});
	},
	showYrData : function(inputs, yrdatas, yrdatatargets, days, contentId) {
		var myChart = echarts.init(document.getElementById(contentId));
		option = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ 'Input', 'Y/R data', 'Y/R target' ]
			},
			toolbox : {
				show : true,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			dataZoom: {
				 show: true,
			        start : 0,
			        end : 100
		    },
			xAxis : [ {
				type : 'category',
				data : days,
				axisLabel : {
					show : true,
					interval : 'auto',
					rotate : -30,
					magin : 8,
					textStyle : {
						fontFamily : '微软雅黑',
						fontWeight : 'bolder'
					}
				},
			} ],
			yAxis : [ {
				type : 'value',
				scale : true,
				name : 'Input',
				axisLabel : {
					formatter : '{value}'
				},
				boundaryGap : [ 0, 0.2 ]
			}, {
				type : 'value',
				scale : true,
				name : 'Y/R data',
				axisLabel : {
					formatter : '{value}%'
				},
				min:98,
				boundaryGap : [ 0, 0.2 ]
			} ],
			calculable : true,
			grid : {
				x : 40,
				x2 : 100,
				y2 : 150
			},																													
			series : [
					{
						name : 'Input',
						type : 'bar',
						yAxisIndex : 0,
						barMaxWidth:30,
						barMinHeight:10,
						data : inputs,
						itemStyle : {
							normal : {
								color : function(params) {
									if (params.dataIndex > -1) {
										if (Number(yrdatas[params.dataIndex]) < Number(yrdatatargets[params.dataIndex])) {
											return 'rgb(221,37,11)';
										} else {
											return 'rgb(27,137,216)';
										}
									}
								},
								label : {
									show : true,
									position : 'insideTop',
									textStyle : {
										color : 'rgba(0,0,0,0.8)',
										align : 'center',
										fontWeight : 'bolder'
									}
								}
							}
						}
					}, {
						name : 'Y/R data',
						type : 'line',
						yAxisIndex : 1,
						data : yrdatas,
						itemStyle : {
							normal : {
								label : {
									show : true,
									position : 'inside',
									formatter : '{c}%',
									textStyle : {
										align : 'center',
									}
								}
							}
						}
					}, {
						name : 'Y/R target',
						type : 'line',
						yAxisIndex : 1,
						data : yrdatatargets,
					} ]
		};
		myChart.setOption(option);
		myChart.on('click', function(param) {
			hour.charClickEvent(param);
		});
	},
	charClickEvent : function(param) {
		var data = new Object();
		data.project = $("#project").val();
		data.route = $("#route").val();
		data.line = $("#line").val();
		data.station = $("#station").val();
		// 因为在进入页面的时候就已经将这些数据存入到SearchMark
		data.SearchMark = sessionStorage.getItem("SearchMark");
		// 在这里获取横轴的坐标，如果是day就直接将数据传到后台 横轴的数据在后面会用到，因为这个数据
		// 本身就是不存在的，只是因为选择了它 在后面的页面中他是一个很好的参数
		data.endTime = param.name;
		sessionStorage.setItem("endTime", param.name);
		sessionStorage.setItem("day", param.name.substring(0,10));
		var url = "";
		if (data.line != "" && data.station != "") {
			url = "search/dayToIssuesPages.spring";
		} else if (data.line == "" &&  data.station != "") {
			url = "search/dayToLinePages.spring";
		} else if (data.line != ""  &&   data.station == "") {
			url = "search/dayToStationPages.spring";
		} else {
			url = "search/dayToLinePages.spring";
		}
		;
		$.post(url, {}, function(res) {
			$('#containerBody').html(res);
		}, 'html');
	}
}
