<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<%
		pageContext.setAttribute("APP_PATH", request.getContextPath());
	
%>
		<script type="text/javascript" src="${APP_PATH }/static/js/jquery-1.12.4.min.js">
		</script>
		<link href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js">
		</script>
</head>
<body>
<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h3>收货地址信息</h3>
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
		
		<!-- 显示表格数据 -->
		<div class="row" style="margin-top:20px">
			<div class="col-md-12" >
				<table class="table table-hover" id="show_productMsg">
				    <thead>
					<tr>
						
						<th>已存收货地址</th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${shippingList.getList()}" var="shippingList">
					
						<tr>
							<th>
							收件人：${shippingList.receiverName }
							收件人：receiverPhone：${shippingList.receiverPhone }
							收件人人手机：${shippingList.receiverMobile }
							
							地址：${shippingList.receiverProvince }
							${shippingList.receiverCity }
							${shippingList.receiverDistrict }
							${shippingList.receiverAddress }
							邮编${shippingList.receiverZip }
							
							</th>
							
							<th>
							
								 <a class="btn btn-info btn-sm" href="order/addToOrder?shippingId=${shippingList.id }">
									<span class="glyphicon glyphicon-check" aria-hidden="true"></span>
									添加到订单</a>
								</a>
								
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
			<div class="col-md-6">当前 ${shippingList.pageNum }页,总${shippingList.pages }
				页,总 ${shippingList.total } 条记录</div>
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
				<ul class="pagination">
					<li><a href="${APP_PATH }/user/shipping/list?pn=1">首页</a></li>
					<c:if test="${shippingList.hasPreviousPage }">
						<li><a href="${APP_PATH }/user/shipping/list?pageNum=${shippingList.pageNum-1}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
					</c:if>


					<c:forEach items="${shippingList.navigatepageNums }" var="page_Num">
						<c:if test="${page_Num == shippingList.pageNum }">
							<li class="active"><a href="#">${page_Num }</a></li>
						</c:if>
						<c:if test="${page_Num != shippingList.pageNum }">
							<li><a href="${APP_PATH }/user/shipping/list?pageNum=${page_Num }">${page_Num }</a></li>
						</c:if>

					</c:forEach>
					<c:if test="${shippingList.hasNextPage }">
						<li><a href="${APP_PATH }/user/shipping/list?pageNum=${shippingList.pageNum+1 }"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:if>
					<li><a href="${APP_PATH }/user/shipping/list?pageNum=${shippingList.pages}">末页</a></li>
				</ul>
				</nav>
			</div>
		</div> 
		
	</div>
</body>
</html>