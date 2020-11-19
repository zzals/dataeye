<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><spring:message code="app.title" text="DateEye" /></title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<c:import url="/deresources/commonlib/popup_css" />
	<c:import url="/deresources/commonlib/js" />
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/<spring:theme code="theme.base.dir"/>/stylesheets/dataeye-objectinfo.css"/>
	<script src="<spring:url value="/assets/javascripts-lib/gojs/go.js"/>"></script>
<style type="text/css">
/* CSS for the traditional context menu */
#datasetContextMenu {
	z-index: 300000;
	position: absolute;
	left: 5px;
	border: 1px solid #444;
	background-color: #F5F5F5;
	display: none;
	box-shadow: 0 0 10px rgba(0, 0, 0, .4);
	font-size: 12px;
	font-family: sans-serif;
	font-weight: bold;
}

#datasetContextMenu ul {
	list-style: none;
	top: 0;
	left: 0;
	margin: 0;
	padding: 0;
}

#datasetContextMenu li a {
	position: relative;
	min-width: 60px;
	color: #444;
	display: inline-block;
	padding: 6px;
	text-decoration: none;
	cursor: pointer;
}

#datasetContextMenu li:hover {
	background: #CEDFF2;
	color: #EEE;
}

#datasetContextMenu li ul li {
	display: none;
}

#datasetContextMenu li ul li a {
	position: relative;
	min-width: 60px;
	padding: 6px;
	text-decoration: none;
	cursor: pointer;
}

#datasetContextMenu li:hover ul li {
	display: block;
	margin-left: 0px;
	margin-top: 0px;
}
</style>
</head>
<body>
<!-- UI Object -->
<div id="container">
    <!-- HEADER -->
    <div id="header" style="display: none;">
        <div class="popup_Title_Area">
            <div class="popup_title"></div>
        </div>
    </div>
    <!-- //HEADER -->
	<section class="content">
		<div class="row">
			<div class="col-xs-12">
				<div class="box">
		            <div class="box-body">
		                <div id="datasetDiagram" style="width: 100%; height: 745px"></div>
		                <div id="datasetContextMenu">
						    <ul>
						      <li id="viewDetail"><a href="#" target="_self">상세보기</a></li>
						    </ul>
						</div>
				    </div>
		            <!-- /.box-body -->
		          </div>
		          <!-- /.box -->				
			</div>
		</div>
	</section>
</div>
<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>
<script src="<spring:url value="/app/common/metacore/datasetView4.js"/>"></script>
</body>
</html>