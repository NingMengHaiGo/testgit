$(document).ready(function(){
	var proMSG;
	checkUserLogin();
	listProductAll();
	
});
var isLogin=1;
function checkUserLogin(){
	$.ajax({
		url:"user/checkUserLogin.action",
		type:"post",
		async:true,
		data:{'type':'1'},
		success:function(data){
			if(data.status == 0){
				$("#loginModal").addClass("hidden");
				$(".afterLogin").removeClass("hidden");
				$(".afterLogin").html("你好,"+data.data.username+"!");
				$(".myInfoShow").removeClass("hidden");
				$(".uploadShow").removeClass("hidden");
				$(".cartShow").removeClass("hidden");
				isLogin = 0;
				if(data.data.role == 1){
					$(".managerShow").removeClass("hidden");
				}else{
					$(".managerShow").addClass("hidden");
				}
			}else{
				isLogin = 1;
				
			}
		
		}
	});
}

function listProductAll(){
	$.ajax({
		url:"user/listProductAll.action",
		type:"post",
		async:true,
		success:function(result){
			if(result.status == 0){
				proMSG = result.data;
				for(var item in proMSG){
					
					$("#productsShow").append('<div class="col-md-3 agileinfo_new_products_grid">'
					+'<div class="agile_ecommerce_tab_left agileinfo_new_products_grid1">'
					+	'<div class="hs-wrapper hs-wrapper1">'
					+		'<img src="http://localhost'+proMSG[item].mainImage+'" alt=" " class="img-responsive" />'
					+		'<img src="http://localhost'+proMSG[item].detail+'" alt=" " class="img-responsive" />'
					+		'<img src="http://localhost'+proMSG[item].subImage+'" alt=" " class="img-responsive" />'
						+	'<div class="w3_hs_bottom w3_hs_bottom_sub">'
						+		'<ul>'
						+			'<li>'
						+				'<a href="#" data-toggle="modal" data-target="#myModal'+item+'"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a>'
						+			'</li>'
						+		'</ul>'
						+	'</div>'
						+'</div>'
						+'<h5>'
						+'<p style="color:red">'
						+proMSG[item].name
						+'</p>'
						+'</h5>'
						+'<div class="simpleCart_shelfItem">'
						+	'<p>'
						+   '<label>'
						+	'库存:'
						+    proMSG[item].stock+'&nbsp;&nbsp;'
						+'</label>'
						+   '单价：'
						+   '<i class="item_price">'+proMSG[item].price+'￥</i></p>'
						+	'<form action="#" method="post" onsubmit="addToCart('+proMSG[item].id+')">'
//						+       '<input type="hidden" name="cmd" value="_cart" />'
						+       '<input type="hidden" name="productId" value="'+proMSG[item].id+'" />'
						+		'<input type="hidden" name="w3ls_item" value="'+proMSG[item].name+'"> '
						+		'<input type="hidden" name="amount" value="'+proMSG[item].price+'"> '  
						+		'<button type="submit" class="w3ls-cart">添加到购物车</button>'
						+	'</form>'
						+'</div>'
					+'</div>'
				+'</div> ');
					
					
					
					
					$("#new_products").before('<div class="modal video-modal fade" id="myModal'+item+'" tabindex="-1" role="dialog" aria-labelledby="myModal'+item+'">'
					+'<div class="modal-dialog" role="document" style="width:60%">'
							+'<div class="modal-content">'
							+'<div class="modal-header">'	
								+'<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'						
								+'</div>'	
								+'<section>'
									+'<div class="modal-body">'
									+	'<div class="col-md-4 modal_body_left">'
//											+'<div class="hs-wrapper hs-wrapper1">'
											+	'<img src="http://localhost'+proMSG[item].mainImage+'" alt=" " class="img-responsive" />'
//											+	'<img src="http://localhost'+proMSG[item].detail+'" alt=" " class="img-responsive" />'
//											+	'<img src="http://localhost'+proMSG[item].subImage+'" alt=" " class="img-responsive" />'
//											+'<div>'
									+	'</div>'
										+'<div class="col-md-7 modal_body_right">'
										+	'<h4>'
										+	proMSG[item].name
										+   '</h4>'
										+'<table style="float:right;">'
										+'<tr>'
//										+'<th>'+'当前状态:'+'<td id='+proMSG[item].id+'id'+'>'
										
										+'</td>'
				  						+'</th>'
										+'</tr>'
										+'</table>'
									/*	+	'<h4>'
										+	proMSG[item].name
										+   '</h4>'*/
										+	'<p> '
										+    proMSG[item].subtitle
										+   '</p>'
										+	'<div class="modal_body_right_cart simpleCart_shelfItem">'
										+'<table >'
										+'<tr>'
										+'<th>'+'库存:'+'<td id="totalCartCount">'
										+ proMSG[item].stock+'&nbsp;&nbsp;&nbsp;&nbsp;'
										+'</td>'
										+'</th>'
										+'<th>'
										+'单价:'
				  						+'<td id="item_price">'+proMSG[item].price+'￥'+'</td>'+'</th>'
										+'</tr>'
										+'</table>'
										+'<p>'
										+'</p>'
//										+'<h5>'+'商品卖家'+'</h5>'
										+'<p>'
										+'</p>'
										+'<table >'
										+'<tr>'
										+'<th>'+'卖家:'+'<td id="totalCartCount">'
										+ proMSG[item].userName+'&nbsp;&nbsp;&nbsp;&nbsp;'
										+'</td>'
										+'</th>'
										+'<th>'
										+'发布时间:'
				  						+'<td id="item_price">'+changeTime(proMSG[item].createTime)+'</td>'+'</th>'
										+'</tr>'
										+'</table>'
										+'<p>'
										+'</p>'
										/*	+	'<p>'
											+	'库存:'
											+    proMSG[item].stock+' '
											+   '单价：'
											+	'<i class="item_price">'+proMSG[item].price+'元</i></p>'*/
										+	'<form action="#" method="post" onsubmit="addToCart('+proMSG[item].id+')">'
//										+       '<input type="hidden" name="cmd" value="_cart" />'
										+       '<input type="hidden" name="productId" value="'+proMSG[item].id+'" />'
										+		'<input type="hidden" name="w3ls_item" value="'+proMSG[item].name+'"> '
										+		'<input type="hidden" name="amount" value="'+proMSG[item].price+'"> '  
										+		'<button type="submit" class="w3ls-cart">添加到购物车</button>'
										+	'</form>'
											+'</div>'
										+'</div>'
										+'<div class="clearfix"> </div>'
									+'</div>'
								+'</section>'
							+'</div>'
						+'</div>'
						+'<div>');
							
//					if(proMSG[item].status == 1){
//						$('#'+proMSG[item].id+'id').after('在售');
//					}
					
				}
				var clearDiv = document.createElement("input");
				$(clearDiv).attr("class","clearfix hidden");
				$("#productsShow").append(clearDiv);
				
			}else{
				alert("商品查询失败！");
			}
		
		}
	});
}
function addToCart(id){
	$.ajax({
		url:"user/cart/custAddPro.action",
		type:"post",
		async:false,
		data:{'productId':id},
		success:function(result){
		if(result.status == 0){
			alert("已添加到购物车！")
			}else{
			alert("购物车添加失败，请重试！")
			}
		
		}
	});
}
function showLoginModal(){
	$('#myModal88').modal('show');
}
function toUpload(){
	if(isLogin == 0){
		$("#upload").load("upload.jsp",function(){
			$("#upload_modal").modal({"backdrop":"static"});
			
		});
	}
	
}
function checkRepwd(){
	var pwd1 = document.getElementById("pwd1").value;
	var pwd2 = document.getElementById("pwd2").value;
	if(pwd1 != pwd2  && pwd2!='') {
	document.getElementById("tishi").innerHTML="<font color='lightred'>两次密码不相同</font>";
		return false;
	}
}
function userRegister(){
	var username = $("#registerForm").find("[name='username']").val();
	var email = $("#registerForm").find("[name='email']").val();
	var phone = $("#registerForm").find("[name='phone']").val();
	var password =  $("#registerForm").find("[name='password']").val();
	$.ajax({
		url:"user/custRegister.action",
		type:"post",
		async:false,
		data:{'username':username,'email':email,'phone':phone,'password':password},
		success:function(data){
			alert(data.msg)
			if(data.status != 0){
				return false;
			}
		
		}
	});
}
function signIn(){
//	debugger
	var username = $("#loginForm").find("[name='username']").val();
	var password =  $("#loginForm").find("[name='password']").val();
	$.ajax({
		url:"user/customerLogin.action",
		type:"post",
		async:false,
		data:{'username':username,'password':password},
		success:function(data){
			if(data.status == 0){
				
			}else{
				alert("error");
			}
		
		}
	});
}

