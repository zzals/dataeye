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
<link type="text/css" rel="stylesheet" href="<spring:url value="/assets/javascripts-lib/fileUpload/jquery.fileupload.css"/>">
<script src="<spring:url value="/assets/javascripts-lib/fileUpload/jquery.fileupload.js"/>"></script>
<script src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
<style type="text/css">
.content-body {
	height : calc(100Vh - 220px);
}
</style>  
</head>
<body class="de-popup">
	<section id="header">
        <div class="popup_Title_Area">
            <div class="popup_title"></div>
	        <div class="box-tools pull-right">
	        	<button type="button" class="close" onclick="window.close()">x</button>
	        </div>
        </div>
    </section>
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
					<div class="navbar-form navbar-left">
						<label class="control-label" for="inputSuccess">
							<i class="glyphicon glyphicon-ok"></i>APP 시스템
						</label>
						<div class="form-group">
							<select id="selAppSys" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;"></select>
						</div>
					</div>
					<div class="navbar-form navbar-left">
						<label class="control-label" for="inputSuccess">
							<i class="glyphicon glyphicon-ok"></i>DB 인스턴스
						</label>
						<div class="form-group">
							<select id="selDbInst" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;"></select>
						</div>
					</div>
					<div class="navbar-form navbar-left">
						<label class="control-label" for="inputSuccess">
							<i class="glyphicon glyphicon-ok"></i>데이터베이스
						</label>
						<div class="form-group">
							<select id="selDatabase" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;"></select>
						</div>
					</div>
					
					<div class="navbar-form navbar-right de-obj-btn ">
						<button id="btnSave" class="btn btn-default btn-sm"  role="button"><i class="fa fa-floppy-o"></i> 저장</button>
					</div>
					<div class="navbar-form navbar-right de-obj-btn ">
						<span class="btn btn-success fileinput-button">
					        <i class="glyphicon glyphicon-plus"></i>
					        <span>업로드</span>
					        <input id="btnUpload" type="file" name="files[]" multiple="">
					    </span>
					</div>
				</div>
			</div>
		</nav>	
		<div class="content-body">
			<div class="row">
				<div class="col-xs-12">
					<div class="box" style="padding-top: 10px;">
						<div class="box-header">
							<h3 class="box-title" style="color: red;"><i class="fa fa-chevron-right" aria-hidden="true"></i>업로드 결과 : <span id="uploadMessage">N/A</span></h3>
						</div>
			            <div class="box-body">
							<table id="jqGrid1"></table>
							<div id="jqGridPager1"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script> 	
<script src="<spring:url value="/app/portal/pages/dsgn/db/040102L.p02.js"/>"></script>
</body>
</html>