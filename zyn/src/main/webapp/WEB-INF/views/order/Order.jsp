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
				<h3>订单信息</h3>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-md-2 col-md-offset-10">
			<a class="btn btn-danger" id="exportexcel" href="exportOrder">
				<span class="glyphicon glyphicon-open-file">
				导出订单数据</span>
			</a>
			</div>
		</div>
		
		<div class="row" style="margin-top: 20">
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						
						<!-- <th>id</th> -->
						<th>订单号</th>
						<th>付款金额</th>
						
						<th>付款时间</th>
						<th>创建时间</th>
						<th>预计发货时间</th>
						<th>预计到达时间</th>
						
						
					</tr>
					<c:forEach items="${listOrder}" var="listOrder">
						<tr>
							
							<%-- <th>${listOrder.id }</th> --%>
							<th>${listOrder.orderNo }</th>
							<th>${listOrder.payment }<span style="color:red;">元</span></th>
							
							<th>${listOrder.createTime }</th>
							<th>${listOrder.createTime }</th>
							
							<th>${listOrder.createTime }</th>
							
							<th>${listOrder.createTime }</th>
							
							
						</tr>
						
					</c:forEach>
				</table>
			</div>
		</div>
	
	</div>
</body>
</html>