function loginOut() {
	   if(window.confirm('确定退出？')){
	       $.ajax({
	    	url:"user/logout.action",
	   		type:"post",
	   		async:true,
	   		success:function(data){
	   			if(data.status == 0){
	   				$("#loginModal").removeClass("hidden");
					$(".afterLogin").addClass("hidden");
//					alert(111111)
		   		 	return true;
	   			}
	   			
	   		}
	       });
	      
	   }else{
		   return false;
	   }
}

function toProduct(){
	 $.ajax({
	    	url:"user/turnProductView.action",
	   		type:"post",
	   		async:true,
	   		success:function(data){
	   		}
	       });
}

//myInfo
function getMyInfo(){
	$("#myBasicInfo").load("bsqd/myAllInfo/myBasicInfo.html",function(){
		$("#myBasicInfoModal").modal({'backdrop':'static'});
		$("#myBasicInfoModal").one("shown.bs.modal",function(e){
//			if(isLogin == 0){
			 $.ajax({
			    	url:"user/checkUserLogin.action",
			   		type:"post",
			   		async:true,
			   		success:function(data){
			   			$("#myBasicInfoForm").find("[name='username']").val(data.data.username);
			   			$("#myBasicInfoForm").find("[name='email']").val(data.data.email);
			   			$("#myBasicInfoForm").find("[name='phone']").val(data.data.phone);
			   		}
			       });
			
//			}
		});
	});
}
function modifyMyInfo(){
	var email =  $("#myBasicInfoForm").find("[name='email']").val();
	var phone =  $("#myBasicInfoForm").find("[name='phone']").val();
	 $.ajax({
	    	url:"user/update_information.action",
	   		type:"post",
	   		async:true,
	   		data:{"email":email,"phone":phone,"question":"","answer":""},
	   		success:function(data){

	   		}
	       });
	
}
//getMyAddress
function getMyAddress(){
//	console.log("isLogin1:"+isLogin);
	$("#myAddress").load("bsqd/myAllInfo/myAddress.html",function(){
		$("#myAddressModal").modal({'backdrop':'static'});
//		console.log("isLogin:"+isLogin);
		$("#myAddressModal").one("shown.bs.modal",function(e){
//			if(isLogin == 0){
			getMyAddressData();
			
//			}
		});
	});
}
//myOrder
function getMyOrder(){
	
	$("#myOrder").load("bsqd/myAllInfo/myOrder.html",function(){
		$("#myOrderModal").modal({'backdrop':'static'});
		$("#myOrderModal").one("shown.bs.modal",function(e){
//			if(isLogin == 0){
			getMyOrderData();
//			}
		})
	});
}
//myStuff
function getMyStuff(){
	$("#myStuff").load("bsqd/myAllInfo/myStuff.html",function(){
		$("#myStuffModal").modal({'backdrop':'static'});
		$("#myStuffModal").one("shown.bs.modal",function(e){
//			if(isLogin == 0){
			getMyStuffData();
//			}
		})
	});
}

