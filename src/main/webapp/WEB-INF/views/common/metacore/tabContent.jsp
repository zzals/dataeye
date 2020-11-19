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
      href="<spring:url value="/assets/javascripts-lib/datetimepicker/bootstrap-datetimepicker.min.css"/>">
<link type="text/css" rel="stylesheet"
      href="<spring:url value="/assets/javascripts-lib/selectpicker/bootstrap-select.min.css"/>">
<link type="text/css" rel="stylesheet"
      href="<spring:url value="/assets/javascripts-lib/fileUpload/jquery.fileupload.css"/>">
<link type="text/css" rel="stylesheet"
         href="${pageContext.request.contextPath}/<spring:theme code="theme.base.dir"/>/stylesheets/dataeye-objectinfo.css"/>
      
<script src="<spring:url value="/assets/javascripts/dataeye.metaui.js"/>"></script>
<script src="<spring:url value="/assets/javascripts/dataeye.validator.js"/>"></script>
<script src="<spring:url value="/assets/javascripts-lib/datetimepicker/moment.min.js"/>"></script>
<script src="<spring:url value="/assets/javascripts-lib/datetimepicker/bootstrap-datetimepicker.min.js"/>"></script>
<script src="<spring:url value="/assets/javascripts-lib/datetimepicker/ko.js"/>"></script>
<script src="<spring:url value="/assets/javascripts-lib/selectpicker/bootstrap-select.min.js"/>"></script>

<script src="<spring:url value="/assets/javascripts-lib/fileUpload/jquery.fileupload.js"/>"></script>
<style type="text/css">
.content > #tabContentBody {
	height: calc(100vh - 20px);
    overflow: auto;
}
.content > .navbar + #tabContentBody {
	height: calc(100vh - 60px);
    overflow: auto;
}
</style>
</head>
<body style="overflow: auto;">
<!-- UI Object -->
<div id="container">
    <!-- HEADER -->
    <div id="header"></div>
    <!-- //HEADER -->
    <section class="content">
        <div id="tabContentBody"></div>
    </section>
</div>
<input type="hidden" id="objInfoReqParam">	
<script type="text/javascript">
$("input#objInfoReqParam").data("data", JSON.parse('${data}'));
$("input#objInfoReqParam").data("authInfo", JSON.parse('${AUTH_JSON}'));
</script>
<script src="<spring:url value="/app/common/metacore/tabContent.js"/>"></script>
</body>
</html>