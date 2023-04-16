<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LoginPages</title>
<script type="text/javascript" src="/static/js/jquery-1.12.4.min.js">
</script>
</head>
<body>

<form action="user/login.action" method="post">
    UserName:<input type="text" name="username"/><br>
    PassWord:<input type="password" name="password"/><br>
    <input type="submit" value="submit">
    <input type="reset" value="reset">
    <!-- <div class="username">
        账号：<input type="text" autocomplete=off name="username"/>
          <div id="name_prompt"></div>
    </div>
    <div class="password">
        密码：<input type="password" name="password"/>
        <div id="pwrd_prompt"></div>
    </div>
    <button class="btn" type="button">登陆</button> -->
   <!-- <script>
   $(document).ready(function(){
	   
	 //name验证方法
       function NameValidation(){
           // alert("name");
           var name = ($('.username>input')[0]).value;
           console.log(name);
           if(name==null || name==""){
               $('#name_prompt').html("用户名不能为空");
           }
          /*  }else if(!f_EmailCheck(name) && !f_MobilCheck(name)){
               $('#name_prompt').html("请输入正确的邮箱或手机格式");
           }else if(f_EmailCheck(name)){
               $('#name_prompt').html("");
               FunAjax(name);
           }else if(f_MobilCheck(name)){
               //设置验证码为电话类。。。。。。
               $('#name_prompt').html("");
               FunAjax(name);
           } */
       }

	   
	   });

   </script> -->
</form>

</body>
</html>