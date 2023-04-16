<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.zhao.deep.utils.ConfigReader"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ListCategory</title>
<%
		pageContext.setAttribute("APP_PATH", request.getContextPath());
		String ipNignx = (String) ConfigReader.getValue("nginx.ip");
	%>
		<script type="text/javascript" src="${APP_PATH }/static/js/jquery-1.12.4.min.js">
		</script>
		<link href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js">
		</script>
		<script type="text/javascript">

		 function showCategory(id,pid,state){
			var categoryId = id;
			var parentId = pid;
			var state = state;
			/* alert(id);
			alert(pid); */
			if(parentId==0)
				{ 
					rebulid_addCategory2(categoryId);
				}
			
			  $.ajax({
					url:"get_category2",
					data:"categoryId="+categoryId,
					type:"GET",
					success:function(result){
						
						//alert(result.data[0].parentId);
						 rebulid_addCategory(result);
						 rebuild_category(result,state);
						/*  rebulid_addCategory(result); */
					}
				 });
			}
			 
		 function  rebulid_addCategory2(Id){

			var categoryId = Id;
			 $("#addCategory").empty();            
			 $("#categoryMessage").empty();
			 var categoryAdd = $("<button></button>").addClass("btn btn-primary btn-sm add_Category").append($("<span></span>").
					 addClass("glyphicon glyphicon-plus")).append("添加类别");
			 categoryAdd.attr("categoryId",categoryId);
			 categoryAdd.appendTo("#addCategory");
			 //alert(categoryId);
			 } 
		 
		 
		  function  rebulid_addCategory(result){
			
			 var categoryId = result.data[0].parentId;
			
			 $("#addCategory").empty();
			 var categoryAdd = $("<button></button>").addClass("btn btn-primary btn-sm add_Category").append($("<span></span>").
					 addClass("glyphicon glyphicon-plus")).append("添加类别");
			 categoryAdd.attr("categoryId",categoryId);
			 categoryAdd.appendTo("#addCategory");
			 } 
			 
			//触发第二次添加函数
		  $(document).on("click",".add_Category ",function(){
			 
				/* alert($(this).attr("edit-id")); */
				var categoryId=$(this).attr("categoryId");
				 
				/*  $("#emp_add_modal_btn").modal({
			           // backdrop: false, // 相当于data-backdrop
			           // keyboard: false, // 相当于data-keyboard
			            //show: true, // 相当于data-show
			            //remote: "" // 相当于a标签作为触发器的href
			        }); */
			     $("#emp_add_modal_btn2").modal();
				 $("#categoryId").val(categoryId);
			        
			});
		//构建二次分类
		 function rebuild_category(result,state){
			 $("#categoryMessage").empty();
			var categoryMsg = result.data;
			var state = state;
			//alert(state);
			
			$.each(categoryMsg,function(index,item){
				var Tr = $("<tr></tr>");
			    var Th = $("<th></th>");
			    if(state == 0){
			    	var CategoryMsg = $("<button></button>").addClass("btn btn-default show_category3").append(item.name);
				    CategoryMsg.attr("cid2",item.id);
				    CategoryMsg.attr("pid2",item.parentId);
				 }else{
					 var CategoryMsg = $("<button></button>").addClass("btn btn-default show_product2").append(item.name);
					    CategoryMsg.attr("cid2",item.id);
				}
				
				var CategoryMsgs = $("<span></span>").append(CategoryMsg);
				
				var DeleteCategory = $("<button></button>").addClass("btn btn-danger  del_btn").append($("<span></span>").
						addClass("glyphicon glyphicon-trash")).append("删除");
				//var DeleteCategorys = $("<th></th>").append(DeleteCategory);

				var EditCategory=  $("<button></button>").addClass("btn btn-primary  edit_btn")
				.append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
				EditCategory.attr("categoryId2",item.id);
				//var EditCategorys = $("<th></th>").append(EditCategory);
				$("<table></table>").addClass("table").append(Tr).append(Th).append(CategoryMsgs)
						   .append(EditCategory)
						   .appendTo("#categoryMessage")
				
				});
			 }

		//zhao update 第三次分类
		  $(document).on("click",".show_category3 ",function(){
			  var id=$(this).attr("cid2");
			  var pid=$(this).attr("pid2");
			  /* alert(id);
			  alert(pid); */
			  var state = 1;
			  showCategory(id,pid,state);
		  });
		 //显示商品
		  $(document).on("click",".show_product2 ",function(){
			  var cid=$(this).attr("cid2");
			   // alert(cid);
			    $.ajax({
					url:"showProducts",
					data:"cid="+cid,
					type:"GET",
					success : function(result){
						 showProduct(result);
				
				 }
			  });
		  });
		  
		  function showProduct(result){
					//alert(result[0].id);
					 $("#bbb").empty();
					 $("#ddd").empty();
					 $("#addCategory").empty();
					 $("#categoryMessage").empty();
					 $("<div></div>").addClass("col-md-12").append("当前类别已有商品").appendTo("#aaa");
					$.each(result,function(index,item){
						var Tr= $("<tr></tr>");
						
						var tableTd1 = $('<th></th>').append("书名:").append(item.name);
						var tableTd2 = $('<th></th>').append("副名:").append(item.subtitle);
						var tableTd3 = $('<th></th>').append("商品ID").append(item.id);
						var tableTd4 = $('<th></th>').append("类别ID").append(item.categoryId);
						
						<%-- var tableTd3 = $('<th></th>').append("<img src=<%=ipNignx%>+item.mainImage >");
						var tableTd4 = $('<th></th>').append('<%=ipNignx%>').append(item.subImage);
						var tableTd5 = $('<th></th>').append('<%=ipNignx%>').append(item.detail);  --%>
						var tableTd6 = $('<th></th>').append("价格:").append(item.price);
						var tableTd7 = $('<th></th>').append("库存:").append(item.stock);
						var tableTd8 = $('<th></th>').append(item.status=="1"?"已上架":"未上架" );
						$("<table></table>").addClass("table").append(Tr).append(tableTd3).append(tableTd4)
						.append(tableTd1).append(tableTd2)
						  .append(tableTd6)
						  .append(tableTd7).append(tableTd8)
						  .appendTo("#categoryMessage")
						});
					
			  } 
		 
		 
		 //触发第二次编辑
		  $(document).on("click",".edit_btn ",function(){
			 
				/* alert($(this).attr("edit-id")); */
			 	var id=$(this).attr("categoryId2"); 
				
				//alert(id);
			      $("#edit_modal_btn2").modal();
				 $("#Id2").val(id); 
			        
			});

				//第一次编辑 
				  function editCategory(categoryId){
					// alert(categoryId);
					 $("#edit_modal_btn").modal();
					 $("#Id").val(categoryId);
			    }

				//第一次添加下级分类
				 function addNextCategory(categoryId){
					 alert(categoryId);
					 $("#emp_add_modal_btn2").modal();
					 $("#categoryId").val(categoryId);
			    }
		</script> 
