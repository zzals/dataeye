<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="content" content="">
		<meta name="author" content="">
		<meta name="pageid" content="html_template">
		<title><tiles:insertAttribute name="title"/></title>
		<link rel="icon" href="${pageContext.request.contextPath}<spring:theme code="theme.base.dir"/>/images/favicon.ico">
		
		<tilesx:useAttribute id="styles" name="styles" classname="java.util.List" ignore="true" />
		<c:forEach var="cssName" items="${styles}">
			<link type="text/css" href="<c:url value="${cssName}"/>" rel="stylesheet" media="screen" />
		</c:forEach>
		<link href="${pageContext.request.contextPath}/<spring:theme code="theme.base.dir"/>/fonts/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/<spring:theme code="theme.base.dir"/>/css/style.css" rel="stylesheet" type="text/css">  
		<link href="${pageContext.request.contextPath}/<spring:theme code="theme.base.dir"/>/css/style-responsive.css" rel="stylesheet" type="text/css">
		
		<tilesx:useAttribute id="javascript" name="javascript" classname="java.util.List" ignore="true" />
		<c:forEach var="jsName" items="${javascript}">
			<script type="text/javascript" src="<c:url value="${jsName}"/>"></script>
		</c:forEach>
		
		
		
	</head>
	<body>
		<section id="container" >
		    <tiles:insertAttribute name="header"/>
		    
			<tiles:insertAttribute name="left"/>
		    <!-- **********************************************************************************************************************************************************
		    	MAIN CONTENT
		    *********************************************************************************************************************************************************** -->
			<!--main content start-->
		    <section id="main-content">
		    	<section class="wrapper">
		        	<tiles:insertAttribute name="center"/>
				</section><!--/wrapper -->
			</section><!-- /MAIN CONTENT -->
		    <tiles:insertAttribute name="footer"/>
		</section>
		
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/assets/theme/default/js/common-scripts.js"></script>
		<script type="text/javascript">
		    $(document).ready(function () {
		    	DataEye.i18n.loadBundles();
		    });
		</script>
	</body>
</html>