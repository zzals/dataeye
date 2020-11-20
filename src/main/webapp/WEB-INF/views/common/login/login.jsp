<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>eBay | Log in</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/icon/favicon_32_deye.ico"/>
  
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="<spring:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<spring:url value="/assets/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
  <!-- Theme style -->
  <link rel="stylesheet" href="<spring:url value="/assets/stylesheets/dataeye-main.css"/>">
  <!-- iCheck -->
  <link rel="stylesheet" href="<spring:url value="/assets/javascripts-lib/iCheck/square/blue.css"/>">
  
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page" style="background:#fff;">
<div class="login-box">
  <div class="login-logo">
    <!-- <a href="login?evt=login"><img src="assets/images/login_logo.png" style="margin-right:10px;">login</a> -->
    <a href="login?evt=login"><img src="assets/images/dataeye_logo.png">login</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body" style="border:1px solid #ccc;">
    <p class="login-box-msg">Sign in to start your session</p>

    <form id="potalLogin" action="login/auth" method="post" onsubmit="return loginDo();">
      <div class="form-group has-feedback">
        <input type="text" name="de_username" id="de_username" class="form-control" value="" placeholder="User Id">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="de_pwd" id="de_pwd" class="form-control" value="" placeholder="Password">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input type="checkbox"> Remember Me
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button id="loginButton" type="button" class="btn btn-primary btn-block btn-flat">Sign In</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
    <form id="mstrLogin" action="http://192.168.100.73:8080/MicroStrategy/_custom/jsp/sso.jsp" method="post">
    <input type="hidden" name="uid" id="uid" value="admin" />
    <input type="hidden" name="returnUrl" id="returnUrl" value="http://192.168.100.73:8080/MicroStrategy/servlet/mstrWeb?evt=3186&src=mstrWeb.3186&subscriptionID=7D955EB04774CEE23C7D1796900219F8&Server=WIN-4FNG27AEFGQ&Project=ebay&Port=0&share=1" />
    <input type="hidden" name="promptsAnswerXML" id="promptsAnswerXML" value="%3Crsl%3E%3Cpa%20pt%3D%27%27%20pin%3D%270%27%20did%3D%2758167E1F4095014D4C48A2AE9EC8FAE9%27%20tp%3D%2710%27%3E201401%3C%2Fpa%3E%3Cpa%20pt%3D%27%27%20pin%3D%270%27%20did%3D%273EFE762D4760A232336F59837DCD1313%27%20tp%3D%2710%27%3E%3Cmi%3E%3Ces%3E%3Cat%20did%3D%276FF6148E455DE5AB9CEAFC83B9BC2A1F%27%20tp%3D%2712%27%2F%3E%3Ce%20emt%3D%271%27%20ei%3D%276FF6148E455DE5AB9CEAFC83B9BC2A1F%3A1%27%20art%3D%271%27%20disp_n%3D%27%EB%B6%81%EB%8F%99%EB%B6%80%27%2F%3E%3Ce%20emt%3D%271%27%20ei%3D%276FF6148E455DE5AB9CEAFC83B9BC2A1F%3A2%27%20art%3D%271%27%20disp_n%3D%27%EB%8F%99%EB%B6%80%20%EB%8C%80%EC%84%9C%EC%96%91%20%EC%97%B0%EC%95%88%27%2F%3E%3C%2Fes%3E%3C%2Fmi%3E%3C%2Fpa%3E%3C%2Frsl%3E" />
    </form>
    <!-- <form id="mstrLogin" action="http://192.168.100.73:8080/MicroStrategyLibrary/api/auth/login" method="post">
    <input type="hidden" name="username" id="uid" />
    <input type="hidden" name="password" id="pwd" />
    <input type="hidden" name="loginMode" id="loginMode" value="16"/>
    </form> -->
    <!-- <a href="#">I forgot my password</a><br>
    <a href="register.html" class="text-center">Register a new membership</a> -->

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery -->
<script src="<spring:url value="/webjars/jquery/3.1.1/jquery.min.js"/>"></script>
<!-- Bootstrap -->
<script src="<spring:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>
<!-- iCheck -->
<script src="<spring:url value="/assets/javascripts-lib/iCheck/icheck.min.js"/>"></script>
<script type="text/javascript">
	$(function () {
		$('input').iCheck({
			checkboxClass: 'icheckbox_square-blue',
			radioClass: 'iradio_square-blue',
			increaseArea: '20%' // optional
		});

		$('#loginButton').click(function(){
			loginDo();
		});

		$('#de_username, #de_pwd').keydown(function(event) { 
			if (event.keyCode == '13') { 
				loginDo();
			}
	    });
	});
	
 	function loginDo(){
 		if($('#de_username').val() == '' || $('#de_pwd').val() == ''){
			alert('아이디와 비번을 입력하십시오.');
			return;
 	 	}

  		$('#uid').val($('#de_username').val());
  		$('#pwd').val($('#de_pwd').val());

  		if($('#uid').val() != '' && $('#pwd').val() != ''){
	  		$('#mstrLogin').attr('target','ifrm').submit();
		}

  		setTimeout(function() {
	  		$('#potalLogin').submit();
		}, 2000);

 	}
</script>
<iframe name="ifrm" width="0" height="0" frameborder="0"></iframe>
</body>
</html>
