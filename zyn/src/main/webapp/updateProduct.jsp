<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.zhao.deep.utils.ConfigReader"%>
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
		<link href="${APP_PATH }/common/manage/css/salad.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js">		
		</script>
		<script type="text/javascript" src="${APP_PATH }/common/manage/upload.js">
		</script>
		
</head>
<body>
<div class="container">

<div class="col-md-2 col-md-offset-8">
					<button class="btn btn-primary btn-sm" onclick="window.history.back()">
				<span class="glyphicon glyphicon-level-up" aria-hidden="true"></span>
			返回原页面</button>
			</div>
		

		<div class="row">
			<div class="col-md-12">
				<h3>商品编辑</h3>
			</div>
			
		</div>
<hr>
		<div class="row">
			
					<form class="col-md-4 col-md-offset-2" action="save.action" method="post" enctype="multipart/form-data">
				
					  <div class="form-group">
					     商品名
					      <input type="hidden" class="form-control" id="id" name="id" value="${product.id}">
					      <input type="hidden" class="form-control" id="createTime" name="createTime" value="${product.createTime}">
					    <input type="text" class="form-control" id="name" name="name" value="${product.name}">
					     副标题
					    <input type="text" class="form-control" id="subtitle" name="subtitle" value="${product.subtitle}">
					   <!--  所属类别 -->
					    <input type="hidden" class="form-control" id="categoryId" name="categoryId" value="${product.categoryId}">
					    <!--  主图
					    <input type="file" class="form-control" id="file" name="file" title="上传图片"/>
					     附图
					    <input type="file" class="form-control" id="file" name="file" title="上传图片"/>
					     详细
					    <input type="file" class="form-control" id="file" name="file" title="上传图片"/> -->
					  <div class="am-g" style="margin-bottom:1%;margin-top:20px">
					    商品图片素材
					    </div>
					    <%-- 
					    <table>
						    <tr>
						    <th>主图<img alt="" src="<%=ipNignx%>${product.mainImage }" width="100" height="100"></th>
							<th>附图<img alt="" src="<%=ipNignx%>${product.subImages }" width="100" height="100"></th>
							<th>介绍图<img alt="" src="<%=ipNignx%>${product.detail }" width="100" height="100"></th> 
						    </tr>
					    </table><br> --%>
					    
					    
					    <div >
					        <div class="am-g" style="float: left;margin-left: 4%;margin-bottom:2rem;">
					        <div>
					           <div class="am-text-right" style="float: left;margin-right: 20px;">
					             	主图<span style="color:red;">*</span>         
					           </div>
					        	<div class="warper">
							        <div class="content">
							        	
										<img src="<%=ipNignx%>${product.mainImage }" class="img_replace0"/>
							            <input type="file" name="file" value=""  title="上传图片" onchange="getPhoto(this,'img_replace0')"  class="upload_box_select"/>	        	
							        </div>
							    </div> 
					        </div>
					        </div>
					        
					        <div class="am-g" style="float: left;margin-left: 4%;margin-bottom:2rem;">
					        <div>
					           <div class="am-text-right" style="float: left;margin-right: 20px;">
					                         附图<span style="color:red;">*</span>
					           </div>
					        	<div class="warper">
							        <div class="content">
							        	<img src="<%=ipNignx%>${product.subImages }" class="img_replace1"></img>
							            <input type="file" name="file" value="" title="上传图片" onchange="getPhoto(this,'img_replace1')"  class="upload_box_select"/>
							        </div>
							    </div> 
					        </div>
					        </div>
					        
					        <div class="am-g" style="float: left;margin-left: 4%;margin-bottom:2rem;">
					        <div>
					           <div class="am-text-right" style="float: left;margin-right: 20px;">
					                         详细<span style="color:red;">*</span>
					           </div>
					        	<div class="warper">
							        <div class="content">
							        	<img src="<%=ipNignx%>${product.detail }" class="img_replace2"></img>
							            <input  type="file" name="file" value="" title="上传图片" onchange="getPhoto(this,'img_replace2')" required class="upload_box_select"/>
							            <input type="hidden" name="filepath" id="filepath" value="" />
							        </div>
							    </div> 
					        </div>
					        </div>
					      </div>
					    
					  
					      
					     价格
					    <input type="text" class="form-control" id="price" name="price" value="${product.price}">
					     库存
					    <input type="text" class="form-control" id="stock" name="stock" value="${product.stock}">
					     
					  </div>
					  
					  <button type="submit" class="btn btn-danger btn-sm col-md-offset-10">编辑</button>
					  
					</form>
			
		</div>

</div>

</body>
</html>