<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <spring:eval expression="@environment.getProperty('application.security.login-page')" var="loginUrl"></spring:eval> --%>
<a href='<c:url value="${loginUrl}"></c:url>'>login</a>