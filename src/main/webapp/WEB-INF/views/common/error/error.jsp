<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
</head>
<body style="padding: 10px;">
    <div style="font-size: 20px; margin-bottom: 20px;">Big Data Platform</div>
	<br>
	<b>요청 처리 과정에서 에러가 발생하였습니다.</b>
	<br> 
	에러 메세지 : <%= exception.getMessage() %> 
</body>
</html>