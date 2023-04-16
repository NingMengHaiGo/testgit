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
						<th>#</th>
						<th>商品名</th>
						<th>商品副标题</th>
						<th>主图</th>
						<th>附图</th>
						<th>介绍图</th>
						<th>价格</th>
						<th>库存</th>
						<th>是否上架</th>
						
					</tr>
				<c:forEach items="${products}" var="proMsg">
						<tr>
							<th>${proMsg.id }</th>
							<th>${proMsg.name }</th>
							<th>${proMsg.subtitle }</th>
			
							<th><img alt="" src="<%=ipNignx%>${proMsg.mainImage }" width="100" height="100"></th>
							<th><img alt="" src="<%=ipNignx%>${proMsg.subImage }" width="100" height="100"></th>
							<th><img alt="" src="<%=ipNignx%>${proMsg.detail }" width="100" height="100"></th> 
							<th>${proMsg.price }</th>
							<th>${proMsg.stock }</th>
							<th>${proMsg.status=="1"?"已上架":"未上架" }</th>
							
						</tr>
						
				</c:forEach>
				</table>
			</div>
		</div>
</div>
</body>
</html>