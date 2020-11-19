<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/icon/favicon_32_deye.ico"/>

<!-- Bootstrap 3.3.7 -->
<link type="text/css" rel="stylesheet" href="<spring:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>">

<!-- Font Awesome -->
<link type="text/css" rel="stylesheet" href="<spring:url value="/assets/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">

<!-- jquery-ui 1.10.3 -->
<link type="text/css" rel="stylesheet" href="<spring:url value="/assets/stylesheets/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.css"/>">

<!-- jquery layout -->
<link type="text/css" rel="stylesheet" href="<spring:url value="/assets/javascripts-lib/jquery.layout/layout-custom.css"/>">

<!-- jqgrid 5.2.0(bootstrap) -->
<link type="text/css" rel="stylesheet" href="<spring:url value="/assets/javascripts-lib/Guriddo_jqGrid_JS_5.2.0/css/ui.jqgrid-bootstrap.css"/>">

<link type="text/css" rel="stylesheet" href="<spring:url value="/assets/stylesheets/dataeye-main.css"/>">

<!-- Theme style -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/<spring:theme code="theme.base.dir"/>/stylesheets/dataeye-main.css">

<link type="text/css" rel="stylesheet" href="<spring:url value="/assets/javascripts-lib/iCheck/all.css"/>">

<link type="text/css" rel="stylesheet" href="<spring:url value="/assets/javascripts-lib/select2/select2.css"/>">

<!-- DataEye main Skins. Choose a skin from the css/skins
 	folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="<spring:url value="/assets/stylesheets/skins/_all-skins.min.css"/>">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
<script>
document.cookie="theme=<spring:theme code="theme.base.dir"/>";
</script>