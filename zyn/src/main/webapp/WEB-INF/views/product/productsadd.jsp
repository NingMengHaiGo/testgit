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
		<link href="${APP_PATH }/common/manage/css/salad.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js">
		</script>
		<script type="text/javascript" src="${APP_PATH }/common/manage/upload.js">
		</script>
		
		
		<script type="text/javascript">

		
		function func(){
			var vs = $('select  option:selected').val();
			$.ajax({
				url:"category/get_category2",
				data:"categoryId="+vs,
				type:"GET",
				success:function(result){
						 build_select_table(result); 
						// alert(result.data[1].id);
						/* alert(result.data[1].name);
 					    $.each(result.data,function(){ 
							 alert(name); 
							var i;
							for(i=0;i<=result.data.length;i++){
								var name= result.data[i].name;
							console.log(name);
							}
						})  */
					}
			  });
			}

		function build_select_table(result){
			//debugger
			 $("#select_table").empty(); 
			$.each(result.data,function(index,item){
				var selectTable=$("<select></select>");
				var nullOption=$("<option></option>");
				var IdAndName=$("<option></option>").append(item.name);
			})
			
			  var select = $('<select id="myselect" name="categoryId2" onchange="func2()"></select>');
			  for(var i=0;i<result.data.length;i++){
				  select.append('<option value="'+result.data[i].id+'">'+result.data[i].name+'</option>').appendTo("#select_table");
				  }
			}

		function func2(){
			
			var vs2 = $('#myselect  option:selected').val();
			$.ajax({
				url:"category/get_category2",
				data:"categoryId="+vs2,
				type:"GET",
				success:function(result){
						 build_select_table2(result); 
						// alert(result.data[1].id);
						
					}
			  });
		
			}

		function build_select_table2(result){
			 $("#select_table2").empty(); 
			$.each(result.data,function(index,item){
				var selectTable=$("<select></select>");
				var nullOption=$("<option></option>");
				var IdAndName=$("<option></option>").append(item.name);

			})
			
			  var select = $('<select name="categoryId2"></select>');
			  for(var i=0;i<=result.data.length;i++){
				  select.append('<option value="'+result.data[i].id+'">'+result.data[i].name+'</option>').appendTo("#select_table2");
				  }
			}
		

		
		</script>
		
		
</head>




<body>
<div class="modal fade container" id="upload_modal">

		

		<div class="row">
			<div class="col-md-12">
				<h3>商品添加</h3>
			</div>
		</div>
<hr>
		<div class="row">
			
					
					
				<form action="save.action" method="post" enctype="multipart/form-data">
				
					   <div class="am-g am-margin-top-sm" style="margin-bottom:1%;margin-top:-1%;">
				           <div class=" am-u-md-2 am-text-right">
				                        商品名称:
				                        <input name="name" type="text" id="name" placeholder="请输入商品名"  class="am-input-sm">
				                        商品副名:
				                        <input name="subtitle" type="text" id="subtitle" placeholder="请输入商品副名"  class="am-input-sm">
				                        所属类别:
				                        
				                        <select name="categoryId1" onchange="func()"> 
				                        	<c:forEach items="${listCategory}" var="Category">
				                        		<option value=" "></option>
							           			<option value="${Category.id}">${Category.name}</option>
							           			
						           			</c:forEach>
						           		</select> 
						           		
						           		 <div  id="select_table" style="margin-left:556px">
						           		</div> 
						           		
						           		 <div  id="select_table2" style="margin-left:556px">
						           		</div> 
						           		          
				           </div>
				        
        				</div>
        				
        				
        				<div class="am-g" style="margin-bottom:1%; margin-top:20px">
					          <div class=" am-u-md-1 am-text-right">
					                        商品单价: 
					                        <input name="price" type="text" id="price" onkeyup="value=value.replace(/[^\d.]/g,'')" 
					                        required class="am-input-sm"><span style="color:red;">￥</span>
					                        上货数量:
					                        <input name="stock" type="text" id="stock" required class="am-input-sm">
					          </div>
					       
		       			</div>
        				
        				<div class="am-g" style="margin-bottom:1%;margin-top:20px">
        				图片添加
        				</div>
        				
        				<div style="margin-top:20px">
					        <div class="am-g" style="float: left;margin-left: 4%;margin-bottom:2rem;">
					        <div>
					           <div class="am-text-right" style="float: left;margin-right: 20px;">
					             	主图<span style="color:red;">*</span>         
					           </div>
					        	<div class="warper">
							        <div class="content">
							        	
										<img src="${APP_PATH }/common/image/upload.png" class="img_replace0"/>
							            <input type="file" name="file" value="" title="上传图片" onchange="getPhoto(this,'img_replace0')" required class="upload_box_select"/>	        	
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
							        	<img src="${APP_PATH }/common/image/upload.png" class="img_replace1"></img>
							            <input type="file" name="file" value="" title="上传图片" onchange="getPhoto(this,'img_replace1')" required class="upload_box_select"/>
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
							        	<img src="${APP_PATH }/common/image/upload.png" class="img_replace2"></img>
							            <input  type="file" name="file" value="" title="上传图片" onchange="getPhoto(this,'img_replace2')" required class="upload_box_select"/>
							            <input type="hidden" name="filepath" id="filepath" value="" />
							        </div>
							    </div> 
					        </div>
					        </div>
					      </div>
        				
        				<br>
        				
        				
					 
					 <!-- <div class="am-g am-margin-top-sm" style="margin-left:15%;">
			            <div class="am-u-sm-offset-2">
			                <button type="submit" class="am-btn am-btn-primary">添加</button>
			                <button type="button" style="margin-left:10%;" class="am-btn am-btn-primary" onclick="javascript:history.back(-1);">返回</button>
			            </div>
			        </div> -->
			        
			        <div class="am-g" style="margin-bottom:1%;clear: both;margin-top:20px">
        				操作
        				</div>
					 
					  <div class="am-g am-margin-top-sm" style="clear: both; margin-top:20px">
					  
					   	<div >
						  <button type="submit" class="btn btn-danger btn-sm">提交</button>
						  <button type="reset" style="margin-left:5%;" class="btn btn-success btn-sm">重置</button>
						 </div>
					  </div> 
					  
				</form>
			
		</div>

</div>



 


</body>
</html>