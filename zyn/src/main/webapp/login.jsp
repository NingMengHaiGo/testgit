<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<meta name="keywords" content="">
	<meta name="description" content="">
    <title>LoginPage</title>
	
    <link rel="stylesheet" href="common/layui/css/layui.css">
    <link rel="stylesheet" href="common/css/sccl.css">
   
	<script type="text/javascript"
		src="static/js/jquery-1.12.4.min.js"></script>
	
		<link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<script
		src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
  </head>
  
  <body class="login-bg">
  
  
    <div class="login-box">
        <header>
            <h1>系统框架管理后台</h1>
        </header>
        <br>
        <div class="login-main">
			<form action="user/login.action" class="layui-form" method="post">
				<input name="__RequestVerificationToken" type="hidden" value="">                
				<div class="layui-form-item">
					<label class="login-icon">
						<i class="layui-icon">口</i>
					</label>
					<input type="text" name="username" lay-verify="userName" autocomplete="off" placeholder="请输入用户名" class="layui-input">
				</div>
				<div class="layui-form-item">
					<label class="login-icon">
						<i class="layui-icon">口</i>
					</label>
					<input type="password" name="password" lay-verify="password" autocomplete="off" placeholder="请输入密码 " class="layui-input">
				</div>
				
			
			 <div class="layui-form-item">
					
					<div class="pull-left ">
						<!-- <label id="show_own_message"><a href="lostPasswordReset1.jsp"><font color="white">忘记密码？</font></a></label> -->
						<label id="show_own_message"><a href="lostPasswordReset1.jsp"><font color="white">忘记密码？</font></a></label>
					</div>
					
					<div class="pull-right">
					
<!-- 					    <label id="show_own_message"><a href="register.jsp"><font color="white">尚未注册？</font></a></label>
 -->				   
 							<label id="show_own_message"><a href="register.jsp"><font color="white">尚未注册？</font></a></label>
 					 </div>
					
					<div class="clear"></div>
				</div> 
				
				 <div class="layui-form-item">
					
					<div class="pull-right">
						<!-- <button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="login">
							<i class="layui-icon"></i>登陆
						
						</button> -->
						<button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="login">
								<i class="layui-icon"></i>登陆
						</button>
					</div>
					
					
					<div class="clear"></div>
				</div> 
			
				
				
				
				
			</form>        
		</div>
        <!-- <footer>
            <p>xuan Â© www.mycodes.net</p>
        </footer> -->
    </div>
    <script type="text/html" id="code-temp">
        <div class="login-code-box">
            <input type="text" class="layui-input" id="code" />
            <img id="valiCode" src="/manage/validatecode?v=636150612041789540" alt="éªè¯ç " />
        </div>
    </script>
   <!--  <script src="../common/layui/layui.js"></script> -->
   <script type="text/javascript" src="common/layui/layui.js"></script>
    <script>
        layui.use(['layer', 'form'], function () {
            var layer = layui.layer,
				$ = layui.jquery,
				form = layui.form();

            form.verify({
                userName: function (value) {
                    if (value === '')
                        return '用户名为空';
                },
                password: function (value) {
                    if (value === '')
                        return '密码为空';
                }
            });

            var errorCount = 0;

            form.on('submit(login)', function (data) {
				window.location.href = "..common/page/index.html";
                /*if (errorCount > 5) {
                    layer.open({
                        title: '<img src="' + location.origin + '/Plugins/layui/images/face/7.gif" alt="[å®³ç¾]">è¾å¥éªè¯ç ',
                        type: 1,
                        content: document.getElementById('code-temp').innerHTML,
                        btn: ['ç¡®å®'],
                        yes: function (index, layero) {
                            var $code = $('#code');
                            if ($code.val() === '') {
                                layer.msg('è¾å¥éªè¯ç å¦ï¼è®©æç¥éä½ æ¯äººç±»ã');
                                isCheck = false;
                            } else {
                                $('input[name=verifyCode]').val();
                                var params = data.field;
                                params.verifyCode = $code.val();
                                submit($,params);
                                layer.close(index);
                            }
                        },
                        area: ['250px', '150px']
                    });
                    $('#valiCode').off('click').on('click', function () {
                        this.src = '/manage/validatecode?v=' + new Date().getTime();
                    });
                }else{
                    submit($,data.field);
                }

                return false;*/
            });

        });
		
        /*function submit($,params){
            $.post('/manage/login',params , function (res) {
                if (!res.success) {
                    if (res.data !== undefined)
                        errorCount = res.data.errorCount
                    layer.msg(res.message,{icon:2});
                }else
                {
                    layer.msg(res.message,{icon:1},function(index){
                        layer.close(index);
                        location.href='/manage';
                    });
                }
            }, 'json');
        }*/
    </script>
  </body>
</html>