</head>
<body>


<!-- 添加的模态框2 -->
<div class="modal fade" id="emp_add_modal_btn2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
       <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">类别添加</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" action="${APP_PATH }/user/category/add_category" method="post">
		 
		 	<!--  <label class="col-sm-2 control-label" type="hidden">类别id</label> -->
		 
		 	 	<input name="parentId" id="categoryId" type="hidden" class="form-control" value="categoryId" placeholder="类别id">
		 	
		   
		    <label class="col-sm-2 control-label">类别名称</label>
		    <div class="col-sm-10">
		      <input type="text" name="categoryName" class="form-control"  placeholder="类别名称">
		      <span class="help-block"></span>
		    </div>
		   
		  
		   <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="submit" class="btn btn-primary" id="add_btn">添加</button>
	      </div>
		</form>
      </div>
     
    </div>
  </div>
</div>
<!-- 添加的模态框1 -->
<div class="modal fade" id="emp_add_modal_btn" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
       <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">类别添加</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" action="${APP_PATH }/user/category/add_category" method="post">
		 
		 	<!--  <label class="col-sm-2 control-label" type="hidden">类别id</label> -->
		 
		 	 	<input name="parentId" type="hidden" class="form-control" value="${categoryId }" placeholder="类别id">
		 	
		   
		    <label class="col-sm-2 control-label">类别名称</label>
		    <div class="col-sm-10">
		      <input type="text" name="categoryName" class="form-control"  placeholder="类别名称">
		      <span class="help-block"></span>
		    </div>
		   
		  
		   <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="submit" class="btn btn-primary" id="add_btn">添加</button>
	      </div>
		</form>
      </div>
     
    </div>
  </div>
</div>


