$(function() {
	var lines = [ '2017/1/17', '2017/1/19', '2017/1/24', '2017/1/27',
			'2017/2/17', '2017/2/20', '2017/3/17', '2017/4/1' ];
	var data1=[ 80, 0, 0, 0, 170 ];
	var data2=[ 0, 0, 190, 0, 0];
	var data3=[ 0, 60, 0, 0, 0];
	var data4=[ 100, 0, 0, 0, 0 ];
	var data5= [ -50, 0, 50, 0, 0 ];
	var data6=[ 0, 0, 0, 0, 120 ];
	var data7=[ -150, 0, 0, 0, 0];
	var data8=[ 0, 0, 0, 80, 0];
	charts.showCharts(lines,data1,data2,data3,data4,data5,data6,data7,data8);
	 $('.post-module').hover(function () {
	        $(this).find('.description').stop().animate({
	            height: 'toggle',
	            opacity: 'toggle'
	        }, 300);
	    });
})
var charts = {
	showCharts : function(lines,data1,data2,data3,data4,data5,data6,data7,data8) {
		var myChart = echarts.init(document.getElementById("charts"));
		option = {
			tooltip : {
				trigger : 'axis',
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
					var str = params[0].name;
					//[ 'PG01', 'PG02', 'PG06', 'PG08', 'PG10' ]
					var stations={'PG01':180, 'PG02':180,'PG06':180,'PG08':180,'PG10' :180};
//					var stations={'PG01':"密封圈", 'PG02':"密封圈",'PG06':"密封圈",'PG08':"密封圈",'PG10' :"密封圈"};
					var res = "密封圈:";
					var lines = [ '2017/1/17', '2017/1/19', '2017/1/24', '2017/1/27',
					  			'2017/2/17', '2017/2/20', '2017/3/17', '2017/4/1' ];
					for ( var i in params) {
						if(params[i].value!=0){
							res+="</br>相隔天数:"+params[i].value+
							";周期:"+stations[str]+";更换日期:"+lines[i];
							;
						}
					}
					setTimeout(function() {
						// 仅为了模拟异步回调
						callback(ticket, res);
					}, 0)
				}
			},
			legend : {
				data : lines
			},
			toolbox : {
				show : true,
				feature : {
					saveAsImage : {
						show : true
					},
				}
			},
			calculable : true,
			yAxis : [ {
				type : 'value'
			} ],
			xAxis : [ {
				type : 'category',
				data : [ 'PG01', 'PG02', 'PG06', 'PG08', 'PG10' ]
			} ],
			series : [ {
				name : '2017/1/17',
				type : 'bar',
				barWidth:50,
				stack : '总量',
				legendHoverLink:true,
				itemStyle : {
					normal : {
						barBorderRadius:5,
						barBorderWidth:2,
						color : function(params) {
							if (params.dataIndex > -1) {
								if (Number(data1[params.dataIndex]) >=-180&&Number(data1[params.dataIndex]) <=180) {
									return 'rgb(27,137,216)';
								} else {
									return 'rgb(255,0,0)';
								}
							}},
						label : {
							show : function(p) {
								p.value != 0 ? true : false
							},
							position : 'inside'
								,textStyle : {
									color : 'rgb(0,0,0)',
									align : 'center',
									fontWeight : 'bolder'
								}
						}
					}
				},
				data : data1
			}, {
				name : '2017/1/19',
				type : 'bar',
				stack : '总量',
				barWidth:50,
				itemStyle : {
					normal : {
						barBorderRadius:5,
						barBorderWidth:2,
						color : function(params) {
							
							if (params.dataIndex > -1) {
								if (Number(data2[params.dataIndex]) >=-180&&Number(data2[params.dataIndex]) <=180) {
									return 'rgb(27,137,216)';
								} else {
									return 'rgb(255,0,0)';
								}
							}},
						label : {
							show : true,
						formatter:function(p){return p.value != 0 ? p.value :''},
							position : 'inside'
								,textStyle : {
									color : 'rgb(0,0,0)',
									align : 'center',
									fontWeight : 'bolder'
								}
						}
					}
				},
				data : data2
			}, {
				name : '2017/1/24',
				type : 'bar',
				barWidth:50,
				stack : '总量',
				itemStyle : {
					normal : {
						barBorderRadius:5,
						barBorderWidth:2,
						color : function(params) {
							
							if (params.dataIndex > -1) {
								if (Number(data3[params.dataIndex]) >=-180&&Number(data3[params.dataIndex]) <=180) {
									return 'rgb(27,137,216)';
								} else {
									return 'rgb(255,0,0)';
								}
							}},
						label : {
							show : true,
						formatter:function(p){return p.value != 0 ? p.value :''},
							position : 'inside'
								,textStyle : {
									color : 'rgb(0,0,0)',
									align : 'center',
									fontWeight : 'bolder'
								}
						}
					}
				},
				data : data3
			}, {
				name : '2017/1/27',
				type : 'bar',
				stack : '总量',
				barWidth:50,
				itemStyle : {
					normal : {
						barBorderRadius:5,
						barBorderWidth:2,
						color : function(params) {
							
							if (params.dataIndex > -1) {
								if (Number(data4[params.dataIndex]) >=-180&&Number(data4[params.dataIndex]) <=180) {
									return 'rgb(27,137,216)';
								} else {
									return 'rgb(255,0,0)';
								}
							}},
						label : {
							show : true,
						formatter:function(p){return p.value != 0 ? p.value :''},
							position : 'inside'
								,textStyle : {
									color : 'rgb(0,0,0)',
									align : 'center',
									fontWeight : 'bolder'
								}
//							
						}
					}
				},
				data : data4
			}, {
				name : '2017/2/17',
				type : 'bar',
				stack : '总量',
				barWidth:50,
				itemStyle : {
					normal : {
						barBorderRadius:5,
						barBorderWidth:2,
						color : function(params) {
							
							if (params.dataIndex > -1) {
								if (Number(data5[params.dataIndex]) >=-180&&Number(data5[params.dataIndex]) <=180) {
									return 'rgb(27,137,216)';
								} else {
									return 'rgb(255,0,0)';
								}
							}},
						label : {
							show : true,
						formatter:function(p){return p.value != 0 ? p.value :''},
							position : 'inside'
								,textStyle : {
									color : 'rgb(0,0,0)',
									align : 'center',
									fontWeight : 'bolder'
								}
//						
						}
					}
				},
				data :data5
			}, {
				name : '2017/2/20',
				type : 'bar',
				stack : '总量',
				barWidth:50,
				itemStyle : {
					normal : {
						barBorderRadius:5,
						barBorderWidth:2,
						color : function(params) {
							
							if (params.dataIndex > -1) {
								if (Number(data6[params.dataIndex]) >=-180&&Number(data6[params.dataIndex]) <=180) {
									return 'rgb(27,137,216)';
								} else {
									return 'rgb(255,0,0)';
								}
							}},
						label : {
							show : true,
						formatter:function(p){return p.value != 0 ? p.value :''},
							position : 'inside'
								,textStyle : {
									color : 'rgb(0,0,0)',
									align : 'center',
									fontWeight : 'bolder'
								}
//						
						}
					}
				},
				data : data6
			}, {
				name : '2017/3/17',
				type : 'bar',
				stack : '总量',
				barWidth:50,
				itemStyle : {
					normal : {
						barBorderRadius:5,
						barBorderWidth:2,
						color : function(params) {
							
							if (params.dataIndex > -1) {
								if (Number(data7[params.dataIndex]) >=-180&&Number(data7[params.dataIndex]) <=180) {
									return 'rgb(27,137,216)';
								} else {
									return 'rgb(255,0,0)';
								}
							}},
						label : {
							show : true,
						formatter:function(p){return p.value != 0 ? p.value :''},
							position : 'inside'
								,textStyle : {
									color : 'rgb(0,0,0)',
									align : 'center',
									fontWeight : 'bolder'
								}
						}
					}
				},
				data : data7
			}, {
				name : '2017/4/1',
				type : 'bar',
				stack : '总量',
				barWidth:50,
				itemStyle : {
					normal : {
						barBorderRadius:5,
						barBorderWidth:2,
						color : function(params) {
							if (params.dataIndex > -1) {
								if (Number(data8[params.dataIndex]) >=-180&&Number(data8[params.dataIndex]) <=180) {
									return 'rgb(27,137,216)';
								} else {
									return 'rgb(255,0,0)';
								}
							}
//							
//							Number(params.value)>= 0 && Number(params.value) <= 180 ? 'black' : 'red'
						},
						label : {
							show : true,
							formatter:function(p){return p.value != 0 ? p.value :''},
							position : 'inside'
								,textStyle : {
									color : 'rgb(0,0,0)',
									align : 'center',
									fontWeight : 'bolder'
								}
						}
					}
				},
				data : data8
			} ]
		};
		myChart.setOption(option);
	}
}
