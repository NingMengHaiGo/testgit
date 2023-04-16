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
	<script type="text/javascript" src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>		
	<link href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<script src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
	
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
				<h1 class="col-md-offset-5">用户注册</h1>
			</div>
		</div>
		<hr>

		<div class="row">
			
					<form class="col-md-3 col-md-offset-5" action="user/register.action" method="post">
				
					  <div class="form-group">
					     <h4>用户名</h4>
					    <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名">
					     <h4>密码</h4>
					    <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
					     <h4>邮箱</h4>
					    <input type="text" class="form-control" id="email" name="email" placeholder="请输入邮箱">
					     <h4>电话</h4>
					    <input type="text" class="form-control" id="phone" name="phone" placeholder="请输入您的联系方式">
					     <h4>密码找回问题</h4>
					    <input type="text" class="form-control" id="question" name="question" placeholder="请输入密码找回问题">
					     <h4>找回问题回答</h4>
					    <input type="text" class="form-control" id="answer" name="answer" placeholder="请输入找回问题回答">
					     
					  </div>
					  
					  <button type="submit" class="btn btn-danger">提交</button>
					  <button type="reset" class="btn btn-success">重置</button>
					</form>
			
		</div>

</div>
<!-- <form action="user/register.action" method="post">
    UserName:<input type="text" name="username"/><br>
    PassWord:<input type="password" name="password"/><br>
    Email:<input type="text" name="email"/><br>
    Phone:<input type="text" name="phone"/><br>
    Question:<input type="text" name="question"/><br>
    Answer:<input type="text" name="answer"/><br>
    <input type="submit" value="submit">
    <input type="reset" value="reset">
</form> -->
</body>
</html>