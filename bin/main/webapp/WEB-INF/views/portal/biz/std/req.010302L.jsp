<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="page-id" content="req.010302L">
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<title><spring:message code="app.title" text="DateEye" /></title>
<c:import url="/deresources/commonlib/popup_css" />
<c:import url="/deresources/commonlib/js" />
<script src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
</head>
<body class="de-popup">
	<section id="header">
        <div class="popup_Title_Area">
            <div class="popup_title">표준용어 결재</div>
	        <div class="box-tools pull-right">
	        	<button type="button" class="close" onclick="window.close()">x</button>
	        </div>
        </div>
    </section>
    <!-- Maincontent -->
	<section class="content">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="collapsed navbar-toggle" data-toggle="collapse" aria-expanded="false">
						<span class="sr-only">Toggle navigation</span> 
						<span class="icon-bar"></span> 
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a href="#" class="navbar-brand"></a>
				</div>
				<div class="collapse navbar-collapse">
					<div class="navbar-form navbar-right">
						<button type="button" id="btnAddTerm" class="btn btn-default" style="display: none;">용어추가</a>
						<button type="button" id="btnTempSave" class="btn btn-default" style="display: none;">임시저장</a>
						<button type="button" id="btnAprvReq" class="btn btn-default" style="display: none;">결재요청</a>
						<button type="button" id="btnAprvDo" class="btn btn-default" style="display: none;">승인처리</a>
					</div>
				</div>
			</div>
		</nav>	
		<div class="col-md-12 content-body">
			<table id="jqGrid"></table>
			<div id="jqGridPagedr"></div>
		</div>
	</section>

	<div class="modal fade" id="aprvReqPopModal" tabindex="-1" role="dialog" aria-labelledby="aprvReqModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false"></div>
	<div class="modal fade" id="aprvDoPopModal" tabindex="-1" role="dialog" aria-labelledby="aprvReqModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false"></div>
		
<input type="hidden" id="APRV_ID">
<input type="hidden" id="APRV_DETL_ID">
<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>    	
<script src="<spring:url value="/app/portal/pages/biz/std/req.010302L.js"/>"></script>
</body>
</html>