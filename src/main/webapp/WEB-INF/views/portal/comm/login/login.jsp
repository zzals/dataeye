<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>::: DataEye :::</title>
</head>
<body>
<div class="container">
	Portal Login Page
    <form method="post" action="./login/auth">
        <input type="text" name="username">
        <input type="password" name="password">
        <input type="submit" value="login">
    </form>
</div>
</body>
</html>