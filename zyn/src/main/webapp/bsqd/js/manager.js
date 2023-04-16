$(document).ready(function(){
	checkUserLogin();
})
function checkUserLogin(){
	$.ajax({
		url:"user/checkUserLogin.action",
		type:"post",
		async:true,
		data:{'type':'1'},
		success:function(data){
			if(data.status == 1){
				window.location.href = "index.html";
			}else{
				manageProducts();
			}
		
		}
	});
}
//管理员
function manageProducts(){
	$("#manageProducts").removeClass("hidden");
	$("#manageUser").addClass("hidden");

	var table = $("#manageProductsTable").DataTable({
		"ajax":{
			"url":"user/listProductAllRMAP.action",
			"type":"get",
//			 "dataSrc": "",
			"data":{},
		},
		"aLengthMenu": [10,15,20],
//		 "searching": true,
         // 是否允许排序
         "ordering": true,
//         "scrollX": true,
         "destroy": true,
//         "autoWidth": false,
         "columns":[
        	 {"data":"name"},
        	 {"data":"mainImage"},
        	 {"data":"subtitle"},
        	 {"data":"price"},
        	 {"data":"stock"},
        	 {"data":"userName"},
        	 {"data":""}
         ],
         
         "columnDefs":[
        	 {

        		 targets: 1,
        		 orderable:false,
        		 render:function(a,b,c,d){
        			 var div =document.createElement('div');
        			 $(div).append('<img src="http://localhost'+c.mainImage+'" style="width:60px;height:70px">')
        			 return $(div).html();
        		 }
        	 
        	 },
        	 {
        		 targets: 5,
        		 orderable:false,
        		 render:function(a,b,c,d){
        			 if(c.userName == null || c.userName == ""){
        				 c.userName = "空";
        				 return 'kong';
        			 }else{
        				 return c.userName;
        			 }
        			 
        		 }
        	 },
        	 {
        		 targets: 6,
        		 orderable:false,
        		 render:function(a,b,c,d){
        			 var div = document.createElement("div");
        			 $(div).append('<button class="btn btn-primary" onclick="deleteProducts('+c.id+')">删除</button>');
//        					 +'<button class="btn btn-primary" receiverName="'+c.price+'" receiverPhone="'+c.price+'" receiverProvince="'+c.price+'"'
//        					 +'receiverDistrict="'+c.price+'" receiverAddress="'+c.price+'" id="'+c.price+'"'
//        					 +'onclick="modifyMyAddress(this)">修改</button>');
        			 return  $(div).html();
        		 }
        	 }
        	 
         ],
//         order:[
//        	 [0,null]
//         ],
         "language":{
 	        "processing": "加载中......",
 	        "sSearch": "搜索:",
 	        "lengthMenu": "显示 _MENU_ 项记录",
 	        "info": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
 	        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
 	        "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
 	        "emptyTable": "空数据",
 	        "zeroRecords": "没有记录",
 	        "paginate": {
 	            "first": "第一页",
 	            "last": "最后一页",
 	            "next": "下一页",
 	            "previous": "上一页"
 	        }
 		},
         "initComplete": function() {
             table.draw(true);
         }
	});
}
function deleteProducts(id){
	 if(window.confirm('确定删除？')){
			$.ajax({
		    	url:"user/custDeleteStuff.action",
		   		type:"post",
		   		async:true,
		   		data:{"id":id},
		   		success:function(result){
		   			if(result.status == 0){
		   				manageProducts();
		   			}else{
		   				alert(result.msg);
		   			}
		   		}
		       });
		 }
}
function manageUsers(){
	$("#manageUser").removeClass("hidden");
	$("#manageProducts").addClass("hidden");

	var table = $("#manageUserTable").DataTable({
		"ajax":{
			"url":"user/getAllUserMSGList.action",
			"type":"get",
//			 "dataSrc": "",
//			"data":{"id":100013},
		},
		"aLengthMenu": [5,10,15],
//		 "searching": true,
         // 是否允许排序
         "ordering": true,
//         "scrollX": true,
         "destroy": true,
//         "autoWidth": false,
         "columns":[
        	 {"data":"username"},
        	 {"data":"phone"},
        	 {"data":"email"},
        	 {"data":""}
         ],
         
         "columnDefs":[
        	 {
    		 targets: 3,
    		 orderable:false,
    		 render:function(a,b,c,d){
    			 var div = document.createElement("div");
    			 $(div).append('<button class="btn btn-primary" onclick="deleteUsers('+c.id+')">注销</button>');
//    					 +'<button class="btn btn-primary" receiverName="'+c.email+'" receiverPhone="'+c.email+'" receiverProvince="'+c.email+'"'
//    					 +'receiverDistrict="'+c.email+'" receiverAddress="'+c.email+'" id="'+c.email+'"'
//    					 +'onclick="modifyMyAddress(this)">修改</button>');
    			return $(div).html();
    		 }
    	 }
        	 
         ],
         "language":{
 	        "processing": "加载中......",
 	        "sSearch": "搜索:",
 	        "lengthMenu": "显示 _MENU_ 项记录",
 	        "info": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
 	        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
 	        "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
 	        "emptyTable": "空数据",
 	        "zeroRecords": "没有记录",
 	        "paginate": {
 	            "first": "第一页",
 	            "last": "最后一页",
 	            "next": "下一页",
 	            "previous": "上一页"
 	        }
 		},
         "initComplete": function() {
             table.draw(true);
         }
	});

}
function deleteUsers(id){
	 if(window.confirm('确定删除？')){
		 $.ajax({
				url:"user/deleteUser.action",
				type:"post",
				data:{"id":id},
				success:function(data){
					manageUsers();
				}
			})
	 }
	
	
} 
function myCart(){
	window.location.href = "cart.html";
}