function getMyAddressData(){
	var table=$("#myAddressTable").DataTable({
		"ajax":{
			"url":"user/shipping/listMyshipping.action",
			"type":"post",
//			"data":{"cid":"100087"},
		},
		"aLengthMenu": [5,10,15],
		 "searching": true,
         // 是否允许排序
         "ordering": true,
//         "scrollX": true,
         "destroy": true,
         "autoWidth": false,
         "columns":[
        	 {"data":"receiverName"},
        	 {"data":"receiverMobile"},
        	 {"data":"receiverProvince"},
        	 {"data":""}
         ],
         
         "columnDefs":[
        	 {
        		 targets: 2,
        		 orderable:false,
        		 render:function(a,b,c,d){
        			 var myAddress = c.receiverProvince+c.receiverCity+c.receiverDistrict+c.receiverAddress;
        			 return myAddress;
        		 }
        	 },
        	 {
        		 targets: 3,
        		 orderable:false,
        		 render:function(a,b,c,d){
        			 var div = document.createElement("div");
        			 var deleteButton = document.createElement("button");
        			 $(deleteButton).text("删除");
        			 var addButton = document.createElement("button");
        			 $(addButton).text("添加");
        			 $(div).append(deleteButton);
        			 $(div).append(addButton);
        			return $(div).html();
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
function getMyOrderData(){
	var table = $("#myOrderTable").DataTable({
		"ajax":{
			"url":"user/order/getMyOrder.action",
			"type":"post",
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
        	 {"data":"id"},
        	 {"data":"orderNo"},
        	 {"data":"sendTime"},
         ],
         
         "columnDefs":[
//        	 {
//        		 targets: 2,
//        		 orderable:false,
//        		 render:function(a,b,c,d){
//        			 var myAddress = c.receiverProvince+c.receiverCity+c.receiverDistrict+c.receiverAddress;
//        			 return myAddress;
//        		 }
//        	 }
        	 
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

function getMyStuffData(){
	var table = $("#myStuffTable").DataTable({
		"ajax":{
			"url":"user/order/getMyOrder.action",
			"type":"post",
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
        	 {"data":"id"},
        	 {"data":"orderNo"},
        	 {"data":"sendTime"},
        	 {"data":"payment"}
         ],
         
         "columnDefs":[
//        	 {
//        		 targets: 2,
//        		 orderable:false,
//        		 render:function(a,b,c,d){
//        			 var myAddress = c.receiverProvince+c.receiverCity+c.receiverDistrict+c.receiverAddress;
//        			 return myAddress;
//        		 }
//        	 }
        	 
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

function myCart(){
	window.location.href = "cart.html";
}
function changeTime(time){
    if(time){
        var oDate = new Date(time*1),
            oYear = oDate.getFullYear(),
            oMonth = oDate.getMonth()+1,
            oDay = oDate.getDate(),
            oHour = oDate.getHours(),
            oMin = oDate.getMinutes(),
            oSen = oDate.getSeconds(),
            oTime = oYear +'-'+ getBz(oMonth) +'-'+ getBz(oDay) +' '+ getBz(oHour) +':'+ getBz(oMin) +':'+getBz(oSen);//拼接时间
        return oTime;
    }else{
        return "";
    }

}
//补0
function getBz(num){
    if(parseInt(num) < 10){
        num = '0'+num;
    }
    return num;
}