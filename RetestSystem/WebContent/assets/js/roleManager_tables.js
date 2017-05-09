$(function(){
	//分页属性绑定点击事件
	$('#firstPage').bind('click',function(){
		var url="manager/goRoleManager_tables_jsp.spring";
		var data=new Object();
		data.page=1;
		roleManager.goUrl(url, data);
	});
	$('#endPage').bind('click',function(){
		var url="manager/goRoleManager_tables_jsp.spring";
		var data=new Object();
		data.page=$('#pageCount').val()==0?1:$('#pageCount').val();
		roleManager.goUrl(url, data);
	});
	$('.PageNum').bind('click',function(){
		var url="manager/goRoleManager_tables_jsp.spring";
		var data=new Object();
		alert($('.PageNum').val());
		data.page=$('.PageNum').text();
		roleManager.goUrl(url, data);
	});
	//设置模态框
	$('.showPermissions').attr({'data-toggle':"modal" ,"data-target":"#showPermissionsModel"});
	$('.addPermissions').attr({'data-toggle':"modal" ,"data-target":"#addPermissionsModel"});
	$('#newRole').attr({'data-toggle':"modal" ,"data-target":"#addRoleModal"});
	$('#addRoleModal').on('hidden.bs.modal', function (e) {
		var url="manager/goRoleManager_tables_jsp.spring";
		var data=new Object();
		data.page=1;
		roleManager.goUrl(url, data);
	});
	//模态框点击事件
	$('.showPermissions').bind('click',function(){
		var url="manager/showPermissions.spring";
		var data=new Object();
		data.page=1;
		sessionStorage.setItem('roleid', $('#r_id').val());
		$.ajax({
			   type: "POST",
			   url:url,
			   data: data,
			   success: function(msg){
				   $('.showPermissionsHead').html('权限列表');
				   $('.showPermissionsHead').html(msg);
			   }
			});
	});
	$('#saveNewRole').bind('click',function(){
		alert('click');
		var url="manager/saveNewRole.spring";
		var data=new Object();
		data.role_name=$('#role_name').val();
		data.role_descrition=$('#role_descrition').val();
		$.post(url,data,function(data){
		alert(data);	
		},'html');
	});
});
var roleManager={
			goUrl:function(url,data){
				$.ajax({
					   type: "POST",
					   url: url,
					   data: data,
					   success: function(msg){
						   $('.main').html(msg);
					   }
					});
			}
}
