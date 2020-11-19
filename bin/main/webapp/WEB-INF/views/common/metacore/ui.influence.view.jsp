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
<script src="<spring:url value="/assets/javascripts-lib/gojs/go.js"/>"></script>
<script src="<spring:url value="/assets/javascripts-lib/gojs/extensions/DragZoomingTool.js"/>"></script>
<style type="text/css">
/* CSS for the traditional context menu */
#influenceViewContextMenu {
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

#influenceViewContextMenu ul {
	list-style: none;
	top: 0;
	left: 0;
	margin: 0;
	padding: 0;
}

#influenceViewContextMenu li a {
	position: relative;
	min-width: 60px;
	color: #444;
	display: inline-block;
	padding: 6px;
	text-decoration: none;
	cursor: pointer;
}

#influenceViewContextMenu li:hover {
	background: #CEDFF2;
	color: #EEE;
}

#influenceViewContextMenu li ul li {
	display: none;
}

#influenceViewContextMenu li ul li a {
	position: relative;
	min-width: 60px;
	padding: 6px;
	text-decoration: none;
	cursor: pointer;
}

#influenceViewContextMenu li:hover ul li {
	display: block;
	margin-left: 0px;
	margin-top: 0px;
}
</style>
</head>
<body class="de-popup">
	<section class="content">
		<div class="container-fluid">
			<div class="col-md-12">
				<div class="box">
		            <div class="box-header">
		              	<div class="box-tools">
							<button type="button" id="btnNewWindow" class="btn btn-default"><i class="fa fa-window-maximize" aria-hidden="true"></i> 새창</button>
							<button type="button" id="btnZoomOut" class="btn btn-default"><i class="fa fa-search-minus" aria-hidden="true"></i> 축소</button>
							<button type="button" id="btnZoomIn" class="btn btn-default"><i class="fa fa-search-plus" aria-hidden="true"></i> 확대</button>
						</div>
		            </div>				
		            <div class="box-body">
		                <div id="datasetDiagram" style="width: 100%; height: 745px"></div>
		                <div id="influenceViewContextMenu">
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

<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>
<script src="<spring:url value="/app/common/metacore/ui.view.main.js"/>"></script>
</body>
</html>