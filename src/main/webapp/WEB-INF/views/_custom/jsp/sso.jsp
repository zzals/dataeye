<%@ page contentType="text/html; charset=utf-8" %>
<%
	// 웹세션에 사용자 식별자를 설정 후, ESM에서 세션 셍성 시 이용
	request.getSession().setAttribute("_uid", request.getParameter("uid"));
	response.sendRedirect(request.getContextPath() + "/servlet/mstrWeb");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>SSO Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
</head>
<body>
SSO 링크 호출 처리 중 입니다...
</body>
</html>