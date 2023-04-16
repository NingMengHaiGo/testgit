<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
	<meta charset="utf-8">
	<link href="common/manage/css/salad.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
  	<div class="modal fade" id="upload_modal">
  		<div class="modal-dialog" style="width:70%">
  			<div class="panel panel-primary">
	  			<div class="panel-heading">上传闲置</div>
	  			<div class="panel-body"><p></p></div>
	  			<form class="form-horizontal" method="post" action="user/save.action">
	  					<div class="form-group">
						   <label class="control-label col-xs-2">宝贝名称:</label>
						   <div class="col-xs-3">
						   		<input name="name" type="text" class="form-control">
						   </div>
						    <label class="control-label col-xs-2">宝贝价格:</label>
						   <div class="col-xs-3">
						   		<input name="price" type="number" class="form-control" min="1.00" max="100000.00" step="0.01">
						   </div>
						 </div>
						 <div class="form-group">
						   <label class="control-label col-xs-2">宝贝数量:</label>
						   <div class="col-xs-3">
						   		<input name="stock" type="number" class="form-control" min="1" max="100">
						   </div>
						   <label class="control-label col-xs-2">宝贝类别:</label>
						   <div class="col-xs-3">
						   		<select class="input-sm form-control" name="categoryId1">
						   			<option value="">---------请选择---------</option>
						   			<option value="">服装</option>
						   			<option value="">家居</option>
						   			<option value="">电子产品</option>
						   			<option value="">其它</option>
						   		</select>
						   </div>
						 </div>
						 <div class="form-group">
						    <label class="control-label col-xs-2">宝贝描述:</label>
						    <div class="col-xs-8">
						    	<textarea class="form-control" rows="2" name="subtitle"></textarea>
						    </div>
						 </div>
						 <div class="form-group">
				  			<label class="control-label col-xs-2">宝贝图片:</label>
	        				
	        				<div>
						        <div class="am-g">
						        	<div class="warper col-sm-2">
								        <div class="content">
											<img src="imgs/addpic.jpg" class="img_replace0"/>
								            <input type="file" name="file" value="" title="上传图片" onchange="getPhoto(this,'img_replace0')" required class="upload_box_select"/>	        	
								        </div>
								    </div> 
						        </div>
						        
						        <div class="am-g" >
						        	<div class="warper col-sm-2">
								        <div class="content">
								        	<img src="imgs/addpic.jpg" class="img_replace1"></img>
								            <input type="file" name="file" value="" title="上传图片" onchange="getPhoto(this,'img_replace1')" required class="upload_box_select"/>
								        </div>
								    </div> 
						        </div>
						        
						        <div class="am-g">
						        	<div class="warper col-sm-2">
								        <div class="content">
								        	<img src="imgs/addpic.jpg" class="img_replace2"></img>
								            <input  type="file" name="file" value="" title="上传图片" onchange="getPhoto(this,'img_replace2')" required class="upload_box_select"/>
								            <input type="hidden" name="filepath" id="filepath" value="" />
								        </div>
								    </div> 
						        </div>
						      </div>
        				</div>
						 <div class="form-group">
						 	<div class="col-xs-12 text-right">
						 		<button type="submit" class="btn btn-default btn-sm">应用</button>
						 		<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
						 	</div>
						 </div>
			  		</form>
	  				
	  				
	  		</div>
  		</div>
  	</div>
  </body>
</html>