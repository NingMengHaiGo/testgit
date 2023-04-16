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
				<h1 class="col-md-offset-5">密码找回</h1>
			</div>
		</div>

		<div class="row">
			
					<form class="col-md-2 col-md-offset-5" action="forget_check_answer.action" method="post">
					  <div class="form-group">
					    <h4>用户名:${username}</h4>
					  </div>
					  <div class="form-group">
					    <h4>验证问题:${question.data}</h4>
					  </div>
					  <div class="form-group">
					    <h4>您的答案</h4>
					    <input type="hidden" id="username" name="username" value="${username}">
					    <input type="hidden" id="question" name="question" value="${question.data}">
					    <input type="text" class="form-control" id="answer" name="answer" placeholder="请输入您的答案">
					    
					  </div>
					  
					  <button type="submit" class="btn btn-primary">提交</button>
					</form>
			
		</div>

</div>
</body>
</html>