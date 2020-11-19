<%@ page language="java" contentType="application/json" %><%@ page import="java.util.*" %><%
List<Map<String,Object>> sampleList = (List<Map<String,Object>>)request.getAttribute("sampleList");
%>{"root":"rows","page":1,"total":1,"records":<%=sampleList.size() %>,"rows":[<%
	boolean lCheck = true;
    boolean mCheck = true;
	for(Map<String,Object> cont:sampleList) {
		
		if(lCheck) {
			out.print("{");
			lCheck = false;
		} else {
			out.print(",{");
		}
		mCheck = true;
		for( String key : cont.keySet() ){

			if(mCheck) {
				%>"<%=key %>":"<%=cont.get(key) %>"<%	
				mCheck = false;
			} else {
				%>,"<%=key %>":"<%=cont.get(key) %>"<%	
			}
			
		}
		out.print("}");					
		
	}

%>]}