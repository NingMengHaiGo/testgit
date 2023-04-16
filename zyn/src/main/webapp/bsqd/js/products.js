$(document).ready(function(){
	var url = window.location.href;
	_categoryId = url.split('=')[1];
	categoryId = parseInt(_categoryId);
//	console.log(typeof(categoryId));
	getCategory(categoryId);
	checkUserLogin();
});
function showLoginModal(){
	$('#myModal88').modal('show');
}
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
				$(".cartShow").removeClass("hidden");
			}else{
				
			}
		
		}
	});
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
function getCategory(categoryId){
	$.ajax({
		url:"user/category/listSelProByCid.action",
		type:"get",
		async:true,
		data:{'id':categoryId},
		success:function(result){
			console.log(result)
//			if(result.status == 0){
//				getProducts(100087);
				var listData = result.data;
				getProducts(listData[0].id);
				for(var item in listData){
					var _id = listData[item].id
					$("#categoryList").append("<li onclick='getProducts("+_id+")'><a>"+listData[item].name+"</a></li>");
				}
//				getProducts(100087);
//			}
		}
	});
}
function getProducts(id){
	$("#productsRightModal").empty();
	$.ajax({
		url:"user/category/showProducts.action",
		type:"get",
		async:true,
		data:{'cid':id},
		success:function(result){
//			if(result.status == 0){
//				productsMsg = result.data[0];
//				productHeadMsg = result.data[1];
				productsMsg = result;
				for(var item in productsMsg){
					
					$("#productsRightModal").append('<div class="col-md-4 agileinfo_new_products_grid agileinfo_new_products_grid_mobiles">'
					+'<div class="agile_ecommerce_tab_left mobiles_grid">'
					+	'<div class="hs-wrapper hs-wrapper1">'
					+		'<img src="http://localhost'+productsMsg[item].mainImage+'" alt=" " class="img-responsive" />'
					+		'<img src="http://localhost'+productsMsg[item].detail+'" alt=" " class="img-responsive" />'
					+		'<img src="http://localhost'+productsMsg[item].subImage+'" alt=" " class="img-responsive" />'
						+	'<div class="w3_hs_bottom w3_hs_bottom_sub">'
						+		'<ul>'
						+			'<li>'
						+				'<a href="#" data-toggle="modal" data-target="#myModal'+item+'"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a>'
						+			'</li>'
						+		'</ul>'
						+	'</div>'
						+'</div>'
						+'<h5>'+productsMsg[item].name+'</h5>'
						+'<div class="simpleCart_shelfItem">'
						+	'<p><i class="item_price">'+productsMsg[item].price+'￥</i></p>'
						+	'<form action="#" method="post">'
						+       '<input type="hidden" name="cmd" value="_cart" />'
						+       '<input type="hidden" name="add" value="1" />'
						+		'<input type="hidden" name="w3ls_item" value="Red Laptop"> '
						+		'<input type="hidden" name="amount" value="500.00"> '  
						+		'<button type="submit" class="w3ls-cart">添加到购物车</button>'
						+	'</form>'
						+'</div>'
					+'</div>'
				+'</div> ');
					
					
					
					
					$("#productsMsg").before('<div class="modal video-modal fade" id="myModal'+item+'" tabindex="-1" role="dialog" aria-labelledby="myModal'+item+'">'
					+'<div class="modal-dialog" role="document">'
							+'<div class="modal-content">'
							+'<div class="modal-header">'	
								+'<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'						
								+'</div>'	
								+'<section>'
									+'<div class="modal-body">'
									+	'<div class="col-md-5 modal_body_left">'
//											+'<div class="hs-wrapper hs-wrapper1">'
											+	'<img src="http://localhost'+productsMsg[item].mainImage+'" alt=" " class="img-responsive" />'
//											+	'<img src="http://localhost'+productsMsg[item].detail+'" alt=" " class="img-responsive" />'
//											+	'<img src="http://localhost'+productsMsg[item].subImage+'" alt=" " class="img-responsive" />'
//											+'<div>'
									+	'</div>'
										+'<div class="col-md-7 modal_body_right">'
										+	'<h4>'+productsMsg[item].name+'</h4>'
										+	'<p> 描述</p>'
										+	'<div class="modal_body_right_cart simpleCart_shelfItem">'
											+	'<p><i class="item_price">'+productsMsg[item].price+'元</i></p>'
											+     '<form action="#" method="post">'
											+		'<input type="hidden" name="cmd" value="_cart">'
												+    '<input type="hidden" name="add" value="1"> '
												+	'<input type="hidden" name="w3ls_item" value="Mobile Phone1">' 
												+	'<input type="hidden" name="amount" value="350.00">'   
												+	'<button type="submit" class="w3ls-cart">添加到购物车</button>'
												+'</form>'
											+'</div>'
										+'</div>'
										+'<div class="clearfix"> </div>'
									+'</div>'
								+'</section>'
							+'</div>'
						+'</div>'
						+'<div>');
							
				}
				var clearDiv = document.createElement("input");
				$(clearDiv).attr("class","clearfix hidden");
				$("#productsRightModal").append(clearDiv);
				
//			}else{
//				alert("商品查询失败！");
//			}
		
		}
	});
}
function myCart(){
	window.location.href = "cart.html";
}