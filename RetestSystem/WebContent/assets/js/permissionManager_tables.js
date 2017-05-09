$(function(){
	$('#firstPage').bind('click',function(){
		var url="manager/goPermissionManager_tables_jsp.spring";
		var data=new Object();
		data.page=1;
		data.roleid=sessionStorage.getItem('roleid');
		permissionListManager.goUrl(url, data);
	});
	$('#endPage').bind('click',function(){
		var url="manager/goPermissionManager_tables_jsp.spring";
		var data=new Object();
		data.page=$('#pageCount').val()==0?1:$('#pageCount').val();
		data.roleid=sessionStorage.getItem('roleid');
		permissionListManager.goUrl(url, data);
	});
	$('.PageNum').bind('click',function(){
		var url="manager/goPermissionManager_tables_jsp.spring";
		var data=new Object();
		data.page=$('.PageNum').text();
		data.roleid=sessionStorage.getItem('roleid');
		permissionListManager.goUrl(url, data);
	});
	$('#newPermisssionName').attr({'data-toggle':"modal" ,"data-target":"#addnewPermisssionModal"});
	$('#addnewPermisssionModal').on('hidden.bs.modal', function (e) {
		var url="manager/goPermissionManager_tables_jsp.spring";
		var data=new Object();
		data.page=1;
		permissionListManager.goUrl(url, data);
	});
	// 模态框点击事件
	$('#newPermisssionName').bind('click',function(){
		alert('click');
		var url="manager/newPermisssion.spring";
		var data=new Object();
		$.post(url,data,function(list){
				alert(list);
				console.log(list);
//				 var list=msg.plist;
				 alert(list);
				 console.log(list);
				 var html="";
				 for(var i in list){
					 html+="<option databaseId='"+list[i].p_id+"'>"+list[i].name+"</option>";
				 }
				 alert(html);
				 $('#permissionParent').html(html);
				 //给id为permissionParent的节点添加editableSelect选择
				 $("#permissionParent").editableSelect({
					    effects: 'slide',
					    filter:true
					});
			   },'json'
			)
	});
		$('#saveNewPermisssion').bind('click',function(){
			alert("123");
			var url="manager/saveNewPermisssion.spring";
			var data=new Object();
			//权限名
			data.permission_name=$('#permission_name').val();
			//权限url
			data.permission_urlPatten=$('#permission_urlPatten').val();
			//权限深度
			data.permission_depth=$('#permission_depth').val();
			//权限描述
			data.permission_descrition=$('#permission_descrition').val();
			//上级权限id
//			data.permissionParent=$('#permissionParent').val();
//			alert(data.permissionParent);
//			alert("------------");
//			alert($('#permissionParent').parent().parent().find(".selected").html());
//			alert($('#permissionParent').parent().parent().find(".selected").prop("databaseId"));
			data.permissionParent=$('#permissionParent').parent().find(".selected").attr("databaseId");
			$.post(url,data,function(data){
			alert(data);	
			});
		});
		
});
var permissionListManager={
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
