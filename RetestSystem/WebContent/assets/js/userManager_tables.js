$(function(){
	$('#firstPage').bind('click',function(){
		var url="manager/goUserManager_tables_jsp.spring";
		var data=new Object();
		data.page=1;
		userManager.goUrl(url, data);
	});
	$('#endPage').bind('click',function(){
		var url="manager/goUserManager_tables_jsp.spring";
		var data=new Object();
		data.page=$('#pageCount').val()==0?1:$('#pageCount').val();
		userManager.goUrl(url, data);
	});
	$('.PageNum').bind('click',function(){
		var url="manager/goUserManager_tables_jsp.spring";
		var data=new Object();
		alert($('.PageNum').val());
		data.page=$('.PageNum').text();
		userManager.goUrl(url, data);
	});
});
var userManager={
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
