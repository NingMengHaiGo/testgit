<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<p><a href="user/logout.action"></a>退出登陆</p>
<p>用户登陆的信息</p>
<table>
	<tr>
		<td>${Response.status}</td><td>${Response.msg}</td>
	</tr>
	<tr>
		<td>${Response.data.username}</td><td>${Response.data.email}</td><td>${Response.data.phone}</td><td>${Response.data.question}</td>
		<td>${Response.data.answer}</td><td>${Response.data.role}</td><td>${Response.data.createTime}</td><td>${Response.data.updateTime}</td>
	</tr>

</table>

</body>
</html>