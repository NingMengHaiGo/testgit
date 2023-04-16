$(document).ready(function(){
	$("#modifyMyStuff").addClass("hidden");
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
				uploadMyStuff();
			}
		
		}
	});
}

function uploadMyStuff(){
	$("#uploadMyStuff").removeClass("hidden");
	$("#myInfo").addClass("hidden");
	$("#myAddress").addClass("hidden");
	$("#myOrder").addClass("hidden");
	$("#myStuff").addClass("hidden");
	$("#modifyMyStuff").addClass("hidden");
}
function stuffFormSubmit(){
	var name=$("#myStuffForm").find("[name='name']").val();
	var price=$("#myStuffForm").find("[name='price']").val();
	var stock=$("#myStuffForm").find("[name='stock']").val();
	var subtitle=$("#myStuffForm").find("[name='subtitle']").val();
	var categoryId=$("#myStuffForm").find("[name='categoryId']").val();
	var mainImage=$("#myStuffForm").find("[name='mainImage']").val();
	var subImage=$("#myStuffForm").find("[name='subImage']").val();
	var detail=$("#myStuffForm").find("[name='detail']").val();
	var product = {"name":name,"price":price,"stock":stock,"subtitle":subtitle,"categoryId":categoryId,"mainImage":mainImage,"subImage":subImage,"detail":detail};
	
	$.ajax({
		url:"user/save.action",
		type:"post",
		data:{"product":product},
		success:function(res){
			console.log(res)
		}
	})
}
//我的上传的宝贝
function getMyStuff(){
	$("#uploadMyStuff").addClass("hidden");
	$("#myInfo").addClass("hidden");
	$("#myAddress").addClass("hidden");
	$("#myOrder").addClass("hidden");
	$("#modifyMyStuff").addClass("hidden");
	$("#myStuff").removeClass("hidden");
	var table = $("#myStuffTable").DataTable({
		"ajax":{
			"url":"user/cust_get_ownProduct.action",
			"type":"get",
//			 "dataSrc": "",
//			"data":{"id":100013},
		},
		"aLengthMenu": [5,10,15],
//		 "searching": true,
         // 是否允许排序
         "ordering": true,
//         "scrollY": true,
         "destroy": true,
//         "autoWidth": false,
         "columns":[
        	 {"data":"name"},
        	 {"data":"mainImage"},
        	 {"data":"price"},
        	 {"data":"stock"},
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
        		 targets:4,
        		 orderable:false,
        		 render:function(a,b,c,d){
        			 var div =document.createElement('div');
        			 $(div).append('<button class="btn btn-primary" onclick="deleteMyStuff('+c.id+')">删除</button>'
        					 +'<button class="btn btn-primary" id = "'+c.id+'" name="'+c.name+'" price="'+c.price+'" stock="'+c.stock+'"'
        					 +'subtitle="'+c.subtitle+'" categoryId="'+c.categoryId+'" createTime="'+c.createTime+'"'
        					 +'mainImage="'+c.mainImage+'" subImage="'+c.subImage+'" detail="'+c.detail+'"'
        					 +'onclick="modifyMyStuff(this)">修改</button>');
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

function deleteMyStuff(id){
	 if(window.confirm('确定删除？')){
		$.ajax({
	    	url:"user/custDeleteStuff.action",
	   		type:"post",
	   		async:true,
	   		data:{"id":id},
	   		success:function(result){
	   			if(result.status == 0){
	   				getMyStuff();
	   			}else{
	   				alert(result.msg);
	   			}
	   		}
	       });
	 }else{
		 
	 }
}

function selectCateOne(){
	$("#category2Div").empty();
	if($("#categoryId1").val()==1){ 
		$("#category2Div").append('		<select class="input-sm form-control categoryId" name="categoryId2" id="categoryId2" onchange="selectCateTwo(100001)">'
								  +' 			<option value=""></option>'
								  +' 			<option value="100005">男装</option>'
								   +'			<option value="100006">女装</option>'
								   +'			<option value="100007">童装</option>'
								   +'		</select>');
	}else if($("#categoryId1").val()==2){
		$("#category2Div").append('<select class="input-sm form-control categoryId" name="categoryId2" onchange="selectCateTwo()">'
				  +' 			<option value=""></option>'
				  +' 			<option value="100008">家具</option>'
				   +'			<option value="100009">电器</option>'
				   +'			<option value="100010">厨房</option>'
				   +'		</select>');
	}else if($("#categoryId1").val()==3){
		$("#category2Div").append('	<select class="input-sm form-control categoryId" name="categoryId2" onchange="selectCateTwo()">'
				  +' 			<option value=""></option>'
				  +' 			<option value="100011">相机</option>'
				   +'			<option value="100012">手机</option>'
				   +'			<option value="100013">电脑</option>'
				   +'		</select>');
	}else if($("#categoryId1").val()==4){
		$("#category2Div").append('	<select class="input-sm form-control categoryId" name="categoryId2" onchange="selectCateTwo()">'
				  +' 			<option value=""></option>'
				  +' 			<option value="100014">其它</option>'
				   +'		</select>');
	}
}
function selectCateTwo(){
	var id = $(".categoryId").val();
	console.log(id)
	$.ajax({
		url:"user/category/listSelProByCid.action",
		type:"get",
		async:true,
		data:{'id':id},
		success:function(result){
//			if(result.status == 0){
				var listData = result.data;
				var select = document.createElement("select");
				$("#category3Div").append(select);
				$(select).attr({"class":"input-sm form-control","name":"categoryId"});
				for(var item in listData){
					var _id = listData[item].id
					$(select).append('<option value="'+_id+'">'+listData[item].name+'</option>');
				}
//			}
		}
	});
	$("#category3Div").empty();
}
function myCart(){
	window.location.href = "cart.html";
}

function modifyMyStuff(obj){
	$("#uploadMyStuff").addClass("hidden");
	$("#myInfo").addClass("hidden");
	$("#myAddress").addClass("hidden");
	$("#myOrder").addClass("hidden");
	$("#myStuff").addClass("hidden");
	$("#modifyMyStuff").removeClass("hidden");
		$("#modifyMystuffForm").find("[name='id']").val($(obj).attr("id"));
		$("#modifyMystuffForm").find("[name='createTime']").val($(obj).attr("createTime"));
		$("#modifyMystuffForm").find("[name='name']").val($(obj).attr("name"));
		$("#modifyMystuffForm").find("[name='subtitle']").val($(obj).attr("subtitle"));
		$("#modifyMystuffForm").find("[name='categoryId']").val($(obj).attr("categoryId"));
		$("#bg-img1").attr("src","http://localhost"+$(obj).attr("mainImage"));
		$("#bg-img2").attr("src","http://localhost"+$(obj).attr("subImage"));
		$("#bg-img3").attr("src","http://localhost"+$(obj).attr("detail"));
		$("#modifyMystuffForm").find("[name='categoryId']").val($(obj).attr("categoryId"));
		$("#modifyMystuffForm").find("[name='price']").val($(obj).attr("price"));
		$("#modifyMystuffForm").find("[name='stock']").val($(obj).attr("stock"));
}