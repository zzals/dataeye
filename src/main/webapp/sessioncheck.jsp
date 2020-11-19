<%@page import="kr.co.penta.dataeye.spring.web.session.SessionInfoSupport"%>
<%@page import="kr.co.penta.dataeye.spring.web.session.SessionInfo"%>
<%@ page import="java.util.*"%>

<%
	SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
	out.println("========= sessionInfo ==================");
	out.println("user id ="+sessionInfo.getUserId());
	out.println("user id ="+sessionInfo.getUserName());
	out.println("========= sessionInfo ==================");
	
    Enumeration keys = session.getAttributeNames();
    while (keys.hasMoreElements()) {

        String key = (String) keys.nextElement();
        out.println(key + ": " + session.getValue(key) + "<br>");
    }
%>
