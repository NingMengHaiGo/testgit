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
	<meta name="keywords" content="sccluiæ¡æ¶">
	<meta name="description" content="sccluiä¸ºè½»éçº§çç½ç«åå°ç®¡çç³»ç»æ¨¡çã">
    <title>LoginPage</title>
	<%
		pageContext.setAttribute("APP_PATH", request.getContextPath());
	%>
    <link rel="stylesheet" href="${APP_PATH }/common/css/sccl.css">
	<link href="${APP_PATH }/common/layui/css/layui.css" rel="stylesheet" type="text/css"/>
   
	<script type="text/javascript"
		src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
	
		<link href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<script
		src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
  </head>
  
  <body class="login-bg">
  
  
  
    <div class="login-box">
        <header>
            <h1>系统框架管理后台</h1>
        </header>
        <br>
        <div class="login-main">
			<form action="${APP_PATH }/user/login.action" class="layui-form" method="post">
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
						<a href="${APP_PATH }/lostPasswordReset1.jsp"><font color="white">忘记密码</font></a>
					</div>
					
					<div class="pull-right">
					    <a href="${APP_PATH }/register.jsp"><font color="white">尚未注册</font></a>
				    </div>
					
					<div class="clear"></div>
				</div> 
				
				 <div class="layui-form-item">
					
					<div class="pull-right">
						<button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="login">
							<i class="layui-icon"></i>登陆
						</button>
					</div>
					
					
					<div class="clear"></div>
				</div> 
			
				
				<!-- <div class="layui-form-item">
					<div class="pull-left login-remember">
						<label>è®°ä½å¸å·ï¼</label>

						<input type="checkbox" name="rememberMe" value="true" lay-skin="switch" title="è®°ä½å¸å·"><div class="layui-unselect layui-form-switch"><i></i></div>
					</div>
					
					<div class="pull-left login-remember">
						<label id="show_own_message"  data-toggle="modal" data-target="#showModal">å¿è®°å¯ç ï¼</label>
						<label id="show_own_message"><a href="lostPasswordReset1.jsp">å¿è®°å¯ç ï¼</a></label>

						<input type="checkbox" name="rememberMe" value="true" lay-skin="switch" title="è®°ä½å¸å·"><div class="layui-unselect layui-form-switch"><i></i></div>
					</div>
					
					<div class="pull-right">
						<button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="login">
							<i class="layui-icon">î</i> ç»å½
						</button>
					</div>
					<div class="clear"></div>
				</div> -->
				
				
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
                        return 'è¯·è¾å¥ç¨æ·å';
                },
                password: function (value) {
                    if (value === '')
                        return 'è¯·è¾å¥å¯ç ';
                }
            });

            var errorCount = 0;

            form.on('submit(login)', function (data) {
				window.location.href = "..common/page/index.html";   //跳到指定页面
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
