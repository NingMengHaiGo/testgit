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
				getMyCart();
			}
		
		}
	});
}
//我的购物车
function getMyCart(){
	$(".myCartDiv").removeClass("hidden");
	$(".myPayDiv").addClass("hidden");
	$("#myCart").removeClass("hidden");
	$("#myAddress").addClass("hidden");
	$("#myOrder").addClass("hidden");
	//$("#cartMsg").show();
	var table=$("#myCartTable").DataTable({
		"ajax":{
			"url":"cart/getMyCartList.action",
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
        	 {"data":""},
        	 {"data":"productName"},
        	 {"data":"productMainImage"},
        	 {"data":"productSubtitle"},
        	 {"data":"productPrice"},
        	 {"data":"quantity"},
        	 {"data":""}
         ],
         
         "columnDefs":[
        	 {
        		 targets: 0,
        		 orderable:false,
        		 render:function(a,b,c,d){
        			 if(c.productChecked == 1){
        				 return '<input type="checkbox" id="'+c.id+'" checked="checked" onclick="changeCheckStatus('+c.id+')">';
        			 }else{
        				 return '<input type="checkbox" id="'+c.id+'" onclick="changeCheckStatus('+c.id+')">';
        			 }
        			
        		 }
        	 },
        	 {
        		targets:2,
        		orderable:false,
        		render:function(a,b,c,d){
        			 var div =document.createElement('div');
        			 $(div).append('<img src="http://localhost'+c.productMainImage+'" style="width:60px;height:70px">')
        			 return $(div).html();
        		}
        	 },
        	 {
         		targets:5,
         		orderable:false,
         		render:function(a,b,c,d){
         			 return '<input type="number" max="10" min="1" value="'+c.quantity+'" id="'+c.productId+'" onchange="changeQuantity(this)">';
         		}
         	 },
        	 {
        		 targets: 6,
        		 orderable:false,
        		 render:function(a,b,c,d){
        			 var div = document.createElement("div");
        			 $(div).append('<button class="btn btn-primary" onclick="deleteMyCartPro('+c.productId+')">删除</button>');
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
	//查询已经选中商品的总价
	showTotalPriceAndCount();
}
function showTotalPriceAndCount(){
	$.ajax({
		url:"get_cart_product_count.action",
		type:"post",
		success:function(count){
			if(count.status == 0){
				$("#totalCartCount").html(count.data+'件，');
			}
		}
	});
	
	$.ajax({
		url:"cart/custListCart.action",
		type:"post",
		success:function(result){
			if(result.status == 0){
				$("#totalPrice").html('总价:'+result.data.cartTotalPrice+'元');
			}
		}
	});
}
function changeCheckStatus(pid){
	//console.log($("input[type='checkbox']").is(':checked'));
	var ckBoxObj = document.getElementById(pid);
	var checked;
//	debugger
	if(ckBoxObj.checked == true){
		checked = 1;
	}else{
		checked = 0;
	}
	//改变该商品在购物车中的选中状态
	$.ajax({
		url:"user/cart/select.action",
		type:"post",
		data:{"productId":pid,"checked":checked},
		success:function(result){
			if(result.status == 0){
				$.ajax({
					url:"cart/custListCart.action",
					type:"post",
					success:function(result){
						if(result.status == 0){
							$("#totalPrice").html('总价:'+result.data.cartTotalPrice+'元');
						}
					}
				});
			}
		}
	});
	//查询已经选中商品的总价
	//showTotalPriceAndCount();	
}

function changeQuantity(obj){
	$.ajax({
		url:"user/cart/update.action",
		data:{"count":obj.value,"productId":obj.id},
		type:"POST",
		success:function(result){
			if(result.status == 0){
				showTotalPriceAndCount()
				/*$("#totalPrice").html('总价:'+result.data.cartTotalPrice+'元');*/
				}
			}
	});
}
function toPayHtml(){
	$(".myCartDiv").addClass("hidden");
	$(".myPayDiv").removeClass("hidden");
	$.ajax({
		url:"user/shipping/listMyshipping.action",
		type:"post",
		success:function(data){
			var data = data.data;
			for(var item in data){
				var receiverAddress = data[item].receiverProvince +data[item].receiverDistrict + data[item].receiverAddress;
				$("#selectMyPayAddress").append('<option value="'+data[item].id+'">收件人：'+data[item].receiverName+',手机号：'+data[item].receiverPhone+',地址：'+receiverAddress+'</option>')
			}
		}
	});
	//显示已经选中商品列表
	var table=$("#toPayTable").DataTable({
		"ajax":{
			"url":"cart/getMyCartCheckedList.action",
			"type":"post",
		},
//		"aLengthMenu": [5,10,15],
		"paging": false,
		 "bInfo": false, 
		 "searching": false,
         // 是否允许排序
         "ordering": true,
//         "scrollX": true,
         "destroy": true,
         "autoWidth": false,
         "columns":[
        	 {"data":"productName"},
        	 {"data":"productMainImage"},
        	 {"data":"productPrice"},
        	 {"data":"quantity"},
         ],
         
         "columnDefs":[
        	 {
        		targets:1,
        		orderable:false,
        		render:function(a,b,c,d){
        			 var div =document.createElement('div');
        			 $(div).append('<img src="http://localhost'+c.productMainImage+'" style="width:60px;height:70px">')
        			 return $(div).html();
        		}
        	 }
        	 
         ],
         "initComplete": function() {
             table.draw(true);
         }
	});
	
	//添加商品个数及总价
	$.ajax({
		url:"cart/myCartCheckedPriceAndCount.action",
		type:"post",
		success:function(result){
				$("#payCount").html(result.count+'件 '+" ,");
				$("#payPrice").html('总价:'+result.totalpayPrice+'元');
			
		}
	});
}

//最终结算
function prepareToPay(){
	var selectedAddress = $('#selectMyPayAddress option:selected').val();
	$.ajax({
		url:"user/shipping/order/order.action",
		type:"post",
		data:{"shippingId":selectedAddress,"status":0},
		success:function(result){
			if(result.status == 0){
				alert("选中商品已结算成功！");
				window.location.href = "cart.html";
			}else{
				alert("选中商品结算失败！");
			}
		}
	});
}

//我的收货地址
function getMyAddress(){
	$("#myAddress").removeClass("hidden");
	$("#myOrder").addClass("hidden");
	$("#myCart").addClass("hidden");
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
        	 {"data":"receiverPhone"},
        	 {"data":"receiverProvince"},
        	 {"data":""}
         ],
         
         "columnDefs":[
        	 {
        		 targets: 2,
        		 orderable:false,
        		 render:function(a,b,c,d){
        			 var myAddress = c.receiverProvince+c.receiverDistrict+c.receiverAddress;
        			 return myAddress;
        		 }
        	 },
        	 {
        		 targets: 3,
        		 orderable:false,
        		 render:function(a,b,c,d){
        			 var div = document.createElement("div");
        			 $(div).append('<button class="btn btn-primary" onclick="deleteMyAddress('+c.id+')">删除</button>'
        					 +'<button class="btn btn-primary" receiverName="'+c.receiverName+'" receiverPhone="'+c.receiverPhone+'" receiverProvince="'+c.receiverProvince+'"'
        					 +'receiverDistrict="'+c.receiverDistrict+'" receiverAddress="'+c.receiverAddress+'" id="'+c.id+'"'
        					 +'onclick="modifyMyAddress(this)">修改</button>');
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
function collapseForm(obj){
	if(obj == "#myAddressForm"){
		$("#myAddressCollapse").removeClass("in");
	}
	
}
function addMyAddress(){
	$("#myAddressCollapse").addClass("in");
	$(".addressAdd").removeClass("hidden");
	$(".addressUpdate").addClass("hidden");
	$("#myCart").addClass("hidden");
}

function addressAdd(){
	var receiverName =  $("#myAddressForm").find("[name='receiverName']").val();
	var receiverPhone =  $("#myAddressForm").find("[name='receiverPhone']").val();
	var receiverProvince =  $("#myAddressForm").find("[name='receiverProvince']").val();
	var receiverDistrict =  $("#myAddressForm").find("[name='receiverDistrict']").val();
	var receiverAddress =  $("#myAddressForm").find("[name='receiverAddress']").val();
	var receiverCity =  $("#myAddressForm").find("[name='receiverCity']").val();
	var Shipping = {receiverName : receiverName,receiverPhone : receiverPhone,receiverProvince : receiverProvince,
			receiverDistrict : receiverDistrict,receiverAddress : receiverAddress};
	 $.ajax({
	    	url:"user/shipping/add.action",
	   		type:"post",
	   		/*async:true,*/
	   		data:Shipping,
	   		success:function(result){
	   			/*if(result.status == 0){*/
//	   				$("#myAddressCollapse").removeClass("in");
	   				getMyAddress();
	   			/*}else{
	   				alert(result.data.msg);
	   			}*/
	   		}
	       });
}

function deleteMyCartPro(id){
	 if(window.confirm('确定删除？')){
			$.ajax({
		    	url:"user/cart/delete_products.action",
		   		type:"post",
		   		async:true,
		   		data:{"productIds":id},
		   		success:function(result){
		   			alert(result.msg);
		   			getMyCart();
		   		}
		       });
		 }else{
			 
		 }
}

function deleteMyAddress(id){
	 if(window.confirm('确定删除？')){
			$.ajax({
		    	url:"user/shipping/delete.action",
		   		type:"post",
		   		async:true,
		   		data:{"shippingId":id},
		   		success:function(result){
		   			if(result.status == 0){
		   				getMyAddress();
		   			}else{
		   				alert(result.msg);
		   			}
		   		}
		       });
		 }else{
			 
		 }
}
function modifyMyAddress(obj){
	$("#myAddressCollapse").addClass("in");
	$(".addressAdd").addClass("hidden");
	$(".addressUpdate").removeClass("hidden");
	$(".receiverHidden").append('<input name="id" type="text" class="form-control">')
	$("#myAddressForm").find("[name='receiverName']").val($(obj).attr("receiverName"));
	 $("#myAddressForm").find("[name='receiverPhone']").val($(obj).attr("receiverPhone"));
	 $("#myAddressForm").find("[name='receiverProvince']").val($(obj).attr("receiverProvince"));
	 $("#myAddressForm").find("[name='receiverDistrict']").val($(obj).attr("receiverDistrict"));
	 $("#myAddressForm").find("[name='receiverAddress']").val($(obj).attr("receiverAddress"));
	 $("#myAddressForm").find("[name='id']").val($(obj).attr("id"));
}
function addressUpdate(){
	var receiverName =  $("#myAddressForm").find("[name='receiverName']").val();
	var receiverPhone =  $("#myAddressForm").find("[name='receiverPhone']").val();
	var receiverProvince =  $("#myAddressForm").find("[name='receiverProvince']").val();
	var receiverDistrict =  $("#myAddressForm").find("[name='receiverDistrict']").val();
	var receiverAddress =  $("#myAddressForm").find("[name='receiverAddress']").val();
	var receiverCity =  $("#myAddressForm").find("[name='receiverCity']").val();
	var id =  $("#myAddressForm").find("[name='id']").val();
	var Shipping = {receiverName : receiverName,receiverPhone : receiverPhone,receiverProvince : receiverProvince,
			receiverDistrict : receiverDistrict,receiverAddress : receiverAddress,id: id};
 $.ajax({
    	url:"user/shipping/update.action",
   		type:"post",
   		async:true,
   		data:Shipping,
   		success:function(result){
   			if(result.status == 0){
//   				$("#myAddressCollapse").removeClass("in");
   				getMyAddress();
   			}else{
   				alert(result.msg);
   			}
   		}
       });
}
//我的订单
function getMyOrder(){
	$("#myAddress").addClass("hidden");
	$("#myOrder").removeClass("hidden");
	$("#myCart").addClass("hidden");
	var table = $("#myOrderTable").DataTable({
		"ajax":{
			"url":"user/order/custPayedOrder.action",
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
        	 {"data":"productName"},
        	 {"data":"orderNo"},
        	 {"data":"productImage"},
        	 {"data":"quantity"},
        	 {"data":"currentUnitPrice"},
        	 {"data":"totalPrice"},
        	 {"data":"createTime"},
         ],
         
         "columnDefs":[
        	 {
        		 targets: 2,
        		 orderable:false,
        		 render:function(a,b,c,d){
        			 var div =document.createElement('div');
        			 $(div).append('<img src="http://localhost'+c.productImage+'" style="width:60px;height:70px">')
        			 return $(div).html();
        		 }
        	 },
        	 {
        		 targets: 6,
        		 orderable:false,
        		 render:function(a,b,c,d){
        			 return changeTime(c.createTime);
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