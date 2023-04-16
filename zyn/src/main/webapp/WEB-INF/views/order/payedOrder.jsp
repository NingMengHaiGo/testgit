<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.zhao.deep.utils.ConfigReader"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
</head>
<body>
<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h3>已购历史</h3>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-2 col-md-offset-10">
				
				<button class="btn btn-primary btn-sm" onclick="window.history.back()">
					<span class="glyphicon glyphicon-level-up" aria-hidden="true"></span>
					返回原页面
				</button>
			
			</div>	
		</div>
		
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<h4>已购商品</h4>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						
						<th>订单号</th>
						<th>商品名</th>
						<th>商品图</th>
						
						<th>历史单价</th>
						<th>购买数量</th>
						<th>总价</th>
						<th>购买日期</th>
						
						
					</tr>
					<c:forEach items="${listPayedOrder}" var="PayedOrder">
						<tr>
							
							<th>${PayedOrder.orderNo }</th>
							<th>${PayedOrder.productName }</th>
							<th><img alt="" src="<%=ipNignx%>${PayedOrder.productImage }" width="100" height="100"></th>
							
							<th>${PayedOrder.currentUnitPrice }</th>
							<th>${PayedOrder.quantity }个</th>
							
							<th><span style="color:red;">${PayedOrder.totalPrice }元</span></th>
							<th>${PayedOrder.createTime }</th>
						</tr>
						
					</c:forEach>
				</table>
			</div>
		</div>
		
		
		
		
		
	</div>


</body>
</html>