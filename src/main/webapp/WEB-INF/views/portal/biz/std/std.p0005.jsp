<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="page-id" content="std.p0005">
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<title><spring:message code="app.title" text="DateEye" /></title>
<c:import url="/deresources/commonlib/popup_css" />
<c:import url="/deresources/commonlib/js" />
<script src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.handsontable/jquery.handsontable.css">
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.handsontable/jquery.handsontable.full.js"></script>
<style type="text/css">
	.htCheckboxRendererInput {
		vertical-align: middle;
		margin-left: 14px;
	}
	.content-body {
		height : calc(100Vh - 120px);
	}
</style>    
</head>
<body class="de-popup">
	<section id="header">
        <div class="popup_Title_Area">
            <div class="popup_title">도메인 일괄등록</div>
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
					<div class="navbar-form navbar-right de-obj-btn ">
						<c:if test="${AUTH.getRAuth() == 'Y'}">
							<a id="dmnUploadTplDown" class="btn btn-default">템플릿 다운로드</a>
							<a id="dmnUploadTplUpload" class="btn btn-default">업로드</a>
							<a id="dmnVerifyChkAction" class="btn btn-default">도메인검증</a>
						</c:if>
						<c:if test="${AUTH.getCAuth() == 'Y'}">
							<a id="batchSaveAction" class="btn btn-default">일괄등록</a>
						</c:if>
					</div>
				</div>
			</div>
		</nav>	
		<div class="col-md-12 content-body">
			<div id="auto-dmn-list" role="grid-area" style="height: 500px; overflow: scroll;"></div>
		</div>
	</section>
	
<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>    	
<script src="<spring:url value="/app/portal/pages/biz/std/std.p0005.js"/>"></script>
</body>
</html>