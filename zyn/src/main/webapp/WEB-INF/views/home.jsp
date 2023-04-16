<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<div class="container">

		<div class="row">
			<div class="col-md-12">
				<h1 class="col-md-offset-5"></h1>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12">
				<h1 class="col-md-offset-5"></h1>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<h3 class="col-md-offset-5">信息均在此处显示</h3>
			</div>
		</div>

		

</div>
</body>
</html>