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
			<h3>个人信息</h3>
				<!-- <h3 class="col-md-offset-5">个人信息</h3> -->
			</div>
		</div>
<hr>
		<div class="row">
			
					<!-- <form class="col-md-3 col-md-offset-5" action="update_information.action" method="post"> -->
					 <form class="col-md-4 col-md-offset-2" action="update_information.action" method="post">
					  	用户名
					  <input type="text" class="form-control" id="username" name="username"  placeholder="${response.data.username}" readonly>
					  
					  <div class="form-group">
					    邮箱
					     <input type="text" class="form-control" id="email" name="email"  value="${response.data.email}">
					  </div>
					  <div class="form-group">
					    电话
					     <input type="text" class="form-control" id="phone" name="phone" value="${response.data.phone}">
					  </div>
					  <div class="form-group">
					    验证问题
					     <input type="text" class="form-control" id="question" name="question" value="${response.data.question}">
					  </div>
					   <div class="form-group">
					    验证答案
					     <input type="text" class="form-control" id="answer" name="answer" value="${response.data.answer}">
					  </div>
					  <div class="form-group">
					    创建时间
					     <input type="text" class="form-control" id="createTime" name="createTime"  placeholder="${response.data.createTime}" readonly>
					  </div>
					  <div class="form-group">
					    上次更新时间
					     <input type="text" class="form-control" id="updateTime" name="updateTime"  placeholder="${response.data.updateTime}" readonly>
					  </div>
					  <div>
					  		<button type="submit" class="btn btn-primary col-md-offset-10">更新</button>
					  		<!-- <button onclick="window.location.href('toResetPassWord.action')" class="btn btn-primary">修改密码</button> -->
					  </div>
					</form>
			
		</div>

</div>

</body>
</html>