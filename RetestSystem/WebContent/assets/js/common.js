$(function(){
//	window.addEventListener('popstate', function(e){
//			console.log(window.location.hash);
//			var day=sessionStorage.getItem("dayContent")
//			var line=sessionStorage.getItem("lineContent")
//			var station=sessionStorage.getItem("stationContent")
//			if(window.location.hash.indexOf("line")>0){
//				$('.main').html(line);return ;
//			}
//			if(window.location.hash.indexOf("station")>0){
//				$('.main').html(station);
//				return ;
//			}
//			if(window.location.hash.indexOf("goDay_tables_jsp")>0||window.location.hash==""){
//				$('.main').html(day);
//				return ;}
//			
//		console.log(this);
//	});
			common.getCurrentUser();
			$('.login_submit').bind('click',function(){
					var data=new Object();	
					data.workId=$('#workId').val();
					data.password=$('#password').val();
					var url='manager/login.spring';
					$.ajax({
						type : "POST",
						url : url,
						data:data,
						success : function(msg) {
							$('.main').html(msg);
						}
					});
	})
	
					
					common.goUrl("manager/goDay_tables_jsp.spring")
				//	 $('.main').load("manager/goDay_tables_jsp.spring");
					$('#indexRedirect').bind('click',function(){
						 common.goUrl("manager/goDay_tables_jsp.spring")
					});
					$('#searchByDay').bind('click',function(){
						 common.goUrl("manager/goDay_tables_jsp.spring")
					});
					$('#searchByShift').bind('click',function(){
						 common.goUrl("manager/goShift_tables_jsp.spring")
				
					});
					$('#searchByHour').bind('click',function(){
						 common.goUrl("manager/goHour_tables_jsp.spring")
				
					});
					$('#usermanager').bind('click',function(){
						 common.goUrl("manager/goUserManager_tables_jsp.spring")
					});
					$('#rolemanager').bind('click',function(){
						 common.goUrl("manager/goRoleManager_tables_jsp.spring");
					});
					$('#permissionmanager').bind('click',function(){
						 common.goUrl("manager/goPermissionManager_tables_jsp.spring");
					});
					$('#download').bind('click',function(){
						common.goUrl("manager/goDownload_tables_jsp.spring")
					});
})

			var common={
					goUrl : function(url) {
						$.ajax({
							type : "POST",
							url : url,
							data:{},
							success : function(msg) {
								$('.main').html(msg);
							}
						});
					},
					getCurrentUser:function(){
						var url="manager/getCurrentUser.spring";
						$.ajax({
							type : "POST",
							url : url,
							data:{},
							success : function(msg) {
								$('#loginFlag_control_Body').text(msg.info);
								if(msg.status){
									$('#loginOut_control_Body').show();
									$('.navbar-collapse-loginBody').remove($('.loginControlBody'));
								}else{
									$('#loginOut_control_Body').hide();
									$('.navbar-collapse-loginBody').append($('.loginControlBody'));
								}
							}
						});
					}
			}
