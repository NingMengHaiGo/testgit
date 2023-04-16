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
<div class="container">

		

		<div class="row">
			<div class="col-md-12">
			<h3>新增收货地址</h3>
			</div>
		</div>
<hr>
		<div class="row">
			
					 <form class="col-md-4 col-md-offset-2" action="add" method="post">
					  收货人姓名
					  <input type="text" class="form-control" id="receiverName" name="receiverName"  placeholder="收货人姓名">
					  
					  <div class="form-group">
					    收货固定电话
					     <input type="text" class="form-control" id="receiverPhone" name="receiverPhone"  placeholder="0394-2496688">
					  </div>
					  <div class="form-group">
					    收货移动电话
					     <input type="text" class="form-control" id="receiverMobile" name="receiverMobile" placeholder="15038706691">
					  </div>
					  <div class="form-group">
					   省份
					     <input type="text" class="form-control" id="receiverProvince" name="receiverProvince" placeholder="河南省">
					  </div>
					   <div class="form-group">
					    城市
					     <input type="text" class="form-control" id="receiverCity" name="receiverCity" placeholder="南阳市">
					  </div>
					  <div class="form-group">
					    区/县
					     <input type="text" class="form-control" id="receiverDistrict" name="receiverDistrict"  placeholder="宛城区">
					  </div>
					  <div class="form-group">
					    详细地址
					     <input type="text" class="form-control" id="receiverAddress" name="receiverAddress"  placeholder="南阳理工学院">
					  </div>
					  <div class="form-group">
					    邮编
					     <input type="text" class="form-control" id="receiverZip" name="receiverZip"  placeholder="466600">
					  </div>
					  
					  <div>
					  		<button type="submit" class="btn btn-primary col-md-offset-10">提交</button>
					  		
					  </div>
					</form>
			
		</div>

</div>

</body>
</html>