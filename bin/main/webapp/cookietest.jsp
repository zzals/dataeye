<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	Cookie cookie = new Cookie("cookieNamessss","xxxxxx");
	
	cookie.setMaxAge(60*60); // 유효기간 설정 (초)
	
	cookie.setSecure(false); // 쿠키를 SSL 이용할때만 전송하도록
	
	cookie.setHttpOnly(false); // https 일때(보안)  
	cookie.setPath("/"); 
	cookie.setDomain("www.daum.net");
	
	response.addCookie(cookie);
%>