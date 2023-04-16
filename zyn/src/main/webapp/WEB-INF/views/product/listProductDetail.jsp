<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
</head>
<body>
<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h3>商品详细信息</h3>
			</div>
			<div class="col-md-2 col-md-offset-8">
					<button class="btn btn-primary btn-sm" onclick="window.history.back()">
				<span class="glyphicon glyphicon-level-up" aria-hidden="true"></span>
			返回原页面</button>
			</div>
			
		</div>
			<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						<th>#编号</th><th>${product.id }</th>
					</tr>
					<tr>	
						<th>商品名</th><th>${product.name }</th>
					</tr>
					<tr>
						<th>商品介绍</th><th>${product.subtitle }</th>
					</tr>
					<tr>
						<th>主图</th><th><img alt="" src="<%=ipNignx%>${product.mainImage }" width="100" height="100"></th>
						<th>附图</th><th><img alt="" src="<%=ipNignx%>${product.subImages }" width="100" height="100"></th>
						<th>介绍图</th><th><img alt="" src="<%=ipNignx%>${product.detail }" width="100" height="100"></th> 
					</tr>
					<tr>
						<th>库存</th><th>${product.stock }</th>
						<th>单价</th><th>${product.price }</th>
					</tr>
					<tr>
						<th>是否上架</th><th>${product.status=="1"?"已上架":"未上架" }</th>
						<th>上架时间</th><th>${product.createTime }</th>
					</tr>
					<tr>
						<th>商家描述</th><th>该商品店家${product.userName }，</th><th>${user.createTime }来到至诚二手交易市场天，</th><th>实人认证已通过，信誉保证</th>
					</tr>
					<tr>
						<th>商家电话：</th><th>${user.phone }</th>
						<th>邮箱：${user.email }</th>
					</tr>
				</table>
			</div>
		</div>
</div>
</body>
</html>