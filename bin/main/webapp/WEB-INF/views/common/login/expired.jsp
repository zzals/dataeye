<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>DataEye | Log in</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/icon/favicon_32_deye.ico"/>
  
  <link type="text/css" rel="stylesheet" href="<spring:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>">
  <link rel="stylesheet" href="<spring:url value="/assets/stylesheets/error.css"/>">
  <script src="<spring:url value="/webjars/jquery/3.1.1/jquery.min.js"/>"></script>
  <script src="<spring:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>
  <script src="<spring:url value="/webjars/bootbox/4.4.0/bootbox.js"/>"></script>
  
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
<spring:eval expression="@environment.getProperty('application.security.login-page')" var="loginUrl"></spring:eval>
<div class="error_wrap">
    <h1 class="logo"><a href="#"><img src="<spring:url value="/assets/images/error/logo.gif"/>"></a></h1>
	<div class="error_cont"> <img src="<spring:url value="/assets/images/error/error.gif"/>">
        <div class="error_code">이용에 불편을 드려 죄송합니다.</div>     
        <div class="error_txt"><spring:message code="http.status.message.expired" text="Not Found"></spring:message></div>  
    </div>
    <div class="error_button_Area">
        <a id="goLogin" href="#"><span class="error_btn">로그인 페이지로</span></a>
    </div> 
</div>

<script type="text/javascript">
$(document).ready(function () {
	$("#goLogin").on("click", function(e){
		var target = window.top;
		while(target.opener) {
			target = target.opener;
		}
		
		if (target.de_main_check) {
			target.location.href = "<spring:url value="${loginUrl}"/>";
			self.close();
		} else {
			if (target.name === "") {
				target.location.href = "<spring:url value="${loginUrl}"/>";
			} else {
				bootbox.alert("대상 페이지(Main)가 존재하지 않습니다.<br />현재 창을 닫습니다.", function(e){
					self.close();
				});
			}
		}	
	});
});
</script>
</body>
</html>