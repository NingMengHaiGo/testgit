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
				<h3>订单信息</h3>
			</div>
		</div>
		
		
		
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<h4>商品信息</h4>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						
						<!-- <th>id</th>
						<th>userId</th>
						<th>productId</th> -->
						
						<!-- <th>主名</th>
						<th>副名</th>
						<th>主图</th>
						<th>数量</th>
						<th>单价</th>
						<th>总价</th> -->
						
					</tr>
					<c:forEach items="${cartMsg}" var="cartMsg">
						<tr>
							
							<%-- <th>${cartMsg.id }</th>
							 <th>${cartMsg.userId }</th>
							<th>${cartMsg.productId }</th> --%>
							
							<th>产品类别：${cartMsg.productName }</th>
							<th>产品名：${cartMsg.productSubtitle }</th>
							<th><img alt="" src="<%=ipNignx%>${cartMsg.productMainImage }" width="100" height="100"></th>
							<th>数量：${cartMsg.quantity }个</th>
							
							<th>单价：${cartMsg.productPrice }元</th>
							<th>总价：<span style="color:red;">${cartMsg.productTotalPrice }元</span></th>
							
						</tr>
						
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="row col-md-offset-9">
			
				<div class="col-md-6"><span style="color:red;">总价${cartMsg2.data.cartTotalPrice}元</span></div>
				
		</div>
		
		<div class="row">
			<div class="col-md-12">
				<h4>收货地址信息</h4>
			</div>
		</div>
		<div class="row" style="margin-top:20px">
			<div class="col-md-12" >
				<table class="table table-hover">
				<tr>
							<th>
							收件人：${selectedShipping.receiverName }
							收件人receiverPhone：${selectedShipping.receiverPhone }
							收件人人手机：${selectedShipping.receiverMobile }
							
							地址：${selectedShipping.receiverProvince }
							${selectedShipping.receiverCity }
							${selectedShipping.receiverDistrict }
							${selectedShipping.receiverAddress }
							邮编${selectedShipping.receiverZip }
							
							</th>
					</tr>		
				</table>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-2 col-md-offset-10">
				<a role="button" class="btn btn-danger btn-sm"  href="order?shippingId=${selectedShipping.id }&status=0">
									<span class="glyphicon glyphicon-check " aria-hidden="true"></span>
									付款
				</a>
				
				<button class="btn btn-primary btn-sm" onclick="window.history.back()">
					<span class="glyphicon glyphicon-level-up" aria-hidden="true"></span>
					取消订单
				</button>
				
				
			</div>	
		</div>
		
		
		
	</div>

</body>
</html>