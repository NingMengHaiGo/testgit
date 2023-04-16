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

<!-- <div class="col-md-2 col-md-offset-8">
					<button class="btn btn-primary btn-sm" onclick="window.history.back()">
				<span class="glyphicon glyphicon-level-up" aria-hidden="true"></span>
			返回原页面</button>
			</div>
 -->
		

		<div class="row">
			<div class="col-md-12">
				<h3>密码重置</h3>
			</div>
			
		</div>
<hr>
		<div class="row">
			
					<form class="col-md-4 col-md-offset-2" action="reset_password.action" method="post">
					  <div class="form-group">
					    <h4>用户名:${userMsg.username}</h4>
					  </div>
					 
					  <div class="form-group">
					    <h4>请输入您的旧密码</h4>
					    <input type="password" class="form-control" id="passwordOld" name="passwordOld" placeholder="请输入您旧密码">
					    
					  </div>
					  
					   <div class="form-group">
					    <h4>请输入您的新密码</h4>
					    <input type="password" class="form-control" id="passwordNew" name="passwordNew" placeholder="请输入您新密码">
					    
					  </div>
					  
					  <button type="submit" class="btn btn-primary col-md-offset-10">提交</button>
					</form>
			
		</div>

</div>
</body>
</html>