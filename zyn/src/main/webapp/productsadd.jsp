<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
 <%-- <%
		pageContext.setAttribute("APP_PATH", request.getContextPath());
	%>
	<script type="text/javascript" src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>		
	<link href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<script src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script> --%>
</head>
<body>
<!-- <div class="container">

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
				<h1 class="col-md-offset-5">商品添加</h1>
			</div>
		</div>

		<div class="row">
			
					<form class="col-md-2 col-md-offset-5" action="user/save.action" method="post" enctype="multipart/form-data">
				
					  <div class="form-group">
					     <h4>商品名</h4>
					    <input type="text" class="form-control" id="name" name="name" placeholder="请输入商品名">
					     <h4>副标题</h4>
					    <input type="text" class="form-control" id="subtitle" name="subtitle" placeholder="请输入副标题">
					     <h4>主图</h4>
					    <input type="file" class="form-control" id="file" name="file" title="上传图片"/>
					     <h4>附图</h4>
					    <input type="file" class="form-control" id="file" name="file" title="上传图片"/>
					     <h4>详细</h4>
					    <input type="file" class="form-control" id="file" name="file" title="上传图片"/>
					     <h4>价格</h4>
					    <input type="text" class="form-control" id="price" name="price" placeholder="请输入改商品价格">
					     <h4>库存</h4>
					    <input type="text" class="form-control" id="stock" name="stock" placeholder="请输入改商品库存">
					     
					  </div>
					  
					  <button type="submit" class="btn btn-danger">提交</button>
					  <button type="reset" class="btn btn-success">重置</button>
					</form>
			
		</div>

</div> -->
<form action="user/save.action" method="post" enctype="multipart/form-data">
	name:<input type="text" name="name" /><br>
	subtitle:<input type="text" name="subtitle" /><br>
	mainImage:<input type="file" name="file" title="上传图片"/><br>
	subImage:<input type="file" name="file" title="上传图片"/><br>
	detail:<input type="file" name="file" title="上传图片"/><br>
	price:<input type="text" name="price" /><br>
	stock:<input type="text" name="stock" /><br>
	<input type="submit" value="submit"/> 
	<input type="reset" value="reset" />
</form> 

</body>
</html>