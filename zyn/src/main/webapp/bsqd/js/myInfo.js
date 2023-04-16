$(document).ready(function(){
	checkUserLogin();
})
function checkUserLogin(){
	$.ajax({
		url:"user/checkUserLogin.action",
		type:"post",
		async:true,
		data:{'type':'1'},
		success:function(data){
			if(data.status == 1){
				window.location.href = "index.html";
			}else{
				getMyInfo();
			}
		
		}
	});
}
//我的资料
function getMyInfo(){
	$("#passwordReset").addClass("hidden");
	$("#myInfo").removeClass("hidden");
//	$("#myBasicInfo").load("bsqd/myAllInfo/myBasicInfo.html",function(){
//		$("#myBasicInfoModal").modal({'backdrop':'static'});
//		$("#myBasicInfoModal").one("shown.bs.modal",function(e){
//			if(isLogin == 0){
			 $.ajax({
			    	url:"user/checkUserLogin.action",
			   		type:"post",
			   		async:true,
			   		success:function(data){
			   			$("#myBasicInfoForm").find("[name='username']").val(data.data.username);
			   			$("#myBasicInfoForm").find("[name='email']").val(data.data.email);
			   			$("#myBasicInfoForm").find("[name='phone']").val(data.data.phone);
			   		}
			       });
			
//			}
//		});
//	});
}
function modifyMyInfo(){
	var email =  $("#myBasicInfoForm").find("[name='email']").val();
	var phone =  $("#myBasicInfoForm").find("[name='phone']").val();
	 $.ajax({
	    	url:"user/update_information.action",
	   		type:"post",
	   		async:true,
	   		data:{"email":email,"phone":phone,"question":"","answer":""},
	   		success:function(data){

	   		}
	       });
	
}
//密码修改页面显示
function passwordReset(){
	$("#myInfo").addClass("hidden");
	$("#passwordReset").removeClass("hidden");
	$.ajax({
    	url:"user/checkUserLogin.action",
   		type:"post",
   		async:true,
   		success:function(data){
   			$("#myPasswordResetForm").find("[name='username']").val(data.data.username);
   		}
       });

}
//密码修改提交
function passwordReset2(){
	var passwordOld =  $("#myPasswordResetForm").find("[name='passwordOld']").val();
	var passwordNew =  $("#myPasswordResetForm").find("[name='passwordNew']").val();
//	debugger
	$.ajax({
    	url:"user/reset_password.action",
   		type:"post",
   		async:true,
   		data:{"passwordOld":passwordOld,"passwordNew":passwordNew},
   		success:function(data){
//   			alert(1)
//   			alert(data.msg);
   		}
       });

}
function checkRepwd(){
	var pwd1 = document.getElementById("psd1").value;
	var pwd2 = document.getElementById("psd2").value;
	if(pwd1 != pwd2  && pwd2!='') {
	document.getElementById("tishix").innerHTML="<font color='red'>两次密码不相同</font>";
		return false;
	}
}
function myCart(){
	window.location.href = "cart.html";
}