<!-- 编辑的模态框2 -->
<div class="modal fade" id="edit_modal_btn2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
       <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">类别编辑</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" action="${APP_PATH }/user/category/set_category_name" method="post">
		 
		 	<!--  <label class="col-sm-2 control-label" type="hidden">类别id</label> -->
	
		 	 	<input name="categoryId" id="Id2" type="hidden" class="form-control" value="Id2"   placeholder="类别id">
		 	
		   
		    <label class="col-sm-2 control-label">类别名称</label>
		    <div class="col-sm-10">
		      <input type="text" name="categoryName" class="form-control"  placeholder="类别名称">
		      <span class="help-block"></span>
		    </div>
		   
		  
		   <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="submit" class="btn btn-primary" id="edit_btn">编辑</button>
	      </div>
		</form>
      </div>
     
    </div>
  </div>
</div>


<!-- 编辑的模态框1 -->
<div class="modal fade" id="edit_modal_btn" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
       <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">类别编辑</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" action="${APP_PATH }/user/category/set_category_name" method="post">
		 
		 	<input name="categoryId" id="Id" type="hidden" class="form-control" value="Id" placeholder="类别id">
		 	
		    <label class="col-sm-2 control-label">类别名称</label>
		    <div class="col-sm-10">
		      <input type="text" name="categoryName" class="form-control"  placeholder="类别名称">
		      <span class="help-block"></span>
		    </div>
		   
		   <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="submit" class="btn btn-primary" id="edit_btn">编辑</button>
	      </div>
		</form>
      </div>
      
    </div>
  </div>
</div>


<!-- new添加下级分类的模态框1 -->
<div class="modal fade" id="edit_modal_btn" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
       <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">类别编辑</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" action="${APP_PATH }/user/category/set_category_name" method="post">
		 
		 	<input name="categoryId" id="Id" type="hidden" class="form-control" value="Id" placeholder="类别id">
		 	
		    <label class="col-sm-2 control-label">类别名称</label>
		    <div class="col-sm-10">
		      <input type="text" name="categoryName" class="form-control"  placeholder="类别名称">
		      <span class="help-block"></span>
		    </div>
		   
		   <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="submit" class="btn btn-primary" id="edit_btn">编辑</button>
	      </div>
		</form>
      </div>
      
    </div>
  </div>
</div>




<div class="container">
		<!-- 标题 -->
		<div class="row" id="aaa">
			<div class="col-md-12" id="bbb">
				<h3>商品类别</h3>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
		
		
		
			<div class="col-md-2 col-md-offset-10" id="addCategory">
				<a class="btn btn-primary btn-sm"  role="button" data-toggle="modal" data-target="#emp_add_modal_btn">
					<span class="glyphicon glyphicon-plus" aria-hidden="true" >添加类别</span>
					
				</a>
				
			</div>
			
		</div>
		<div class="row" id="ddd">
			<div class="col-md-12">
			<h4>当前商厂已有类别</h4>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12" id="categoryMessage">
				<table class="table">
					
					<c:forEach items="${categoryMsg}" var="categoryMsg">
						<tr>
						<th>
							<%-- <a class="btn btn-default" href="get_category?categoryId=${categoryMsg.id }" role="button">${categoryMsg.name }</a> --%>
							 <a class="btn btn-default" role="button" onclick="showCategory(${categoryMsg.id },${categoryMsg.parentId},0)">${categoryMsg.name }</a>
							
							 <!-- <a class="btn btn-danger" href="#" role="button">
								<span class="glyphicon glyphicon-remove" aria-hidden="true">删除</span>
							</a> -->
							
							<!-- data-toggle="modal" data-target="#edit_modal_btn" -->
							<a class="btn btn-danger" role="button"  onclick="editCategory(${categoryMsg.id})">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true">编辑</span>
							</a>
							<a class="btn btn-danger" role="button"  onclick="addNextCategory(${categoryMsg.id})">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true">添加下级分类</span>
							</a>
							</th>
						
							</tr>
						
					</c:forEach>
					
				</table>
			</div>
		</div>
		
		<!-- <div>
						<div class="col-md-2 col-md-offset-10">
								<a class="btn btn-primary btn-sm" onclick="window.history.back()">
								<span class="glyphicon glyphicon-level-up" aria-hidden="true"></span>
							返回原页面</a>
						</div>
					</div>
 -->
	</div>

</body>
</html>