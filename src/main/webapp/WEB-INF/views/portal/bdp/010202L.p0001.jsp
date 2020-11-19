<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<title><spring:message code="app.title" text="DateEye" /></title>
<c:import url="/deresources/commonlib/popup_css"/>
<c:import url="/deresources/commonlib/js"/>
<link rel="stylesheet" type="text/css" href="<spring:url value="/assets/javascripts-lib/chosen_v1.5.1/chosen.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<spring:url value="/assets/javascripts-lib/jquery.handsontable/0.29.0/handsontable.full.css"/>"/>
<script type="text/javascript" src="<spring:url value="/assets/javascripts-lib/jquery.handsontable/0.29.0/handsontable.full.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/assets/javascripts-lib/chosen_v1.5.1/chosen.jquery.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/assets/javascripts-lib/jquery.handsontable/0.29.0/handsontable-chosen-editor.js"/>"></script>
<style type="text/css">
td.htHidden {
	overflow: hidden !important;
	white-space: nowrap !important;
}
.content .box-body {
	height: -moz-calc(100vh - 120px);
    height: -webkit-calc(100vh - 120px);
    height: calc(100vh - 120px);
}
</style>
</head>
<body class="de-popup">
	<section id="header">
        <div class="popup_Title_Area">
            <div class="popup_title">L0 데이터셋 속성 </div>
	        <div class="box-tools pull-right">
	        	<button type="button" class="close" onclick="window.close()">x</button>
	        </div>
        </div>
    </section>
    <!-- Maincontent -->
	<section class="content">
		<div class="container-fluid">
           	<div class="col-md-12">  
				<div class="form-horizontal">
					<div class="box bdt">
						<div class="box-header">
			              	<h3 class="box-title"><i class="fa fa-chevron-right" aria-hidden="true"></i></h3>
			              	<div class="box-tools">
				                <button id="bntBatchSaveAction" type="button" class="btn btn-default">일괄등록</button>
				        	</div>
			            </div>
						<div class="box-body" style="overflow: auto;">
							<div id="handsonTable" class="hot"></div>
			            </div>
			        </div>	
		        </div>				
			</div>
		</div>
	</section>

	<!-- //Maincontent -->

<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>	
<script src="<spring:url value="/app/portal/pages/bdp/010202L.p0001.js"/>"></script>
</body>
</html>