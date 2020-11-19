<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<title><spring:message code="app.title" text="DateEye" /></title>
<c:import url="/deresources/commonlib/popup_css" />
<c:import url="/deresources/commonlib/js" />
<script src="<spring:url value="/assets/javascripts-lib/gojs/go.js"/>"></script>
	<script src="<spring:url value="/assets/javascripts-lib/gojs/extensions/DragZoomingTool.js"/>"></script>
<style type="text/css">
/* CSS for the traditional context menu */
#influenceContextMenu {
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

#influenceContextMenu ul {
	list-style: none;
	top: 0;
	left: 0;
	margin: 0;
	padding: 0;
}

#influenceContextMenu li a {
	position: relative;
	min-width: 60px;
	color: #444;
	display: inline-block;
	padding: 6px;
	text-decoration: none;
	cursor: pointer;
}

#influenceContextMenu li:hover {
	background: #CEDFF2;
	color: #EEE;
}

#influenceContextMenu li ul li {
	display: none;
}

#influenceContextMenu li ul li a {
	position: relative;
	min-width: 60px;
	padding: 6px;
	text-decoration: none;
	cursor: pointer;
}

#influenceContextMenu li:hover ul li {
	display: block;
	margin-left: 0px;
	margin-top: 0px;
}
canvas {
	outline: none;
}
.box-body {
	margin: 0 0 0 0 !important;
}
</style>
</head>
<body class="de-popup">
    <!-- HEADER -->
    <section id="header" style="display: none;">
        <div class="popup_Title_Area">
            <div class="popup_title"></div>
	        <div class="box-tools pull-right">
	        	<button type="button" class="close" onclick="window.close()">x</button>
	        </div>
        </div>
    </section>
    <!-- //HEADER -->
	<section class="content">
		<div class="container-fluid">
			<div class="box">
	            <div class="box-body">
	                <div id="influenceDiagramDiv" style="width: 100%; height: 700px; margin: 5px;"></div>
	                <div id="influenceContextMenu">
					    <ul>
					      <li id="viewDetail"><a href="#" target="_self">상세보기</a></li>
					      <li id="viewLineage"><a href="#" target="_self">◀ Lineage</a></li>
					      <li id="viewImpact"><a href="#" target="_self">▶ Impact</a></li>
					    </ul>
					</div>
			    </div>
	            <!-- /.box-body -->
	        </div>
	        <!-- /.box -->				
			</div>
	</section>

<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>
<script src="<spring:url value="/app/common/metacore/influenceView.js"/>"></script>
</body>
</html>