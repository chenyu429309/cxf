$(function() {
	$(".breadDay").nextAll().remove();
	if($(".breadStation").length==0){
			$('.breadcrumb').append("<li class='breadStation'><a href='#' class='astyleClass'><span	style='color: #808080;'>STATION</span></a><i></i>");//
	}
		$('.pageHead').hide();
	homeStations.setData();
});
var homeStations = {
	setData : function() {
		var data = new Object();
		data.project = $('#project').val();// 机种
		data.route = $('#route').val();// 节点
		data.shift = $('#shift').val();// 节点
		data.line = $('#line').val();// 线体
		data.station = $('#station').val();// 站位
		data.startTime = $('#startTime').val();// 开始时间
		if(sessionStorage.getItem("endTime")==null)
			data.endTime = $('#endTime').val();// 结束时间
			else
			data.endTime =sessionStorage.getItem("endTime")// 结束时间
		data.day = sessionStorage.getItem("day");// horizontalData
		data.page = 1;
		data.SearchMark = sessionStorage.getItem("SearchMark");// 是否为2为day
		/**
		 * 从sessionStorage中直接获取，只有在从首页直接到station页面的时候可以更改isStation，
		 * 在从line页面跳转之后直接变成false，不管isStation是否为true 问：如果返回时怎么确定isStation的值？？？
		 */
		var url = "search/stationPageInitialChartsANDtables.spring";
		$.post(url, data, function(res) {
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
					homeStations.showRrData(inputDatas,
							rrdatasDatas, rrdatatargetsDatas,
							horizontals,
							dayRateList.rrDataContent);
					homeStations.showYrData(inputDatas,
							yrdataDatas, yrdatatargetDatas,
							horizontals,
							dayRateList.yrDataContent);
					
					homeStations.setTables();
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
	setTables:function(){
		var data = new Object();
		data.project = $('#project').val();// 机种
		data.route = $('#route').val();// 节点
		data.shift = $('#shift').val();// 节点
		data.line = $('#line').val();// 线体
		data.station = $('#station').val();// 站位
		data.startTime = $('#startTime').val();// 开始时间
		if(sessionStorage.getItem("endTime")==null)
			data.endTime = $('#endTime').val();// 结束时间
			else
			data.endTime =sessionStorage.getItem("endTime")// 结束时间
		data.day=sessionStorage.getItem("day");//horizontalData
		data.page = 1;
		data.SearchMark = sessionStorage.getItem("SearchMark");// 是否为2为day
		var url = "search/stationPageInitialTables.spring";
		$.post(url, data, function(res) {
			var heads = res.heads;
			var pageList = res.pageList;
			var html = tableCreate.create(heads, pageList);
			var contentName=".Tables";
			$(''+contentName).html(html);
			tableCreate.pageUtils(url, data,contentName);
		},'json');
	},
	charClickEvent : function(param) {
		var data = new Object();
		data.project = $("#project").val();
		data.route = $("#route").val();
		data.shift = $("#shift").val();
		//只有当为小时时，才会有e_day的出现
		data.endTime = sessionStorage.getItem("endTime");
		data.line = $("#line").val();
		//data.station = $("#station").val();
		//因为在进入页面的时候就已经将这些数据存入到SearchMark
		data.SearchMark = sessionStorage.getItem("SearchMark");
		data.day = sessionStorage.getItem("day");
		// 在这里获取横轴的坐标，如果是day就直接将数据传到后台 横轴的数据在后面会用到，因为这个数据
		//本身就是不存在的，只是因为选择了它 在后面的页面中他是一个很好的参数
		data.station = param.name;
		$('#station').val(data.station);
		var url = "";
		if (data.line!="") {
			url = "search/dayToIssuesPages.spring";
		} else {
			url = "search/dayToLinePages.spring";
		}
		$.post(url, {}, function(res) {
			$('#containerBody').html(res);
		}, 'html');
		
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
			xAxis : [ {
				type : 'category',
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
				data : days
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
				x : 200,
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
						barBorderRadius:30,
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
									show : false,
									position : 'insideTop',
									textStyle : {
										color : 'rgba(0,0,0,0.8)',
										align : 'center',
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
									formatter : function(p){
										if(p.value==0.00||p.value==100.00){
											return "";
										}else
											 return p.value+'%';;
									},
									textStyle : {
										color : 'rgba(0,0,0,0.8)',
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
			 homeStations.charClickEvent(param);
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
			xAxis : [ {
				type : 'category',
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
				data : days
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
				name : 'Y/R Rate',
				axisLabel : {
					formatter : '{value}%'
				},
				min:98,
				boundaryGap : [ 0, 0.2 ]
			} ],
			calculable : true,
			grid : {
				x : 200,
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
						barBorderRadius:30,
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
									show : false,
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
						name : 'Y/R Rate',
						type : 'line',
						yAxisIndex : 1,
						data : yrdatas,
						itemStyle : {
							normal : {
								label : {
									show : true,
									position : 'inside',
									formatter :function(p){
										if(p.value==0.00||p.value==100.00){
											return "";
										}else
											 return p.value+'%';;
									},
									textStyle : {
										color : 'rgba(0,0,0,0.8)',
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
			 homeStations.charClickEvent(param);
//			$('.main').load("day_Issues.html");
		});
	}
}
