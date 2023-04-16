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
		
		<script type="text/javascript">
			function select(){
				var MistsSubtitle = document.getElementById("subtitle").value;
				//alert(MistsSubtitle);
				$.ajax({
					url:"selectByMists",
					data:"subtitle="+MistsSubtitle,
					type:"POST",
					success:function(result){
							 build_product_table(result);
							 
						}
				  });
				}

			function build_product_table(result){

				
				 $("#select_bycondition").empty();
				 $("#page_table").empty();
				 $("#show_productMsg tbody").empty();
				
				$.each(result.data,function(index,item){
					
					
					
					/*  var producttable = $('<table class="table table-hover"></table>'); */
					/*  var tableTr1 = $('<tr>'); */
					 var tableTd1 = $('<th></th>').append(item.id);
					 var tableTd2 = $('<th></th>').append(item.name);
					 var tableTd3 = $('<th></th>').append(item.subtitle);
					 var tableTd4 = $('<th></th>').append(item.price);
					 var tableTd5 = $('<th></th>').append(item.stock);
					 var tableTd6 = $('<th></th>').append(item.status=="1"?"已上架":"未上架" );
					 
					 var editBtn= $("<button></button>").addClass("btn btn-info btn-sm edit_btn").
					 	 append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
					 editBtn.attr("edit-id",item.id);

					 var detailBtn= $("<button></button>").addClass("btn btn-success btn-sm detail_btn").
				 	 append($("<span></span>").addClass("glyphicon glyphicon-list-alt")).append("详细信息");
					 detailBtn.attr("detail-id",item.id);

					 var toCartBtn= $("<button></button>").addClass("btn btn-warning btn-sm toCart_btn").
				 	 append($("<span></span>").addClass("glyphicon glyphicon-shopping-cart")).append("加入购物车");
					 toCartBtn.attr("toCart-id",item.id);

					 var delBtn= $("<button></button>").addClass("btn btn-danger btn-sm del_btn").
				 	 append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
					 delBtn.attr("del-id",item.id);
				 	 
					 var btnTd = $("<td></td>").append(editBtn).append(" ").append(detailBtn).append(" ").append(toCartBtn)
					 	.append(" ").append(delBtn);
					 
					$('<tr></tr>').append(tableTd1)
					.append(tableTd2)
					.append(tableTd3)
					.append(tableTd4)
					.append(tableTd5)
					.append(tableTd6)
					.append(btnTd)
					.appendTo("#show_productMsg tbody");
					})
				}

			//jquery新版没有live，使用on进行替代
			//编辑
			$(document).on("click",".edit_btn ",function(){
			
				/* alert($(this).attr("edit-id")); */
				var editId=$(this).attr("edit-id");
				
				window.location.href ="toUpdateProduct?id="+editId;
			});

			//显示详细信息
			$(document).on("click",".detail_btn ",function(){
				
				/* alert($(this).attr("detail-id")); */
				var detailId=$(this).attr("detail-id");
				
				window.location.href ="listProductwithDetail?id="+detailId;
			});

			//加入购物车
			 $(document).on("click",".toCart_btn ",function(){
				
				/* alert($(this).attr("toCart-id")); */
				var toCartId=$(this).attr("toCart-id");
				
				window.location.href ="cart/add?productId="+toCartId+"&count="+1;
				
			}); 

			//删除
			$(document).on("click",".del_btn ",function(){
				
				/* alert($(this).attr("del-id")); */
				var delId=$(this).attr("del-id");
				
				window.location.href ="deleteProduct?id="+delId;
			});
		
		</script>
		
</head>
<body>
<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h3>商品信息</h3>
			</div>
		</div>
		<!-- 搜索 -->
		<div class="row" id="select_table">
			<!-- <form class="form-inline col-md-offset-8" action="selectByMists" method="post"> -->
			<form class="form-inline col-md-offset-8">
				<div class="form-group">
					<input type="text" class="form-control" id="subtitle" name="subtitle" placeholder="模糊搜索">
					<a class="btn btn-primary btn-sm" type="submit" onclick="select()">
						<span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>
						搜索
					</a>
				</div>
			</form>
		</div>
		
		<!-- 按条件查询 -->
		<%-- <div class="row" id="select_bycondition">
			<div class="col-md-12">
			<table>
				<tr>
					<td>
						<span style="color:red;">根目录类别:</span>
			
										<select name="categoryId1" onchange="func()"> 
				                        	<c:forEach items="${listCategory}" var="Category">
				                        		<option value=" "></option>
							           			<option value="${Category.id}">${Category.name}</option>
							           			
						           			</c:forEach>
						           		</select> 
				    </td>
				    <td>     		
						<span style="color:red;">分目录类别:	</span>	
					</td>
			
				</tr>
			</table>
			</div>	      
		</div> --%>
		
		
		<!-- 显示表格数据 -->
		<div class="row" style="margin-top:20px">
			<div class="col-md-12" >
				<table class="table table-hover" id="show_productMsg">
				    <thead>
					<tr>
						<th>#</th>
						<th>商品名</th>
						<th>商品副标题</th>
						
						<th>价格</th>
						<th>库存</th>
						<th>是否上架</th>
						<th>发布者</th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${pageInfo.list}" var="proMsg">
					
						<tr>
							<th>${proMsg.id }</th>
							<th>${proMsg.name }</th>
							<th>${proMsg.subtitle }</th>
							
							<th>${proMsg.price }</th>
							<th>${proMsg.stock }</th>
							<th>${proMsg.status=="1"?"已上架":"未上架" }</th>
							<th>${proMsg.userName }</th>
							<th>
								<button class="btn btn-info btn-sm">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><a href="toUpdateProduct?id=${proMsg.id}">
									编辑</a>
								</button>
								<button class="btn btn-success btn-sm">
									<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span><a href="listProductwithDetail?id=${proMsg.id}">
									详细信息</a>
								</button>
								<button class="btn btn-warning btn-sm">
									<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span><a href="cart/add?productId=${proMsg.id}&&count=1">
									加入购物车</a>
								</button>
								<button  class="btn btn-danger btn-sm">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span><a href="deleteProduct?id=${proMsg.id}">
									删除</a>
								</button>
								
							</th>
						</tr>
						
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		
		
		<!-- 显示分页信息 -->
		<div class="row" id="page_table">
			<!--分页文字信息  -->
			<div class="col-md-6">当前 ${pageInfo.pageNum }页,总${pageInfo.pages }
				页,总 ${pageInfo.total } 条记录</div>
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
				<ul class="pagination">
					<li><a href="${APP_PATH }/user/getProductMessage?pn=1">首页</a></li>
					<c:if test="${pageInfo.hasPreviousPage }">
						<li><a href="${APP_PATH }/user/getProductMessage?pn=${pageInfo.pageNum-1}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
					</c:if>


					<c:forEach items="${pageInfo.navigatepageNums }" var="page_Num">
						<c:if test="${page_Num == pageInfo.pageNum }">
							<li class="active"><a href="#">${page_Num }</a></li>
						</c:if>
						<c:if test="${page_Num != pageInfo.pageNum }">
							<li><a href="${APP_PATH }/user/getProductMessage?pn=${page_Num }">${page_Num }</a></li>
						</c:if>

					</c:forEach>
					<c:if test="${pageInfo.hasNextPage }">
						<li><a href="${APP_PATH }/user/getProductMessage?pn=${pageInfo.pageNum+1 }"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:if>
					<li><a href="${APP_PATH }/user/getProductMessage?pn=${pageInfo.pages}">末页</a></li>
				</ul>
				</nav>
			</div>
		</div>
		
	</div>

</body>
</html>