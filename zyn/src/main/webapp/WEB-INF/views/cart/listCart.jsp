<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.zhao.deep.utils.ConfigReader"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<%
		pageContext.setAttribute("APP_PATH", request.getContextPath());
		String ipNignx = (String) ConfigReader.getValue("nginx.ip");
	%>
		<script type="text/javascript" src="${APP_PATH }/static/js/jquery-1.12.4.min.js">
		</script>
		<link href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js">
		</script>
		
		<style>
			.minus{
				/* margin-left:0px; */
				
			}
			.plus{
				float:left;
				height: 30px;
   			    width: 30px;
			}
		
		.table input[type='text']{
			text-align:center;
			width:70px;
		}
		</style>
		
		<script type="text/javascript">
		/* 未选中的选中 */
			function ifChecked(){
				
				var a = document.getElementById("checkId1");
				var b = document.getElementById("checkId1").value;
				//alert(a.checked);选中
				if( a.checked){
								
								 To_check(b);
								 //refresh();
								 
					}
				
				}
			function To_check(b){
				/* alert("...........");
				alert(b); */
				$.ajax({
					
					url:"select",
					data:"productId="+b+"&checked="+1,
					type:"GET",
					success:function(result){
							
							 
						}
				  });
				 refresh();
				
				}


			 function refresh(){
			    window.location.reload();
			    setTimeout(refresh , 500);   
			} 
			
			/* 选中的取消选中 */
			function ifChecked2(){
				
				var c = document.getElementById("checkId2");
				var d = document.getElementById("checkId2").value;
				//alert(d);
				if( ! c.checked){
								  
								 To_uncheck(d);
								 //refresh();
					}
				
				}
			function  To_uncheck(d){
			
				$.ajax({
					url:"select",
					data:"productId="+d+"&checked="+0,
					type:"GET",
					success:function(result){
						
						 
					}
			  });
				refresh();
			}

			
		</script>
</head>
<body>

<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h3>购物车信息</h3>
			</div>
		</div>
		
		<!-- 按钮 -->
		 <div class="row">
		
			<!-- <div class="col-md-2 col-md-offset-10">
				<button class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span>清空购物车</button>
				<button class="btn btn-danger">删除</button>
			</div> -->
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						<!-- <th>
							
    							<label><input type="checkbox" value=""></label>
							
						</th>  -->
						<th>id</th>
						<th>userId</th>
						<th>productId</th>
						
						<th>主名</th>
						<th>副名</th>
						<th>主图</th>
						<th>数量</th>
						<th>单价</th>
						<th>总价</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${cartMsg}" var="cartMsg">
						<tr>
							<%-- <th>
								
									 <c:if test='${cartMsg.productChecked==0}'>
									 	
									    <input type="checkbox"  id="checkId1" name="checkbox" value="${cartMsg.productId }" onchange="ifChecked()">
									 </c:if>
    								 <c:if test='${cartMsg.productChecked==1}'>
    								 	
									 	<input type="checkbox"  id="checkId2" name="checkbox" value="${cartMsg.productId }" checked="checked" onchange="ifChecked2()">
									 </c:if>
								
							</th> --%>
							<th>${cartMsg.id }</th>
							<th>${cartMsg.userId }</th>
							<th>${cartMsg.productId }</th>
							
							<th>${cartMsg.productName }</th>
							<th>${cartMsg.productSubtitle }</th>
							<th><img alt="" src="<%=ipNignx%>${cartMsg.productMainImage }" width="100" height="100"></th>
							<th class="input-group ">
								
								<%-- <span class="input-group-addon minus" onclick="changeOper('-')">-</span>
								  	
								 <input type="text" id="input_num" class="number form-control input-sm" value="${cartMsg.quantity }" style="width:50px"/>
								  	
								 <span class="input-group-addon plus" onclick="changeOper('+')">+</span>
 --%>
								<button onclick="mathOper('-',${cartMsg.productId})">-</button>
								<input class="calResult" type="text" value="${cartMsg.quantity }">
								<button onclick="mathOper('+',${cartMsg.productId})">+</button>
							</th>
 							<th>${cartMsg.productPrice }</th>
							<th>${cartMsg.productTotalPrice }</th>
							<th>
								<a role="button" class="btn btn-danger btn-sm" href="delete_products?productIds=${cartMsg.productId }">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
									删除
								</a>
							</th>
						</tr>
						
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="row" id="showTotalPrice">
			
				<div class="col-md-6">总价${cartMsg2.data.cartTotalPrice}元</div>
				
		</div>
		
		
		<div class="row">
			<div class="col-md-2 col-md-offset-10">
				<a role="button" class="btn btn-success btn-sm"  href="${APP_PATH }/user/shipping/list?id=1">
									<span class="glyphicon glyphicon-check " aria-hidden="true"></span>
									选择收货地址
				</a>
			</div>	
		</div>
		
		
		
	</div>
	<script>
	
		/* function changeOper(oper){
			
			event.stopPropagation(); 
			var inputNum = document.getElementById("input_num");
			if (!inputNum) return;
			var curr = inputNum.value;
			
			if(isNaN(curr)) curr = 1; 
			switch(oper){
			case '+' :
				++curr;
				console.log(curr);
				break;
			case '-' :
				if(curr <= 0){
					break;
				}else{
				--curr;
				console.log(curr);}
				break;
			} 
			inputNum.value = curr;
		} 
		 */
		 function mathOper(oper,id,e){
			     /* 为了实现多种浏览器兼容 */
			     
				e = e || window.event;
		        e.target = e.target || e.srcElement; 
				var rsltEl = document.getElementsByClassName('calResult');
				for (var i = 0; i <rsltEl.length; i++) {
					if(e.target.parentNode.parentNode==rsltEl[i].parentNode.parentNode){//被点击的span标签和对应的input标签同祖父
						var curr = parseInt(rsltEl[i].value);
						if(isNaN(curr)) curr = 1;
						switch(oper){
						case '+':
							++ curr;
							break;
						case '-':
							if(--curr < 0) curr = 0;
							break;
						}
						rsltEl[i].value = curr;
						//更新数据库数据
						changeCount(curr,id);
					}
				};			
			}

			function changeCount(curr,id){
				
				$.ajax({
						url:"update",
						data:"count="+curr+"&productId="+id, 
						type:"POST",
						success:function(result){
								//alert(result.data.cartTotalPrice);
								show_current_totalPrice(result.data.cartTotalPrice);
							}
					});
				}

			function show_current_totalPrice(totalPrice){
				$("#showTotalPrice").empty();

				$("<div></div>").addClass("col-md-6").append(totalPrice).append("元")
				.appendTo("#showTotalPrice");
				 refresh();
				}
	</script>
</body>
</html>