$(function() {
	$(".breadDay").nextAll().remove();
	if($(".breadIssue").length==0){
			$('.breadcrumb').append("<li class='breadIssue'><a href='#' class='astyleClass'><span	style='color: #808080;'>ISSUES</span></a><i></i>");//
	}
	$('.pageHead').hide();
	homeIssues.setData();
});
var homeIssues = {
	setData : function() {
		// var counts = [ 6, 5, 4, 4, 1 ];
		// var Issues = [
		// 'GPS Carrier to Noise ratio',
		// 'Beidou Carrier to Noise ratio',
		// 'Overall Result WCDMA V4 FBRX Sweep Run (FBRx Cal) for WTR3925
		// Bands',
		// 'Overall Result CDMA XO DC Calibration',
		// 'Overall Result LTE Band 8 V4 Sweep 3 Calculate' ];
		// var StationIDs = [ 'PGPD_F01-4FRF-05A_29_CELL-CAL',
		// 'PGPD_F01-4FRF-05_1_CELL-CAL', 'PGPD_F01-4FRF-05A_40_CELL-CAL',
		// 'PGPD_F01-4FRF-05_10_CELL-CAL', 'PGPD_F01-4FRF-05A_26_CELL-CAL' ];
		var data = new Object();
		data.project = $('#project').val();// 机种
		data.route = $('#route').val();// 节点
		data.shift = $('#shift').val();// 节点
		data.line = $('#line').val();// 线体
		data.station = $('#station').val();// 站位
		data.startTime = $('#startTime').val();// 开始时间
		// if (sessionStorage.getItem("endTime") == null)
		// data.endTime = $('#endTime').val();// 结束时间
		// else
		data.endTime = sessionStorage.getItem("endTime")// 结束时间
		data.day = sessionStorage.getItem("day");// horizontalData
		data.SearchMark = sessionStorage.getItem("SearchMark");// 是否为2为day
		/**
		 * 从sessionStorage中直接获取，只有在从首页直接到station页面的时候可以更改isStation，
		 * 在从line页面跳转之后直接变成false，不管isStation是否为true 问：如果返回时怎么确定isStation的值？？？
		 */
		var url = "search/IssuePageInitialQuery.spring";
		$.post(url, data, function(res) {
			var concentrateByRetest = res.concentrateByRetest;
			var concentrateByFail = res.concentrateByFail;
			var topIssueByFail = res.topIssueByFail;
			var topIssueByRetest = res.topIssueByRetest;
			//第一个图：top5 Retest 集中站位
				var counts=[];
				var issues=[];
				var list=concentrateByRetest.list;
				var count =concentrateByRetest.count;
				for(var i in list){
					var info=list[i];
					counts.push(info[1]);
					issues.push(info[0]);
				}
				homeIssues.showPieCharts(issues, homeIssues.pieChartData(
						counts, issues), 'ConcentrateByRetest',count,'top5');
				if(issues.length==0){
					$('#collapseOne1').removeAttr("class");
					$('#collapseOne1').attr({ class: "panel-collapse collapse"});
				}else{
					$('#collapseOne1').removeAttr("class");
					$('#collapseOne1').attr({ class: "panel-collapse collapse in"});
				}
				//第二个图：top5 Fail 集中站位
				 counts=[];
				 issues=[];
				list=concentrateByFail.list;
				count =concentrateByFail.count;
				for(var i in list){
					var info=list[i];
					counts.push(info[1]);
					issues.push(info[0]);
				}
				homeIssues.showPieCharts(issues, homeIssues.pieChartData(
						counts, issues), 'ConcentrateByFail',count,'top5');
				if(issues.length==0){
					$('#collapseOne').removeAttr("class");
					$('#collapseOne').attr({ class: "panel-collapse collapse"});
				}
				else{
					$('#collapseOne').removeAttr("class");
					$('#collapseOne').attr({ class: "panel-collapse collapse in"});
				}
			//第三个图：top5 Fail issue 
				 counts=[];
				 issues=[];
			 list=topIssueByFail.list;
			 count =topIssueByFail.count;
				for(var i in list){
					var info=list[i];
					counts.push(info[1]);
					issues.push(info[0]);
				}
				homeIssues.showPieCharts(issues, homeIssues.pieChartData(
						counts, issues), 'TopIssueByFail',count,'issue');
				if(issues.length==0){
					$('#collapseOne2').removeAttr("class");
					$('#collapseOne2').attr({ class: "panel-collapse collapse"});
				}
				else{
					$('#collapseOne2').removeAttr("class");
					$('#collapseOne2').attr({ class: "panel-collapse collapse in"});
				}
				//第四个图：top5 Retest  issue 
				 counts=[];
				 issues=[];
				 list=topIssueByRetest.list;
				 count =topIssueByRetest.count;
				for(var i in list){
					var info=list[i];
					counts.push(info[1]);
					issues.push(info[0]);
				}
				homeIssues.showPieCharts(issues, homeIssues.pieChartData(
						counts, issues), 'TopIssueByRetest',count,'issue');
				if(issues.length==0){
					$('#collapseOne3').removeAttr("class");
					$('#collapseOne3').attr({ class: "panel-collapse collapse"});
				}
				else{
					$('#collapseOne3').removeAttr("class");
					$('#collapseOne3').attr({ class: "panel-collapse collapse in"});
				}
		},'json')
	},
	pieChartData : function(values, names) {
		var data = [];
		for ( var i in values) {
			var obj = new Object();
			obj['value'] = values[i];
			obj['name'] = names[i];
			data.push(obj);
		}
		return data;
	},
	showPieCharts : function(datas, names, contentId,count,flag) {
		var myChart = echarts.init(document.getElementById(contentId));
		option = {
			tooltip : {
				trigger : 'item',
//				trigger : 'axis',
//				formatter : "{a} <br/>{b} : {c} ({d}%)",
				showContent:true,
				showDelay : 0,
				hideDelay : 50,
				transitionDuration : 0,
				borderRadius : 8,
				borderWidth : 2,
				padding : 10, // [5, 10, 15, 20]
				position : function(p) {
					return [ p[0] + 10, p[1] - 10 ];
				},
				textStyle : {
					decoration : 'none',
					fontFamily : 'Verdana, sans-serif',
					fontSize : 15,
				},
				formatter : function(params, ticket, callback) {
					var res="";
					if(flag=='top5'){
							for ( var i in params) {
								var values=names[i];
								if(values!=undefined){
									var valueData=values.value;
									var nameData=values.name;
										res+="</br>集中STATION:   "+nameData+
										";</br>数量:   "+valueData+";  总占比:  "+(valueData/count*100).toFixed(2)+'%'+'</br>';
								}
							}
					}
					if(flag=='issue'){
						for ( var i in params) {
							var values=names[i];
							if(values!=undefined){
								var valueData=values.value;
								var nameData=values.name;
									res+="</br>集中ISSUE:   "+nameData+
									";</br>数量:   "+valueData+";  总占比:  "+(valueData/count*100).toFixed(2)+'%'+';</br>';
							}
						}
					}
					setTimeout(function() {
						// 仅为了模拟异步回调
						callback(ticket, res);
					}, 0)
				}
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : datas
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
						type : [ 'pie', 'funnel' ],
						option : {
							funnel : {
								x : '25%',
								width : '50%',
								funnelAlign : 'left',
								max : 1548
							}
						}
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			series : [ {
				name : function(flag){
					if(flag=='top5'){
						return '集中STATION';
					}
					if(flag='issue'){
						return '集中ISSUE';
					}
				},
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : names
			} ]
		};
		myChart.setOption(option);
	}
}
