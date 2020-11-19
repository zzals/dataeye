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
    <link type="text/css" rel="stylesheet" href="<spring:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>">
    <script src="<spring:url value="/webjars/jquery/3.1.1/jquery.min.js"/>"></script>
    <script src="<spring:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>
    <script src="<spring:url value="/webjars/bootbox/4.4.0/bootbox.js"/>"></script>
    <script src="<spring:url value="/assets/javascripts-lib/postMessage/postmessage.min.js"/>"></script>
</head>
<body>
<script type="text/javascript">
    $(document).ready(function () {
        bootbox.alert('세션이 유효하지 않습니다.', function () {
            if (window.opener != null) {
                $.postMessage({action: 'sessionExpired', mode: 'opener'}, '*', window.opener);
                self.close();
            } if (window.parent != null) {
                $.postMessage({action: 'sessionExpired', mode: 'parent'}, '*', parent);
            }
        });
    });
</script>
</body>
</html>